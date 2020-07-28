package by.vit.tasklist.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Role entity.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@Entity
@Table(name = "roles")
@Data
@EqualsAndHashCode(callSuper = false)
public class Role extends SQLEntity {
    /**
     * Role name.
     */
    @Column(nullable = false, unique = true)
    private String name;

}
