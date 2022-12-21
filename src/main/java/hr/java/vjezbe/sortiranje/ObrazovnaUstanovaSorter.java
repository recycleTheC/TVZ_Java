package hr.java.vjezbe.sortiranje;

import hr.java.vjezbe.entitet.ObrazovnaUstanova;

import java.util.Comparator;

public class ObrazovnaUstanovaSorter implements Comparator<ObrazovnaUstanova> {
    @Override
    public int compare(ObrazovnaUstanova o1, ObrazovnaUstanova o2) {
        if(o1.getStudenti().size() < o2.getStudenti().size()){
            return -1;
        }
        else if(o1.getStudenti().size() > o2.getStudenti().size()){
            return 1;
        }
        else {
            return o1.getNaziv().compareTo(o2.getNaziv());
        }
    }
}
