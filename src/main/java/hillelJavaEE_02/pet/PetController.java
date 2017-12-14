package hillelJavaEE_02.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class PetController {
    private Map<Integer, Pet> pets = new HashMap<Integer, Pet>() {{
        put(0, new Pet("Tom", "Cat", 3));
        put(1, new Pet("Jerry", "Mouse", 1));
    }};

    private AtomicInteger counter = new AtomicInteger(1);

    //@RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping("/greeting")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam Optional<String> species,
                             @RequestParam Optional<Integer> age) {
        Predicate<Pet> speciesFilter = species.map(this::filterBySpecies).orElse(pet -> true);
        Predicate<Pet> ageFilter = age.map(this::filterByAge).orElse(pet -> true);
        Predicate<Pet> complexFilter = speciesFilter.and(ageFilter);

        return pets.values().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        if(id >= pets.size()) {
            return ResponseEntity
                    .notFound().build();
                    //.badRequest().body(new ErrorBody("There is no pet with ID = " + id));
        }

        return ResponseEntity.ok(pets.get(id));
    }

    @PostMapping("/pets")
    public ResponseEntity<Void> createPet(@RequestBody Pet pet) {
        Integer id = counter.incrementAndGet();
        pets.put(id, pet);
        return ResponseEntity.created(URI.create("/pets/" + id)).build();
    }

    @PutMapping("/pets/{id}")
    public void updatePet(@PathVariable Integer id, @RequestBody Pet pet) {
        pets.put(id, pet);
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {
        if(!pets.containsKey(id)) {
            throw new NoSuchPetException();
        }
        pets.remove(id);
    }

    private Predicate<Pet> filterBySpecies(String species) {
        return pet -> pet.getSpecies().equals(species);
    }
}

@ResponseStatus(HttpStatus.BAD_REQUEST)
class NoSuchPetException extends RuntimeException {

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