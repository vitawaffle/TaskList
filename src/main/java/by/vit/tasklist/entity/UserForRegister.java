package by.vit.tasklist.entity;

import lombok.Data;

/**
 * This type is used when registering a new user.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-23
 */
@Data
public class UserForRegister {
    /**
     * Username.
     */
    private String username;

    /**
     * Password.
     */
    private String password;

}
