package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PretragaStudenataController {
    @FXML
    private TextField imeStudentaField, prezimeStudentaField, jmbagStudentaField;
    @FXML
    private DatePicker datumRodjenjaStudentaField;
    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> imeStudentaColumn, prezimeStudentaColumn, jmbagStudentaColumn, datumRodjenjaColumn;
    private List<Student> studentList;
    public void initialize() {
        studentList = Datoteke.ucitajStudente();

        imeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIme()));
        prezimeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrezime()));
        jmbagStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getJmbag()));
        datumRodjenjaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        studentTableView.setItems(FXCollections.observableList(studentList));
    }
    public void dohvatiStudente(){
        String ime = imeStudentaField.getText();
        String prezime = prezimeStudentaField.getText();
        String jmbag = jmbagStudentaField.getText();
        LocalDate datumRodjenja = datumRodjenjaStudentaField.getValue();

        List<Student> filtriraniStudenti = studentList.stream().filter(s -> s.getIme().toLowerCase().contains(ime.toLowerCase()) && s.getPrezime().toLowerCase().contains(prezime.toLowerCase()) && s.getJmbag().startsWith(jmbag) && (datumRodjenja != null ? s.getDatumRodjenja().equals(datumRodjenja) : true)).toList();
        studentTableView.setItems(FXCollections.observableList(filtriraniStudenti));
    }
}
