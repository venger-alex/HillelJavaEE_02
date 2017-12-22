package hillelJavaEE_02.pet;

import hillelJavaEE_02.converter.HibernateDateConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String species;
    private Integer age;
    //@Convert(converter = HibernateDateConverter.class)
    private LocalDate birthDate;
    @OneToOne(cascade = CascadeType.ALL)
    private MedicalCard medicalCard;

    public Pet(String name, String species, Integer age, LocalDate birthDate, MedicalCard medicalCard) {
        this.name = name;
        this.species = species;
        this.age = age;
        this.birthDate = birthDate;
        this.medicalCard = medicalCard;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
