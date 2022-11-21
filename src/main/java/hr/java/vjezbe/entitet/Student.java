package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Klasa Student sadrži informacije o osobi koja pohađa studij na obrazovnoj ustanovi
 */
public class Student extends Osoba{
    private String jmbag;
    private LocalDate datumRodjenja;

    public Student(String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public Student(String ime, String prezime) {
        super(ime, prezime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return jmbag.equals(student.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
