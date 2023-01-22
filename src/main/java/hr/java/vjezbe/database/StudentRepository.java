package hr.java.vjezbe.database;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository {
    public static Optional<Student> dohvatiStudent(Long id) throws BazaPodatakaException {
        Optional<Student> student = Optional.empty();

        try (Connection db = Database.connectToDatabase()){
            PreparedStatement upit = db.prepareStatement("SELECT * FROM STUDENT WHERE ID = ? LIMIT 1");
            upit.setLong(1, id);

            ResultSet rezultat = upit.executeQuery();

            while (rezultat.next()) {
                String ime = rezultat.getString("IME");
                String prezime = rezultat.getString("PREZIME");
                String jmbag = rezultat.getString("JMBAG");
                LocalDate date = rezultat.getDate("DATUM_RODJENJA").toLocalDate();

                student = Optional.of(new Student(id, ime, prezime, jmbag, date));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return student;
    }
    public static List<Student> dohvatiStudente() throws BazaPodatakaException {
        List<Student> studenti = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery("SELECT * FROM STUDENT");

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                String ime = rezultat.getString("IME");
                String prezime = rezultat.getString("PREZIME");
                String jmbag = rezultat.getString("JMBAG");
                LocalDate date = rezultat.getDate("DATUM_RODJENJA").toLocalDate();

                studenti.add(new Student(id, ime, prezime, jmbag, date));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return studenti;
    }

public static List<Student> dohvatiStudenteZaRodjendan(LocalDate datum) throws BazaPodatakaException {
    List<Student> studenti = new ArrayList<>();

    try (Connection db = Database.connectToDatabase()){
        PreparedStatement upit = db.prepareStatement("SELECT * FROM STUDENT WHERE DAY(DATUM_RODJENJA) = DAY(?) AND MONTH(DATUM_RODJENJA) = MONTH(?)");
        upit.setDate(1, Date.valueOf(datum));
        upit.setDate(2, Date.valueOf(datum));

        ResultSet rezultat = upit.executeQuery();

        while (rezultat.next()) {
            Long id = rezultat.getLong("ID");
            String ime = rezultat.getString("IME");
            String prezime = rezultat.getString("PREZIME");
            String jmbag = rezultat.getString("JMBAG");
            LocalDate date = rezultat.getDate("DATUM_RODJENJA").toLocalDate();

            studenti.add(new Student(id, ime, prezime, jmbag, date));
        }
    } catch (SQLException | IOException e) {
        throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
    }

    return studenti;
}

    public static List<Student> dohvatiStudente(Student studentFilter) throws BazaPodatakaException {
        List<Student> studenti = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM STUDENT WHERE 1 = 1");

            if(Optional.ofNullable(studentFilter).isPresent()){
                if (Optional.ofNullable(studentFilter.getIme()).isPresent()) {
                    if(!studentFilter.getIme().isBlank()) sqlUpit.append(" AND lower(IME) like '%").append(studentFilter.getIme().toLowerCase()).append("%'");
                }
                if (Optional.ofNullable(studentFilter.getPrezime()).isPresent()) {
                    if(!studentFilter.getPrezime().isBlank()) sqlUpit.append(" AND lower(PREZIME) like '%").append(studentFilter.getPrezime().toLowerCase()).append("%'");
                }
                if (Optional.ofNullable(studentFilter.getJmbag()).isPresent()) {
                    if(!studentFilter.getJmbag().isBlank()) sqlUpit.append(" AND JMBAG like '").append(studentFilter.getJmbag().trim()).append("%'");
                }
                if (Optional.ofNullable(studentFilter.getDatumRodjenja()).isPresent()) {
                    sqlUpit.append(" AND DATUM_RODJENJA = '").append(studentFilter.getDatumRodjenja().format(DateTimeFormatter.ISO_DATE)).append("'");
                }
            }

            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery(sqlUpit.toString());

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                String ime = rezultat.getString("IME");
                String prezime = rezultat.getString("PREZIME");
                String jmbag = rezultat.getString("JMBAG");
                LocalDate date = rezultat.getDate("DATUM_RODJENJA").toLocalDate();

                studenti.add(new Student(id, ime, prezime, jmbag, date));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return studenti;
    }

    public static void spremi(Student student) throws BazaPodatakaException {
        try (Connection db = Database.connectToDatabase()) {
            PreparedStatement insertUpit = db.prepareStatement("INSERT INTO STUDENT(IME, PREZIME, JMBAG, DATUM_RODJENJA) VALUES (?, ?, ?, ?)");
            insertUpit.setString(1, student.getIme());
            insertUpit.setString(2, student.getPrezime());
            insertUpit.setString(3, student.getJmbag());
            insertUpit.setDate(4, Date.valueOf(student.getDatumRodjenja()));
            insertUpit.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            //logger.error(poruka, ex);
            throw new BazaPodatakaException(poruka, ex);
        }

    }
}
