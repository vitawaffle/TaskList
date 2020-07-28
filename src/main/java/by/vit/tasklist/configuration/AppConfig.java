package by.vit.tasklist.configuration;

import by.vit.tasklist.entity.Role;
import by.vit.tasklist.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Application configuration class.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-28
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    /**
     * Role repository.
     */
    private final RoleRepository roleRepository;

    /**
     * Basic role names.
     */
    private final List<String> basicRoleNames = Arrays.asList(
            "ADMIN",
            "USER"
    );

    /**
     * This bean initialize basic roles.
     */
    @Bean
    public void initBasicRoles() {
        for (var roleName : basicRoleNames) {
            if (roleRepository.findByName(roleName) == null) {
                val role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }

}
