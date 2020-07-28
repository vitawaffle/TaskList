package by.vit.tasklist.service;

import by.vit.tasklist.entity.Password;
import by.vit.tasklist.entity.Role;
import by.vit.tasklist.entity.User;
import by.vit.tasklist.exception.UnauthorizedException;
import by.vit.tasklist.repository.RoleRepository;
import by.vit.tasklist.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * User service.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@Service
@RequiredArgsConstructor
@PropertySource("classpath:ErrorMessages.properties")
public class UserServiceImpl implements UserService {
    /** User repository. */
    private final UserRepository userRepository;

    /** Role repository */
    private final RoleRepository roleRepository;

    /** Password encoder */
    private final PasswordEncoder passwordEncoder;

    /** Unauthorized error message */
    @Value("${user-service.unauthorized}")
    private String unauthorizedErrorMessage;

    @Override
    public Long updatePassword(final String username, final Password password) {
        val user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(password.getOldPassword(), user.getPassword()))
            throw new UnauthorizedException(unauthorizedErrorMessage);
        user.setPassword(passwordEncoder.encode(password.getNewPassword()));
        return save(user);
    }

    @Override
    public Long register(final User user) {
        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        user.setEnable(true);
        return save(user);
    }

    @Override
    public void enableById(final Long id) {
        val user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEnable(true);
            save(user);
        }
    }

    @Override
    public void disableById(final Long id) {
        val user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setEnable(false);
            save(user);
        }
    }

    @Override
    public void updateRolesById(final Long id, final List<Role> roles) {
        val user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setRoles(roles);
            save(user);
        }
    }

    @Override
    public Page<User> getPaginated(final Pageable pageable) {
        val startItem = pageable.getPageNumber() * pageable.getPageSize();
        val users = userRepository.findAll();
        var page = Collections.<User> emptyList();
        if (startItem < users.size())
            page = users.subList(startItem, Math.min(startItem + pageable.getPageSize(), users.size()));
        return new PageImpl<>(page, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), users.size());
    }

    @Override
    public User getById(final Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Long save(final User user) {
        return userRepository.save(user).getId();
    }

    @Override
    public void deleteById(final Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ignore) {}
    }

}
