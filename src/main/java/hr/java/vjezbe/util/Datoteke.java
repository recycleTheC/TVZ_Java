package hr.java.vjezbe.util;

import hr.java.vjezbe.entitet.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Datoteke {
    /**
     * Učitavanje podataka o profesorima
     * @return polje profesora
     */
    public static List<Profesor> ucitajProfesore(){
        List<Profesor> profesori = new ArrayList<>();

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

        return profesori;
    }

    /**
     * Učitavanje podataka o predmetima
     * @param profesori polje profesora
     * @param studenti polje studenata
     * @return polje predmeta
     */
    public static List<Predmet> ucitajPredmete(List<Profesor> profesori, List<Student> studenti){
        List<Predmet> predmeti = new ArrayList<>();

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
                if(idUpisanihStudenata.size() != upisaniStudenti.size() && !idUpisanihStudenata.contains(0L)) throw new RuntimeException("Neispravan zapis studenata u datoteci predmeta!");

                predmeti.add(new Predmet(id, sifra, naziv, ectsBodovi, nositelj.get(), upisaniStudenti));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return predmeti;
    }

    /**
     * Učitavanje podataka o studentima
     * @return polje studenata
     */
    public static List<Student> ucitajStudente() {
        List<Student> studenti = new ArrayList<>();

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

                String gdjeZivi = zapisi.get(i+7);
                String prehrana = zapisi.get(i+8).replace(";", " ");
                String tipStudenta = zapisi.get(i+9);

                Student student = new StudentBuilder(id).setIme(ime).setPrezime(prezime).setJmbag(jmbag).setDatumRodjenja(datumRodjenja).setOcjenaZavrsni(ocjenaZavrsni, ocjenaObrane).createStudent();
                student.setGdjeZivi(gdjeZivi);
                student.setPrehrana(prehrana);
                student.setTipStudenta(tipStudenta);

                studenti.add(student);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return studenti;
    }

    /**
     * Učitavanje podataka o ispitnim rokovima
     * @param predmeti dostupni predmeti
     * @param studenti dostupni studenti
     * @return ispitni rokovi
     */
    public static List<Ispit> ucitajIspitneRokove(List<Predmet> predmeti, List<Student> studenti){
        List<Ispit> ispiti = new ArrayList<>();

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

        return ispiti;
    }

    /**
     * Učitavanje obrazovnih ustanova
     * @param profesori lista svih profesora
     * @param studenti lista svih studenata
     * @param predmeti lista svih predmeta
     * @param ispiti lista svih ispita
     * @return lista obrazovnih ustanova
     */

    public static List<ObrazovnaUstanova> ucitajObrazovneUstanove(List<Profesor> profesori, List<Student> studenti, List<Predmet> predmeti, List<Ispit> ispiti){
        List<ObrazovnaUstanova> ustanove = new ArrayList<>();

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

                if(tipUstanove == 1) ustanove.add(new VeleucilisteJave(idUstanove, nazivUstanove, predmetiUstanove, studentiUstanove, profesoriUstanove, ispitiUstanove));
                else if(tipUstanove == 2) ustanove.add(new FakultetRacunarstva(idUstanove, nazivUstanove, predmetiUstanove, studentiUstanove, profesoriUstanove, ispitiUstanove));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ustanove;
    }

    public static OptionalLong maxIdProfesora(){
        return ucitajProfesore().stream().mapToLong(Entitet::getId).max();
    }

    public static OptionalLong maxIdStudenta(){
        return ucitajStudente().stream().mapToLong(Entitet::getId).max();
    }

    public static OptionalLong maxIdIspita(){
        List<Profesor> profesori = ucitajProfesore();
        List<Student> studenti = ucitajStudente();
        List<Predmet> predmeti = ucitajPredmete(profesori, studenti);
        return ucitajIspitneRokove(predmeti, studenti).stream().mapToLong(Entitet::getId).max();
    }

    public static OptionalLong maxIdPredmeta(){
        List<Profesor> profesori = ucitajProfesore();
        List<Student> studenti = ucitajStudente();
        return ucitajPredmete(profesori, studenti).stream().mapToLong(Entitet::getId).max();
    }

    public static void unosProfesora(Profesor profesor){
        System.out.println("Unos profesora u datoteku...");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Profesor.NAZIV_DATOTEKE, true))){
            writer.write("\n" + profesor.getId().toString() + "\n");
            writer.write(profesor.getSifra()+ "\n");
            writer.write(profesor.getIme()+ "\n");
            writer.write(profesor.getPrezime()+ "\n");
            writer.write(profesor.getTitula());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unosStudenta(Student student){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Student.NAZIV_DATOTEKE, true))){
            writer.write(student.getId().toString() + "\n");
            writer.write(student.getIme()+ "\n");
            writer.write(student.getPrezime()+ "\n");
            writer.write(student.getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))+ "\n");
            writer.write(student.getJmbag()+ "\n");
            writer.write(student.getOcjenaZavrsni() + "\n");
            writer.write(student.getOcjenaObrana() + "\n");
            writer.write(student.getGdjeZivi() + "\n");
            writer.write(student.getPrehrana() + "\n");
            writer.write(student.getTipStudenta() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unosPredmeta(Predmet predmet){
        System.out.println("Unos predmeta u datoteku...");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Predmet.NAZIV_DATOTEKE, true))){
            writer.write("\n" + predmet.getId().toString() + "\n");
            writer.write(predmet.getSifra()+ "\n");
            writer.write(predmet.getNaziv()+ "\n");
            writer.write(predmet.getBrojEctsBodova()+ "\n");
            writer.write(predmet.getNositelj().getId()+ "\n");

            if(predmet.getStudenti().size() > 0){
                predmet.getStudenti().forEach(student -> {
                    try {
                        writer.write(student.getId() + " ");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            else writer.write("0");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unosIspita(Ispit ispit){
        System.out.println("Unos ispita u datoteku...");

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Ispit.NAZIV_DATOTEKE, true))){
            writer.write("\n" + ispit.getId().toString() + "\n");
            writer.write(ispit.getPredmet().getId() + "\n");
            writer.write(ispit.getStudent().getId() + "\n");
            writer.write(ispit.getOcjena().getBrojcanaOznaka() + "\n");
            writer.write(ispit.getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
