package by.vit.tasklist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Error used to send unauthorized status.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {
    /** This constructor creates exception with default error message. */
    public UnauthorizedException() {
        super();
    }

    /**
     * This constructor creates exception with specified error message.
     *
     * @param message - error message.
     */
    public UnauthorizedException(final String message) {
        super(message);
    }

}
