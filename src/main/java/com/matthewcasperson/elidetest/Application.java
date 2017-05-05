package com.matthewcasperson.elidetest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The entry point to our spring boot application
 */
@SpringBootApplication
@EnableTransactionManagement    // Elide requires transaction support, which we enable here
@Import(ElideConfig.class)
public class Application {
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
