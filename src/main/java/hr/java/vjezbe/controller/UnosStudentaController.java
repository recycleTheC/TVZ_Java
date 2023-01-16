package hr.java.vjezbe.controller;

import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.iznimke.BazaPodatakaException;
import hr.java.vjezbe.util.MessageBox;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class UnosStudentaController {
    @FXML
    private TextField imeStudentaField;
    @FXML
    private TextField prezimeStudentaField;
    @FXML
    private TextField jmbagStudentaField;
    @FXML
    private TextField zavrsniField;
    @FXML
    private TextField obranaField;
    @FXML
    private DatePicker datumRodjenjaStudentaField;
    public void spremiStudenta(){
        String ime = imeStudentaField.getText();
        String prezime = prezimeStudentaField.getText();
        String jmbag = jmbagStudentaField.getText();
        LocalDate datumRodjenja = datumRodjenjaStudentaField.getValue();
        //int zavrsni = 0, obrana = 0;

        StringBuilder greska = new StringBuilder();

        if(ime.isBlank()) greska.append("Ime studenta nije uneseno!\n");
        if(prezime.isBlank()) greska.append("Prezime studenta nije unesno!\n");
        if(jmbag.isBlank()) greska.append("JMBAG studenta nije unesen!\n");
        if(datumRodjenja == null) greska.append("Datum rođenja studenta nije unesen!\n");

        /*if(zavrsniField.getText().isBlank()) greska.append("Ocjena završnog rada nije unesena!\n");
        else zavrsni = Integer.parseInt(zavrsniField.getText());

        if(obranaField.getText().isBlank()) greska.append("Ocjena obrane završnog rada nije unesena!\n");
        else obrana = Integer.parseInt(obranaField.getText());*/

        if(!greska.isEmpty()){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Unos studenta", "Nedostaju vrijednosti", greska.toString());
        }
        else{
            try{
                StudentRepository.spremi(new Student(ime, prezime, jmbag, datumRodjenja));
                MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos studenta", "Uspješan unos", "Student " + ime + " " + prezime + " uspješno dodan!");
            } catch (BazaPodatakaException e) {
                MessageBox.pokazi(Alert.AlertType.ERROR, "Unos studenta", "Neuspješan unos", "Student " + ime + " " + prezime + " nije dodan u bazu!\n" + e.getCause().getMessage());
            }
        }
    }
}
