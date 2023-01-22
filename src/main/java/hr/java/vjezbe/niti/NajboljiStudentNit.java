package hr.java.vjezbe.niti;

import hr.java.vjezbe.database.IspitRepository;
import hr.java.vjezbe.database.StudentRepository;
import hr.java.vjezbe.entitet.Ispit;
import hr.java.vjezbe.entitet.Student;
import hr.java.vjezbe.glavna.MainApplication;
import hr.java.vjezbe.iznimke.BazaPodatakaException;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class NajboljiStudentNit implements Runnable {

    @Override
    public void run() {
        try {
            List<Student> studenti = StudentRepository.dohvatiStudente();
            Student najbolji = null;
            Double najbolji_prosjek = null;

            for(Student student : studenti){
                List<Ispit> ispiti = IspitRepository.dohvatiIspite(new Ispit(null, student, null, null));
                OptionalDouble prosjek = ispiti.stream().mapToDouble(ispit -> ispit.getOcjena().getBrojcanaOznaka()).average();

                if(prosjek.isPresent()){
                    if(Optional.ofNullable(najbolji_prosjek).isEmpty()){
                        najbolji = student;
                        najbolji_prosjek = prosjek.getAsDouble();
                    }
                    else if (prosjek.getAsDouble() > najbolji_prosjek){
                        najbolji = student;
                        najbolji_prosjek = prosjek.getAsDouble();
                    }
                }
            }

            if(Optional.ofNullable(najbolji).isPresent()){
                MainApplication.getMainStage().setTitle("Najbolji student: " + najbolji.getImeIPrezime() + "(" + najbolji_prosjek + ")");
            }
            else{
                MainApplication.getMainStage().setTitle("Najbolji student nije određen!" );
            }
        } catch (BazaPodatakaException e) {
            e.printStackTrace();
        }
    }
}
