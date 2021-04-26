package com.stalion73.web.owner;

import java.util.Date;
import java.util.stream.Collectors;


import java.util.List;
import java.util.Random;
import java.util.ArrayList;

import com.stalion73.model.Servise;
import com.stalion73.model.User;
import com.stalion73.model.Authorities;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import io.restassured.specification.RequestSpecification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.test.context.TestPropertySource;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
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

    @BeforeMethod
    public void loginToken(){
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
        
        this.headers.add("Authorization", usr.getToken());
    }

    @AfterMethod
    public void logout(){
        given().queryParam("username", "aug")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200);
    }



    @Test
    public void createGood(){
        servise = new Servise();
        id = 1;
        servise.setName("name_1");
        servise.setDescription("description_1");
        given().pathParam("id", id)
            .basePath("/servises/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(servise)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(201);
    }

    @Test
    public void createBadNotOwned(){
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
        
    }

    @Test
    public void deleteGood(){
        List<Servise> priorServises = new ArrayList<>();
        priorServises = given()
        .basePath("/servises")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(priorServises.getClass());

        Integer priorSize = priorServises.size();
        assertThat(priorSize).isGreaterThan(0);

        Random randomGenerator = new Random();
        Integer rand =  randomGenerator.nextInt(priorSize);

        given().pathParam("id", rand)
        .basePath("/servises/{id}")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .delete().prettyPeek()
        .then()
        .statusCode(204);

        List<Servise> postServises = new ArrayList<>();
        postServises = given()
        .basePath("/servises")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(postServises.getClass());

        Integer postSize = postServises.size();
        assertThat(priorSize).isGreaterThan(postSize);
    }

    @Test
    public void deleteBad(){
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
