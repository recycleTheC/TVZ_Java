package hr.java.vjezbe.entitet;

import java.io.Serializable;

public class Zbirka implements Serializable {
    private Long idStudenta;
    private String sifraPredmeta;
    private Ocjena ocjena;

    public Zbirka(Long idStudenta, String sifraPredmeta, Ocjena ocjena) {
        this.idStudenta = idStudenta;
        this.sifraPredmeta = sifraPredmeta;
        this.ocjena = ocjena;
    }

    public Long getIdStudenta() {
        return idStudenta;
    }

    public String getSifraPredmeta() {
        return sifraPredmeta;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }
}
