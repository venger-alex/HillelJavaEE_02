package hillelJavaEE_02;


import hillelJavaEE_02.pet.PetRepository;
import hillelJavaEE_02.pet.PetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HillelJavaEe02Config {

    @Bean
    PetService petService(PetRepository petRepository){
        return new PetService(petRepository);
    }
}
