package com.md.blog;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import java.time.Clock;


@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Blog API")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Melih Düzgün")
                                .url("https://github.com/Mduzgunn/blog")
                                .email("aduzgun99@gmail.com.tr"))
                        .description("This is simply a blog application\n"));
                       // .license(new License().name("MD Blog API Licence")));
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }
}
