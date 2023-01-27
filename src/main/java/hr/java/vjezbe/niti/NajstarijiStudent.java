package hr.java.vjezbe.niti;

import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class NajstarijiStudent implements Runnable {
    @Override
    public void run() {
        try {
            Optional<Student> najstariji = StudentRepository.dohvatiNajstarijegStudenta();

            if(najstariji.isPresent()){
                Student student = najstariji.get();
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Studenti", "Najstariji student", "Najstariji student je " + student.getImeIPrezime() + "\n");
            }
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }
}
