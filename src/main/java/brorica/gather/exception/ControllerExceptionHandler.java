package brorica.gather.exception;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage duplicateException(Exception exception) {
        return new ErrorMessage(exception.getMessage(), LocalDateTime.now());
    }
}
