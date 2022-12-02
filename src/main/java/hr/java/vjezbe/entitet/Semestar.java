package hr.java.vjezbe.entitet;

public enum Semestar {
    LJETNI("Ljetni semestar"),
    ZIMSKI("Zimski semestar");

    private final String tip;

    Semestar(String tip) {
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }
}
