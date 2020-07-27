package by.vit.tasklist.service;

import by.vit.tasklist.entity.Password;
import by.vit.tasklist.entity.User;
import by.vit.tasklist.entity.UserForRegister;

/**
 * User service interface.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-27
 */
public interface UserService extends PaginatedService<User, Long> {
    /**
     * This method registers new user with specified username and password.
     *
     * @param userForRegister - object with user's username and password.
     * @return id of saved user.
     */
    Long register(UserForRegister userForRegister);

    /**
     * This method updates password for user with specified username.
     *
     * @param password - object with current and new user's passwords.
     * @return id of updated user.
     */
    Long updatePassword(String username, Password password);

}
