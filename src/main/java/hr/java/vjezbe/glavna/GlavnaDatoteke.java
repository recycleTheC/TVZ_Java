package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import hr.java.vjezbe.sortiranje.ObrazovnaUstanovaSorter;
import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GlavnaDatoteke {

    private static final Logger logger = LoggerFactory.getLogger(GlavnaDatoteke.class);
    private static final int BROJ_PROFESORA = 2;
    private static final int BROJ_STUDENATA = 2;
    private static final int BROJ_PREDMETA = 3;

    private static final int BROJ_ISPITNIH_ROKOVA = 2;

    /**
     * Učitavanje podataka o profesorima
     * @param profesori polje profesora
     */
    private static void ucitajProfesore(List<Profesor> profesori){
        System.out.println("Učitavanje profesora...");
        try(BufferedReader reader = new BufferedReader(new FileReader(Profesor.NAZIV_DATOTEKE))) {
            List<String> zapisi = reader.lines().toList();

            for(int i = 0; i < zapisi.size(); i += Profesor.BROJ_ZAPISA_U_DATOTEKAMA){
                Long id = Long.parseLong(zapisi.get(i));
                String sifra = zapisi.get(i+1);
                String ime = zapisi.get(i+2);
                String prezime = zapisi.get(i+3);
                String titula = zapisi.get(i+4);

                profesori.add(new Profesor.Builder(id, sifra).osoba(ime, prezime).saTitulom(titula).build());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Učitavanje podataka o predmetima
     * @param predmeti polje predmeta
     * @param profesori polje profesora
     */
    private static void ucitajPredmete(List<Predmet> predmeti, List<Profesor> profesori, List<Student> studenti){
        System.out.println("Učitavanje predmeta...");
        try(BufferedReader reader = new BufferedReader(new FileReader(Predmet.NAZIV_DATOTEKE))){
            List<String> zapisi = reader.lines().toList();

            for(int i = 0; i < zapisi.size(); i += Predmet.BROJ_ZAPISA_U_DATOTEKAMA){
                Long id = Long.parseLong(zapisi.get(i));
                String sifra = zapisi.get(i+1);
                String naziv = zapisi.get(i+2);
                Integer ectsBodovi = Integer.parseInt(zapisi.get(i+3));

                Long idNositelja = Long.parseLong(zapisi.get(i+4));
                Optional<Profesor> nositelj = profesori.stream().filter(profesor -> profesor.getId().equals(idNositelja)).findFirst();
                if(nositelj.isEmpty()) throw new RuntimeException("ID profesora je pogrešan ili ne postoji profesor s navedenim ID-em.");

                List<Long> idUpisanihStudenata = Arrays.stream(zapisi.get(i + 5).split(" ")).map(Long::parseLong).toList();
                Set<Student> upisaniStudenti = studenti.stream().filter(student -> idUpisanihStudenata.contains(student.getId())).collect(Collectors.toSet());
                if(idUpisanihStudenata.size() != upisaniStudenti.size()) throw new RuntimeException("Neispravan zapis studenata u datoteci predmeta!");

                predmeti.add(new Predmet(id, sifra, naziv, ectsBodovi, nositelj.get(), upisaniStudenti));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Učitavanje podataka o studentima
     * @param studenti polje studenata
     */
    private static void ucitajStudente(List<Student> studenti) {
        System.out.println("Učitavanje studenata...");
        try(BufferedReader reader = new BufferedReader(new FileReader(Student.NAZIV_DATOTEKE))){
            List<String> zapisi = reader.lines().toList();

            for(int i = 0; i < zapisi.size(); i += Student.BROJ_ZAPISA_U_DATOTEKAMA){
                Long id = Long.parseLong(zapisi.get(i));
                String ime = zapisi.get(i+1);
                String prezime = zapisi.get(i+2);
                LocalDate datumRodjenja = LocalDate.parse(zapisi.get(i+3), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                String jmbag = zapisi.get(i+4);
                int ocjenaZavrsni = Integer.parseInt(zapisi.get(i+5));
                int ocjenaObrane = Integer.parseInt(zapisi.get(i+6));

                studenti.add(new StudentBuilder(id).setIme(ime).setPrezime(prezime).setJmbag(jmbag).setDatumRodjenja(datumRodjenja).setOcjenaZavrsni(ocjenaZavrsni, ocjenaObrane).createStudent());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Učitavanje podataka o ispitnim rokovima
     * @param ispiti dostupni ispiti
     * @param predmeti dostupni predmeti
     * @param studenti dostupni studenti
     */
    private static void ucitajIspitneRokove(List<Ispit> ispiti, List<Predmet> predmeti, List<Student> studenti){
        System.out.println("Učitavanje ispitnih rokova...");
        try(BufferedReader reader = new BufferedReader(new FileReader(Ispit.NAZIV_DATOTEKE))){
            List<String> zapisi = reader.lines().toList();

            for(int i = 0; i < zapisi.size(); i += Ispit.BROJ_ZAPISA_U_DATOTEKAMA){
                Long idIspita = Long.parseLong(zapisi.get(i));

                Long idPredmeta = Long.parseLong(zapisi.get(i + 1));
                Optional<Predmet> odabraniPredmet = predmeti.stream().filter(predmet -> predmet.getId().equals(idPredmeta)).findFirst();
                if(odabraniPredmet.isEmpty()) throw new RuntimeException("Ne postoji predmet naveden na ispitnom roku!");

                Long idStudenta = Long.parseLong(zapisi.get(i + 2));
                Optional<Student> odabraniStudent = studenti.stream().filter(student -> student.getId().equals(idStudenta)).findFirst();
                if(odabraniStudent.isEmpty()) throw new RuntimeException("Ne postoji student naveden na ispitnom roku!");

                Integer ocjena = Integer.parseInt(zapisi.get(i + 3));

                LocalDateTime datumIVrijeme = LocalDateTime.parse(zapisi.get(i + 4), DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm"));

                ispiti.add(new Ispit(idIspita, odabraniPredmet.get(), odabraniStudent.get(), ocjena, datumIVrijeme));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void ucitajObrazovneUstanove(Sveuciliste<ObrazovnaUstanova> ustanove, List<Profesor> profesori, List<Student> studenti, List<Predmet> predmeti, List<Ispit> ispiti){
        System.out.println("Učitavanje obrazovnih ustanova...");
        try(BufferedReader reader = new BufferedReader(new FileReader(ObrazovnaUstanova.NAZIV_DATOTEKE))){
            List<String> zapisi = reader.lines().toList();

            for(int i = 0; i < zapisi.size(); i += ObrazovnaUstanova.BROJ_ZAPISA_U_DATOTEKAMA){
                Long idUstanove = Long.parseLong(zapisi.get(i));
                Integer tipUstanove = Integer.parseInt(zapisi.get(i + 1));
                String nazivUstanove = zapisi.get(i + 2);

                List<Long> idProfesora = Arrays.stream(zapisi.get(i + 3).split(" ")).map(Long::parseLong).toList();
                List<Profesor> profesoriUstanove= profesori.stream().filter(profesor -> idProfesora.contains(profesor.getId())).toList();

                List<Long> idStudenata = Arrays.stream(zapisi.get(i + 4).split(" ")).map(Long::parseLong).toList();
                List<Student> studentiUstanove = studenti.stream().filter(student -> idStudenata.contains(student.getId())).toList();

                List<Long> idPredmeta = Arrays.stream(zapisi.get(i + 5).split(" ")).map(Long::parseLong).toList();
                List<Predmet> predmetiUstanove = predmeti.stream().filter(predmet -> idPredmeta.contains(predmet.getId())).toList();

                List<Long> idIspita = Arrays.stream(zapisi.get(i + 6).split(" ")).map(Long::parseLong).toList();
                List<Ispit> ispitiUstanove = ispiti.stream().filter(ispit -> idIspita.contains(ispit.getId())).toList();

                if(tipUstanove == 1) ustanove.dodajObrazovnuUstanovu(new VeleucilisteJave(idUstanove, nazivUstanove, predmetiUstanove, studentiUstanove, profesoriUstanove, ispitiUstanove));
                else if(tipUstanove == 2) ustanove.dodajObrazovnuUstanovu(new FakultetRacunarstva(idUstanove, nazivUstanove, predmetiUstanove, studentiUstanove, profesoriUstanove, ispitiUstanove));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    private static <T extends ObrazovnaUstanova> void serijalizirajUstanove(Sveuciliste<T> ustanove){
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ObrazovnaUstanova.NAZIV_SERIJALIZIRANE_DATOTEKE))) {
            for (ObrazovnaUstanova ustanova : ustanove.getObrazovneUstanove()) {
                out.writeObject(ustanova);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        Sveuciliste<ObrazovnaUstanova> ustanove = new Sveuciliste<>();
        List<Profesor> profesori = new ArrayList<>();
        List<Student> studenti = new ArrayList<>();
        List<Predmet> predmeti = new ArrayList<>();
        List<Ispit> ispiti = new ArrayList<>();

        ucitajProfesore(profesori);
        ucitajStudente(studenti);
        ucitajPredmete(predmeti, profesori, studenti);
        ucitajIspitneRokove(ispiti, predmeti, studenti);
        ucitajObrazovneUstanove(ustanove, profesori, studenti, predmeti, ispiti);

        System.out.println();

        for(ObrazovnaUstanova ustanova : ustanove.getObrazovneUstanove()){
            profesoriNaPredmetima(ustanova.getProfesoriNaPredmetima());
            studentiNaPredmetima(ustanova.getPredmeti());
            ispisStudenataSIzvrsnim(ustanova.getIspiti());

            for(Student student : ustanova.getStudenti()){
                List<Ispit> ispitiStudenta = null;

                if(ustanova instanceof VeleucilisteJave veleuciliste){
                    ispitiStudenta = veleuciliste.filtrirajIspitePoStudentu(veleuciliste.getIspiti(), student);
                }
                else if(ustanova instanceof FakultetRacunarstva fakultet){
                    ispitiStudenta = fakultet.filtrirajIspitePoStudentu(fakultet.getIspiti(), student);
                }

                if(studentSNegativnomOcjenom(ispitiStudenta)){
                    continue;
                }

                if(ustanova instanceof VeleucilisteJave veleuciliste){
                    try {
                        System.out.println("Konačna ocjena studija studenta " + student.getImeIPrezime()+" je " + veleuciliste.izracunajKonacnuOcjenuStudijaZaStudenta(ispitiStudenta, student.getOcjenaZavrsni(), student.getOcjenaObrana()));
                    }
                    catch (NemoguceOdreditiProsjekStudentaException ex) {
                        logger.error("Greska prilikom odredivanja ocjene studenta", ex);
                    }
                }
                else if(ustanova instanceof FakultetRacunarstva fakultet){
                    try{
                        System.out.println("Konačna ocjena studija studenta " + student.getImeIPrezime()+" je " + fakultet.izracunajKonacnuOcjenuStudijaZaStudenta(ispitiStudenta, student.getOcjenaZavrsni(), student.getOcjenaObrana()));
                    }
                    catch (NemoguceOdreditiProsjekStudentaException ex){
                        logger.error("Greska prilikom odredivanja ocjene studenta", ex);
                    }
                }
            }

            Student najbolji = ustanova.odrediNajuspjesnijegStudentaNaGodini(2022); //namjerno hardkodirano!!!
            System.out.println("Najbolji student 2022. godine je " + najbolji.getImeIPrezime() + " JMBAG: " + najbolji.getJmbag());

            if(ustanova instanceof FakultetRacunarstva fakultet){
                try {
                    Student nagradjen = fakultet.odrediStudentaZaRektorovuNagradu();
                    if(nagradjen != null) System.out.println("Student koji je osvojio rektorovu nagradu je: " + nagradjen.getImeIPrezime() + " JMBAG: " + nagradjen.getJmbag());
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
                    //System.exit(-1);
                    break;
                }

            }

            System.out.println();
        }

        for (ObrazovnaUstanova ustanova : ustanove.getObrazovneUstanove().stream().sorted(new ObrazovnaUstanovaSorter()).toList()){
            System.out.println(ustanova.getNaziv()+": " + ustanova.getStudenti().size() + " studenta");
        }

        serijalizirajUstanove(ustanove);
    }
}
