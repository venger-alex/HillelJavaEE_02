package hillelJavaEE_02.doctor;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Specialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private SpecializationsName name;

    public Specialization(SpecializationsName name) {
        this.name = name;
    }
}
