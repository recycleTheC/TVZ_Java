package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa za Fakultet računarstva, nasljeđuje Obrazovnu ustanovu i mora implementirati Diplomski
 * Sadrži sve metode s implementacijom specifičnom za fakultete računarstva
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public FakultetRacunarstva(String naziv, List<Predmet> predmeti, List<Student> studenti, List<Profesor> profesori, List<Ispit> ispiti) {
        super(naziv, predmeti, studenti, profesori, ispiti);
    }

    /**
     * Vraća konačnu ocjenu studija za unesene ispite i tražene ocjene
     * @param ispiti polje ispita jednog studenta
     * @param ocjenaDiplomskogRada ocjena diplomskog rada
     * @param ocjenaObraneDiplomskogRada ocjena obrane diplomskog rada
     * @return konačna ocjenu studija prema uvjetima
     * @throws NemoguceOdreditiProsjekStudentaException student ima jedan ili više nepoložen predmet (ispit s ocjenom nedovoljan)
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, int ocjenaDiplomskogRada, int ocjenaObraneDiplomskogRada) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal prosjek = new BigDecimal(0);

        try{
            prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);
        }
        catch (NemoguceOdreditiProsjekStudentaException ex){
            throw ex;
        }

        return prosjek.multiply(BigDecimal.valueOf(3)).add(BigDecimal.valueOf(ocjenaDiplomskogRada)).add(BigDecimal.valueOf(ocjenaObraneDiplomskogRada)).divide(BigDecimal.valueOf(5));
    }

    /**
     * Pomoćna metoda za određivanje broja ispita s određenom ocjenom
     * @param ispiti
     * @param ocjena
     * @return broj ispita koji zadovoljavaju uvjete ocjene
     */
    private int brojIspitaSOcjenom(List<Ispit> ispiti, int ocjena){
        int n = 0;

        for(Ispit ispit : ispiti){
            if(ispit.getOcjena().equals(ocjena)) n++;
        }

        return n;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        List<Student> studenti = this.getStudenti();

        Student najuspjesniji = studenti.get(studenti.size() - 1);
        List<Ispit> ispitiNajuspjesnijeg = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), najuspjesniji),godina);
        int brojIspitaNajuspjesnijeg = this.brojIspitaSOcjenom(ispitiNajuspjesnijeg, 5);

        for(int i = studenti.size() - 2; i >= 0; i--){
            List<Ispit> ispiti = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), studenti.get(i)), godina);
            int brojIspita = this.brojIspitaSOcjenom(ispiti, 5);

            if(brojIspita > brojIspitaNajuspjesnijeg){
                najuspjesniji = studenti.get(i);
                ispitiNajuspjesnijeg = ispiti;
                brojIspitaNajuspjesnijeg = brojIspita;
            }
        }

        return najuspjesniji;
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladihStudenataException {
        List<Student> studenti = this.getStudenti();
        studenti.sort(Comparator.comparing(Student::getDatumRodjenja));

        List<Student> studentiSJednakimDatumimaRodjenja = new ArrayList<>();

        for(int i = studenti.size() - 1; i > 0; i--){
            boolean jednaki = false;

            for(int j = i - 1; j >= 0; j--){
                if(studenti.get(i).getDatumRodjenja().equals(studenti.get(j).getDatumRodjenja())){
                    studentiSJednakimDatumimaRodjenja.add(studenti.get(j));
                    jednaki = true;
                    break;
                }
            }

            if(jednaki){
                studentiSJednakimDatumimaRodjenja.add(studenti.get(i));
            }
        }

        if(studentiSJednakimDatumimaRodjenja.size() > 0){
            throw new PostojiViseNajmladihStudenataException(studentiSJednakimDatumimaRodjenja);
        }

        Student najuspjesnijiStudent = null;
        BigDecimal prosjekNajuspjesnijeg = BigDecimal.ZERO;

        boolean postavljenNajuspjesniji = false;

        for(int i = studenti.size() -2; i >=0; i--){
            List<Ispit> ispiti = this.filtrirajIspitePoStudentu(this.getIspiti(), studenti.get(i));
            BigDecimal prosjek = BigDecimal.ZERO;

            try{
                prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);
            }
            catch (NemoguceOdreditiProsjekStudentaException ex) {
                System.out.println("Student " + studenti.get(i).getImeIPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!" );
                logger.error("Nije moguce odrediti prosjek studenta", ex);
                continue;
            }

            if(!postavljenNajuspjesniji){
                najuspjesnijiStudent = studenti.get(i);
                prosjekNajuspjesnijeg = prosjek;
                postavljenNajuspjesniji = true;
            }
            else if(prosjek.compareTo(prosjekNajuspjesnijeg) >= 0){
                najuspjesnijiStudent = studenti.get(i);
                prosjekNajuspjesnijeg = prosjek;
            }
        }

        return najuspjesnijiStudent;
    }
}
