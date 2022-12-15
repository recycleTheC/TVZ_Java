package hr.java.vjezbe.entitet;

import java.util.Objects;

public class Entitet {
    private Long id;

    public Entitet(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
