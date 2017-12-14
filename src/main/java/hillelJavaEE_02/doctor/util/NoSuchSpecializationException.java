package hillelJavaEE_02.doctor.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSuchSpecializationException extends RuntimeException {
    public NoSuchSpecializationException(String message) {
        super(message);
    }
}
