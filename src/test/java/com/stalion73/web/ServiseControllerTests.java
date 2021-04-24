package com.stalion73.web;


import java.util.Date;
import java.util.stream.Collectors;
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
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.get;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@TestPropertySource("file:src/main/resources/application.properties")
public class ServiseControllerTests {

    private Integer id;
    private Integer size;
    private Servise servise;
    private RequestSpecification specification;

    private HttpHeaders headers = new HttpHeaders();

  
    @Value("${server.port}")
    private int portNumber;

    @Test
    public void randomEndPoint(){
        User user = new User();
        user.setUsername("josito");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());

        given().queryParam("username", "josito")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200);

        /*
         path("mystore.book.author[0]");
            System.out.println ("Here comes the Auther of the first book" + author);
        */
    }
  
    @Test
    public void oneGood() {
        User user = new User();
        user.setUsername("josito");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());

        Integer id = 1;
        given().pathParam("id", id)
            .basePath("/servises/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get().prettyPeek()
            .then()
            .statusCode(200);
        
        given().queryParam("username", "josito")
            .basePath("/users/logout")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(200);

    }

    @Test
    public void oneBad() {
        User user = new User();
        user.setUsername("josito");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());

        Integer id = Integer.MAX_VALUE - 1;
        Problem p = given().pathParam("id", id)
            .basePath("/servises/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get().prettyPeek()
            .then()
            .statusCode(404)
            .extract().body().as(org.springframework.hateoas.mediatype.problem.Problem.class);
        
        assertEquals("Ineffected ID", p.getTitle());
        assertEquals("The provided ID doesn't exist", p.getDetail());

        given().queryParam("username", "josito")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200);
    }

    @Test
    public void allGood(){
        User user = new User();
        user.setUsername("josito");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
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
            .get().prettyPeek()
            .then()
            .statusCode(200)
            .extract().body().as(servises.getClass());
        // this value shall be dynamically updated
        size = servises.size();
        assertEquals(servises.size(), size);

        given().queryParam("username", "josito")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200);

    }

    @Test
    public void createGood(){
        User user = new User();
        user.setUsername("aug");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());

        servise = new Servise();
        id = 1;
        servise.setName("name_1");
        servise.setDescription("description_1");
        given().pathParam("id", id)
            .basePath("/servises/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .body(servise)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(201);
        
        given().queryParam("username", "aug")
            .basePath("/users/logout")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    public void createBadNotOwned(){
        User user = new User();
        user.setUsername("aug");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());

        servise = new Servise();
        // aug doesn't own this servise
        id = 3;
        servise.setName("name_1");
        servise.setDescription("description_1");
        Problem p = given().pathParam("id", id)
            .basePath("/servises/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .body(servise)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(403)
            .extract().body().as(org.springframework.hateoas.mediatype.problem.Problem.class);
        
            assertEquals("Not owned", p.getTitle());
            assertEquals("The request servise is not up to your provided credentials.", p.getDetail());
        
        given().queryParam("username", "aug")
            .basePath("/users/logout")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    public void deleteGood(){
        User user = new User();
        user.setUsername("aug");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());

        List<Servise> servises = new ArrayList<>();
        id = given()
        .basePath("/servises")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract().body().as(servises.getClass()).size();

        given().pathParam("id", id)
        .basePath("/servises/{id}")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .delete().prettyPeek()
        .then()
        .statusCode(204);

        given().queryParam("username", "aug")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200);
        
    }

    @Test
    public void deleteBad(){
        User user = new User();
        user.setUsername("aug");
        user.setPassword("1234");
        User usr =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);
        
        headers.add("Authorization", usr.getToken());
        id = Integer.MAX_VALUE;

        Problem p = given().pathParam("id", id)
        .basePath("/servises/{id}")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .delete().prettyPeek()
        .then()
        .statusCode(404)
        .extract().body().as(Problem.class);

        assertEquals("Ineffected ID", p.getTitle());
        assertEquals("The provided ID doesn't exist", p.getDetail());

        given().queryParam("username", "aug")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200);
        
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
