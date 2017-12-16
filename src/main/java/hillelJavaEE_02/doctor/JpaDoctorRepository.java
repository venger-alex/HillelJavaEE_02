package hillelJavaEE_02.doctor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JpaDoctorRepository extends JpaRepository<Doctor, Integer> {
    Optional<Doctor> findById(Integer id);

    @Query("SELECT doctor FROM Doctor AS doctor " +
            "WHERE (doctor.specialization = :specialization OR :specialization IS NULL)" +
            "  AND (doctor.name = :name OR :name IS NULL ) ")
    List<Doctor> findNullableBySpecializationAndName(@Param("specialization") String specialization,
                                                     @Param("name") String name);
}
