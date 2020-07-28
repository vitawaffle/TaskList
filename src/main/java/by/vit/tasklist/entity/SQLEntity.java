package by.vit.tasklist.entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass for application entities.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@MappedSuperclass
@Data
public class SQLEntity {
    /** Entity id from database. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

}
