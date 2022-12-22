package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.util.Datoteke;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PregledIspitaController {
    @FXML
    private TextField filterPredmetaField;
    @FXML
    private TextField filterIspitaField;
    @FXML
    private TextField filterStudenataField;

    @FXML
    private TableColumn<Predmet, String> nazivPredmetaColumn;
    @FXML
    private TableColumn<Predmet, String> sifraPredmetaColumn;

    @FXML
    private TableColumn<Ispit, String> idIspitaColumn;

    @FXML
    private TableColumn<Ispit, String> datumIspitaColumn;

    @FXML
    private TableColumn<Student, String> jmbagStudentaColumn;
    @FXML
    private TableColumn<Student, String> imePrezimeStudentaColumn;

    @FXML
    private TableView<Predmet> pregledPredmeta;
    @FXML
    private TableView<Ispit> pregledIspita;
    @FXML
    private TableView<Student> pregledStudenata;
    @FXML
    private TextArea detalji;

    List<Profesor> profesorList;
    List<Predmet> predmetList;
    List<Student> studentList;
    List<Ispit> ispitList;

    public void initialize() {
        profesorList = Datoteke.ucitajProfesore();
        studentList = Datoteke.ucitajStudente();
        predmetList = Datoteke.ucitajPredmete(profesorList, studentList);
        ispitList = Datoteke.ucitajIspitneRokove(predmetList, studentList);

        // predmet
        nazivPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getNaziv()));
        sifraPredmetaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getSifra()));
        pregledPredmeta.setItems(FXCollections.observableList(predmetList));

        // ispit
        idIspitaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getId().toString()));
        datumIspitaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getDatumIVrijeme().format(DateTimeFormatter.ofPattern("dd.MM.yyyy. HH:mm"))));
        pregledIspita.setItems(FXCollections.observableList(ispitList));

        // studenti
        jmbagStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getJmbag()));
        imePrezimeStudentaColumn.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getImeIPrezime()));
        pregledStudenata.setItems(FXCollections.observableList(studentList));
    }

    public void filtrirajIspite(String sifraPredmeta){
        List<Ispit> filtriraniIspiti = ispitList.stream().filter(ispit -> ispit.getPredmet().getSifra().toLowerCase().equals(sifraPredmeta.toLowerCase())).toList();
        pregledIspita.setItems(FXCollections.observableList(filtriraniIspiti));
    }

    public void filtrirajStudente(String jmbagStudenta){
        List<Student> filtriraniStudenti = studentList.stream().filter(student -> student.getJmbag().toLowerCase().startsWith(jmbagStudenta)).toList();
        pregledStudenata.setItems(FXCollections.observableList(filtriraniStudenti));
    }

    public void filtrirajPredmete(String sifraPredmeta){
        List<Predmet> filtriraniPredmeti = predmetList.stream().filter(predmet -> predmet.getSifra().toLowerCase().startsWith(sifraPredmeta.toLowerCase())).toList();
        pregledPredmeta.setItems(FXCollections.observableList(filtriraniPredmeti));
    }

    public void filtriranjePredmetaIzFielda(){
        String unos = filterPredmetaField.getText();
        filtrirajPredmete(unos);
    }

    public void filtrirajIspite(Long id){
        List<Ispit> filtriraniIspiti = ispitList.stream().filter(predmet -> id == null || predmet.getId().equals(id)).toList();
        pregledIspita.setItems(FXCollections.observableList(filtriraniIspiti));
    }

    public void filtriranjeStudenataIzFielda(){
        String unos = filterStudenataField.getText();
        filtrirajStudente(unos);
    }

    public void filtriranjeIspitaIzFielda(){
        Long id = null;
        try{
            id = Long.parseLong(filterIspitaField.getText());
        }
        catch (RuntimeException ex){
            //
        }
        filtrirajIspite(id);
    }

    public void filtrirajIspitePoPredmetu(){
        Predmet odabrani = pregledPredmeta.getSelectionModel().getSelectedItem();
        if(odabrani != null){
            filtrirajIspite(odabrani.getSifra());
        }
    }

    public void filtrirajStudentePoIspitu(){
        Ispit odabrani = pregledIspita.getSelectionModel().getSelectedItem();
        if(odabrani != null){
            filtrirajStudente(odabrani.getStudent().getJmbag());
        }
    }

    public void detaljiPredmet(){
        Predmet odabrani = pregledPredmeta.getSelectionModel().getSelectedItem();
        String tekst = "Predmet " + odabrani.getSifra() + "\n" + odabrani.getNaziv() + "\n" + odabrani.getNositelj().getImeIPrezime();
        detalji.setText(tekst);
    }

    public void detaljiIspit(){
        Ispit odabrani = pregledIspita.getSelectionModel().getSelectedItem();
        String tekst = "Predmet " + odabrani.getId() + "\n" + odabrani.getPredmet().getNaziv() + "\n" + odabrani.getStudent().getImeIPrezime() + "\nocjena: " + odabrani.getOcjena().getSlovnaOznaka();
        detalji.setText(tekst);
    }

    public void detaljiStudent(){
        Student odabrani = pregledStudenata.getSelectionModel().getSelectedItem();
        String tekst = "Student " + odabrani.getJmbag() + "\n" + odabrani.getImeIPrezime() + "\n" + odabrani.getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        detalji.setText(tekst);
    }
}
