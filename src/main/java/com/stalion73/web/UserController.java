package com.stalion73.web;

import org.springframework.beans.factory.annotation.Autowired;
import com.stalion73.model.Authorities;
import com.stalion73.model.User;
import com.stalion73.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private final static HttpHeaders headers = new HttpHeaders();

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		User usuario = this.userService.findUser(user.getUsername()).get();
		if (usuario.getPassword().equals(usuario.getPassword())) {
			String token = getJWTToken(usuario);
			headers.add("token", token);
			usuario.setToken(token);
		} else {
			user = null;
		}
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(usuario);
	}

	private String getJWTToken(User user) {
		String secretKey = "mySecretKey";
		List<Authorities> authorities = new ArrayList<>(user.getAuthorities());
		// System.out.println(authorities);
		String token = Jwts.builder().setId("softtekJWT").setSubject(user.getUsername())
				.claim("authorities", authorities.stream().map(Authorities::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 6000000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}