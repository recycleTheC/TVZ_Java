package hr.java.vjezbe.database;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfesorRepository {
    public static List<Profesor> dohvatiProfesore() throws BazaPodatakaException {
        List<Profesor> profesori = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery("SELECT * FROM PROFESOR");

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                String ime = rezultat.getString("IME");
                String prezime = rezultat.getString("PREZIME");
                String sifra = rezultat.getString("SIFRA");
                String titula = rezultat.getString("TITULA");

                profesori.add(new Profesor(id, sifra, ime, prezime, titula));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return profesori;
    }

    public static Optional<Profesor> dohvatiProfesora(Long id) throws BazaPodatakaException {
        Optional<Profesor> profesor = Optional.empty();

        try (Connection db = Database.connectToDatabase()){
            PreparedStatement upit = db.prepareStatement("SELECT * FROM PROFESOR WHERE ID = ? LIMIT 1");
            upit.setLong(1, id);

            ResultSet rezultat = upit.executeQuery();

            while (rezultat.next()) {
                String ime = rezultat.getString("IME");
                String prezime = rezultat.getString("PREZIME");
                String sifra = rezultat.getString("SIFRA");
                String titula = rezultat.getString("TITULA");

                profesor = Optional.of(new Profesor(id, sifra, ime, prezime, titula));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return profesor;
    }

    public static List<Profesor> dohvatiProfesore(Profesor profesorFilter) throws BazaPodatakaException {
        List<Profesor> profesori = new ArrayList<>();

        try (Connection db = Database.connectToDatabase()){
            StringBuilder sqlUpit = new StringBuilder("SELECT * FROM PROFESOR WHERE 1 = 1");

            if(Optional.ofNullable(profesorFilter).isPresent()){
                if (!profesorFilter.getIme().isBlank()) {
                    sqlUpit.append(" AND lower(IME) like '%").append(profesorFilter.getIme().toLowerCase()).append("%'");
                }
                if (!profesorFilter.getPrezime().isBlank()) {
                    sqlUpit.append(" AND lower(PREZIME) like '%").append(profesorFilter.getPrezime().toLowerCase()).append("%'");
                }
                if (!profesorFilter.getSifra().isBlank()) {
                    sqlUpit.append(" AND lower(SIFRA) like '").append(profesorFilter.getSifra().trim().toLowerCase()).append("%'");
                }
                if (!profesorFilter.getTitula().isBlank()) {
                    sqlUpit.append(" AND lower(TITULA) like '").append(profesorFilter.getTitula().toLowerCase()).append("%'");
                }
            }

            Statement upit = db.createStatement();
            ResultSet rezultat = upit.executeQuery(sqlUpit.toString());

            while (rezultat.next()) {
                Long id = rezultat.getLong("ID");
                String ime = rezultat.getString("IME");
                String prezime = rezultat.getString("PREZIME");
                String sifra = rezultat.getString("SIFRA");
                String titula = rezultat.getString("TITULA");

                profesori.add(new Profesor(id, sifra, ime, prezime, titula));
            }
        } catch (SQLException | IOException e) {
            throw new BazaPodatakaException("Greška pri radu s bazom podataka!", e);
        }

        return profesori;
    }

    public static void spremi(Profesor profesor) throws BazaPodatakaException {
        try (Connection db = Database.connectToDatabase()) {
            PreparedStatement insertUpit = db.prepareStatement("INSERT INTO PROFESOR(IME, PREZIME, SIFRA, TITULA) VALUES (?, ?, ?, ?)");
            insertUpit.setString(1, profesor.getIme());
            insertUpit.setString(2, profesor.getPrezime());
            insertUpit.setString(3, profesor.getSifra());
            insertUpit.setString(4, profesor.getTitula());
            insertUpit.executeUpdate();
        } catch (SQLException | IOException ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            //logger.error(poruka, ex);
            throw new BazaPodatakaException(poruka, ex);
        }

    }
}
