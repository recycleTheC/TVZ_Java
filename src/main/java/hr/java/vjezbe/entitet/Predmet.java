package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa Predmet sadrži osnovne informacije o predmetu u obrazovnim ustanovama
 */
public class Predmet {
    private String sifra;
    private String naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Set<Student> studenti;

    private Semestar semestar;

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = new HashSet<>();
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(Integer brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public Set<Student> getStudenti() {
        return studenti;
    }

    public void setStudent(Student student) {
        this.studenti.add(student);
    }

    public Semestar getSemestar() {
        return semestar;
    }

    public void setSemestar(Semestar semestar) {
        this.semestar = semestar;
    }

    @Override
    public String toString() {
        return "Predmet: " + naziv;
    }
}
