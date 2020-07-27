package by.vit.tasklist;

import by.vit.tasklist.entity.Password;
import by.vit.tasklist.entity.Role;
import by.vit.tasklist.entity.User;
import by.vit.tasklist.entity.UserForRegister;
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

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService srv;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    private Long id;

    private Long roleId;

    @BeforeEach
    public void init() {
        val user = new User();
        user.setUsername("user1");
        user.setPassword(encoder.encode("password"));
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        user.setEnable(true);

        id = userRepository.save(user).getId();
        roleId = roleRepository.findByName("USER").getId();
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }

    @Test
    public void getPaginated_InRange_ShouldReturnNotEmpty() {
        assertFalse(srv.getPaginated(PageRequest.of(0, 1)).isEmpty());
    }

    @Test
    public void getPaginated_OutOfRange_ShouldReturnEmpty() {
        assertTrue(srv.getPaginated(PageRequest.of(1, 1)).isEmpty());
    }

    @Test
    public void getById_ExistingId_ShouldReturnNotNull() {
        assertNotNull(srv.getById(id));
    }

    @Test
    public void getById_NotExistingId_ShouldReturnNull() {
        assertNull(srv.getById(0L));
    }

    @Test
    public void save_Valid_ShouldDoesNotThrow() {
        val role = new Role();
        role.setId(roleId);

        val user = new User();
        user.setUsername("user2");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertDoesNotThrow(() -> srv.save(user));
    }

    @Test
    public void save_Update_ShouldDoesNotThrow() {
        val role = new Role();
        role.setId(roleId);

        val user = new User();
        user.setId(id);
        user.setUsername("user1");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertDoesNotThrow(() -> srv.save(user));
    }

    @Test
    public void save_NullUsername_ShouldThrowException() {
        val role = new Role();
        role.setId(roleId);

        val user = new User();
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> srv.save(user));
    }

    @Test
    public void save_NotUniqueUsername_ShouldThrowException() {
        val role = new Role();
        role.setId(roleId);

        val user = new User();
        user.setUsername("user1");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> srv.save(user));
    }

    @Test
    public void save_NullPassword_ShouldThrowException() {
        val role = new Role();
        role.setId(roleId);

        val user = new User();
        user.setUsername("user2");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> srv.save(user));
    }

    @Test
    public void save_NotExistingRoleId_ShouldThrowException() {
        val role = new Role();
        role.setId(0L);

        val user = new User();
        user.setUsername("user2");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));
        user.setEnable(true);

        assertThrows(Exception.class, () -> srv.save(user));
    }

    @Test
    public void save_NullRoleId_ShouldThrowException() {
        val user = new User();
        user.setUsername("user2");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(new Role()));
        user.setEnable(true);

        assertThrows(Exception.class, () -> srv.save(user));
    }

    @Test
    public void save_NullEnable_ShouldThrowException() {
        val role = new Role();
        role.setId(roleId);

        val user = new User();
        user.setUsername("user2");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));

        assertThrows(Exception.class, () -> srv.save(user));
    }

    @Test
    public void register_Valid_ShouldDoesNotThrow() {
        val user = new UserForRegister();
        user.setUsername("user2");
        user.setPassword("passwrod");

        assertDoesNotThrow(() -> srv.register(user));
    }

    @Test
    public void updatePassword_Valid_ShouldDoesNotThrow() {
        val password = new Password();
        password.setCurrentPassword("password");
        password.setNewPassword("NewSuperPassword1");

        assertDoesNotThrow(() -> srv.updatePassword("user1", password));
    }

    @Test
    public void updatePassword_WrongCurrentPassword_ShouldThrowUnauthorizedException() {
        val password = new Password();
        password.setCurrentPassword("12");
        password.setNewPassword("NewSuperPassword1");

        assertThrows(UnauthorizedException.class, () -> srv.updatePassword("user1", password));
    }

}
