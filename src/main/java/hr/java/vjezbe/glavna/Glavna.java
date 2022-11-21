package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

public class Glavna {

    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_PREDMETA = 2;

    private static final int BROJ_ISPITNIH_ROKOVA = 2;

    private static void ucitajProfesore(Scanner ulaz, Profesor[] profesori){
        for(int i = 0; i < BROJ_PROFESORA; i++){
            System.out.printf("Unesite %d. profesora:\n", i+1);

            System.out.print("Unesite šifru profesora: ");
            String sifra = ulaz.nextLine();

            System.out.print("Unesite ime profesora: ");
            String ime = ulaz.nextLine();

            System.out.print("Unesite prezime profesora: ");
            String prezime = ulaz.nextLine();

            System.out.print("Unesite titulu profesora: ");
            String titula = ulaz.nextLine();

            profesori[i] = new Profesor.Builder(sifra).osoba(ime, prezime).saTitulom(titula).build();
        }
    }

    private static void ucitajPredmete(Scanner ulaz, Predmet[] predmeti, Profesor[] profesori){
        for (int i = 0; i < BROJ_PREDMETA; i++){
            System.out.printf("Unesite %d. predmet:\n", i+1);

            System.out.print("Unesite šifru predmeta: ");
            String sifra = ulaz.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String naziv = ulaz.nextLine();

            System.out.printf("Unesite broj ECTS bodova za predmet '%s': ", naziv);
            int ectsBodovi = ulaz.nextInt();
            ulaz.nextLine();

            System.out.println("Odaberite profesora: ");
            for (int j = 0; j < BROJ_PROFESORA; j++){
                System.out.printf("%d. %s %s\n", j+1, profesori[j].getIme(), profesori[j].getPrezime());
            }
            System.out.print("Odabir >> ");
            int brojNositelja = ulaz.nextInt();
            ulaz.nextLine();

            System.out.printf("Unesite broj studenata za predmet '%s': ", naziv);
            int brojStudenata = ulaz.nextInt();
            ulaz.nextLine();

            predmeti[i] = new Predmet(sifra, naziv, ectsBodovi, profesori[brojNositelja-1]);
            predmeti[i].setStudenti(new Student[brojStudenata]);
        }
    }

    private static void ucitajStudente(Scanner ulaz, Student[] studenti){
        for(int i = 0; i < BROJ_STUDENATA; i++){
            System.out.printf("Unesite %d. studenta:\n", i+1);

            System.out.print("Unesite ime studenta: ");
            String ime = ulaz.nextLine();

            System.out.print("Unesite prezime studenta: ");
            String prezime = ulaz.nextLine();

            System.out.printf("Unesite datum rođenja studenta %s %s u formatu (dd.MM.yyyy.): ", prezime, ime);
            LocalDate datumRodjenja = LocalDate.parse(ulaz.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));

            System.out.printf("Unesite JMBAG studenta %s %s: ", prezime, ime);
            String jmbag = ulaz.nextLine();

            studenti[i] = new StudentBuilder().setIme(ime).setPrezime(prezime).setJmbag(jmbag).setDatumRodjenja(datumRodjenja).createStudent();
        }
    }

    private static void ucitajIspitneRokove(Scanner ulaz, Ispit[] ispiti, Predmet[] predmeti, Student[] studenti){
        for (int i = 0; i < BROJ_ISPITNIH_ROKOVA; i++){
            System.out.printf("Unesite %d. ispitni rok:\n", i+1);

            System.out.println("Odaberite predmet: ");
            for(int j = 0; j < BROJ_PREDMETA; j++){
                System.out.printf("%d. %s\n", j+1, predmeti[j].getNaziv());
            }
            System.out.print("Odabir >> ");
            int brojPredmeta = ulaz.nextInt();
            ulaz.nextLine();

            Predmet odabraniPredmet = predmeti[brojPredmeta-1];

            System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = ulaz.nextLine();
            System.out.print("Unesite zgradu dvorane: ");
            String zgradaDvorane = ulaz.nextLine();

            Dvorana dvorana = new Dvorana(nazivDvorane, zgradaDvorane);

            System.out.println("Odaberite studenta: ");
            for(int k = 0; k < BROJ_STUDENATA; k++){
                System.out.printf("%d. %s %s\n", k+1, studenti[k].getIme(),studenti[k].getPrezime());
            }
            System.out.print("Odabir >> ");
            int brojStudenta = ulaz.nextInt();
            ulaz.nextLine();
            Student odabraniStudent = studenti[brojStudenta-1];

            System.out.print("Odaberite ocjenu na ispitu (1-5): ");
            int ocjena = ulaz.nextInt();
            ulaz.nextLine();

            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            LocalDateTime datumIVrijeme = LocalDateTime.parse(ulaz.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm"));

            Student[] noviStudenti = Arrays.copyOf(odabraniPredmet.getStudenti(),odabraniPredmet.getStudenti().length+1);

            for(int k = 0; k < noviStudenti.length; k++){
                if(noviStudenti[k] == null){
                    noviStudenti[k] = odabraniStudent;
                }
            }

            odabraniPredmet.setStudenti(noviStudenti);

            ispiti[i] = new Ispit(odabraniPredmet, odabraniStudent, ocjena, datumIVrijeme);
            ispiti[i].setDvorana(dvorana);
        }
    }

    public static void ispisStudenataSIzvrsnim(Ispit[] ispiti){
        for(int i = 0; i < BROJ_ISPITNIH_ROKOVA; i++){
            if(ispiti[i].getOcjena().equals(5)){
                System.out.println("Student " + ispiti[i].getStudent().getIme() + " " +
                        ispiti[i].getStudent().getPrezime() +  " je ostvario ocjenu '" + "izvrstan" +
                        "' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
            }
        }
    }

    public static void main(String[] args){
        Scanner ulaz = new Scanner(System.in);

        System.out.print("Unesite broj obrazovnih ustanova: ");
        Integer brojUstanova = ulaz.nextInt();
        ulaz.nextLine();

        ObrazovnaUstanova[] ustanove = new ObrazovnaUstanova[brojUstanova];

        for(int i = 0; i < brojUstanova; i++){
            System.out.println("Unesite podatke za " + (i+1) + ". obrazovnu ustanovu:");

            Profesor[] profesori = new Profesor[BROJ_PROFESORA];
            Student[] studenti = new Student[BROJ_STUDENATA];
            Predmet[] predmeti = new Predmet[BROJ_PREDMETA];
            Ispit[] ispiti = new Ispit[BROJ_ISPITNIH_ROKOVA];

            ucitajProfesore(ulaz,profesori);
            ucitajPredmete(ulaz, predmeti, profesori);
            ucitajStudente(ulaz, studenti);
            ucitajIspitneRokove(ulaz, ispiti, predmeti, studenti);
            ispisStudenataSIzvrsnim(ispiti);

            System.out.print("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
            Integer tipUstanove = ulaz.nextInt();
            ulaz.nextLine();

            System.out.print("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = ulaz.nextLine();

            if(tipUstanove == 1) ustanove[i] = new VeleucilisteJave(nazivUstanove, predmeti, studenti, profesori, ispiti);
            else if(tipUstanove == 2) ustanove[i] = new FakultetRacunarstva(nazivUstanove, predmeti, studenti, profesori, ispiti);

            for(int j = 0; j < ustanove[i].getStudenti().length; j++){
                System.out.print("Unesite ocjenu završnog rada za studenta: " + ustanove[i].getStudenti()[j].getImeIPrezime() + ": ");
                Integer ocjenaZavrsni = ulaz.nextInt();
                ulaz.nextLine();

                System.out.print("Unesite ocjenu obrane završnog rada za studenta: " + ustanove[i].getStudenti()[j].getImeIPrezime() + ": ");
                Integer ocjenaObrana = ulaz.nextInt();
                ulaz.nextLine();

                if(ustanove[i] instanceof VeleucilisteJave veleuciliste){
                    System.out.println("Konačna ocjena studija studenta " + veleuciliste.getStudenti()[j].getImeIPrezime()+" je " + veleuciliste.izracunajKonacnuOcjenuStudijaZaStudenta(veleuciliste.filtrirajIspitePoStudentu(veleuciliste.getIspiti(), veleuciliste.getStudenti()[j]), ocjenaZavrsni, ocjenaObrana));
                }
                else if(ustanove[i] instanceof FakultetRacunarstva fakultet){
                    System.out.println("Konačna ocjena studija studenta " + fakultet.getStudenti()[j].getImeIPrezime()+" je " + fakultet.izracunajKonacnuOcjenuStudijaZaStudenta(fakultet.filtrirajIspitePoStudentu(fakultet.getIspiti(), fakultet.getStudenti()[j]), ocjenaZavrsni, ocjenaObrana));
                }
            }

            Student najbolji = ustanove[i].odrediNajuspjesnijegStudentaNaGodini(2022); //namjerno hardkodirano!!!
            System.out.println("Najbolji student 2022. godine je " + najbolji.getImeIPrezime() + " JMBAG: " + najbolji.getJmbag());

            if(ustanove[i] instanceof FakultetRacunarstva fakultet){
                Student nagradjen = fakultet.odrediStudentaZaRektorovuNagradu();
                System.out.println("Student koji je osvojio rektorovu nagradu je: " + najbolji.getImeIPrezime() + " JMBAG: " + najbolji.getJmbag());
            }
        }
    }
}
