package hillelJavaEE_02.greetings;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class GreetingController {
    private GreetingComponent greetingComponent;

    @GetMapping("/hello")
    public String hello() {
        return greetingComponent.getRandomGreeting();
    }
}
