package hr.java.vjezbe.entitet;

import hr.java.vjezbe.glavna.Glavna;
import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

/**
 * Klasa Veleučilište Jave nasljeđuje Obrazovnu ustanovu i implementira metode sučelja Visokoškolska
 * Sadrži sve metode s implementacijom specifičnom za veleučilišta
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    public VeleucilisteJave(String naziv, List<Predmet> predmeti, List<Student> studenti, List<Profesor> profesori, List<Ispit> ispiti) {
        super(naziv, predmeti, studenti, profesori, ispiti);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, int ocjenaZavrsnogRada, int ocjenaObraneZavrsnogRada) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal prosjek = new BigDecimal(0);

        try{
            prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);
        }catch (NemoguceOdreditiProsjekStudentaException ex){
            throw ex;
        }

        return prosjek.multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(ocjenaZavrsnogRada)).add(BigDecimal.valueOf(ocjenaObraneZavrsnogRada)).divide(BigDecimal.valueOf(4));
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        List<Student> studenti = this.getStudenti();

        boolean postavljenNajuspjesniji = false;

        Student najuspjesniji = null;
        BigDecimal prosjekNajuspjesnijeg = BigDecimal.ZERO;

        for(int i = 0; i < studenti.size(); i++){
            List<Ispit> ispiti = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), studenti.get(i)), godina);
            BigDecimal prosjek = BigDecimal.ZERO;

            try{
                prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);
            }
            catch (NemoguceOdreditiProsjekStudentaException ex){
                System.out.println("Student " + studenti.get(i).getImeIPrezime() + " zbog negativne ocjene na jednom od ispita ima prosjek „nedovoljan (1)“!" );
                logger.error("Nije moguce odrediti prosjek studenta", ex);
                continue;
            }

            if(!postavljenNajuspjesniji){
                najuspjesniji = studenti.get(i);
                prosjekNajuspjesnijeg = prosjek;
                postavljenNajuspjesniji = true;
            }
            else if(prosjek.compareTo(prosjekNajuspjesnijeg) >= 0){
                najuspjesniji = studenti.get(i);
                prosjekNajuspjesnijeg = prosjek;
            }
        }

        return najuspjesniji;
    }
}
