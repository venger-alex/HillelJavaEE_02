package hillelJavaEE_02.doctor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findById(Integer id);
}
