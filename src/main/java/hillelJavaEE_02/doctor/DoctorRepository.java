package hillelJavaEE_02.doctor;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class DoctorRepository {
    private Map<Integer, Doctor> doctors = new ConcurrentHashMap<Integer, Doctor>() {{
        put(0, new Doctor(0, "Doc", "surgeon"));
        put(1, new Doctor(1, "Alex", "doctor"));
    }};

    public Collection<Doctor> findAll() {
        return doctors.values();
    }

    public Optional<Doctor> findById(Integer id) {
        return Optional.ofNullable(doctors.get(id));
    }

    public Doctor save(Doctor doctor) {
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
