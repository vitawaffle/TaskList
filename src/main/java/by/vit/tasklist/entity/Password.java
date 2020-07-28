package by.vit.tasklist.entity;

import lombok.Data;

/**
 * Object for password update.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@Data
public class Password {
    /** Current user's password. */
    private String oldPassword;

    /** New user's password. */
    private String newPassword;

}
