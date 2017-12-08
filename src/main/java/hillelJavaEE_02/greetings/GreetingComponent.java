package hillelJavaEE_02.greetings;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Data
@Component
public class GreetingComponent {
    private List<String> greetingsList = new CopyOnWriteArrayList<>(
            Arrays.asList(new String[]{"Hello world!", "Hola world!", "Bonjour world!"}));

    public String getRandomGreeting() {
        return greetingsList.get(ThreadLocalRandom.current().nextInt(0, greetingsList.size()));
    }
}
