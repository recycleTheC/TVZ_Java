package hr.java.vjezbe.controller;

import hr.java.vjezbe.controller.PretragaStudenataController;
import hr.java.vjezbe.glavna.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {
    public void openPretragaStudenataView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PretragaStudenataController.class.getResource("pretraga-studenata-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Pretraga studenata - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void openPretragaProfesoraView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PretragaProfesoraController.class.getResource("pretraga-profesora-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Pretraga profesora - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }
    public void openPretragaPredmetaView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PretragaProfesoraController.class.getResource("pretraga-predmeta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Pretraga predmeta - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void openPretragaIspitaView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PretragaProfesoraController.class.getResource("pretraga-ispita-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Pretraga ispita - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void openUnosProfesoraView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UnosProfesoraController.class.getResource("unos-profesora-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Unos profesora - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void openUnosStudenataView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UnosStudentaController.class.getResource("unos-studenata-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Unos studenata - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void openUnosPredmetaView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UnosPredmetaController.class.getResource("unos-predmeta-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Unos predmeta - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }

    public void openUnosIspitaView() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UnosIspitaController.class.getResource("unos-ispita-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainApplication.getMainStage().setTitle("Unos ispita - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        MainApplication.getMainStage().setScene(scene);
        MainApplication.getMainStage().show();
    }
}
