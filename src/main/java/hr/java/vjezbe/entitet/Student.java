package hr.java.vjezbe.entitet;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Klasa Student sadrži informacije o osobi koja pohađa studij na obrazovnoj ustanovi
 */
public class Student extends Osoba implements Serializable {
    private String jmbag;
    private LocalDate datumRodjenja;

    private int ocjenaZavrsni, ocjenaObrana;

    public static final String NAZIV_DATOTEKE = "dat/studenti_tekst.txt";
    public static final int BROJ_ZAPISA_U_DATOTEKAMA = 10;

    private String tipStudenta, prehrana, gdjeZivi;

    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja) {
        super(id, ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
    }

    public Student(Long id, String ime, String prezime, String jmbag, LocalDate datumRodjenja, int ocjenaZavrsni, int ocjenaObrana) {
        super(id, ime, prezime);
        this.jmbag = jmbag;
        this.datumRodjenja = datumRodjenja;
        this.ocjenaZavrsni = ocjenaZavrsni;
        this.ocjenaObrana = ocjenaObrana;
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

    public Student(Long id, String ime, String prezime) {
        super(id, ime, prezime);
    }

    public int getOcjenaZavrsni() {
        return ocjenaZavrsni;
    }

    public int getOcjenaObrana() {
        return ocjenaObrana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return getJmbag().equals(student.getJmbag());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getJmbag());
    }

    @Override
    public String toString() {
        return this.getImeIPrezime();
    }

    public String getTipStudenta() {
        return tipStudenta;
    }

    public void setTipStudenta(String tipStudenta) {
        this.tipStudenta = tipStudenta;
    }

    public String getGdjeZivi() {
        return gdjeZivi;
    }

    public void setGdjeZivi(String gdjeZivi) {
        this.gdjeZivi = gdjeZivi;
    }

    public String getPrehrana() {
        return prehrana;
    }

    public void setPrehrana(String prehrana) {
        this.prehrana = prehrana;
    }
}
