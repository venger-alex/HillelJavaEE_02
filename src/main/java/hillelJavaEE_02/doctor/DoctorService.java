package hillelJavaEE_02.doctor;

import hillelJavaEE_02.doctor.util.NoSuchSpecializationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final JpaDoctorRepository doctorRepository;
    private final DoctorConfig doctorConfig;
    private final SpecializationService specializationService;

    public List<String> getSpecializations() {
        return doctorConfig.getSpecializations();
    }

    public List<Doctor> getDoctors(Optional<List<String>> specializations,
                                   Optional<String> name) {

        List<Specialization> listSpecializations = new ArrayList<>();
        if(specializations.isPresent()) {
            listSpecializations = specializations.get().stream()
                    .map(specializationName -> specializationService.findByName(specializationName))
                    .filter(mayBeSpec -> mayBeSpec.isPresent())
                    .map(spec -> spec.get())
                    .collect(Collectors.toList());
        }

        if(!listSpecializations.isEmpty() && name.isPresent()) {
            return doctorRepository.findBySpecializationsInAndNameStartingWithIgnoreCase(listSpecializations, name.get());
        } else if(!listSpecializations.isEmpty()) {
            return doctorRepository.findBySpecializationsIn(listSpecializations);
        } else if(name.isPresent()) {
            return doctorRepository.findByNameStartingWithIgnoreCase(name.get());
        }
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getById(Integer id) {
        return doctorRepository.findById(id);
    }

    public Doctor save(Doctor doctor) {
        if(!this.getSpecializations().contains(doctor.getSpecializations())) {
            throw new NoSuchSpecializationException("The specialty should be from the list in the configuration: " + doctor.getSpecializations());
        }

        return doctorRepository.save(doctor);
    }

    public Optional<Doctor> delete(Integer id) {
        Optional<Doctor> mayBeDoctor = doctorRepository.findById(id);

        mayBeDoctor.ifPresent(doctor -> doctorRepository.delete(doctor.getId()));

        return mayBeDoctor;
    }
}
