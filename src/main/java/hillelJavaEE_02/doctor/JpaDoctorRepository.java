package hillelJavaEE_02.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findById(Integer id);

    List<Doctor> findByNameStartingWithIgnoreCase(String name);

    List<Doctor> findBySpecializationsIn(Collection<Specialization> specializations);

    List<Doctor> findBySpecializationsInAndNameStartingWithIgnoreCase(Collection<Specialization> specializations, String name);
}
