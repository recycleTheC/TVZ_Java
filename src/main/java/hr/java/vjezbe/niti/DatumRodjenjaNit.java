package hr.java.vjezbe.niti;

import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.List;

public class DatumRodjenjaNit implements Runnable {
    @Override
    public void run() {
        try {
            List<Student> studenti = StudentRepository.dohvatiStudenteZaRodjendan(LocalDate.now());

            StringBuilder poruka = new StringBuilder("Čestitajte rođendan sljedećim studentima: \n");
            studenti.forEach(student -> poruka.append(student.getImeIPrezime()).append("\n"));

            MessageBox.pokazi(Alert.AlertType.INFORMATION, "Studenti", "Rođendan studenata", poruka.toString());
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }
}
