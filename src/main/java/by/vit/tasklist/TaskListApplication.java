package by.vit.tasklist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class.
 *
 * @author Vitaly Lobatsevich
 * @version 1.0
 * @since 2020-07-23
 */
@SpringBootApplication
public class TaskListApplication {
    /**
     * Application startup method.
     *
     * @param args - command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(TaskListApplication.class, args);
    }

}
