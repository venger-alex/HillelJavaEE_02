package hillelJavaEE_02.pet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {
    //@RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @GetMapping("/greeting")
    public String helloWorld() {
        return "Hello world!";
    }
}
