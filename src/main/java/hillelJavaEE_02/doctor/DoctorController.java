package hillelJavaEE_02.doctor;

import hillelJavaEE_02.doctor.util.ErrorBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
public class DoctorController {
    private Map<Integer, Doctor> doctors = new ConcurrentHashMap<Integer, Doctor>() {{
        put(0, new Doctor(0, "Doc", "surgeon"));
        put(1, new Doctor(1, "Alex", "doctor"));
    }};

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
        Optional<Doctor> findDoctor = Optional.ofNullable(doctors.get(id));

        return findDoctor.map(doctor -> ResponseEntity.ok(doctor))
                            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {
        if(doctor.getId() != null) {
            return ResponseEntity.badRequest().body(new ErrorBody("You can not create a doctor with a predefined ID"));
        }

        doctor.setId(ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE));
        doctors.put(doctor.getId(), doctor);
        return ResponseEntity.created(URI.create("/doctors/" + doctor.getId())).build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        if(!doctors.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        if(!id.equals(doctor.getId())) {
            return ResponseEntity.badRequest().body(new ErrorBody("You can not change doctor ID"));
        }

        doctor.setId(id);
        doctors.put(id, doctor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id) {
        if(!doctors.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        doctors.remove(id);
        return ResponseEntity.noContent().build();
    }
}

