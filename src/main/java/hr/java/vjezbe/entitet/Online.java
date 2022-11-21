package hr.java.vjezbe.entitet;

/**
 * Sučelje koje zahtjeva implementiranje metoda za software na kojem će se provoditi ispit
 */
public sealed interface Online permits Ispit {
    void setSoftwareZaIspit(String software);
    String getSoftwareZaIspit();
}
