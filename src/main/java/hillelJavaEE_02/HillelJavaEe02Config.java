package hillelJavaEE_02;


import hillelJavaEE_02.doctor.Doctor;
import hillelJavaEE_02.doctor.JpaDoctorRepository;
import hillelJavaEE_02.pet.*;
import hillelJavaEE_02.store.Medicine;
import hillelJavaEE_02.store.MedicineRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class HillelJavaEe02Config {

//    @Bean
//    PetService petService(JpaPetRepository petRepository){
//        return new PetService(petRepository);
//    }

    @Bean
    CommandLineRunner initDb(JpaPetRepository repository) {
        return args -> {
            if(!repository.findAll().isEmpty()) {
                return;
            }

            List<Prescription> tomsPrescriptions = new ArrayList<>();
            tomsPrescriptions.add(new Prescription("paracetamol", LocalDate.now(), 3, MedicineType.PERORAL));
            tomsPrescriptions.add(new Prescription("asperin", LocalDate.now(), 2, MedicineType.PERORAL));
            List<Prescription> jerriesPrescriptions = new ArrayList<>();
            jerriesPrescriptions.add(new Prescription("asperin", LocalDate.now(), 4, MedicineType.PERORAL));

            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-bla");
            MedicalCard jerriesCard = new MedicalCard(LocalDate.now(), "foo-bar");
            repository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), tomsCard, tomsPrescriptions));
            repository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), jerriesCard, jerriesPrescriptions));
        };
    }

    @Bean
    CommandLineRunner initDoctorDb(JpaDoctorRepository repository) {
        return args -> {
            if(!repository.findAll().isEmpty()) {
                return;
            }

            repository.save(new Doctor("Alex", "surgeon"));
            repository.save(new Doctor("Bob", "therapeut"));
        };
    }

    @Bean
    CommandLineRunner initMedicineStore(MedicineRepository repository) {
        return args -> {
            if(!repository.findAll().isEmpty()) {
                return;
            }

            repository.save(new Medicine("Brilliant green", 1));

        };
    }


}
