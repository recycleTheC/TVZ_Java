package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

/**
 * Klasa Ispit saržava sve osnovne informacije o ispitima koji se polažu u obrazovnim ustanovama
 */
public final class Ispit implements Online {
    private Predmet predmet;
    private Student student;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;
    private Dvorana dvorana;
    private String software;

    public Ispit(Predmet predmet, Student student, Integer ocjena, LocalDateTime datumIVrijeme) {
        this.predmet = predmet;
        this.student = student;
        this.datumIVrijeme = datumIVrijeme;
        this.setOcjena(ocjena);
    }

    public Ispit(Predmet predmet, Student student, Ocjena ocjena, LocalDateTime datumIVrijeme) {
        this.predmet = predmet;
        this.student = student;
        this.datumIVrijeme = datumIVrijeme;
        this.ocjena = ocjena;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = switch(ocjena){
            case 1: yield Ocjena.NEDOVOLJAN;
            case 2: yield Ocjena.DOVOLJAN;
            case 3: yield Ocjena.DOBAR;
            case 4: yield Ocjena.VRLO_DOBAR;
            case 5: yield Ocjena.IZVRSTAN;
            default: yield Ocjena.NIJE_UNESENA;
        };
    }

    public void setOcjena(Ocjena ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    public Dvorana getDvorana() {
        return dvorana;
    }

    public void setDvorana(Dvorana dvorana) {
        this.dvorana = dvorana;
    }

    @Override
    public void setSoftwareZaIspit(String software) {
        this.software = software;
    }

    @Override
    public String getSoftwareZaIspit() {
        return this.software;
    }
}