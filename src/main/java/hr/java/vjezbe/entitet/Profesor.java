package hr.java.vjezbe.entitet;

/**
 * Klasa Profesor sadrži osnovne informacije o osobi koja predaje u obrazovnoj ustanovi
 */
public class Profesor extends Osoba {
    private String sifra, titula;

    /**
     * Graditelj klase Profesor
     */
    public static class Builder{
        String ime, prezime, sifra, titula;

        /**
         * Postavljanje šifre profesora
         * @param sifra jedinstvena šifra profesora
         */
        public Builder(String sifra){
            this.sifra = sifra;
        }

        /**
         * Postavljanje imena i prezimena profesora
         * @param ime ime
         * @param prezime prezime
         * @return referenca na graditelja objekta
         */
        public Builder osoba(String ime, String prezime){
            this.ime = ime;
            this.prezime = prezime;

            return this;
        }

        /**
         * Postavljanje akademske titule
         * @param titula titula
         * @return referenca na graditelja objekta
         */
        public Builder saTitulom(String titula){
            this.titula = titula;

            return this;
        }

        /**
         * Vraća konstruirani objekt Profesor
         * @return profesor
         */
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
