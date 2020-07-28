package by.vit.tasklist.repository;

import by.vit.tasklist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository interface.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * This method finds user by username.
     *
     * @param username - username.
     * @return user or null.
     */
    User findByUsername(String username);

}
