package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_PREDMETA = 2;

    private static final int BROJ_ISPITNIH_ROKOVA = 2;

    /**
     * Učitavanje podataka o profesorima
     * @param ulaz skener za konzolu
     * @param profesori polje profesora
     */
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

    /**
     * Učitavanje podataka o predmetima
     * @param ulaz skener za konzolu
     * @param predmeti polje predmeta
     * @param profesori polje profesora
     */
    private static void ucitajPredmete(Scanner ulaz, Predmet[] predmeti, Profesor[] profesori){
        for (int i = 0; i < BROJ_PREDMETA; i++){
            System.out.printf("Unesite %d. predmet:\n", i+1);

            System.out.print("Unesite šifru predmeta: ");
            String sifra = ulaz.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String naziv = ulaz.nextLine();

            //System.out.printf("Unesite broj ECTS bodova za predmet '%s': ", naziv);
            int ectsBodovi = ucitajBroj(ulaz, "Unesite broj ECTS bodova za predmet '" + naziv + "': ");
            //ulaz.nextLine();

            System.out.println("Odaberite profesora: ");
            for (int j = 0; j < BROJ_PROFESORA; j++){
                System.out.printf("%d. %s %s\n", j+1, profesori[j].getIme(), profesori[j].getPrezime());
            }
            //System.out.print("Odabir >> ");
            int brojNositelja = ucitajBroj(ulaz, "Odabir >> ");
            //ulaz.nextLine();

            //System.out.printf("Unesite broj studenata za predmet '%s': ", naziv);
            int brojStudenata = ucitajBroj(ulaz, "Unesite broj studenata za predmet '" + naziv +"': ");
            //ulaz.nextLine();

            predmeti[i] = new Predmet(sifra, naziv, ectsBodovi, profesori[brojNositelja-1]);
            predmeti[i].setStudenti(new Student[brojStudenata]);
        }
    }

    /**
     * Učitavanje podataka o studentima
     * @param ulaz skener za konzolu
     * @param studenti polje studenata
     */
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

    /**
     * Učitavanje podataka o ispitnim rokovima
     * @param ulaz skener za konzolu
     * @param ispiti dostupni ispiti
     * @param predmeti dostupni predmeti
     * @param studenti dostupni studenti
     */
    private static void ucitajIspitneRokove(Scanner ulaz, Ispit[] ispiti, Predmet[] predmeti, Student[] studenti){
        for (int i = 0; i < BROJ_ISPITNIH_ROKOVA; i++){
            System.out.printf("Unesite %d. ispitni rok:\n", i+1);

            System.out.println("Odaberite predmet: ");
            for(int j = 0; j < BROJ_PREDMETA; j++){
                System.out.printf("%d. %s\n", j+1, predmeti[j].getNaziv());
            }
            //System.out.print("Odabir >> ");
            int brojPredmeta = ucitajBroj(ulaz, "Odabir >> ");
            //ulaz.nextLine();

            Predmet odabraniPredmet = predmeti[brojPredmeta-1];

            /*System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = ulaz.nextLine();
            System.out.print("Unesite zgradu dvorane: ");
            String zgradaDvorane = ulaz.nextLine();*/

            Dvorana dvorana = new Dvorana("dvorana", "zgrada"); // hardkodirano jer je izbaceno iz teksta zadatka

            System.out.println("Odaberite studenta: ");
            for(int k = 0; k < BROJ_STUDENATA; k++){
                System.out.printf("%d. %s %s\n", k+1, studenti[k].getIme(),studenti[k].getPrezime());
            }
            //System.out.print("Odabir >> ");
            int brojStudenta = ucitajBroj(ulaz, "Odabir >> ");
            //ulaz.nextLine();
            Student odabraniStudent = studenti[brojStudenta-1];

            //System.out.print("Odaberite ocjenu na ispitu (1-5): ");
            int ocjena = ucitajBroj(ulaz, "Odaberite ocjenu na ispitu (1-5): ");
            //ulaz.nextLine();

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

    /**
     * Ispisuje studente koji su na ispitima dobili ocjene izvrstan (5)
     * @param ispiti polje ocjenjenih ispita
     */
    public static void ispisStudenataSIzvrsnim(Ispit[] ispiti){
        for(int i = 0; i < BROJ_ISPITNIH_ROKOVA; i++){
            if(ispiti[i].getOcjena().equals(5)){
                System.out.println("Student " + ispiti[i].getStudent().getIme() + " " +
                        ispiti[i].getStudent().getPrezime() +  " je ostvario ocjenu '" + "izvrstan" +
                        "' na predmetu '" + ispiti[i].getPredmet().getNaziv() + "'");
            }
        }
    }

    /**
     * Vraća učitani broj i provjerava unos
     * @param unos skener za konzolu
     * @param poruka poruka koja će biti ispisana prije unosa (upute za korisnika)
     * @return ispravno učitani broj
     */
    private static Integer ucitajBroj(Scanner unos, String poruka) {
        System.out.print(poruka);
        Integer broj = 0;
        boolean neispravno;

        do{
            neispravno = false;
            try{
                broj = unos.nextInt();
            }
            catch(InputMismatchException ex){
                System.out.println("Neispravan podatak! Ponovite unos broja.");
                System.out.print(poruka);
                logger.error("Unesen neispravan broj!", ex);
                neispravno = true;
            }
            unos.nextLine();
        }
        while(neispravno);

        return broj;
    }

    /**
     * Provjerava ima li student ispit s negativnom ocjenom
     * @param ispiti ispiti studenta za kojeg se provjera status
     * @return ima li student negativnu ocjenu
     */
    private static boolean studentSNegativnomOcjenom(Ispit[] ispiti){
        for(Ispit ispit : ispiti){
            if(ispit.getOcjena() == 1) return true;
        }
        return  false;
    }

    public static void main(String[] args) {
        Scanner ulaz = new Scanner(System.in);

        //System.out.print("Unesite broj obrazovnih ustanova: ");
        Integer brojUstanova = ucitajBroj(ulaz, "Unesite broj obrazovnih ustanova: ");

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

            //System.out.print("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
            Integer tipUstanove = ucitajBroj(ulaz, "Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");
            //ulaz.nextLine();

            System.out.print("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = ulaz.nextLine();

            if(tipUstanove == 1) ustanove[i] = new VeleucilisteJave(nazivUstanove, predmeti, studenti, profesori, ispiti);
            else if(tipUstanove == 2) ustanove[i] = new FakultetRacunarstva(nazivUstanove, predmeti, studenti, profesori, ispiti);

            for(int j = 0; j < ustanove[i].getStudenti().length; j++){

                Ispit[] ispitiStudenta = new Ispit[0];

                if(ustanove[i] instanceof VeleucilisteJave veleuciliste){
                    ispitiStudenta = veleuciliste.filtrirajIspitePoStudentu(veleuciliste.getIspiti(), veleuciliste.getStudenti()[j]);
                }
                else if(ustanove[i] instanceof FakultetRacunarstva fakultet){
                    ispitiStudenta = fakultet.filtrirajIspitePoStudentu(fakultet.getIspiti(), fakultet.getStudenti()[j]);
                }

                if(studentSNegativnomOcjenom(ispitiStudenta)){
                    continue;
                }

                Integer ocjenaZavrsni = ucitajBroj(ulaz, "Unesite ocjenu završnog rada za studenta: " + ustanove[i].getStudenti()[j].getImeIPrezime() + ": ");
                Integer ocjenaObrana = ucitajBroj(ulaz, "Unesite ocjenu obrane završnog rada za studenta: " + ustanove[i].getStudenti()[j].getImeIPrezime() + ": ");

                if(ustanove[i] instanceof VeleucilisteJave veleuciliste){
                    try {
                        System.out.println("Konačna ocjena studija studenta " + veleuciliste.getStudenti()[j].getImeIPrezime()+" je " + veleuciliste.izracunajKonacnuOcjenuStudijaZaStudenta(veleuciliste.filtrirajIspitePoStudentu(veleuciliste.getIspiti(), veleuciliste.getStudenti()[j]), ocjenaZavrsni, ocjenaObrana));
                    }
                    catch (NemoguceOdreditiProsjekStudentaException ex) {
                        logger.error("Greska prilikom odredivanja ocjene studenta", ex);
                    }
                }
                else if(ustanove[i] instanceof FakultetRacunarstva fakultet){
                    try{
                        System.out.println("Konačna ocjena studija studenta " + fakultet.getStudenti()[j].getImeIPrezime()+" je " + fakultet.izracunajKonacnuOcjenuStudijaZaStudenta(fakultet.filtrirajIspitePoStudentu(fakultet.getIspiti(), fakultet.getStudenti()[j]), ocjenaZavrsni, ocjenaObrana));
                    }
                    catch (NemoguceOdreditiProsjekStudentaException ex){
                        logger.error("Greska prilikom odredivanja ocjene studenta", ex);
                    }
                }
            }

            Student najbolji = ustanove[i].odrediNajuspjesnijegStudentaNaGodini(2022); //namjerno hardkodirano!!!
            System.out.println("Najbolji student 2022. godine je " + najbolji.getImeIPrezime() + " JMBAG: " + najbolji.getJmbag());

            if(ustanove[i] instanceof FakultetRacunarstva fakultet){
                try{
                    Student nagradjen = fakultet.odrediStudentaZaRektorovuNagradu();
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + najbolji.getImeIPrezime() + " JMBAG: " + najbolji.getJmbag());
                }
                catch (PostojiViseNajmladihStudenataException ex){
                    logger.error("Postoji vise najmladjih studenata", ex);
                    System.out.println("Program završava s izvođenjem.");
                    System.out.print("Pronađeno je više najmlađih studenata s istim datumom rođenja, a to su: ");

                    for (int k = 0; k < ex.studenti.length; k++) {
                        if (k > 0) {
                            System.out.print(", ");
                        }
                        System.out.print(ex.studenti[k].getImeIPrezime());
                    }

                    System.out.println(".");
                    System.exit(-1);
                }

            }
        }
    }
}
