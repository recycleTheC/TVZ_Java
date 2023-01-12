package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import hr.java.vjezbe.util.MessageBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.util.List;

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
        List<Student> studentList = Datoteke.ucitajStudente();
        List<Profesor> profesorList = Datoteke.ucitajProfesore();
        List<Predmet> predmetList = Datoteke.ucitajPredmete(profesorList, studentList);

        predmetBox.setItems(FXCollections.observableList(predmetList));
        studentBox.setItems(FXCollections.observableList(studentList));
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
            Ispit ispit = new Ispit(Datoteke.maxIdIspita().getAsLong() + 1, predmet, student, ocjena, datum);
            Datoteke.unosIspita(ispit);
            MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos ispita", "Uspješan unos", "Ispit je uspješno unesen!");
        }
    }
}
