package hr.java.vjezbe.controller;

import hr.java.vjezbe.glavna.MainApplication;
import hr.java.vjezbe.util.MessageBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;

public class RadnoVrijemeController {
    @FXML
    private Spinner<Integer> vrijemeH;
    @FXML
    private Spinner<Integer> vrijemeMin;

    public void initialize(){
        vrijemeH.getValueFactory().setValue(MainApplication.radnoVrijemeH);
        vrijemeMin.getValueFactory().setValue(MainApplication.radnoVrijemeM);
    }

    public void postavi(){
        MainApplication.radnoVrijemeH = vrijemeH.getValue();
        MainApplication.radnoVrijemeM = vrijemeMin.getValue();

        MessageBox.pokazi(Alert.AlertType.INFORMATION, "Radno vrijeme", "Radno vrijeme", "Spremljeno!");
    }
}
