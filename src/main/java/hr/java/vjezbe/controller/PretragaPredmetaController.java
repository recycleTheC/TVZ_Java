package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.PredmetRepository;
import hr.java.vjezbe.database.ProfesorRepository;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
public class PretragaPredmetaController {
    @FXML
    private TextField nazivPredmetaField, sifraPredmetaField;
    @FXML
    private Spinner<Integer> brojEctsBodovaSpinner;
    @FXML
    private TableView<Predmet> predmetTableView;
    @FXML
    private ChoiceBox<Profesor> profesorChoiceBox;
    @FXML
    private TableColumn<Predmet, String> nazivPredmetaColumn, sifraPredmetaColumn, brojEctsBodovaColumn, nositeljPredmetaColumn;
    public void initialize() {
        nazivPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNaziv()));
        sifraPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSifra()));
        brojEctsBodovaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getBrojEctsBodova().toString()));
        nositeljPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNositelj().getImeIPrezime()));

        try {
            predmetTableView.setItems(FXCollections.observableList(PredmetRepository.dohvatiPredmete()));
            profesorChoiceBox.setItems(FXCollections.observableList(ProfesorRepository.dohvatiProfesore()));
        } catch (BazaPodatakaException ex) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }
    public void dohvatiPredmete(){
        String naziv = nazivPredmetaField.getText();
        String sifra = sifraPredmetaField.getText();
        Integer ects = brojEctsBodovaSpinner.getValue();
        Profesor nositelj = profesorChoiceBox.getValue();

        try {
            predmetTableView.setItems(FXCollections.observableList(PredmetRepository.dohvatiPredmete(new Predmet(sifra, naziv, ects, nositelj))));
        } catch (BazaPodatakaException ex) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }
}
