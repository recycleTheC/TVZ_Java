package hr.java.vjezbe.util;

import javafx.scene.control.Alert;

public class MessageBox {
    public static void  pokazi(Alert.AlertType tip, String naslov, String zaglavlje, String poruka){
        Alert alert = new Alert(tip);
        alert.setTitle(naslov);
        alert.setHeaderText(zaglavlje);
        alert.setContentText(poruka);
        alert.show();
    }
}
