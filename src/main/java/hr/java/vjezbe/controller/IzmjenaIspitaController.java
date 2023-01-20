package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.IspitRepository;
import hr.java.vjezbe.database.PredmetRepository;
import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import tornadofx.control.DateTimePicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class IzmjenaIspitaController {
    @FXML
    private ChoiceBox<Predmet> predmetBox;
    @FXML
    private ChoiceBox<Student> studentBox;
    @FXML
    private Spinner<Integer> ocjenaSpinner;
    @FXML
    private DateTimePicker datumIspitaField;

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
    @FXML
    private Button spremiButton;
    Ispit ispit;

    public void initialize() {
        nazivPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPredmet().getNaziv()));
        imePrezimeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getStudent().getImeIPrezime()));
        ocjenaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getOcjena().getSlovnaOznaka()));
        datumIspitaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"))));
        datumIspitaField.setFormat("dd.MM.yyyy. HH:mm");

        try {
            predmetBox.setItems(FXCollections.observableList(PredmetRepository.dohvatiPredmete()));
            studentBox.setItems(FXCollections.observableList(StudentRepository.dohvatiStudente()));
            ispitTableView.setItems(FXCollections.observableList(IspitRepository.dohvatiIspite()));
        } catch (BazaPodatakaException e) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", e.getMessage(), e.getCause().getMessage());
        }
    }

    public void odabir(){
        ispit = ispitTableView.getSelectionModel().getSelectedItem();

        if(Optional.ofNullable(ispit).isPresent()){
            predmetBox.setDisable(false);
            datumIspitaField.setDisable(false);
            ocjenaSpinner.setDisable(false);
            studentBox.setDisable(false);
            spremiButton.setDisable(false);

            predmetBox.setValue(ispit.getPredmet());
            datumIspitaField.setValue(ispit.getDatumIVrijeme().toLocalDate());
            ocjenaSpinner.getValueFactory().setValue(ispit.getOcjena().getBrojcanaOznaka());
            studentBox.setValue(ispit.getStudent());
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
            MessageBox.pokazi(Alert.AlertType.ERROR, "Izmjena ispita", "Nedostatak informacija", greska.toString());
        }
        else{
            try{
                IspitRepository.izmjeni(new Ispit(ispit.getId(), predmet, student, ocjena, datum));
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Izmjena ispita", "Uspješna izmjena", "Ispit je uspješno izmjenjen!");

                predmetBox.setItems(FXCollections.observableList(PredmetRepository.dohvatiPredmete()));
                studentBox.setItems(FXCollections.observableList(StudentRepository.dohvatiStudente()));
                ispitTableView.setItems(FXCollections.observableList(IspitRepository.dohvatiIspite()));

                predmetBox.setDisable(true);
                datumIspitaField.setDisable(true);
                ocjenaSpinner.setDisable(true);
                studentBox.setDisable(true);
                spremiButton.setDisable(true);

                predmetBox.setValue(null);
                datumIspitaField.setValue(null);
                ocjenaSpinner.getValueFactory().setValue(0);
                studentBox.setValue(null);
                ispit = null;
            }
            catch (BazaPodatakaException ex){
                MessageBox.pokazi(Alert.AlertType.ERROR, "Izmjena ispita", "Neuspješna izmjena", "Ispit nije uspješno izmjenjen!\n" + ex.getCause().getMessage());
            }
        }
    }
}
