package hillelJavaEE_02.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findById(Integer id);

    List<Doctor> findByNameStartingWithIgnoreCase(String name);

    List<Doctor> findBySpecializationIn(Collection<String> specializations);

    List<Doctor> findBySpecializationInAndNameStartingWithIgnoreCase(Collection<String> specializations, String name);
}
