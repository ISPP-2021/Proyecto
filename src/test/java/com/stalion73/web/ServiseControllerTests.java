package com.stalion73.web;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

import com.stalion73.model.Servise;
import com.stalion73.model.User;
import com.stalion73.security.JWTAuthorizationFilter;
import com.stalion73.model.Authorities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;


import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@TestPropertySource("file:src/main/resources/application.properties")
public class ServiseControllerTests {
    
    private Integer id;
    private Servise servise;
    private RequestSpecification specification;

    private final static HttpHeaders headers = new HttpHeaders();

  
    @Value("${server.port}")
    private int portNumber;
  
    @Test
    public void one() {
        User user = new User();
        user.setUsername("josito");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);

        
        headers.add("Authorization", usr.getToken());
       
        List<Servise> servises = new ArrayList<>();
        servises = given()
            .basePath("/servises")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get()
            .then()
            .statusCode(200)
            .extract().body().as(servises.getClass());

            given()
                .basePath("/servises/1")
                .port(8080)
                .contentType("application/json")
                .headers(headers)
                .when()
                .get().prettyPrint();

            given()
                .basePath("/servises/1")
                .port(8080)
                .contentType("application/json")
                .headers(headers)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().body();
              
             

       //get("/servises?id=1").then().statusCode(200);

       //get("/servises?id=1").then().statusCode(200).assertThat()
        //.body("id", equalTo(1)); 
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
  
}
