package hillelJavaEE_02.doctor;

import hillelJavaEE_02.doctor.util.ErrorBody;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping("/doctors/specializations")
    public List<String> getSpecializations() {
        return doctorService.getSpecializations();
    }

    @GetMapping("/doctors")
    public List<Doctor> getDoctors(@RequestParam Optional<List<String>> specializations,
                                   @RequestParam Optional<String> name) {
        return doctorService.getDoctors(specializations, name);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<?> getDoctorById(@PathVariable Integer id) {
        Optional<Doctor> findDoctor = doctorService.getById(id);

        return findDoctor.map(doctor -> ResponseEntity.ok(doctor))
                            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/doctors")
    public ResponseEntity<?> createDoctor(@RequestBody Doctor doctor) {
        if(doctor.getId() != null) {
            return ResponseEntity.badRequest().body(new ErrorBody("You can not create a doctor with a predefined ID"));
        }

        Doctor saved = doctorService.save(doctor);

        return ResponseEntity.created(URI.create("/doctors/" + saved.getId())).build();
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<?> updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        if(!doctorService.getById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if(!id.equals(doctor.getId())) {
            return ResponseEntity.badRequest().body(new ErrorBody("You can not change doctor ID"));
        }

        doctor.setId(id);
        doctorService.save(doctor);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<?> deleteDoctor(@PathVariable Integer id) {
        if(!doctorService.getById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

