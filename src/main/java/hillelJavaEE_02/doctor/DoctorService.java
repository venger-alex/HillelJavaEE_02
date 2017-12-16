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
    private final JpaDoctorRepository doctorRepository;
    private final DoctorConfig doctorConfig;

    public List<String> getSpecializations() {
        return doctorConfig.getSpecializations();
    }

    public List<Doctor> getDoctors(Optional<String> specialization,
                                   Optional<String> name) {

        return doctorRepository.findNullableBySpecializationAndName(specialization.orElse(null),
                                                                        name.orElse(null));
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
        Optional<Doctor> mayBeDoctor = doctorRepository.findById(id);

        mayBeDoctor.ifPresent(doctor -> doctorRepository.delete(doctor.getId()));

        return mayBeDoctor;
    }
}
