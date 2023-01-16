package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.PredmetRepository;
import hr.java.vjezbe.database.ProfesorRepository;
import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class UnosPredmetaController {
    @FXML
    private TextField nazivPredmetaField;
    @FXML
    private TextField sifraPredmetaField;
    @FXML
    private Spinner<Integer> brojEctsBodovaSpinner;
    @FXML
    private ChoiceBox<Profesor> odabirNositeljaPredmeta;
    @FXML
    private ListView<Student> odabirStudenata;

    public void initialize(){
        try {
            odabirStudenata.setItems(FXCollections.observableList(StudentRepository.dohvatiStudente()));
            odabirNositeljaPredmeta.setItems(FXCollections.observableList(ProfesorRepository.dohvatiProfesore()));
        } catch (BazaPodatakaException e) {
            throw new RuntimeException(e);
        }

        odabirStudenata.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    public void spremiPredmet(){
        String naziv = nazivPredmetaField.getText().toLowerCase();
        String sifra = sifraPredmetaField.getText().toLowerCase();
        Integer ects = brojEctsBodovaSpinner.getValue();
        Profesor nositelj = odabirNositeljaPredmeta.getValue();
        List<Student> studenti = odabirStudenata.getSelectionModel().getSelectedItems();

        StringBuilder greska = new StringBuilder();

        if(naziv.isBlank()) greska.append("Naziv predmeta nije unesen!\n");
        if(sifra.isBlank()) greska.append("Šifra predmeta nije unesena!\n");
        if(ects < 1) greska.append("Broj ECTS bodova je neispravan!\n");
        if(nositelj == null) greska.append("Nije odabran nositelj predmeta!\n");
        //if(studenti == null) greska.append("Nije odabran niti jedan student!");

        if(!greska.isEmpty()){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Unos predmeta", "Nedostatak informacija", greska.toString());
        }
        else{
            Predmet predmet = new Predmet(sifra, naziv, ects, nositelj);

            if(studenti != null){
                studenti.forEach(predmet::setStudent);
            }

            try{
                PredmetRepository.spremi(predmet);
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos predmeta", "Uspjeh", "Predmet je uspješno unesen!");
            } catch (BazaPodatakaException e) {
                MessageBox.pokazi(Alert.AlertType.ERROR, "Unos predmeta", "Neuspjeh", "Predmet nije uspješno unesen!");
            }
        }
    }
}
