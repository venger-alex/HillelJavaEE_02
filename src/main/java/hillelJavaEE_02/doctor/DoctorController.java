package hillelJavaEE_02.doctor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DoctorController {
    private Map<Integer, Doctor> doctors = new ConcurrentHashMap<Integer, Doctor>() {{
        put(0, new Doctor(0, "Doc", "surgeon"));
        put(1, new Doctor(1, "Alex", "doctor"));
    }};

    private AtomicInteger counter = new AtomicInteger(1);

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<String> specialization,
                                   @RequestParam Optional<String> name) {

        Predicate<Doctor> filterBySpecialization = specialization.map(this::filterBySpecialization)
                .orElse(doctor -> true);
        Predicate<Doctor> filterByName = name.map(this::filterByName).orElse(doctor -> true);
        Predicate<Doctor> complexFilter = filterBySpecialization.and(filterByName);

        return doctors.values().stream().filter(complexFilter).collect(Collectors.toList());
    }

    private Predicate<Doctor> filterByName(String name) {
        return doctor -> doctor.getName().startsWith(name);
    }

    private Predicate<Doctor> filterBySpecialization(String specialization) {
        return doctor -> doctor.getSpecialization().equals(specialization);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {
        if(id >= doctors.size())  {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(doctors.get(id));
    }

}
