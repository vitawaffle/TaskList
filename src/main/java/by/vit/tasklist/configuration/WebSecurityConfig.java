package by.vit.tasklist.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Web security configuration class.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-27
 */
@Configuration
public class WebSecurityConfig {
    /**
     * This bean sets default password encoder as BCryptPasswordEncoder.
     *
     * @return BCryptPasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
