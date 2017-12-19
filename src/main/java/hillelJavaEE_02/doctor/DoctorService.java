package hillelJavaEE_02.doctor;

import hillelJavaEE_02.doctor.util.NoSuchSpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final JpaDoctorRepository doctorRepository;
    private final DoctorConfig doctorConfig;

    public List<String> getSpecializations() {
        return doctorConfig.getSpecializations();
    }

    public List<Doctor> getDoctors(Optional<List<String>> specializations,
                                   Optional<String> name) {
        if(specializations.isPresent() && name.isPresent()) {
            return doctorRepository.findBySpecializationInAndNameStartingWithIgnoreCase(specializations.get(), name.get());
        } else if(specializations.isPresent()) {
            return doctorRepository.findBySpecializationIn(specializations.get());
        } else if(name.isPresent()) {
            return doctorRepository.findByNameStartingWithIgnoreCase(name.get());
        }
        return doctorRepository.findAll();
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
