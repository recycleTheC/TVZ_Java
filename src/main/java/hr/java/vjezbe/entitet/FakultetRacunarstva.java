package hr.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;

public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski{
    public FakultetRacunarstva(String naziv, Predmet[] predmeti, Student[] studenti, Profesor[] profesori, Ispit[] ispiti) {
        super(naziv, predmeti, studenti, profesori, ispiti);
    }

    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(Ispit[] ispiti, int ocjenaDiplomskogRada, int ocjenaObraneDiplomskogRada) {
        BigDecimal prosjek = new BigDecimal(0);

        for(Ispit ispit : ispiti){
            prosjek = prosjek.add(BigDecimal.valueOf(ispit.getOcjena()));
        }

        prosjek = prosjek.divide(BigDecimal.valueOf(ispiti.length));

        return prosjek.multiply(BigDecimal.valueOf(3)).add(BigDecimal.valueOf(ocjenaDiplomskogRada)).add(BigDecimal.valueOf(ocjenaObraneDiplomskogRada)).divide(BigDecimal.valueOf(5));
    }

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
    public Student odrediStudentaZaRektorovuNagradu() {
        Student[] studenti = this.getStudenti();

        /*
        Arrays.sort(studenti, (Student a, Student b) -> {
            return a.getDatumRodjenja().compareTo(b.getDatumRodjenja());
        });
         */

        Arrays.sort(studenti, Comparator.comparing(Student::getDatumRodjenja));

        Student najuspjesnijiStudent = studenti[studenti.length-1];
        Ispit[] ispitiNajuspjesnijegStudenta = this.filtrirajIspitePoStudentu(this.getIspiti(), najuspjesnijiStudent);
        BigDecimal prosjekNajuspjesnijeg = this.odrediProsjekOcjenaNaIspitima(ispitiNajuspjesnijegStudenta);

        for(int i = studenti.length-2; i >=0; i--){
            Ispit[] ispiti = this.filtrirajIspitePoStudentu(this.getIspiti(), studenti[i]);
            BigDecimal prosjek = this.odrediProsjekOcjenaNaIspitima(ispiti);

            if(prosjek.compareTo(prosjekNajuspjesnijeg) >= 0){
                najuspjesnijiStudent = studenti[i];
                ispitiNajuspjesnijegStudenta = ispiti;
                prosjekNajuspjesnijeg = prosjek;
            }
        }

        return najuspjesnijiStudent;
    }
}
