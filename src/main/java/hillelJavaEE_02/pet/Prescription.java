package hillelJavaEE_02.pet;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String description;
    private LocalDate start;
    private Integer timePerDay;
    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;

    public Prescription(String description, LocalDate start, Integer timePerDay, MedicineType medicineType) {
        this.description = description;
        this.start = start;
        this.timePerDay = timePerDay;
        this.medicineType = medicineType;
    }
}
