package hillelJavaEE_02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class HillelJavaEe02Application {

	public static void main(String[] args) {
		SpringApplication.run(HillelJavaEe02Application.class, args);
	}
}
