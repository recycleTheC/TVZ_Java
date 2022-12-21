package hr.java.vjezbe.entitet;

import hr.java.vjezbe.iznimke.PostojiViseNajmladihStudenataException;

/**
 * Sučelje koje definira metode potrebne na za Diplomski studij
 */
public interface Diplomski extends Visokoskolska {
    /**
     * Vraća jednog studenta kojem treba biti dodijeljenja rektorova nagrada
     * @return student kojem se dodjeljuje rektorova nagrada
     * @throws PostojiViseNajmladihStudenataException iznimka postojanja više najmlađih studenata s jednakim datumom rođenja
     */
    public Student odrediStudentaZaRektorovuNagradu() throws PostojiViseNajmladihStudenataException;
}
