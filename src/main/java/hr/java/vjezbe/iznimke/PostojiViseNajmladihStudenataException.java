package hr.java.vjezbe.iznimke;

import hr.java.vjezbe.entitet.Student;

import java.util.List;

/**
 * Iznimka koja se baca kada postoji više studenata s istim datumom rođenja (najvećim)
 */
public class PostojiViseNajmladihStudenataException extends RuntimeException  {

    public List<Student> studenti;
    public PostojiViseNajmladihStudenataException() {
        super();
    }

    public PostojiViseNajmladihStudenataException(String message) {
        super(message);
    }

    public PostojiViseNajmladihStudenataException(List<Student> studenti) {
        super();
        this.studenti = studenti;
    }

    public PostojiViseNajmladihStudenataException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostojiViseNajmladihStudenataException(Throwable cause) {
        super(cause);
    }

    protected PostojiViseNajmladihStudenataException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
