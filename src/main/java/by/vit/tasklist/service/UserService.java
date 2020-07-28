package by.vit.tasklist.service;

import by.vit.tasklist.entity.Password;
import by.vit.tasklist.entity.Role;
import by.vit.tasklist.entity.User;

import java.util.List;

/**
 * User service interface.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
public interface UserService extends PaginatedService<User, Long> {
    /**
     * This method updates password for user with specified username.
     *
     * @param username - user's username.
     * @param password - Password class object with current and new passwords.
     * @return id of updated user.
     */
    Long updatePassword(String username, Password password);

    /**
     * This method registers new user.
     *
     * @param user - user object with defined username and password fields.
     * @return registered user id.
     */
    Long register(User user);

    /**
     * This method enables user by id.
     *
     * @param id - user id.
     */
    void enableById(Long id);

    /**
     * This method disables user by id.
     *
     * @param id - user id.
     */
    void disableById(Long id);

    /**
     * This method sets roles for user with specified id.
     *
     * @param id - user id.
     * @param roles - roles.
     */
    void updateRolesById(Long id, List<Role> roles);

}
