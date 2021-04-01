package com.stalion73.web;

import com.stalion73.model.User;
import com.stalion73.service.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	public UserController(UserService userService){
		this.userService = userService;
	}

    @PostMapping("/login")
    public User login(@RequestParam("user") String username, 
                        @RequestParam("password") String password){

			// System.out.println(password);
			User user = this.userService.findUser(username).get();
			// System.out.println(user.getPassword()==password);
			if(user.getPassword().equals(password)){
				String token = getJWTToken(username);
				user.setToken(token);
			} else {
				user=null;
			}
            return user;
    }

    private String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("user");
		System.out.println(grantedAuthorities);
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
    }

}