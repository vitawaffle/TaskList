package by.vit.tasklist.service;

/**
 * Application service interface.
 *
 * @param <T> - entity type with which the service work.
 * @param <ID> - entity id type.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-25
 */
public interface MyService<T, ID> {
    /**
     * This method gets entity by id.
     *
     * @param id - entity id.
     * @return entity or null.
     */
    T getById(ID id);

    /**
     * This method saves entity.
     *
     * @param t - entity for save.
     * @return - id of saved entity.
     */
    ID save(T t);

    /**
     * This method deletes entity by id.
     *
     * @param id - entity id.
     */
    void deleteById(ID id);

}
