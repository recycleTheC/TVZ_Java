package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Apstraktna klasa Obrazovna ustanova koja sadrži osnovne informacije zajedničke svim ustanovama
 */
public abstract class ObrazovnaUstanova {
    private String naziv;
    private List<Predmet> predmeti;
    private List<Student> studenti;
    private List<Profesor> profesori;
    private List<Ispit> ispiti;

    public ObrazovnaUstanova(String naziv, List<Predmet> predmeti, List<Student> studenti, List<Profesor> profesori, List<Ispit> ispiti) {
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

    public List<Predmet> getPredmeti() {
        return predmeti;
    }

    public void setPredmeti(List<Predmet> predmeti) {
        this.predmeti = predmeti;
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    public List<Profesor> getProfesori() {
        return profesori;
    }

    public void setProfesori(List<Profesor> profesori) {
        this.profesori = profesori;
    }

    public List<Ispit> getIspiti() {
        return ispiti;
    }

    public void setIspiti(List<Ispit> ispiti) {
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
    public List<Ispit> ispitiIzGodine(List<Ispit> ispiti, int godina){
        List<Ispit> izvuceni = new ArrayList<>();

        for(Ispit ispit : ispiti){
            if(ispit.getDatumIVrijeme().getYear() == godina){
                izvuceni.add(ispit);
            }
        }

        return izvuceni;
    }
}
