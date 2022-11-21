package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Sučelje Visokoškolska zahtjeva da klase implementiraju metode specifične za Visokoškolske ustavnove
 */
public interface Visokoskolska {
    /**
     * Vraća konačnu ocjenu studija za unesene ispite i tražene ocjene
     * @param ispiti polje ispita jednog studenta
     * @param ocjenaZavrsnogRada ocjena završnog rada
     * @param ocjenaObraneZavrsnogRada ocjena obrane završnog rada
     * @return konačna ocjenu studija prema uvjetima
     * @throws NemoguceOdreditiProsjekStudentaException student ima jedan ili više nepoložen predmet (ispit s ocjenom nedovoljan)
     */
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaZavrsnogRada, int ocjenaObraneZavrsnogRada) throws NemoguceOdreditiProsjekStudentaException;

    /**
     * Vraća prosjek ocjena za sve unesene ispite
     * @param ispiti ispiti jednog studenta
     * @return prosjek ocjena za unesene ispite
     * @throws NemoguceOdreditiProsjekStudentaException iznimka postojanja ispita s ocjenom 1 (nedovoljan)
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal zbroj = new BigDecimal(0);
        int n = 0;

        for(Ispit ispit : ispiti){
            if(ispit.getOcjena() > 1) {
                zbroj = zbroj.add(BigDecimal.valueOf(ispit.getOcjena()));
                n++;
            }
            else {
                throw new NemoguceOdreditiProsjekStudentaException(ispit);
            }
        }

        return zbroj.divide(BigDecimal.valueOf(n));
    }

    private Ispit[] filtrirajPolozeneIspite(Ispit[] ispiti) {
        Ispit[] polozeni = new Ispit[0];
        for(Ispit ispit : ispiti){
            if(ispit.getOcjena() > 1) {
                polozeni = Arrays.copyOf(polozeni, polozeni.length + 1);
                polozeni[polozeni.length - 1] = ispit;
            }
        }

        return polozeni;
    }

    /**
     * Vraća ispite za odabranog studenta
     * @param ispiti svi ispiti iz ustanove
     * @param student odabrani student
     * @return ispiti odabranog studenta
     */
    default Ispit[] filtrirajIspitePoStudentu(Ispit[] ispiti, Student student){
        Ispit[] ispitiStudenta = new Ispit[0];
        for(Ispit ispit : ispiti){
            if(ispit.getStudent().getJmbag().equals(student.getJmbag())) {
                ispitiStudenta = Arrays.copyOf(ispitiStudenta, ispitiStudenta.length + 1);
                ispitiStudenta[ispitiStudenta.length - 1] = ispit;
            }
        }

        return ispitiStudenta;
    }
}
