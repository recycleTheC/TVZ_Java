package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import hr.java.vjezbe.sortiranje.ObrazovnaUstanovaSorter;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_PREDMETA = 3;

    private static final int BROJ_ISPITNIH_ROKOVA = 2;

    /**
     * Učitavanje podataka o profesorima
     * @param ulaz skener za konzolu
     * @param profesori polje profesora
     */
    private static void ucitajProfesore(Scanner ulaz, List<Profesor> profesori){
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

            profesori.add(new Profesor.Builder(sifra).osoba(ime, prezime).saTitulom(titula).build());
        }
    }

    /**
     * Učitavanje podataka o predmetima
     * @param ulaz skener za konzolu
     * @param predmeti polje predmeta
     * @param profesori polje profesora
     */
    private static void ucitajPredmete(Scanner ulaz, List<Predmet> predmeti, List<Profesor> profesori, Map<Profesor, List<Predmet>> mapa){
        for (int i = 0; i < BROJ_PREDMETA; i++){
            System.out.printf("Unesite %d. predmet:\n", i+1);

            System.out.print("Unesite šifru predmeta: ");
            String sifra = ulaz.nextLine();

            System.out.print("Unesite naziv predmeta: ");
            String naziv = ulaz.nextLine();

            int ectsBodovi = ucitajBroj(ulaz, "Unesite broj ECTS bodova za predmet '" + naziv + "': ");

            System.out.println("Odaberite profesora: ");
            for (int j = 0; j < BROJ_PROFESORA; j++){
                System.out.printf("%d. %s %s\n", j+1, profesori.get(j).getIme(), profesori.get(j).getPrezime());
            }
            int brojNositelja = ucitajBroj(ulaz, "Odabir >> ");

            Profesor profesor = profesori.get(brojNositelja - 1);
            Predmet predmet = new Predmet(sifra, naziv, ectsBodovi, profesor);

            List<Predmet> postojeciPredmeti = Optional.ofNullable(mapa.get(profesor)).orElse(new ArrayList<>());
            postojeciPredmeti.add(predmet);
            mapa.put(profesor, postojeciPredmeti);

            predmeti.add(predmet);
        }
    }

    /**
     * Učitavanje podataka o studentima
     * @param ulaz skener za konzolu
     * @param studenti polje studenata
     */
    private static void ucitajStudente(Scanner ulaz, List<Student> studenti){
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

            studenti.add(new StudentBuilder().setIme(ime).setPrezime(prezime).setJmbag(jmbag).setDatumRodjenja(datumRodjenja).createStudent());
        }
    }

    /**
     * Učitavanje podataka o ispitnim rokovima
     * @param ulaz skener za konzolu
     * @param ispiti dostupni ispiti
     * @param predmeti dostupni predmeti
     * @param studenti dostupni studenti
     */
    private static void ucitajIspitneRokove(Scanner ulaz, List<Ispit> ispiti, List<Predmet> predmeti, List<Student> studenti){
        for (int i = 0; i < BROJ_ISPITNIH_ROKOVA; i++){
            System.out.printf("Unesite %d. ispitni rok:\n", i+1);

            System.out.println("Odaberite predmet: ");
            for(int j = 0; j < BROJ_PREDMETA; j++){
                System.out.printf("%d. %s\n", j+1, predmeti.get(j).getNaziv());
            }

            int brojPredmeta = ucitajBroj(ulaz, "Odabir >> ");

            Predmet odabraniPredmet = predmeti.get(brojPredmeta - 1);

            /*System.out.print("Unesite naziv dvorane: ");
            String nazivDvorane = ulaz.nextLine();
            System.out.print("Unesite zgradu dvorane: ");
            String zgradaDvorane = ulaz.nextLine();*/

            Dvorana dvorana = new Dvorana("dvorana", "zgrada"); // hardkodirano jer je izbaceno iz teksta zadatka

            System.out.println("Odaberite studenta: ");
            for(int k = 0; k < BROJ_STUDENATA; k++){
                System.out.printf("%d. %s %s\n", k+1, studenti.get(k).getIme(), studenti.get(k).getPrezime());
            }

            int brojStudenta = ucitajBroj(ulaz, "Odabir >> ");
            Student odabraniStudent = studenti.get(brojStudenta - 1);

            int ocjena = ucitajBroj(ulaz, "Odaberite ocjenu na ispitu (1-5): ");

            System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm): ");
            LocalDateTime datumIVrijeme = LocalDateTime.parse(ulaz.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm"));

            odabraniPredmet.setStudent(odabraniStudent);

            ispiti.add(new Ispit(odabraniPredmet, odabraniStudent, ocjena, datumIVrijeme));
            //ispiti[i].setDvorana(dvorana);
        }
    }

    /**
     * Ispisuje studente koji su na ispitima dobili ocjene izvrstan (5)
     * @param ispiti polje ocjenjenih ispita
     */
    public static void ispisStudenataSIzvrsnim(List<Ispit> ispiti){
        ispiti.stream().filter(ispit -> ispit.getOcjena().equals(Ocjena.IZVRSTAN)).forEach(ispit -> {
            System.out.println("Student " + ispit.getStudent().getImeIPrezime() + " je ostvario ocjenu 'izvrstan' na predmetu '" + ispit.getPredmet().getNaziv() + "'");
        });
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
    private static boolean studentSNegativnomOcjenom(List<Ispit> ispiti){
        for(Ispit ispit : ispiti){
            if(ispit.getOcjena().getBrojcanaOznaka() == 1) return true;
        }
        return  false;
    }

    private static void studentiNaPredmetima(List<Predmet> predmeti){
        predmeti.stream().forEach(predmet -> {
            if(predmet.getStudenti().size() == 0){
                System.out.printf("Nema studenata upisanih na predmet '%s'.\n", predmet.getNaziv());
            }
            else{
                System.out.printf("Studenti upisani na predmet '%s' su: \n", predmet.getNaziv());
                predmet.getStudenti().stream().sorted(new StudentSorter()).forEach(System.out::println);
            }
        });
    }

    private static void profesoriNaPredmetima(Map<Profesor, List<Predmet>> mapa){
        for(Profesor profesor : mapa.keySet()){
            System.out.printf("Profesor %s predaje sljedeće predmete:\n", profesor.getImeIPrezime());
            int n = 1;

            for (Predmet predmet : mapa.get(profesor)) {
                if (predmet.getNositelj().getSifra().equals(profesor.getSifra())) {
                    System.out.printf("%d) %s\n", n, predmet.getNaziv());
                    n++;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner ulaz = new Scanner(System.in);

        Integer brojUstanova = ucitajBroj(ulaz, "Unesite broj obrazovnih ustanova: ");

        Sveuciliste<ObrazovnaUstanova> ustanove = new Sveuciliste<ObrazovnaUstanova>();

        for(int i = 0; i < brojUstanova; i++){
            System.out.println("Unesite podatke za " + (i+1) + ". obrazovnu ustanovu:");

            List<Profesor> profesori = new ArrayList<>();
            List<Student> studenti = new ArrayList<>();
            List<Predmet> predmeti = new ArrayList<>();
            List<Ispit> ispiti = new ArrayList<>();
            Map<Profesor, List<Predmet>> profesoriSPredmetima = new HashMap<>();

            ucitajProfesore(ulaz,profesori);
            ucitajPredmete(ulaz, predmeti, profesori, profesoriSPredmetima);
            profesoriNaPredmetima(profesoriSPredmetima);
            ucitajStudente(ulaz, studenti);
            ucitajIspitneRokove(ulaz, ispiti, predmeti, studenti);
            ispisStudenataSIzvrsnim(ispiti);
            studentiNaPredmetima(predmeti);

            Integer tipUstanove = ucitajBroj(ulaz, "Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, 2 - Fakultet računarstva): ");

            System.out.print("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = ulaz.nextLine();

            if(tipUstanove == 1) ustanove.dodajObrazovnuUstanovu(new VeleucilisteJave(nazivUstanove, predmeti, studenti, profesori, ispiti));
            else if(tipUstanove == 2) ustanove.dodajObrazovnuUstanovu(new FakultetRacunarstva(nazivUstanove, predmeti, studenti, profesori, ispiti));

            for(int j = 0; j < ustanove.dohvatiObrazovnuUstanovu(i).getStudenti().size(); j++){

                List<Ispit> ispitiStudenta = new ArrayList<>();

                if(ustanove.dohvatiObrazovnuUstanovu(i) instanceof VeleucilisteJave veleuciliste){
                    ispitiStudenta = veleuciliste.filtrirajIspitePoStudentu(veleuciliste.getIspiti(), veleuciliste.getStudenti().get(j));
                }
                else if(ustanove.dohvatiObrazovnuUstanovu(i) instanceof FakultetRacunarstva fakultet){
                    ispitiStudenta = fakultet.filtrirajIspitePoStudentu(fakultet.getIspiti(), fakultet.getStudenti().get(j));
                }

                if(studentSNegativnomOcjenom(ispitiStudenta)){
                    continue;
                }

                Integer ocjenaZavrsni = ucitajBroj(ulaz, "Unesite ocjenu završnog rada za studenta: " + ustanove.dohvatiObrazovnuUstanovu(i).getStudenti().get(j).getImeIPrezime() + ": ");
                Integer ocjenaObrana = ucitajBroj(ulaz, "Unesite ocjenu obrane završnog rada za studenta: " + ustanove.dohvatiObrazovnuUstanovu(i).getStudenti().get(j).getImeIPrezime() + ": ");

                if(ustanove.dohvatiObrazovnuUstanovu(i) instanceof VeleucilisteJave veleuciliste){
                    try {
                        System.out.println("Konačna ocjena studija studenta " + veleuciliste.getStudenti().get(j).getImeIPrezime()+" je " + veleuciliste.izracunajKonacnuOcjenuStudijaZaStudenta(veleuciliste.filtrirajIspitePoStudentu(veleuciliste.getIspiti(), veleuciliste.getStudenti().get(j)), ocjenaZavrsni, ocjenaObrana));
                    }
                    catch (NemoguceOdreditiProsjekStudentaException ex) {
                        logger.error("Greska prilikom odredivanja ocjene studenta", ex);
                    }
                }
                else if(ustanove.dohvatiObrazovnuUstanovu(i) instanceof FakultetRacunarstva fakultet){
                    try{
                        System.out.println("Konačna ocjena studija studenta " + fakultet.getStudenti().get(j).getImeIPrezime()+" je " + fakultet.izracunajKonacnuOcjenuStudijaZaStudenta(fakultet.filtrirajIspitePoStudentu(fakultet.getIspiti(), fakultet.getStudenti().get(j)), ocjenaZavrsni, ocjenaObrana));
                    }
                    catch (NemoguceOdreditiProsjekStudentaException ex){
                        logger.error("Greska prilikom odredivanja ocjene studenta", ex);
                    }
                }
            }

            Student najbolji = ustanove.dohvatiObrazovnuUstanovu(i).odrediNajuspjesnijegStudentaNaGodini(2022); //namjerno hardkodirano!!!
            System.out.println("Najbolji student 2022. godine je " + najbolji.getImeIPrezime() + " JMBAG: " + najbolji.getJmbag());

            if(ustanove.dohvatiObrazovnuUstanovu(i) instanceof FakultetRacunarstva fakultet){
                try{
                    Student nagradjen = fakultet.odrediStudentaZaRektorovuNagradu();
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + nagradjen.getImeIPrezime() + " JMBAG: " + nagradjen.getJmbag());
                }
                catch (PostojiViseNajmladihStudenataException ex){
                    logger.error("Postoji vise najmladjih studenata", ex);
                    System.out.println("Program završava s izvođenjem.");
                    System.out.print("Pronađeno je više najmlađih studenata s istim datumom rođenja, a to su: ");

                    for (int k = 0; k < ex.studenti.size(); k++) {
                        if (k > 0) {
                            System.out.print(", ");
                        }
                        System.out.print(ex.studenti.get(k).getImeIPrezime());
                    }

                    System.out.println(".");
                    System.exit(-1);
                }

            }
        }

        for (ObrazovnaUstanova ustanova : ustanove.getObrazovneUstanove().stream().sorted(new ObrazovnaUstanovaSorter()).toList()){
            System.out.println(ustanova.getNaziv()+": " + ustanova.getStudenti().size() + " studenta");
        }
    }
}
