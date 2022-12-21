package hr.java.vjezbe.iznimke;

import hr.java.vjezbe.entitet.Ispit;

/**
 * Izminka koja se baca kada student ima jedan ili više ispita na kojima ima ocjenu nedovoljan (1)
 */
public class NemoguceOdreditiProsjekStudentaException extends Exception {
    public Ispit nepolozeni_ispit;
    public NemoguceOdreditiProsjekStudentaException() {
        super();
    }

    public NemoguceOdreditiProsjekStudentaException(String message) {
        super(message);
    }

    public NemoguceOdreditiProsjekStudentaException(Ispit ispit) {
        super();
        this.nepolozeni_ispit = ispit;
    }

    public NemoguceOdreditiProsjekStudentaException(String message, Throwable cause) {
        super(message, cause);
    }

    public NemoguceOdreditiProsjekStudentaException(Throwable cause) {
        super(cause);
    }

    protected NemoguceOdreditiProsjekStudentaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
