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
 * Application base configuration.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-23
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    /**
     * Base role names.
     */
    private final List<String> baseRoleNames = Arrays.asList(
            "ADMIN",
            "USER"
    );

    /**
     * Role repository.
     */
    private final RoleRepository roleRepository;

    /**
     * This method creates base roles.
     */
    @Bean
    public void initRoles() {
        for (var roleName : baseRoleNames) {
            if (roleRepository.findByName(roleName) == null) {
                val role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
            }
        }
    }

}
