package hillelJavaEE_02.doctor;

import hillelJavaEE_02.doctor.util.NoSuchSpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final DoctorConfig doctorConfig;

    public List<String> getSpecializations() {
        return doctorConfig.getSpecializations();
    }

    public List<Doctor> getDoctors(Optional<String> specialization,
                                   Optional<String> name) {

        Predicate<Doctor> filterBySpecialization = specialization.map(this::filterBySpecialization)
                .orElse(doctor -> true);
        Predicate<Doctor> filterByName = name.map(this::filterByName).orElse(doctor -> true);
        Predicate<Doctor> complexFilter = filterBySpecialization.and(filterByName);

        return doctorRepository.findAll().stream().filter(complexFilter).collect(Collectors.toList());
    }

    private Predicate<Doctor> filterByName(String name) {
        return doctor -> doctor.getName().startsWith(name);
    }

    private Predicate<Doctor> filterBySpecialization(String specialization) {
        return doctor -> doctor.getSpecialization().equals(specialization);
    }

    public Optional<Doctor> getById(Integer id) {
        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {
        if(!this.getSpecializations().contains(doctor.getSpecialization())) {
            throw new NoSuchSpecializationException("The specialty should be from the list in the configuration: " + doctor.getSpecialization());
        }

        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> delete(Integer id) {
        return doctorRepository.delete(id);
    }
}
