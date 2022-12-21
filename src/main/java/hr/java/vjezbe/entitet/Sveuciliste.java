package hr.java.vjezbe.entitet;

import java.util.ArrayList;
import java.util.List;

public class Sveuciliste <T extends ObrazovnaUstanova> {
    private List<T> ustanove;

    public Sveuciliste() {
        this.ustanove = new ArrayList<>();
    }

    public void dodajObrazovnuUstanovu(T ustanova){
        this.ustanove.add(ustanova);
    }

    public ObrazovnaUstanova dohvatiObrazovnuUstanovu(int index){
        return this.ustanove.get(index);
    }

    public List<T> getObrazovneUstanove(){
        return this.ustanove;
    }
}
