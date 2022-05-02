package brorica.gather.exception;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

    private final String message;
    private final LocalDateTime timeStamp;
}
