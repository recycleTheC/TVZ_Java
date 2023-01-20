package hr.java.vjezbe.database;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IspitRepository {
    public static List<Ispit> dohvatiIspite() throws BazaPodatakaException {
        List<Ispit> ispiti = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery("SELECT * FROM ISPIT");

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                Optional<Predmet> predmet = PredmetRepository.dohvatiPredmet(rezultat.getLong("PREDMET_ID"));
                Optional<Student> student = StudentRepository.dohvatiStudent(rezultat.getLong("STUDENT_ID"));
                Integer ocjena = rezultat.getInt("OCJENA");
                LocalDateTime datum = LocalDateTime.ofInstant(rezultat.getTimestamp("DATUM_I_VRIJEME").toInstant(), ZoneId.systemDefault());

                ispiti.add(new Ispit(id, predmet.orElse(null), student.orElse(null), ocjena, datum));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return ispiti;
    }

    public static List<Ispit> dohvatiIspite(Ispit ispitFilter) throws BazaPodatakaException {
        List<Ispit> ispiti = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM ISPIT WHERE 1 = 1");

            if(Optional.ofNullable(ispitFilter).isPresent()){
                if (Optional.ofNullable(ispitFilter.getPredmet()).isPresent()) {
                    List<Predmet> predmeti = PredmetRepository.dohvatiPredmete(ispitFilter.getPredmet());
                    sqlUpit.append(" AND PREDMET_ID IN (").append(predmeti.stream().map(predmet -> predmet.getId().toString()).collect(Collectors.joining(","))).append(")");
                }
                if (Optional.ofNullable(ispitFilter.getStudent()).isPresent()) {
                    List<Student> studenti = StudentRepository.dohvatiStudente(ispitFilter.getStudent());
                    sqlUpit.append(" AND STUDENT_ID IN (").append(studenti.stream().map(student -> student.getId().toString()).collect(Collectors.joining(","))).append(")");
                }
                if (Optional.ofNullable(ispitFilter.getOcjena()).isPresent()) {
                    if(ispitFilter.getOcjena().getBrojcanaOznaka() > 0) sqlUpit.append(" AND OCJENA = ").append(ispitFilter.getOcjena().getBrojcanaOznaka());
                }
                if(Optional.ofNullable(ispitFilter.getDatumIVrijeme()).isPresent()){
                    sqlUpit.append(" AND DATUM_I_VRIJEME like '%").append(ispitFilter.getDatumIVrijeme().format(DateTimeFormatter.ISO_DATE)).append("%'");
                }
            }

            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery(sqlUpit.toString());

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                Optional<Predmet> predmet = PredmetRepository.dohvatiPredmet(rezultat.getLong("PREDMET_ID"));
                Optional<Student> student = StudentRepository.dohvatiStudent(rezultat.getLong("STUDENT_ID"));
                Integer ocjena = rezultat.getInt("OCJENA");
                LocalDateTime datum = LocalDateTime.ofInstant(rezultat.getTimestamp("DATUM_I_VRIJEME").toInstant(), ZoneId.systemDefault());

                ispiti.add(new Ispit(id, predmet.orElse(null), student.orElse(null), ocjena, datum));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return ispiti;
    }

    public static void spremi(Ispit ispit) throws BazaPodatakaException {
        try (Connection db = Database.connectToDatabase()) {
            PreparedStatement insert = db.prepareStatement("INSERT INTO ISPIT(PREDMET_ID, STUDENT_ID, OCJENA, DATUM_I_VRIJEME) VALUES (?, ?, ?, ?)");

            insert.setLong(1, ispit.getPredmet().getId());
            insert.setLong(2, ispit.getStudent().getId());
            insert.setInt(3, ispit.getOcjena().getBrojcanaOznaka());
            insert.setTimestamp(4, Timestamp.valueOf(ispit.getDatumIVrijeme()));
            insert.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }
    public static void obrisi(Long id) throws BazaPodatakaException {
        try (Connection db = Database.connectToDatabase()) {
            PreparedStatement delete = db.prepareStatement("DELETE FROM ISPIT WHERE ID = ?");

            delete.setLong(1, id);
            delete.executeUpdate();
        } catch (SQLException | IOException ex) {
            throw new BazaPodatakaException("Došlo je do pogreške u radu s bazom podataka", ex);
        }
    }

    public static void izmjeni(Ispit ispit) throws BazaPodatakaException {
        try (Connection db = Database.connectToDatabase()) {
            PreparedStatement update = db.prepareStatement("UPDATE ISPIT SET PREDMET_ID = ?, STUDENT_ID = ?, OCJENA = ?, DATUM_I_VRIJEME = ? WHERE ID = ?");

            update.setLong(1, ispit.getPredmet().getId());
            update.setLong(2, ispit.getStudent().getId());
            update.setInt(3, ispit.getOcjena().getBrojcanaOznaka());
            update.setTimestamp(4, Timestamp.valueOf(ispit.getDatumIVrijeme()));
            update.setLong(5, ispit.getId());

            update.executeUpdate();
        } catch (SQLException | IOException ex) {
            throw new BazaPodatakaException("Došlo je do pogreške u radu s bazom podataka", ex);
        }
    }
}
