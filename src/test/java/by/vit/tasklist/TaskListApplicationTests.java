package by.vit.tasklist;

import by.vit.tasklist.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TaskListApplicationTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void shouldExistRoleAdmin() {
        assertNotNull(roleRepository.findByName("ADMIN"));
    }

    @Test
    public void shouldExistRoleUser() {
        assertNotNull(roleRepository.findByName("USER"));
    }

}
