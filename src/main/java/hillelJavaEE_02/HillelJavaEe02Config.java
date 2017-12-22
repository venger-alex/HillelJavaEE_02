package hillelJavaEE_02;


import hillelJavaEE_02.doctor.Doctor;
import hillelJavaEE_02.doctor.JpaDoctorRepository;
import hillelJavaEE_02.pet.JpaPetRepository;
import hillelJavaEE_02.pet.MedicalCard;
import hillelJavaEE_02.pet.Pet;
import hillelJavaEE_02.pet.PetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class HillelJavaEe02Config {

    @Bean
    PetService petService(JpaPetRepository petRepository){
        return new PetService(petRepository);
    }

    @Bean
    CommandLineRunner initDb(JpaPetRepository repository) {
        return args -> {
            if(!repository.findAll().isEmpty()) {
                return;
            }

            MedicalCard tomsCard = new MedicalCard(LocalDate.now(), "bla-bla");
            MedicalCard jerriesCard = new MedicalCard(LocalDate.now(), "foo-bar");
            repository.save(new Pet("Tom", "Cat", 3, LocalDate.now(), tomsCard));
            repository.save(new Pet("Jerry", "Mouse", 1, LocalDate.now(), jerriesCard));
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

}
