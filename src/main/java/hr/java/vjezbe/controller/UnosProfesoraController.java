package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.ProfesorRepository;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class UnosProfesoraController {
    @FXML
    private TextField imeProfesoraField;
    @FXML
    private TextField prezimeProfesoraField;
    @FXML
    private TextField sifraProfesoraField;
    @FXML
    private TextField titulaProfesoraField;

    public void spremiProfesora(){
        String sifra = sifraProfesoraField.getText();
        String ime = imeProfesoraField.getText();
        String prezime = prezimeProfesoraField.getText();
        String titula = titulaProfesoraField.getText();

        StringBuilder greska = new StringBuilder();

        if(sifra.isBlank()) greska.append("Šifra profesora nije unesena!\n");
        if(ime.isBlank()) greska.append("Ime profesora nije uneseno!\n");
        if(prezime.isBlank()) greska.append("Prezime profesora nije unesno!\n");
        if(titula.isBlank()) greska.append("Titula profesora nije unesena!\n");

        if(!greska.isEmpty()){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Unos profesora", "Nedostaju vrijednosti", greska.toString());
        }
        else{
            try{
                ProfesorRepository.spremi(new Profesor(sifra, ime, prezime, titula));
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos profesora", "Uspješan unos", "Profesor " + ime + " " + prezime + " uspješno dodan!");
            } catch (BazaPodatakaException e) {
                MessageBox.pokazi(Alert.AlertType.ERROR, "Unos profesora", "Neuspješan unos", "Profesor " + ime + " " + prezime + " nije dodan u bazu!\n" + e.getCause().getMessage());
            }
        }
    }
}
