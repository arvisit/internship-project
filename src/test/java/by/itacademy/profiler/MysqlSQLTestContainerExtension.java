package by.itacademy.profiler;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;

public class MysqlSQLTestContainerExtension implements BeforeAllCallback, AfterAllCallback {

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        MySQLContainer<?> container =
                new MySQLContainer<>("mysql:8.0")
                        .withDatabaseName("profiler-db");

        container.start();

        System.setProperty("spring.datasource.url", "jdbc:tc:mysql:8:///");
        System.setProperty("spring.liquibase.change-log", "classpath:/db.changelog/changelog-test.xml");
        System.setProperty("spring.security.cors.allowedOrigins", "none");
        System.setProperty("spring.security.cors.allowedMethods", "none");
        System.setProperty("spring.security.jwt.secret", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9");
        System.setProperty("image.storage-dir", "./src/test/resources/image-folder");
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) {
    }
}