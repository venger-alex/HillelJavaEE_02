package hillelJavaEE_02.pet;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import hillelJavaEE_02.store.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {
    private final JpaPetRepository petRepository;
    private final StoreService storeService;

    @Transactional
    public List<Pet> getPetsUsingSingleJpaMethod(Optional<String> species, Optional<Integer> age) {
        List<Pet> nullableBySpeciesAndAge = petRepository.findNullableBySpeciesAndAge(species.orElse(null),
                                                                                        age.orElse(null));

        nullableBySpeciesAndAge.forEach(pet -> System.out.println(pet.getPrescriptions()));

        return nullableBySpeciesAndAge;
    }

    public List<Pet> getPetsUsingSeparateJpaMethods(Optional<String> species, Optional<Integer> age) {
        if (species.isPresent() && age.isPresent()) {
            return petRepository.findBySpeciesAndAge(species.get(), age.get());
        }
        if (species.isPresent()) {
            return petRepository.findBySpecies(species.get());
        }
        if (age.isPresent()) {
            return petRepository.findByAge(age.get());
        }
        return petRepository.findAll();

    }

    public List<Pet> getPetsUsingStreamFilters(Optional<String> species, Optional<Integer> age) {
        Predicate<Pet> specieFilter = species.map(this::filterByScpecies)
                .orElse(pet -> true);

        Predicate<Pet> ageFilter = age.map(this::filterByAge)
                .orElse(pet -> true);

        Predicate<Pet> complexFilter = ageFilter.and(specieFilter);

        return petRepository.findAll().stream()
                .filter(complexFilter)
                .collect(Collectors.toList());
    }

    private Predicate<Pet> filterByAge(Integer age) {
        return pet -> pet.getAge().equals(age);
    }

    private Predicate<Pet> filterByScpecies(String species) {
        return pet -> pet.getSpecies().equals(species);
    }

    public Optional<Pet> getById(Integer id) {
        return petRepository.findById(id);
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Optional<Pet> delete(Integer id) {
        Optional<Pet> mayBePet = petRepository.findById(id);
        mayBePet.ifPresent(pet -> petRepository.delete(pet.getId()));
        //mayBePet.map(Pet::getId).ifPresent(petRepository::delete);
        return mayBePet;
    }

    @Transactional
    @Retryable(ObjectOptimisticLockingFailureException.class)
    public void prescribe(Integer petId,
                          String description,
                          String medicineName,
                          Integer quantity,
                          Integer timesPerDay) {
        Pet pet = petRepository.findById(petId).orElseThrow(RuntimeException::new);

        pet.getPrescriptions().add(new Prescription(description, LocalDate.now(), timesPerDay, MedicineType.PERORAL));

        petRepository.save(pet);

        storeService.decrement(medicineName, quantity);
    }
}
