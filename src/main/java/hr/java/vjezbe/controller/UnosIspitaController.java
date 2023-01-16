package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.IspitRepository;
import hr.java.vjezbe.database.PredmetRepository;
import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;

public class UnosIspitaController {
    @FXML
    private ChoiceBox<Predmet> predmetBox;
    @FXML
    private ChoiceBox<Student> studentBox;
    @FXML
    private Spinner<Integer> ocjenaSpinner;
    @FXML
    private DateTimePicker datumIspitaField;

    public void initialize() {
        datumIspitaField.setFormat("dd.MM.yyyy. HH:mm");

        try {
            predmetBox.setItems(FXCollections.observableList(PredmetRepository.dohvatiPredmete()));
            studentBox.setItems(FXCollections.observableList(StudentRepository.dohvatiStudente()));
        } catch (BazaPodatakaException e) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", e.getMessage(), e.getCause().getMessage());
        }
    }
    public void spremiIspit(){
        Predmet predmet = predmetBox.getValue();
        Student student = studentBox.getValue();
        Integer ocjena = ocjenaSpinner.getValue();
        LocalDateTime datum = datumIspitaField.getDateTimeValue();

        StringBuilder greska = new StringBuilder();

        if(predmet == null) greska.append("Predmet nije odabran!\n");
        if(student == null) greska.append("Student nije odabran!\n");
        if(ocjena == null || ocjena == 0 || !(ocjena >= 1 && ocjena <= 5)) greska.append("Nije unesena ispravna ocjena!\n");
        if(datum == null) greska.append("Datum ispita nije unesen!\n");

        if(!greska.isEmpty()){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Unos ispita", "Nedostatak informacija", greska.toString());
        }
        else{
            try{
                IspitRepository.spremi(new Ispit( predmet, student, ocjena, datum));
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos ispita", "Uspješan unos", "Ispit je uspješno unesen!");
            }
            catch (BazaPodatakaException ex){
                MessageBox.pokazi(Alert.AlertType.ERROR, "Unos ispita", "Neuspješan unos", "Ispit nije uspješno unesen!\n" + ex.getCause().getMessage());
            }
        }
    }
}
