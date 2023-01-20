package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.IspitRepository;
import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

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

    public void initialize() {
        nazivPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPredmet().getNaziv()));
        imePrezimeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getImeIPrezime()));
        ocjenaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOcjena().getSlovnaOznaka()));
        datumIspitaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"))));

        try {
            ispitTableView.setItems(FXCollections.observableList(IspitRepository.dohvatiIspite()));
        } catch (BazaPodatakaException ex) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }
    public void dohvatiIspite(){
        Predmet predmet = new Predmet();
        predmet.setNaziv(nazivPredmetaField.getText());

        Student student = new StudentBuilder().setIme(imeStudentaField.getText()).setPrezime(prezimeStudentaField.getText()).createStudent();

        Integer ocjena = ocjenaSpinner.getValue();
        LocalDate datum = datumIspitaField.getValue();

        try {
            ispitTableView.setItems(FXCollections.observableList(IspitRepository.dohvatiIspite(new Ispit(predmet, student, ocjena, datum != null ? datum.atStartOfDay() : null))));
        } catch (BazaPodatakaException ex) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }

    public void obrisiIspit(){
        Ispit ispit = ispitTableView.getSelectionModel().getSelectedItem();

        if(Optional.ofNullable(ispit).isPresent()){
            try{
                IspitRepository.obrisi(ispit.getId());
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Ispiti", "Brisanje", "Ispit uspješno obrisan!");
            } catch (BazaPodatakaException e) {
                MessageBox.pokazi(Alert.AlertType.WARNING, "Brisanje ispita", "Greška pri radu s bazom podataka", "Nije moguće obrisati ispit!\n" + e.getCause().getMessage());
            }
        }
        else {
            MessageBox.pokazi(Alert.AlertType.WARNING, "Brisanje ispita", "Nije odabran ispit", "Odaberite ispit pa pokušajte ponovno!");
        }

        dohvatiIspite();
    }
}
