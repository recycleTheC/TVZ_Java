package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(List<Ispit> ispiti, int ocjenaZavrsnogRada, int ocjenaObraneZavrsnogRada) throws NemoguceOdreditiProsjekStudentaException;

    /**
     * Vraća prosjek ocjena za sve unesene ispite
     * @param ispiti ispiti jednog studenta
     * @return prosjek ocjena za unesene ispite
     * @throws NemoguceOdreditiProsjekStudentaException iznimka postojanja ispita s ocjenom 1 (nedovoljan)
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(List<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal zbroj = new BigDecimal(0);
        int n = 0;

        for(Ispit ispit : ispiti){
            if(ispit.getOcjena().getBrojcanaOznaka() > 1) {
                zbroj = zbroj.add(BigDecimal.valueOf(ispit.getOcjena().getBrojcanaOznaka()));
                n++;
            }
            else {
                throw new NemoguceOdreditiProsjekStudentaException(ispit);
            }
        }

        if(n == 0) throw new NemoguceOdreditiProsjekStudentaException("Greska");

        return zbroj.divide(BigDecimal.valueOf(n));
    }

    private List<Ispit> filtrirajPolozeneIspite(List<Ispit> ispiti) {
        List<Ispit> polozeni = new ArrayList<>();

        for(Ispit ispit : ispiti){
            if(ispit.getOcjena().getBrojcanaOznaka() > 1) {
                polozeni.add(ispit);
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
    default List<Ispit> filtrirajIspitePoStudentu(List<Ispit> ispiti, Student student){
        return ispiti.stream().filter(ispit -> ispit.getStudent().equals(student)).toList();
    }
}
