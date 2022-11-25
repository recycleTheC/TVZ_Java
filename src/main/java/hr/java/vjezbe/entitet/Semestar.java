package hr.java.vjezbe.entitet;

public enum Semestar {
    NIJE_UNESENO("nije uneseno"),
    LJETNI( "ljetni"),
    ZIMSKI( "zimski");
    private final String oznakaSemestra;

    Semestar(String oznakaSemestra) {
        this.oznakaSemestra = oznakaSemestra;
    }

}
