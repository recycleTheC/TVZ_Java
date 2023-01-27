package hr.java.vjezbe.glavna;

import hr.java.vjezbe.controller.MainController;
import hr.java.vjezbe.niti.DatumRodjenjaNit;
import hr.java.vjezbe.niti.NajboljiStudentNit;
import hr.java.vjezbe.niti.NajstarijiStudent;
import hr.java.vjezbe.niti.RadnoVrijemeNit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class MainApplication extends Application {
    private static Stage mainStage;
    public static Integer radnoVrijemeH = 17, radnoVrijemeM = 25;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Laboratorijske vježbe - Programiranje u jeziku Java - (c) Mario Kopjar 2022.");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Timeline prikazSlavljenika = new Timeline(new KeyFrame(Duration.seconds(10), event -> Platform.runLater(new DatumRodjenjaNit())));
        prikazSlavljenika.setCycleCount(Timeline.INDEFINITE);
        prikazSlavljenika.play();

        Timeline prikazNajboljeg = new Timeline(new KeyFrame(Duration.seconds(3), event -> Platform.runLater(new NajboljiStudentNit())));
        prikazNajboljeg.setCycleCount(Timeline.INDEFINITE);
        prikazNajboljeg.play();

        Timeline prikazNajstarijeg = new Timeline(new KeyFrame(Duration.seconds(5), event -> Platform.runLater(new NajstarijiStudent())));
        prikazNajstarijeg.setCycleCount(Timeline.INDEFINITE);
        prikazNajstarijeg.play();

        Timeline prikazRadnog = new Timeline(new KeyFrame(Duration.seconds(5), event -> Platform.runLater(new RadnoVrijemeNit())));
        prikazRadnog.setCycleCount(Timeline.INDEFINITE);
        prikazRadnog.play();

        launch();
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}