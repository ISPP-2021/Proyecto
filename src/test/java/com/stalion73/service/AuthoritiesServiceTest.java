package com.stalion73.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.stalion73.model.Authorities;
import com.stalion73.model.User;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class AuthoritiesServiceTest {

	@Autowired
	protected AuthoritiesService authoritiesService;

	@Autowired
	protected UserService userService;

	@Test
	void saveAuthoritiesTest() {
		User user = new User();
		user.setUsername("josito");
		Authorities authorities = new Authorities();
		authorities.setId(48);
		authorities.setAuthority("user");
		authorities.setUser(user);
		this.authoritiesService.saveAuthorities(authorities);
		Assertions.assertThat(authorities.getUser().getUsername()).isEqualTo("josito");
		Assertions.assertThat(authorities.getAuthority()).isEqualTo("user");
	}

	@Test
	void saveAuthoritiesTest2() {
		User user = new User();
		user.setUsername("aug");
		Authorities authorities = new Authorities();
		authorities.setId(48);
		authorities.setAuthority("owner");
		authorities.setUser(user);
		this.authoritiesService.saveAuthorities("aug", "owner");
		Assertions.assertThat(authorities.getUser().getUsername()).isEqualTo("aug");
		Assertions.assertThat(authorities.getAuthority()).isEqualTo("owner");
	}

}
