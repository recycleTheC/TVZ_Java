package hr.java.vjezbe.database;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PredmetRepository {
    public static Optional<Predmet> dohvatiPredmet(Long id) throws BazaPodatakaException {
        Optional<Predmet> predmet = Optional.empty();

        try (Connection db = Database.connectToDatabase()){
            PreparedStatement upit = db.prepareStatement("SELECT * FROM PREDMET WHERE ID = ? LIMIT 1");
            upit.setLong(1, id);

            ResultSet rezultat = upit.executeQuery();

            while (rezultat.next()) {
                String naziv = rezultat.getString("NAZIV");
                String sifra = rezultat.getString("SIFRA");
                Integer ects = rezultat.getInt("BROJ_ECTS_BODOVA");
                Optional<Profesor> nositelj = ProfesorRepository.dohvatiProfesora(rezultat.getLong("PROFESOR_ID"));

                predmet = Optional.of(new Predmet(id, sifra, naziv, ects, nositelj.orElse(null)));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return predmet;
    }
    public static List<Predmet> dohvatiPredmete() throws BazaPodatakaException {
        List<Predmet> predmeti = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery("SELECT * FROM PREDMET");

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                String naziv = rezultat.getString("NAZIV");
                String sifra = rezultat.getString("SIFRA");
                Integer ects = rezultat.getInt("BROJ_ECTS_BODOVA");
                Optional<Profesor> nositelj = ProfesorRepository.dohvatiProfesora(rezultat.getLong("PROFESOR_ID"));

                predmeti.add(new Predmet(id, sifra, naziv, ects, nositelj.orElse(null)));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return predmeti;
    }

    public static List<Predmet> dohvatiPredmete(Predmet predmetFilter) throws BazaPodatakaException {
        List<Predmet> predmeti = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PREDMET WHERE 1 = 1");

            if(Optional.ofNullable(predmetFilter).isPresent()){
                if (Optional.ofNullable(predmetFilter.getNaziv()).isPresent()) {
                    if(!predmetFilter.getNaziv().isBlank()) sqlUpit.append(" AND lower(NAZIV) like '%").append(predmetFilter.getNaziv().toLowerCase()).append("%'");
                }
                if (Optional.ofNullable(predmetFilter.getSifra()).isPresent()) {
                    if(!predmetFilter.getSifra().isBlank()) sqlUpit.append(" AND lower(SIFRA) like '").append(predmetFilter.getSifra().trim().toLowerCase()).append("%'");
                }
                if (Optional.ofNullable(predmetFilter.getBrojEctsBodova()).isPresent()) {
                    if(predmetFilter.getBrojEctsBodova() > 0) sqlUpit.append(" AND BROJ_ECTS_BODOVA = ").append(predmetFilter.getBrojEctsBodova().toString());
                }
                if (Optional.ofNullable(predmetFilter.getNositelj()).isPresent()) {
                    sqlUpit.append(" AND PROFESOR_ID = ").append(predmetFilter.getNositelj().getId().toString());
                }
            }

            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery(sqlUpit.toString());

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                String naziv = rezultat.getString("NAZIV");
                String sifra = rezultat.getString("SIFRA");
                Integer ects = rezultat.getInt("BROJ_ECTS_BODOVA");
                Optional<Profesor> nositelj = ProfesorRepository.dohvatiProfesora(rezultat.getLong("PROFESOR_ID"));

                predmeti.add(new Predmet(id, sifra, naziv, ects, nositelj.orElse(null)));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return predmeti;
    }

    public static void spremi(Predmet predmet) throws BazaPodatakaException {
        try (Connection db = Database.connectToDatabase()) {
            db.setAutoCommit(false);
            PreparedStatement insertPredmet = db.prepareStatement("INSERT INTO PREDMET(SIFRA, NAZIV, BROJ_ECTS_BODOVA, PROFESOR_ID) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            insertPredmet.setString(1, predmet.getSifra());
            insertPredmet.setString(2, predmet.getNaziv());
            insertPredmet.setInt(3, predmet.getBrojEctsBodova());
            insertPredmet.setLong(4, predmet.getNositelj().getId());
            insertPredmet.executeUpdate();

            try (ResultSet generatedKeys = insertPredmet.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    predmet.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            PreparedStatement insertStudent = db.prepareStatement("INSERT INTO PREDMET_STUDENT(PREDMET_ID, STUDENT_ID) VALUES (?, ?)");

            for(Student student: predmet.getStudenti()){
                insertStudent.setLong(1, predmet.getId());
                insertStudent.setLong(2, student.getId());
                insertStudent.executeUpdate();
            }
            db.commit();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            //logger.error(poruka, ex);
            throw new BazaPodatakaException(poruka, ex);
        }
    }
}
