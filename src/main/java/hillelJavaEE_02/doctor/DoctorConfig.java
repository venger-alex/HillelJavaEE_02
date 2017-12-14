package hillelJavaEE_02.doctor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Configuration
@ConfigurationProperties(prefix = "doctor-specialization")
public class DoctorConfig {
    private List<String> list;

    DoctorConfig() {
        this.list = new CopyOnWriteArrayList<>();
    }

    public List<String> getList() {
        return this.list;
    }
}
