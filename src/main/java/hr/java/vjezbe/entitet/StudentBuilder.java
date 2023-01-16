package hr.java.vjezbe.entitet;

import java.time.LocalDate;

/**
 * Graditelj klase Student
 */
public class StudentBuilder {
    private Long id;
    private String ime, prezime, jmbag;
    private LocalDate datumRodjenja;
    private int ocjenaZavrsni, ocjenaObrana;

    public StudentBuilder(Long id) {
        this.id = id;
    }
    public StudentBuilder() {
    }
    public StudentBuilder setIme(String ime) {
        this.ime = ime;
        return this;
    }

    public StudentBuilder setPrezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public StudentBuilder setJmbag(String jmbag) {
        this.jmbag = jmbag;
        return this;
    }

    public StudentBuilder setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
        return this;
    }

    public StudentBuilder setOcjenaZavrsni(int zavrsni, int obrana) {
        this.ocjenaZavrsni = zavrsni;
        this.ocjenaObrana = obrana;
        return this;
    }

    /**
     * Vraća konstruirani objekt Student
     * @return student
     */
    public Student createStudent() {
        return new Student(id, ime, prezime, jmbag, datumRodjenja, ocjenaZavrsni, ocjenaObrana);
    }
}