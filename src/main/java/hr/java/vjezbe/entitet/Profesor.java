package hr.java.vjezbe.entitet;

public class Profesor extends Osoba {
    private String sifra, titula;

    public static class Builder{
        String ime, prezime, sifra, titula;

        public Builder(String sifra){
            this.sifra = sifra;
        }

        public Builder osoba(String ime, String prezime){
            this.ime = ime;
            this.prezime = prezime;

            return this;
        }

        public Builder saTitulom(String titula){
            this.titula = titula;

            return this;
        }

        public Profesor build(){
            return new Profesor(this.sifra, this.ime, this.prezime, this.titula);
        }
    }
    private Profesor(String sifra, String ime, String prezime, String titula) {
        super(ime, prezime);
        this.sifra = sifra;
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }
}
