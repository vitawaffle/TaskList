package by.vit.tasklist.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * User entity.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends SQLEntity {
    /** Username. */
    @Column(nullable = false, unique = true)
    private String username;

    /** Password. */
    @Column(nullable = false)
    private String password;

    /** Roles. */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    /** Is the user enable. */
    @Column(nullable = false)
    private Boolean enable;

}
