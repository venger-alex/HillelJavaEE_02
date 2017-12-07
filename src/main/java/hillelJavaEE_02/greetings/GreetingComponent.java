package hillelJavaEE_02.greetings;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Component
public class GreetingComponent {
    private List<String> greetingsList = new ArrayList<String>() {{add("Hello world!");
        add("Hola world!");
        add("Bonjour world!");}};

    public String getRandomGreeting() {
        return greetingsList.get(ThreadLocalRandom.current().nextInt(0, greetingsList.size()));
    }
}
