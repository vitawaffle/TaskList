package by.vit.tasklist.repository;

import by.vit.tasklist.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Role repository interface.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-23
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * This method finds role by name.
     *
     * @param name - role name.
     * @return role or null.
     */
    Role findByName(String name);

}
