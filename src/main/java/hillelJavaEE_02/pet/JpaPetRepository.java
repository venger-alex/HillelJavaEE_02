package hillelJavaEE_02.pet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findById(Integer id);
}
