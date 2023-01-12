package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import hr.java.vjezbe.util.MessageBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

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
    @FXML
    private CheckBox menzaBox;
    @FXML
    private CheckBox restoranBox;
    @FXML
    private CheckBox pekaraBox;
    @FXML
    private CheckBox vlastitaHranaBox;
    @FXML
    private RadioButton redovniButton, izvanredniButton;
    @FXML
    private ChoiceBox<String> gdjeZiviBox;

    public void initialize(){
        gdjeZiviBox.setItems(FXCollections.observableList(List.of("kod skrbnika", "u studentskom domu", "u vlastitom stanu", "u unajmljenom stanu")));
    }
    public void spremiStudenta(){
        String ime = imeStudentaField.getText();
        String prezime = prezimeStudentaField.getText();
        String jmbag = jmbagStudentaField.getText();
        LocalDate datumRodjenja = datumRodjenjaStudentaField.getValue();
        int zavrsni = 0, obrana = 0;

        String tipStudenta = "";
        if(izvanredniButton.isSelected()) tipStudenta = "izvanredni";
        else if(redovniButton.isSelected()) tipStudenta = "redovni";

        String gdjeZivi = gdjeZiviBox.getValue();

        String prehrana = "";
        if(menzaBox.isSelected()) prehrana += "menza;";
        if(restoranBox.isSelected()) prehrana += "restoran;";
        if(pekaraBox.isSelected()) prehrana += "pekara;";
        if(vlastitaHranaBox.isSelected()) prehrana += "vlastita hrana;";

        StringBuilder greska = new StringBuilder();

        if(ime.isBlank()) greska.append("Ime studenta nije uneseno!\n");
        if(prezime.isBlank()) greska.append("Prezime studenta nije unesno!\n");
        if(jmbag.isBlank()) greska.append("JMBAG studenta nije unesen!\n");
        if(datumRodjenja == null) greska.append("Datum rođenja studenta nije unesen!\n");

        if(prehrana.isBlank()) greska.append("Nije odabran način kako se student hrani za vrijeme studija!\n");
        if(gdjeZivi == null) greska.append("Nije odabrano gdje student živi za vrijeme studija!\n");
        if(tipStudenta.isBlank()) greska.append("Nije odabran tip studenta!");

        if(zavrsniField.getText().isBlank()) greska.append("Ocjena završnog rada nije unesena!\n");
        else zavrsni = Integer.parseInt(zavrsniField.getText());

        if(obranaField.getText().isBlank()) greska.append("Ocjena obrane završnog rada nije unesena!\n");
        else obrana = Integer.parseInt(obranaField.getText());

        if(!greska.isEmpty()){
            MessageBox.pokazi(Alert.AlertType.ERROR, "Unos studenta", "Nedostaju vrijednosti", greska.toString());
        }
        else{
            Student student = new Student(Datoteke.maxIdStudenta().getAsLong() + 1, ime, prezime, jmbag, datumRodjenja, zavrsni, obrana);
            student.setPrehrana(prehrana);
            student.setTipStudenta(tipStudenta);
            student.setGdjeZivi(gdjeZivi);

            Datoteke.unosStudenta(student);
            MessageBox.pokazi(Alert.AlertType.INFORMATION, "Unos studenta", "Uspješan unos", "Student " + ime + " " + prezime + " uspješno dodan!");
        }
    }
}
