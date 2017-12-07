package hillelJavaEE_02.greetings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class greetingController {
    @GetMapping("/hello")
    public String hello() {
        List<String> greetingsList = new ArrayList<String>() {{add("Hello world!");
                                                                add("Hola world!");
                                                                add("Bonjour world!");}};
        return greetingsList.get(ThreadLocalRandom.current().nextInt(0, greetingsList.size()));
    }
}
