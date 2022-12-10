package com.userservice.userservice.configs;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

import com.github.javafaker.Faker;
import com.userservice.userservice.models.User;
import com.userservice.userservice.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;

@Service
public class DataBaseSetup implements CommandLineRunner {

    @Autowired
    private UserRepo repo;
    @Value("classpath:init.sql")
    private Resource resource;

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public void run(String... args) throws Exception {

        String sql = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);

        template
                .getDatabaseClient()
                .sql(sql)
                .then()
                .subscribe();
        Faker faker = Faker.instance();

        Flux<User> users = Flux.range(1, 5)
                .delaySubscription(Duration.ofMillis(500))
                .map(i -> {
                    return new User(faker.name().fullName(), faker.random().nextInt(1, 10) * 1000);
                });

        repo.saveAll(users).subscribe();

    }


}
