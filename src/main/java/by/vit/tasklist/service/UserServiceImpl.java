package by.vit.tasklist.service;

import by.vit.tasklist.entity.Password;
import by.vit.tasklist.entity.User;
import by.vit.tasklist.entity.UserForRegister;
import by.vit.tasklist.exception.UnauthorizedException;
import by.vit.tasklist.repository.RoleRepository;
import by.vit.tasklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * User service.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-27
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    /**
     * User repository.
     */
    private final UserRepository repo;

    /**
     * Password encoder.
     */
    private final PasswordEncoder encoder;

    /**
     * Role repository.
     */
    private final RoleRepository roleRepository;

    @Override
    public Page<User> getPaginated(final Pageable pageable) {
        val pageSize = pageable.getPageSize();
        val pageNumber = pageable.getPageNumber();
        val startItem = pageSize * pageNumber;
        val users = repo.findAll();
        var page = Collections.<User> emptyList();
        if (startItem < users.size())
            page = users.subList(startItem, Math.min(startItem + pageSize, users.size()));
        return new PageImpl<>(page, PageRequest.of(pageNumber, pageSize), users.size());
    }

    @Override
    public User getById(final Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Long save(final User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repo.save(user).getId();
    }

    @Override
    public void deleteById(final Long id) {
        try {
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException ignore) {
        }
    }

    @Override
    public Long register(final UserForRegister userForRegister) {
        val user = new User();
        user.setUsername(userForRegister.getUsername());
        user.setPassword(userForRegister.getPassword());
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        user.setEnable(true);
        return save(user);
    }

    @Override
    public Long updatePassword(final String username, final Password password) {
        val user = repo.findByUsername(username);
        if (!encoder.matches(password.getCurrentPassword(), user.getPassword()))
            throw new UnauthorizedException();
        user.setPassword(password.getNewPassword());
        return save(user);
    }

}
