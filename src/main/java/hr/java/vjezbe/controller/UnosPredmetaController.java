package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.util.Datoteke;
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

    public void initialize(){
        List<Profesor> profesori = Datoteke.ucitajProfesore();

        odabirNositeljaPredmeta.setItems(FXCollections.observableList(profesori));
    }
    public void spremiPredmet(){
        String naziv = nazivPredmetaField.getText().toLowerCase();
        String sifra = sifraPredmetaField.getText().toLowerCase();
        Integer ects = brojEctsBodovaSpinner.getValue();
        Profesor nositelj = odabirNositeljaPredmeta.getValue();

        StringBuilder greska = new StringBuilder();

        if(naziv.isBlank()) greska.append("Naziv predmeta nije unesen!\n");
        if(sifra.isBlank()) greska.append("Šifra predmeta nije unesena!\n");
        if(ects < 1) greska.append("Broj ECTS bodova je neispravan!\n");
        if(nositelj == null) greska.append("Nije odabran nositelj predmeta!\n");

        if(!greska.isEmpty()){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Unos predmeta", "Nedostatak informacija", greska.toString());
        }
        else{
            Predmet predmet = new Predmet(Datoteke.maxIdPredmeta().getAsLong() + 1, sifra, naziv, ects, nositelj);
            Datoteke.unosPredmeta(predmet);
            MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos predmeta", "Uspjeh", "Predmet je uspješno unesen!");
        }
    }
}
