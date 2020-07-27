package by.vit.tasklist.entity;

import lombok.Data;

/**
 * This type used when updating the password.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-23
 */
@Data
public class Password {
    /**
     * Current user's password.
     */
    private String currentPassword;

    /**
     * New user's password.
     */
    private String newPassword;

}
