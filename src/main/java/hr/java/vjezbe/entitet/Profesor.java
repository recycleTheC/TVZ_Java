package hr.java.vjezbe.entitet;

import java.io.Serializable;

/**
 * Klasa Profesor sadrži osnovne informacije o osobi koja predaje u obrazovnoj ustanovi
 */
public class Profesor extends Osoba implements Serializable {
    private String sifra, titula;
    public static final String NAZIV_DATOTEKE = "dat/profesori.txt";
    public static final int BROJ_ZAPISA_U_DATOTEKAMA = 5;

    /**
     * Graditelj klase Profesor
     */
    public static class Builder {
        String ime, prezime, sifra, titula;
        Long id;

        /**
         * Postavljanje šifre profesora
         * @param sifra jedinstvena šifra profesora
         */
        public Builder(Long id, String sifra){
            this.sifra = sifra;
            this.id = id;
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
            return new Profesor(this.id, this.sifra, this.ime, this.prezime, this.titula);
        }
    }
    private Profesor(Long id, String sifra, String ime, String prezime, String titula) {
        super(id, ime, prezime);
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
