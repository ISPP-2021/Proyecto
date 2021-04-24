package com.stalion73.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class UserServiceTest {

	@Autowired
	protected UserService userService;

	@Test
	void findOneUserTest() {
		Optional<User> user = this.userService.findUser("josito");
		Assertions.assertThat(user.get().getPassword()).isEqualTo("1234");
	}

	@Test
	void saveUserTest() {
		User user = new User();
		user.setUsername("carlosmu");
		user.setPassword("12345");
		user.setEnabled(true);
		this.userService.saveUser(user);
		Assertions.assertThat(user.getUsername()).isEqualTo("carlosmu");
		Assertions.assertThat(user.getPassword()).isEqualTo("12345");
	}

//	@Test
//	void deleteUserTest() {
//		
//	}

}
