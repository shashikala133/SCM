package com.scm.scm10;

import com.scm.scm10.entities.User;
import com.scm.scm10.helpers.AppConstants;
import com.scm.scm10.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setUserId(UUID.randomUUID().toString());
		user.setName("admin");
		user.setEmail("admin@gmail.com");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setRoles(List.of(AppConstants.APP_ROLE));
		user.setEmailVerified(true);
		user.setEnabled(true);
		user.setAbout("This is dummy user created initially");
		user.setPhoneVerified(true);

		userRepo.findByEmail("admin@gmail.com").ifPresentOrElse(user1 -> {
		}, () -> {
			userRepo.save(user);
			System.out.println("user created");
		});

	}


	}
