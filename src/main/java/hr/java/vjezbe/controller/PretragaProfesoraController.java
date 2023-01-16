package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.ProfesorRepository;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PretragaProfesoraController {
    @FXML
    private TextField imeProfesoraField, prezimeProfesoraField, sifraProfesoraField, titulaProfesoraField;

    @FXML
    private TableView<Profesor> profesorTableView;

    @FXML
    private TableColumn<Profesor, String> imeProfesoraColumn, prezimeProfesoraColumn, sifraProfesoraColumn, titulaProfesoraColumn;

    public void initialize() {
        imeProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getIme()));
        prezimeProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getPrezime()));
        sifraProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSifra()));
        titulaProfesoraColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getTitula()));

        try{
            profesorTableView.setItems(FXCollections.observableList(ProfesorRepository.dohvatiProfesore()));
        }
        catch (BazaPodatakaException ex){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }

    public void dohvatiProfesore(){
        String ime = imeProfesoraField.getText();
        String prezime = prezimeProfesoraField.getText();
        String sifra = sifraProfesoraField.getText();
        String titula = titulaProfesoraField.getText();

        try{
            profesorTableView.setItems(FXCollections.observableList(ProfesorRepository.dohvatiProfesore(new Profesor(0L, sifra, ime, prezime, titula))));
        }
        catch (BazaPodatakaException ex){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Baza podataka", "Greška", ex.getMessage() + ": " + ex.getCause().getMessage());
        }
    }
}
