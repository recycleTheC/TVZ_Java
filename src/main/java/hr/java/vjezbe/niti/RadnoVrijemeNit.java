package hr.java.vjezbe.niti;

import hr.java.vjezbe.glavna.MainApplication;
import hr.java.vjezbe.util.MessageBox;
import javafx.scene.control.Alert;

import java.time.LocalTime;

public class RadnoVrijemeNit implements Runnable {
    @Override
    public void run() {
        if(LocalTime.now().getHour() >= MainApplication.radnoVrijemeH){
            if(LocalTime.now().getMinute() >= MainApplication.radnoVrijemeM){
                MessageBox.pokazi(Alert.AlertType.WARNING, "Rad", "Radno vrijeme", "Radno vrijeme je završilo, odmah se odjavite!");
            }
        }
    }
}
