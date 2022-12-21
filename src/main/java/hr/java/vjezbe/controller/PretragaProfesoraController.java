package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class PretragaProfesoraController {
    @FXML
    private TextField imeProfesoraField, prezimeProfesoraField, sifraProfesoraField, titulaProfesoraField;

    @FXML
    private TableView<Profesor> profesorTableView;

    @FXML
    private TableColumn<Profesor, String> imeProfesoraColumn, prezimeProfesoraColumn, sifraProfesoraColumn, titulaProfesoraColumn;
    private List<Profesor> profesorList;

    public void initialize() {
        profesorList = Datoteke.ucitajProfesore();

        imeProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIme()));
        prezimeProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrezime()));
        sifraProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSifra()));
        titulaProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitula()));

        profesorTableView.setItems(FXCollections.observableList(profesorList));
    }

    public void dohvatiProfesore(){
        String ime = imeProfesoraField.getText();
        String prezime = prezimeProfesoraField.getText();
        String sifra = sifraProfesoraField.getText();
        String titula = titulaProfesoraField.getText();

        List<Profesor> filtriraniProfesori = profesorList.stream().filter(p -> p.getIme().toLowerCase().contains(ime.toLowerCase()) && p.getPrezime().toLowerCase().contains(prezime.toLowerCase()) && p.getSifra().toLowerCase().startsWith(sifra.toLowerCase()) && p.getTitula().toLowerCase().contains(titula.toLowerCase())).toList();
        profesorTableView.setItems(FXCollections.observableList(filtriraniProfesori));
    }
}
