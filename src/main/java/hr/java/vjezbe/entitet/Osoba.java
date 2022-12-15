package hr.java.vjezbe.entitet;

/**
 * Klasa Osoba sadrži osnovne informacije zajedničke svim osobama
 */
public abstract class Osoba extends Entitet {
    private String ime, prezime;

    public Osoba(Long id, String ime, String prezime) {
        super(id);
        this.ime = ime;
        this.prezime = prezime;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getImeIPrezime(){
        return this.ime + " " + this.prezime;
    }


}
