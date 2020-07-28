package by.vit.tasklist.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * A service that returns data page by page.
 *
 * @param <T> - entity type with which the service work.
 * @param <ID> - entity id type.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
public interface PaginatedService<T, ID> extends AppService<T, ID> {
    /**
     * This method returns entities page by page.
     *
     * @param pageable - Pageable class object with defined pageSize and pageNumber fields.
     * @return page of entities or empty page if pageSize and pageNumber values out of entity collection size.
     */
    Page<T> getPaginated(Pageable pageable);

}
