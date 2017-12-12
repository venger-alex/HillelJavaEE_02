package hillelJavaEE_02.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class PetController {
    private List<Pet> pets = new ArrayList<Pet>() {{
        add(new Pet("Tom", "Cat", 3));
        add(new Pet("Jerry", "Mouse", 1));
    }};

    //@RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping("/greeting")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam Optional<String> species) {
        Predicate<Pet> speciesFilter = species.map(this::filterBySpecies).orElse(pet -> true);

        return pets.stream()
                .filter(speciesFilter)
                .collect(Collectors.toList());
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        if(id >= pets.size()) {
            return ResponseEntity
                    //.notFound().build()
                    .badRequest().body(new ErrorBody("There is no pet with ID = " + id));
        }

        return ResponseEntity.ok(pets.get(id));
    }

    @PostMapping("/pets")
    public void createPet(@RequestBody Pet pet) {
        pets.add(pet);
    }

    @PutMapping("/pets/{id}")
    public void updatePet(@PathVariable Integer id, @RequestBody Pet pet) {
        pets.set(id, pet);
    }

    private Predicate<Pet> filterBySpecies(String species) {
        return pet -> pet.getSpecies().equals(species);
    }
}

@Data
@AllArgsConstructor
class ErrorBody {
    private final Integer code = 400;
    private String msg;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Pet {
    private String name;
    private String species;
    private Integer age;
}