package hr.java.vjezbe.controller;

import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.entitet.StudentBuilder;
import hr.java.vjezbe.util.Datoteke;
import hr.java.vjezbe.util.MessageBox;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ImportExportStudentController {
    @FXML
    private TextArea area;
    public void exportStudenata(){
        List<Student> studentiIzListe = Datoteke.ucitajStudente();

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(Student.NAZIV_DATOTEKE))){
            for(Student student : studentiIzListe){
                writer.write(student.getId().toString() + "\n");
                writer.write(student.getIme()+ "\n");
                writer.write(student.getPrezime()+ "\n");
                writer.write(student.getDatumRodjenja().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))+ "\n");
                writer.write(student.getJmbag()+ "\n");
                writer.write(student.getOcjenaZavrsni() + "\n");
                writer.write(student.getOcjenaObrana() + "\n");
                writer.write(student.getGdjeZivi() + "\n");
                writer.write(student.getPrehrana() + "\n");
                writer.write(student.getTipStudenta() + "\n");
            }

            MessageBox.pokazi(Alert.AlertType.INFORMATION, "Export", "Uspjeh", "Export studentata uspješan!");
        } catch (IOException e) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Export", "Neuspjeh", "Export studentata neuspješan!");
        }
    }

    public void importStudenata(){
        try(BufferedReader reader = new BufferedReader(new FileReader(Student.NAZIV_DATOTEKE))){
            List<String> zapisi = reader.lines().toList();
            StringBuilder str = new StringBuilder();

            for(int i = 0; i < zapisi.size(); i += Student.BROJ_ZAPISA_U_DATOTEKAMA){
                String ime = zapisi.get(i+1);
                String prezime = zapisi.get(i+2);
                String jmbag = zapisi.get(i+4);

                String gdjeZivi = zapisi.get(i+7);
                String prehrana = zapisi.get(i+8).replace(";", " ");
                String tipStudenta = zapisi.get(i+9);

                str.append(ime).append(" ").append(prezime).append(", ")
                        .append(jmbag).append(", ").append(tipStudenta).append(", ")
                        .append("živi: ").append(gdjeZivi).append(", ")
                        .append("prehrana: ").append(prehrana)
                        .append("\n");
            }

            area.setText(str.toString());
            MessageBox.pokazi(Alert.AlertType.INFORMATION, "Import", "Uspjeh", "Import studentata uspješan!");
        } catch (IOException e) {
            MessageBox.pokazi(Alert.AlertType.ERROR, "Export", "Uspjeh", "Export studentata neuspješan!");
        }
    }
}
