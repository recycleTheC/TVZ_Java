package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;

public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {
    public VeleucilisteJave(String naziv, Predmet[] predmeti, Student[] studenti, Profesor[] profesori, Ispit[] ispiti) {
        super(naziv, predmeti, studenti, profesori, ispiti);
    }


    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaZavrsnogRada, int ocjenaObraneZavrsnogRada) {
        BigDecimal prosjek = new BigDecimal(0);

        for(Ispit ispit : ispiti){
            prosjek = prosjek.add(BigDecimal.valueOf(ispit.getOcjena()));
        }

        prosjek = prosjek.divide(BigDecimal.valueOf(ispiti.length));

        return prosjek.multiply(BigDecimal.valueOf(2)).add(BigDecimal.valueOf(ocjenaZavrsnogRada)).add(BigDecimal.valueOf(ocjenaObraneZavrsnogRada)).divide(BigDecimal.valueOf(4));
    }

    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        Student[] studenti = this.getStudenti();

        Student najuspjesniji = studenti[0];
        Ispit[] ispitiNajuspjesnijeg = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), najuspjesniji), godina);
        BigDecimal prosjekNajuspjesnijeg = this.odrediProsjekOcjenaNaIspitima(ispitiNajuspjesnijeg);

        for(int i = 1; i < studenti.length; i++){
            Ispit[] ispiti = this.ispitiIzGodine(this.filtrirajIspitePoStudentu(this.getIspiti(), studenti[i]), godina);
            BigDecimal prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);

            if(prosjek.compareTo(prosjekNajuspjesnijeg) >= 0){
                najuspjesniji = studenti[i];
                ispitiNajuspjesnijeg = ispiti;
                prosjekNajuspjesnijeg = prosjek;
            }
        }

        return najuspjesniji;
    }
}
