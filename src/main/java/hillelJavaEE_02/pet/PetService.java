package hillelJavaEE_02.pet;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PetService {
    private final JpaPetRepository petRepository;

    public List<Pet> getPetsUsingSingleJpaMethod(Optional<String> species, Optional<Integer> age) {
        return petRepository.findNullableBySpeciesAndAge(species.orElse(null), age.orElse(null));
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
}
