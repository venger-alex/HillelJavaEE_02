package hillelJavaEE_02.doctor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "doctor-configuration")
@NoArgsConstructor
@Getter
public class DoctorConfig {
    private List<String> specializations = new ArrayList<>();
}
