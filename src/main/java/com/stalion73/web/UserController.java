package com.stalion73.web;

import com.stalion73.model.Authorities;
import com.stalion73.model.User;
import com.stalion73.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController; 
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;

	private final static HttpHeaders headers = new HttpHeaders();

    
    public  static void setup(){
        headers.setAccessControlAllowOrigin("*");
    }

	public UserController(UserService userService){
		this.userService = userService;
	}

    @PostMapping("/login")
    public User login(@RequestParam("user") String username, 
                        @RequestParam("password") String password){

			User user = this.userService.findUser(username).get();
			if(user.getPassword().equals(password)){
				String token = getJWTToken(user);
				user.setToken(token);
			} else {
				user=null;
			}
            // return ResponseEntity user;
			return user;
    }

    private String getJWTToken(User user) {
		String secretKey = "mySecretKey";
		List<Authorities> authorities = new ArrayList<>(user.getAuthorities());
		System.out.println(authorities);
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(user.getUsername())
				.claim("authorities",
						authorities.stream().map(Authorities::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
    }

}