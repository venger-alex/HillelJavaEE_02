package hillelJavaEE_02.doctor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final JpaSpecializationRepository specializationRepository;

    public Optional<Specialization> findByName(String name) {
        SpecializationsName specializationsName = null;
        try {
            specializationsName = SpecializationsName.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException err) {
            return Optional.empty();
        }
        return specializationRepository.findByName(specializationsName);
    }
}
