package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa Predmet sadrži osnovne informacije o predmetu u obrazovnim ustanovama
 */
public class Predmet extends Entitet implements Serializable {
    private String sifra, naziv;
    private Integer brojEctsBodova;
    private Profesor nositelj;
    private Set<Student> studenti;

    public static final String NAZIV_DATOTEKE = "dat/predmeti.txt";
    public static final int BROJ_ZAPISA_U_DATOTEKAMA = 6;

    public Predmet(Long id, String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = new HashSet<>();
    }

    public Predmet(String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj) {
        super();
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = new HashSet<>();
    }

    public Predmet(Long id, String sifra, String naziv, Integer brojEctsBodova, Profesor nositelj, Set<Student> studenti) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
    }

    public Predmet() {
        super();
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

    @Override
    public String toString() {
        return naziv + " (" + nositelj.getImeIPrezime() + ")";
    }
}
