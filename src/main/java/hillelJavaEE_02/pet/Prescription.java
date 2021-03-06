package hillelJavaEE_02.pet;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public Prescription(String description, LocalDate start, Integer timePerDay) {
        this.description = description;
        this.start = start;
        this.timePerDay = timePerDay;
    }
}
