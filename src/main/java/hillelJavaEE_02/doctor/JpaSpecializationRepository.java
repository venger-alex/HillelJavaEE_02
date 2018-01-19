package hillelJavaEE_02.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSpecializationRepository extends JpaRepository<Specialization, Integer> {
    Optional<Specialization> findByName(SpecializationsName name);
}
