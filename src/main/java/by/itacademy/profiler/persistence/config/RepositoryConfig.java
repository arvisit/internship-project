package by.itacademy.profiler.persistence.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EntityScan("by.itacademy.profiler.persistence.model")
@EnableJpaRepositories("by.itacademy.profiler.persistence.repository")
public class RepositoryConfig {
}
