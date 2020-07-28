package by.vit.tasklist;

import by.vit.tasklist.entity.Password;
import by.vit.tasklist.entity.Role;
import by.vit.tasklist.entity.User;
import by.vit.tasklist.exception.UnauthorizedException;
import by.vit.tasklist.repository.RoleRepository;
import by.vit.tasklist.repository.UserRepository;
import by.vit.tasklist.service.UserService;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Long userId;

    private Long roleAdminId;

    private Long roleUserId;

    @BeforeEach
    public void init() {
        userRepository.deleteAll();

        val user = new User();
        user.setUsername("user1");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        user.setEnable(true);

        userId = userRepository.save(user).getId();
        roleAdminId = roleRepository.findByName("ADMIN").getId();
        roleUserId = roleRepository.findByName("USER").getId();
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }

    @Test
    public void getPaginated_InRange_ShouldReturnNotEmpty() {
        assertFalse(userService.getPaginated(PageRequest.of(0, 1)).isEmpty());
    }

    @Test
    public void getPaginated_OutOfRange_ShouldReturnEmpty() {
        assertTrue(userService.getPaginated(PageRequest.of(1, 1)).isEmpty());
    }

    @Test
    public void getById_ExistingId_ShouldReturnNotNull() {
        assertNotNull(userService.getById(userId));
    }

    @Test
    public void getById_NotExistingId_ShouldReturnNull() {
        assertNull(userService.getById(0L));
    }

    @Test
    public void save_Create_Valid_ShouldDoesNotThrow() {
        val role = new Role();
        role.setId(roleUserId);

        val user = new User();
        user.setUsername("user2");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void save_Update_Valid_ShouldDoesNotThrow() {
        val role = new Role();
        role.setId(roleUserId);

        val user = new User();
        user.setId(userId);
        user.setUsername("user2");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertDoesNotThrow(() -> userService.save(user));
    }

    @Test
    public void save_NullUsername_ShouldThrowException() {
        val role = new Role();
        role.setId(roleUserId);

        val user = new User();
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void save_NotUniqueUsername_ShouldThrowException() {
        val role = new Role();
        role.setId(roleUserId);

        val user = new User();
        user.setUsername("user1");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void save_NullPassword_ShouldThrowException() {
        val role = new Role();
        role.setId(roleUserId);

        val user = new User();
        user.setId(userId);
        user.setUsername("user2");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void save_NotExistingRoleId_ShouldThrowException() {
        val role = new Role();
        role.setId(0L);

        val user = new User();
        user.setUsername("user2");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void save_NullRoleId_ShouldThrowException() {
        val user = new User();
        user.setUsername("user2");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(new Role()));
        user.setEnable(true);

        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void save_NullEnable_ShouldThrowException() {
        val role = new Role();
        role.setId(roleUserId);

        val user = new User();
        user.setUsername("user2");
        user.setPassword(passwordEncoder.encode("password"));
        user.setRoles(Collections.singletonList(role));

        assertThrows(Exception.class, () -> userService.save(user));
    }

    @Test
    public void deleteById_ExistingId_ShouldDoesNotThrow() {
        assertDoesNotThrow(() -> userService.deleteById(userId));
    }

    @Test
    public void deleteById_NotExistingId_ShouldDoesNotThrow() {
        assertDoesNotThrow(() -> userService.deleteById(0L));
    }

    @Test
    public void updatePassword_Valid_ShouldDoesNotThrow() {
        val password = new Password();
        password.setOldPassword("password");
        password.setNewPassword("NewSuperPassword1");

        assertDoesNotThrow(() -> userService.updatePassword("user1", password));
    }

    @Test
    public void updatePassword_Valid_ShouldUpdatePassword() {
        val password = new Password();
        password.setOldPassword("password");
        password.setNewPassword("NewSuperPassword1");

        userService.updatePassword("user1", password);
        val user = userRepository.findByUsername("user1");

        assertTrue(passwordEncoder.matches("NewSuperPassword1", user.getPassword()));
    }

    @Test
    public void updatePassword_WrongOldPassword_ShouldThrowUnauthorizedException() {
        val password = new Password();
        password.setOldPassword("SomeWrongPassword");
        password.setNewPassword("NewSuperPassword1");

        assertThrows(UnauthorizedException.class, () -> userService.updatePassword("user1", password));
    }

    @Test
    public void register_Valid_ShouldDoesNotThrow() {
        val user = new User();
        user.setUsername("user2");
        user.setPassword("password");

        assertDoesNotThrow(() -> userService.register(user));
    }

    @Test
    public void enableById_ShouldDoesNotThrow() {
        assertDoesNotThrow(() -> userService.enableById(userId));
    }

    @Test
    public void enableById_ShouldSetEnableTrue() {
        var user = userRepository.findByUsername("user1");
        user.setEnable(false);
        userRepository.save(user);

        userService.enableById(userId);

        user = userRepository.findByUsername("user1");

        assertTrue(user.getEnable());
    }

    @Test
    public void disableById_ShouldDoesNotThrow() {
        assertDoesNotThrow(() -> userService.disableById(userId));
    }

    @Test
    public void disableById_ShouldSetEnableFalse() {
        userService.disableById(userId);

        val user = userRepository.findByUsername("user1");

        assertFalse(user.getEnable());
    }

    @Test
    public void updateRolesById_ShouldDoesNotThrow() {
        val roleAdmin = new Role();
        roleAdmin.setId(roleAdminId);

        val roleUser = new Role();
        roleUser.setId(roleUserId);

        assertDoesNotThrow(() -> {
            userService.updateRolesById(userId, Arrays.asList(roleAdmin, roleUser));
        });
    }

    @Test
    public void updateRolesById_ShouldUpdateRoles() {
        val roleAdmin = new Role();
        roleAdmin.setId(roleAdminId);

        val roleUser = new Role();
        roleUser.setId(roleUserId);

        userService.updateRolesById(userId, Arrays.asList(roleAdmin, roleUser));

        assertEquals(2, userRepository.findByUsername("user1").getRoles().size());
    }

}
