package hillelJavaEE_02.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaPetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findById(Integer id);

    List<Pet> findBySpeciesAndAge(String species, Integer age);

    List<Pet> findBySpecies(String species);

    List<Pet> findByAge(Integer age);

    @Query("SELECT pet FROM Pet AS pet " +
            "JOIN FETCH pet.prescriptions " +
            "JOIN FETCH pet.medicalCard " +
            "WHERE (pet.species = :species OR :species IS NULL) " +
            "   AND (pet.age = :age OR :age IS NULL) ")
    List<Pet> findNullableBySpeciesAndAge(@Param("species") String species, @Param("age") Integer age);
}
