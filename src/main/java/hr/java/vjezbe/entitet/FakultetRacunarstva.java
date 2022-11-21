package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Klasa za Fakultet računarstva, nasljeđuje Obrazovnu ustanovu i mora implementirati Diplomski
 * Sadrži sve metode s implementacijom specifičnom za fakultete računarstva
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Student[] studenti, Profesor[] profesori, Ispit[] ispiti) {
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
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaDiplomskogRada, int ocjenaObraneDiplomskogRada) throws NemoguceOdreditiProsjekStudentaException {
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
    private int brojIspitaSOcjenom(Ispit[] ispiti, int ocjena){
        int n = 0;

        for(Ispit ispit : ispiti){
            if(ispit.getOcjena().equals(ocjena)) n++;
        }

        return n;
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        Student[] studenti = this.getStudenti();

        Student najuspjesniji = studenti[studenti.length-1];
        Ispit[] ispitiNajuspjesnijeg = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), najuspjesniji),godina);
        int brojIspitaNajuspjesnijeg = this.brojIspitaSOcjenom(ispitiNajuspjesnijeg, 5);

        for(int i = studenti.length - 2; i >= 0; i--){
            Ispit[] ispiti = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), studenti[i]), godina);
            int brojIspita = this.brojIspitaSOcjenom(ispiti, 5);

            if(brojIspita > brojIspitaNajuspjesnijeg){
                najuspjesniji = studenti[i];
                ispitiNajuspjesnijeg = ispiti;
                brojIspitaNajuspjesnijeg = brojIspita;
            }
        }

        return najuspjesniji;
    }

    @Override
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladihStudenataException {
        Student[] studenti = this.getStudenti();
        Arrays.sort(studenti, Comparator.comparing(Student::getDatumRodjenja));

        Student[] studentiSJednakimDatumimaRodjenja = new Student[0];

        for(int i = studenti.length - 1; i > 0; i--){
            boolean jednaki = false;
            for(int j = i - 1; j >= 0; j--){
                if(studenti[i].getDatumRodjenja().equals(studenti[j].getDatumRodjenja())){
                    studentiSJednakimDatumimaRodjenja = Arrays.copyOf(studentiSJednakimDatumimaRodjenja, studentiSJednakimDatumimaRodjenja.length+1);
                    studentiSJednakimDatumimaRodjenja[studentiSJednakimDatumimaRodjenja.length-1] = studenti[j];
                    jednaki = true;
                    break;
                }
            }

            if(jednaki){
                studentiSJednakimDatumimaRodjenja = Arrays.copyOf(studentiSJednakimDatumimaRodjenja, studentiSJednakimDatumimaRodjenja.length+1);
                studentiSJednakimDatumimaRodjenja[studentiSJednakimDatumimaRodjenja.length-1] = studenti[i];
            }
        }

        if(studentiSJednakimDatumimaRodjenja.length > 0){
            throw new PostojiViseNajmladihStudenataException(studentiSJednakimDatumimaRodjenja);
        }

        Student najuspjesnijiStudent = null;
        BigDecimal prosjekNajuspjesnijeg = BigDecimal.ZERO;

        boolean postavljenNajuspjesniji = false;

        for(int i = studenti.length-2; i >=0; i--){
            Ispit[] ispiti = this.filtrirajIspitePoStudentu(this.getIspiti(), studenti[i]);
            BigDecimal prosjek = BigDecimal.ZERO;

            try{
                prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);
            }
            catch (NemoguceOdreditiProsjekStudentaException ex) {
                System.out.println("Student " + studenti[i].getImeIPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!" );
                logger.error("Nije moguce odrediti prosjek studenta", ex);
                continue;
            }

            if(!postavljenNajuspjesniji){
                najuspjesnijiStudent = studenti[i];
                prosjekNajuspjesnijeg = prosjek;
                postavljenNajuspjesniji = true;
            }
            else if(prosjek.compareTo(prosjekNajuspjesnijeg) >= 0){
                najuspjesnijiStudent = studenti[i];
                prosjekNajuspjesnijeg = prosjek;
            }
        }

        return najuspjesnijiStudent;
    }
}
