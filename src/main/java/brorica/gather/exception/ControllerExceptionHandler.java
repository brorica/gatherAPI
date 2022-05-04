package brorica.gather.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.NestedServletException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage illegalStateException (Exception exception, HttpServletRequest request) {
        return new ErrorMessage(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage noSuchElementException (Exception exception) {
        return new ErrorMessage(exception.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(NestedServletException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage nestedServletException (Exception exception) {
        return new ErrorMessage(exception.getMessage(), LocalDateTime.now());
    }
}
