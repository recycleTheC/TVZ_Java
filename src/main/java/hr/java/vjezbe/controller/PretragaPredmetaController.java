package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class PretragaPredmetaController {
    @FXML
    private TextField nazivPredmetaField, sifraPredmetaField, nositeljPredmetaField;
    @FXML
    private Spinner<Integer> brojEctsBodovaSpinner;
    @FXML
    private TableView<Predmet> predmetTableView;
    @FXML
    private TableColumn<Predmet, String> nazivPredmetaColumn, sifraPredmetaColumn, brojEctsBodovaColumn, nositeljPredmetaColumn;
    private List<Predmet> predmetList;
    public void initialize() {
        List<Profesor> profesorList = Datoteke.ucitajProfesore();
        List<Student> studentList = Datoteke.ucitajStudente();
        predmetList = Datoteke.ucitajPredmete(profesorList, studentList);

        nazivPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNaziv()));
        sifraPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSifra()));
        brojEctsBodovaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBrojEctsBodova().toString()));
        nositeljPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNositelj().getImeIPrezime()));

        predmetTableView.setItems(FXCollections.observableList(predmetList));
    }
    public void dohvatiPredmete(){
        String naziv = nazivPredmetaField.getText().toLowerCase();
        String sifra = sifraPredmetaField.getText().toLowerCase();
        Integer ects = brojEctsBodovaSpinner.getValue();
        String imeNositelja = nositeljPredmetaField.getText().toLowerCase();

        System.out.println(ects);
        List<Predmet> filtriraniPredmeti = predmetList.stream().filter(p -> p.getNaziv().toLowerCase().contains(naziv) && p.getSifra().toLowerCase().startsWith(sifra) && (ects == 0 || p.getBrojEctsBodova().equals(ects)) && p.getNositelj().getImeIPrezime().toLowerCase().contains(imeNositelja)).toList();
        predmetTableView.setItems(FXCollections.observableList(filtriraniPredmeti));
    }
}
