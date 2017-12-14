package hillelJavaEE_02.doctor;

import hillelJavaEE_02.doctor.util.NoSuchSpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Repository
@RequiredArgsConstructor
public class DoctorRepository {
    private Map<Integer, Doctor> doctors = new ConcurrentHashMap<Integer, Doctor>() {{
        put(0, new Doctor(0, "Doc", "surgeon"));
        put(1, new Doctor(1, "Alex", "doctor"));
    }};

    private final DoctorConfig doctorConfig;

    public List<String> findAllSpecialization() {
        return doctorConfig.getList();
    }

    public Collection<Doctor> findAll() {
        return doctors.values();
    }

    public Optional<Doctor> findById(Integer id) {
        return Optional.ofNullable(doctors.get(id));
    }

    public Doctor save(Doctor doctor) {
        if(!this.findAllSpecialization().contains(doctor.getSpecialization())) {
            throw new NoSuchSpecializationException("The specialty should be from the list in the configuration: " + doctor.getSpecialization());
        }

        if (doctor.getId() == null) {
            doctor.setId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        }
        doctors.put(doctor.getId(), doctor);
        return doctor;
    }

    public Optional<Doctor> delete(Integer id) {
        return Optional.ofNullable(doctors.remove(id));
    }
}
