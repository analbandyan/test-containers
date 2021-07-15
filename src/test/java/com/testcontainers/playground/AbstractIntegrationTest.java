package com.testcontainers.playground;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public interface AbstractIntegrationTest {

    class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.25");

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            mySQLContainer.start();

            TestPropertyValues.of(
                    "spring.datasource.url=" + mySQLContainer.getJdbcUrl()
                    , "spring.datasource.driver-class-name=" + mySQLContainer.getDriverClassName()
                    , "spring.datasource.username=" + mySQLContainer.getUsername()
                    , "spring.datasource.password=" + mySQLContainer.getPassword()
            ).applyTo(applicationContext);
        }
    }
}
