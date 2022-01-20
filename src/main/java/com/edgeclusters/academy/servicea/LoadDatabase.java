package com.edgeclusters.academy.servicea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Employee("Josef Novák", "zámečník")));
      log.info("Preloading " + repository.save(new Employee("Alois Dvořák", "truhlář")));
      log.info("Preloading " + repository.save(new Employee("Antonín Souček", "pokrývač")));
      log.info("Preloading " + repository.save(new Employee("Oldřich Černý", "klempíř")));
      log.info("Preloading " + repository.save(new Employee("Eduard Svoboda", "zedník")));
      log.info("Preloading " + repository.save(new Employee("Jiří Kučera", "řezník")));
    };
  }
}