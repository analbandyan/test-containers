package com.testcontainers.playground;

import com.testcontainers.playground.model.Author;
import com.testcontainers.playground.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.Optional;

@SpringBootApplication
public class TestContainersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestContainersApplication.class, args);
	}

}

@Component
class Test implements CommandLineRunner {

	@Autowired
	private AuthorService authorService;

	@Override
	public void run(String... args) throws Exception {
//		final Optional<Author> aaa = authorService.findByBook("aaa");
//		final boolean empty = aaa.isEmpty();
	}
}
