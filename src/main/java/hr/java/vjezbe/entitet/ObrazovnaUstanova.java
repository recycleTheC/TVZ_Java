package hr.java.vjezbe.entitet;

import java.util.Arrays;

/**
 * Apstraktna klasa Obrazovna ustanova koja sadrži osnovne informacije zajedničke svim ustanovama
 */
public abstract class ObrazovnaUstanova {
    private String naziv;
    private Predmet[] predmeti;
    private Student[] studenti;
    private Profesor[] profesori;
    private Ispit[] ispiti;

    public ObrazovnaUstanova(String naziv, Predmet[] predmeti, Student[] studenti, Profesor[] profesori, Ispit[] ispiti) {
        this.naziv = naziv;
        this.predmeti = predmeti;
        this.studenti = studenti;
        this.profesori = profesori;
        this.ispiti = ispiti;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Predmet[] getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(Predmet[] predmeti) {
        this.predmeti = predmeti;
    }

    public Student[] getStudenti() {
        return studenti;
    }

    public void setStudenti(Student[] studenti) {
        this.studenti = studenti;
    }

    public Profesor[] getProfesori() {
        return profesori;
    }

    public void setProfesori(Profesor[] profesori) {
        this.profesori = profesori;
    }

    public Ispit[] getIspiti() {
        return ispiti;
    }

    public void setIspiti(Ispit[] ispiti) {
        this.ispiti = ispiti;
    }

    /**
     * Vraća najuspješnijeg studenta na zadanoj godini studija
     * @param godina kalendarska godina
     * @return student koji zadovoljava postavljene uvjete
     */
    public abstract Student odrediNajuspjesnijegStudentaNaGodini(int godina);

    /**
     * Vraća polje ispita koji su polagani u zadanog godini
     * @param ispiti polje ispita
     * @param godina broj godine u kojoj je polagan ispit
     * @return polje ispita
     */
    public Ispit[] ispitiIzGodine(Ispit[] ispiti, int godina){
        Ispit[] izvuceni = new Ispit[0];

        for(Ispit ispit : ispiti){
            if(ispit.getDatumIVrijeme().getYear() == godina){
                izvuceni = Arrays.copyOf(izvuceni, izvuceni.length+1);
                izvuceni[izvuceni.length-1] = ispit;
            }
        }

        return izvuceni;
    }
}
