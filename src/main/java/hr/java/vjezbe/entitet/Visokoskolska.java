package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public interface Visokoskolska {
    BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaZavrsnogRada, int ocjenaObraneZavrsnogRada);
    default BigDecimal odrediProsjekOcjenaNaIspitima(Ispit[] ispiti){
        BigDecimal zbroj = new BigDecimal(0);
        int n = 0;

        for(Ispit ispit : ispiti){
            if(ispit.getOcjena() > 1) {
                zbroj = zbroj.add(BigDecimal.valueOf(ispit.getOcjena()));
                n++;
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
