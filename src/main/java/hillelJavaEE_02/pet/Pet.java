package hillelJavaEE_02.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    private Integer id;
    private String name;
    private String species;
    private Integer age;

    public Optional<String> getName() {
        return Optional.ofNullable(name);
    }
}
