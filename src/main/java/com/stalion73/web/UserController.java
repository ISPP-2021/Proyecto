package com.stalion73.web;

import org.springframework.beans.factory.annotation.Autowired;
import com.stalion73.model.Authorities;
import com.stalion73.model.Consumer;
import com.stalion73.model.Supplier;
import com.stalion73.model.User;
import com.stalion73.repository.ConsumerRepository;
import com.stalion73.model.Business;
import com.stalion73.service.ConsumerService;
import com.stalion73.service.SupplierService;
import com.stalion73.service.BusinessService;
import com.stalion73.service.UserService;
import com.stalion73.service.AuthoritiesService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import javax.validation.Valid;


import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RestController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.HttpStatus;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ConsumerService consumerService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private final BusinessService businessService;

	@Autowired
	private final AuthoritiesService authoritiesService;


	private final static HttpHeaders headers = new HttpHeaders();

	public UserController(UserService userService,
						ConsumerService consumerService,
						SupplierService supplierService,
						BusinessService businessService,
						AuthoritiesService authoritiesService) {
				this.consumerService = consumerService;
				this.supplierService = supplierService;
				this.userService = userService;
				this.businessService = businessService;
				this.authoritiesService = authoritiesService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> login(@RequestBody User credentials) {
		Optional<User> user = this.userService.findUser(credentials.getUsername());
		if(!user.isPresent()){
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Ineffected ID")
                    .withDetail("The provided ID doesn't exist"));
        }
		if (user.get().getPassword().equals(credentials.getPassword())) {
			String token = getJWTToken(user.get());
			user.get().setToken(token);
			this.userService.saveUser(user.get());
		} else {
			return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .headers(headers)
                .body(Problem.create()
                    .withTitle("Bad credentials")
                    .withDetail("The provided user credentials didn't match any already existing record."));
		}
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(user.get());
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> profile(SecurityContextHolder contextHolder) {
		String authority = contextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
		if(authority.equals("user")){
			Consumer user = this.consumerService
				.findConsumerByUsername((String)contextHolder.getContext()
												.getAuthentication().getPrincipal()).get();
		
			return ResponseEntity.status(HttpStatus.OK).headers(headers).body(user);
		} else if(authority.equals("owner")) {
        	HttpHeaders headers = new HttpHeaders();
		    Supplier user = this.supplierService.findSupplierByUsername((String)contextHolder.getContext()
												.getAuthentication().getPrincipal()).get();

			Collection<Business> business = this.businessService.findBusinessBySupplierId(user.getId());
			if(business.isEmpty()){
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(business);
			}
			if(!business.isEmpty()){
				return ResponseEntity.status(HttpStatus.OK).headers(headers).body(user);
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers)
							 .body(Problem.create()
							 		.withTitle("Missing appropiated authority")
									.withDetail("The provided authority was not the possible expected values"));
	}

	@RequestMapping(value = "/signup/consumers", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> signUpConsumer(@Valid @RequestBody Consumer consumer,
									BindingResult bindingResult){

		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || ( consumer== null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided user was not successfuly validated"));
		}else{
			//String token = getJWTToken(user)
			//user.setToken(token)
			User user = consumer.getUser();
			String username = user.getUsername();
			if(this.consumerService.findConsumerByUsername(username).isPresent()){
				return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Already existing user")
					.withDetail("I wasn't possible to create an user because of username replication on the data."));
			}
			Set<Authorities> authorities = new HashSet<>();
			Authorities auth = new Authorities();
			auth.setAuthority("user");
			auth.setUser(user);
			authorities.add(auth);
			user.setAuthorities(authorities);
			consumer.setUser(user);
			this.consumerService.save(consumer);
			String userName = user.getUsername();
			consumer = this.consumerService.findConsumerByUsername(userName).get();
			return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(headers)
                    .body(consumer);
		}

	}

	@RequestMapping(value = "/signup/suppliers", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> signUpSupplier(@Valid @RequestBody Supplier supplier,
									BindingResult bindingResult){

		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if (bindingResult.hasErrors() || ( supplier == null)) {
            errors.addAllErrors(bindingResult);
            headers.add("errors", errors.toJSON());
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Validation error")
					.withDetail("The provided user was not successfuly validated"));
		}else{
			//String token = getJWTToken(user)
			//user.setToken(token)
			User user = supplier.getUser();
			String username = user.getUsername();
			if(this.supplierService.findSupplierByUsername(username).isPresent()){
				return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.headers(headers)
				.body(Problem.create()
					.withTitle("Already existing user")
					.withDetail("I wasn't possible to create an user because of username replication on the data."));
			}
			Set<Authorities> authorities = new HashSet<>();
			Authorities auth = new Authorities();
			auth.setAuthority("owner"); 
			auth.setUser(user);
			authorities.add(auth);
			user.setAuthorities(authorities);
			supplier.setUser(user);
			this.supplierService.save(supplier);
			this.userService.saveUser(user);
			this.authoritiesService.saveAuthorities(auth);
			return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(headers)
                    .body(supplier);

		}

	}

	private String getJWTToken(User user) {
		String secretKey = "mySecretKey";
		List<Authorities> authorities = new ArrayList<>(user.getAuthorities());
		String token = Jwts.builder().setId("softtekJWT").setSubject(user.getUsername())
				.claim("authorities", authorities.stream().map(Authorities::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + 6000000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	public String toJSON(String s) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		String json = "";
		try {
			json = mapper.writeValueAsString(s);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

}