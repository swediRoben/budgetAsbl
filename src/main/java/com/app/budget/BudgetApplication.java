package com.app.budget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
//@ComponentScan(basePackages = {"com.app.budget.rest.source.*", "com.app.budget.rest.typesource.*"})
public class BudgetApplication {

    public static void main(final String[] args) {
        SpringApplication.run(BudgetApplication.class, args);
    }

}
