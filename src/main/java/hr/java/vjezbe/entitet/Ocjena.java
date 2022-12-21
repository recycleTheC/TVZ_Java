package hr.java.vjezbe.entitet;

public enum Ocjena {
    NIJE_UNESENA(0, "nije uneseno"),
    IZVRSTAN(5, "izvrstan"),
    VRLO_DOBAR(4, "vrlo dobar"),
    DOBAR(3, "dobar"),
    DOVOLJAN(2, "dovoljan"),
    NEDOVOLJAN(1, "nedovoljan");

    private final int brojcanaOznaka;
    private final String slovnaOznaka;

    public int getBrojcanaOznaka() {
        return brojcanaOznaka;
    }

    public String getSlovnaOznaka() {
        return slovnaOznaka;
    }

    Ocjena(int brojcanaOznaka, String slovnaOznaka) {
        this.brojcanaOznaka = brojcanaOznaka;
        this.slovnaOznaka = slovnaOznaka;
    }
}
