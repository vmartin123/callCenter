package callcenter.config;

import callcenter.dto.ApiError;
import callcenter.utils.CustomStatusCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    private final Logger log = Logger.getLogger(RestResponseEntityExceptionHandler.class.getName());

    // handles all the exceptions
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleControllerException(HttpServletRequest req, Throwable ex) {

        log.severe(ex.getMessage());
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), CustomStatusCodes.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
