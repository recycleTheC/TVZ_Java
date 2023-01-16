package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PretragaStudenataController {
    @FXML
    private TextField imeStudentaField, prezimeStudentaField, jmbagStudentaField;
    @FXML
    private DatePicker datumRodjenjaStudentaField;
    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> imeStudentaColumn, prezimeStudentaColumn, jmbagStudentaColumn, datumRodjenjaColumn;
    public void initialize() {
        imeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIme()));
        prezimeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrezime()));
        jmbagStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getJmbag()));
        datumRodjenjaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        try{
            studentTableView.setItems(FXCollections.observableList(StudentRepository.dohvatiStudente()));
        }
        catch (BazaPodatakaException ex){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }
    public void dohvatiStudente(){
        String ime = imeStudentaField.getText();
        String prezime = prezimeStudentaField.getText();
        String jmbag = jmbagStudentaField.getText();
        LocalDate datumRodjenja = datumRodjenjaStudentaField.getValue();

        Student filterStudent = new Student(0L, ime, prezime, jmbag, datumRodjenja);

        try{
            studentTableView.setItems(FXCollections.observableList(StudentRepository.dohvatiStudente(filterStudent)));
        }
        catch (BazaPodatakaException ex){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }
}
