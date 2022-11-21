package hr.java.vjezbe.entitet;

import java.time.LocalDate;

public class StudentBuilder {
    private String ime;
    private String prezime;
    private String jmbag;
    private LocalDate datumRodjenja;

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

    public Student createStudent() {
        return new Student(ime, prezime, jmbag, datumRodjenja);
    }
}