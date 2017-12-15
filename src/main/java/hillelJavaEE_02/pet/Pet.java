package hillelJavaEE_02.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String species;
    private Integer age;

    public Pet(String name, String species, Integer age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
