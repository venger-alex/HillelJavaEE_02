package hillelJavaEE_02.pet;

import hillelJavaEE_02.pet.dto.PrescriptionInputDto;
import hillelJavaEE_02.store.NoSuchMedicineException;
import hillelJavaEE_02.util.ErrorBody;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class PetController {
    private final PetService petService;

    @GetMapping("/greeting")
    public String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/pets")
    public List<Pet> getPets(@RequestParam Optional<String> species,
                             @RequestParam Optional<Integer> age) {
        return petService.getPetsUsingSingleJpaMethod(species, age);
    }

    @GetMapping("/pets/{id}")
    public ResponseEntity<?> getPetById(@PathVariable Integer id) {
        Optional<Pet> mayBePet = petService.getById(id);

        return mayBePet.map(Object.class::cast)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest()
                        .body(new ErrorBody("there is no pet with ID = " + id)));
    }

    @PostMapping("/pets")
    public ResponseEntity<Void> createPet(@RequestBody Pet pet) {
        Pet saved = petService.save(pet);
        return ResponseEntity.created(URI.create("/pets/" + saved.getId())).build();
    }

    @PutMapping("/pets/{id}")
    public synchronized void updatePet(@PathVariable Integer id, @RequestBody Pet pet) {
        pet.setId(id);
        petService.save(pet);
    }

    @DeleteMapping("/pets/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable Integer id) {
        petService.delete(id)
                .orElseThrow(NoSuchPetException::new);
    }

    @PostMapping("/pets/{id}/prescriptions")
    public void prescribe(@PathVariable Integer id,
                          @RequestBody PrescriptionInputDto dto) {
        petService.prescribe(id,
                                dto.getDescription(),
                                dto.getMedicineName(),
                                dto.getQuantity(),
                                dto.getTimesPerDay());
    }

    @ExceptionHandler(NoSuchMedicineException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void noSuchMedicine(){}

//    @ExceptionHandler(MyException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public void exceptionHandler(MyException exception) {
//        log.error("Error throws");
//    }
}
