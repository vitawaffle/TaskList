package by.vit.tasklist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception is used for send unauthorized status.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-23
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    /**
     * Create exception with default message.
     */
    public UnauthorizedException() {
        super();
    }

    /**
     * Create exception with custom message.
     *
     * @param message - error message.
     */
    public UnauthorizedException(final String message) {
        super(message);
    }

}
