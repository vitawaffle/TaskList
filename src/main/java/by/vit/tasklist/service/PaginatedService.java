package by.vit.tasklist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service that returns data page by page.
 *
 * @param <T> - entity type with which the service work.
 * @param <ID> - entity id type.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-27
 */
public interface PaginatedService<T, ID> extends MyService<T, ID> {
    /**
     * This method gets entities page by page.
     *
     * @param pageable - Pagealbe class object with defined pageSize and pageNumber fields.
     * @return page of entities or empty page if pageSize and pageNumber values out of entity collections size.
     */
    Page<T> getPaginated(Pageable pageable);

}
