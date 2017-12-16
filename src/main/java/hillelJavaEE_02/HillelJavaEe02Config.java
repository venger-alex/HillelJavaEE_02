package hillelJavaEE_02;


import hillelJavaEE_02.doctor.Doctor;
import hillelJavaEE_02.doctor.JpaDoctorRepository;
import hillelJavaEE_02.pet.JpaPetRepository;
import hillelJavaEE_02.pet.Pet;
import hillelJavaEE_02.pet.PetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

            repository.save(new Pet("Tom", "Cat", 3));
            repository.save(new Pet("Jerry", "Mouse", 1));
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
