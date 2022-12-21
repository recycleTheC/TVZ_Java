package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PretragaIspitaController {
    @FXML
    private TextField nazivPredmetaField;
    @FXML
    private TextField imeStudentaField;
    @FXML
    private TextField prezimeStudentaField;
    @FXML
    private Spinner<Integer> ocjenaSpinner;
    @FXML
    private DatePicker datumIspitaField;
    @FXML
    private TableView<Ispit> ispitTableView;
    @FXML
    private TableColumn<Ispit, String> nazivPredmetaColumn;
    @FXML
    private TableColumn<Ispit, String> imePrezimeStudentaColumn;
    @FXML
    private TableColumn<Ispit, String> ocjenaColumn;
    @FXML
    private TableColumn<Ispit, String> datumIspitaColumn;
    private List<Ispit> ispitList;

    public void initialize() {
        List<Profesor> profesorList = Datoteke.ucitajProfesore();
        List<Student> studentList = Datoteke.ucitajStudente();
        List<Predmet> predmetList = Datoteke.ucitajPredmete(profesorList, studentList);
        ispitList = Datoteke.ucitajIspitneRokove(predmetList, studentList);

        nazivPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPredmet().getNaziv()));
        imePrezimeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getImeIPrezime()));
        ocjenaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOcjena().getSlovnaOznaka()));
        datumIspitaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"))));

        ispitTableView.setItems(FXCollections.observableList(ispitList));
    }
    public void dohvatiIspite(){
        String nazivPredmeta = nazivPredmetaField.getText().toLowerCase();
        String imeStudenta = imeStudentaField.getText().toLowerCase();
        String prezimeStudenta = prezimeStudentaField.getText().toLowerCase();
        Integer ocjena = ocjenaSpinner.getValue();
        LocalDate datum = datumIspitaField.getValue();

        List<Ispit> filtriraniIspiti = ispitList.stream()
                .filter(ispit -> ispit.getPredmet().getNaziv().toLowerCase().contains(nazivPredmeta))
                .filter(ispit -> ispit.getStudent().getIme().toLowerCase().contains(imeStudenta))
                .filter(ispit -> ispit.getStudent().getPrezime().toLowerCase().contains(prezimeStudenta))
                .filter(ispit -> ocjena == 0 || ispit.getOcjena().getBrojcanaOznaka() == ocjena)
                .filter(ispit -> datum == null || ispit.getDatumIVrijeme().toLocalDate().equals(datum))
                .toList();
        ispitTableView.setItems(FXCollections.observableList(filtriraniIspiti));
    }
}
