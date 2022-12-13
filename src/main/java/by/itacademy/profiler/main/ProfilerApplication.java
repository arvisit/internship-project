package by.itacademy.profiler.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("by.itacademy.profiler")
@EntityScan("by.itacademy.profiler.persistence.model")
@EnableJpaRepositories("by.itacademy.profiler.persistence.repository")
public class ProfilerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProfilerApplication.class, args);
    }

}
