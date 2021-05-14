package com.stalion73.web.user;

import java.util.Random;

import com.stalion73.model.User;
import com.stalion73.model.Consumer;


import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

import org.springframework.http.HttpHeaders;


import org.springframework.hateoas.mediatype.problem.Problem;
import org.testng.annotations.Test;

public class UserControllerTests {

    HttpHeaders headers = new HttpHeaders();
    
    @Test
    public void loginGood(){
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
        
        String token = usr.getToken();
        this.headers.add("Authorization", token);
        assertThat(token).contains("Bearer");
    }

    @Test
    public void loginIneffectedId(){
        User user = new User();
        user.setUsername("non-exist");
        user.setPassword("1234");

        Problem p =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(404)
        .extract().body().as(Problem.class);
        
        assertEquals("Ineffected ID", p.getTitle());
        assertEquals("The provided ID doesn't exist", p.getDetail());
        
    }

    @Test
    public void loginBadCredentials(){
        User user = new User();
        user.setUsername("josito");
        user.setPassword("4321");

        Problem p =  given()
        .basePath("/users/login")
        .port(8080)
        .contentType("application/json")
        .body(user)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(400)
        .extract().body().as(Problem.class);
        
        assertEquals("Bad credentials", p.getTitle());
        assertEquals("The provided user credentials didn't match any already existing record.", p.getDetail());
    }

    @Test
    public void logout(){
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
        
        String token = usr.getToken();
        this.headers.add("Authorization", token);

        usr = given().queryParam("username", "josito")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(User.class);

        assertThat(usr.isEnabled()).isEqualTo(false);
    }

    @Test
    public void logoutIneffectedId(){
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
        
        String token = usr.getToken();
        this.headers.add("Authorization", token);

        Problem p = given().queryParam("username", "non-exist")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(404)
        .extract().body().as(Problem.class);

        assertEquals("Ineffected ID", p.getTitle());
        assertEquals("The provided ID doesn't exist", p.getDetail());

    }

    /*
    @Test
    public void logoutUserNotLoggedIn(){
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
        
        String token = usr.getToken();
        this.headers.add("Authorization", token);

        Problem p = given().queryParam("username", "marcos")
        .basePath("/users/logout")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(403)
        .extract().body().as(Problem.class);

        assertEquals("User Not logged in", p.getTitle());
        assertEquals("Impossible to logout an user that isn't currently logged in the system", p.getDetail());
    }
    */

    @Test
    public void profile(){
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
        
        String token = usr.getToken();
        this.headers.add("Authorization", token);

       given()
        .basePath("/users/profile")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body();
        
    }

    @Test
    public void signup(){
        Consumer consumer = new Consumer();
        Random rand = new Random();
        consumer.setName("name_" + Integer.valueOf(rand.nextInt()));
        consumer.setLastname("lastname_" + + Integer.valueOf(rand.nextInt()));
        consumer.setDni("00000000A");
        consumer.setEmail(Integer.valueOf(rand.nextInt()) + "@gmail.com");
        User user = new User();
        user.setUsername("new_username_" + Integer.valueOf(rand.nextInt()));
        user.setPassword("1234");
        consumer.setUser(user);

        given()
        .basePath("/users/signup/consumers")
        .port(8080)
        .contentType("application/json")
        .body(consumer)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(201);

        

    }

    @Test
    public void signupBindingErrors(){
        Consumer consumer = new Consumer();
        Random rand = new Random();
        consumer.setName("");
        consumer.setLastname("");
        consumer.setDni("00000000A");
        consumer.setEmail(Integer.valueOf(rand.nextInt()) + "@gmail.com");
        User user = new User();
        user.setUsername("new_username_" + Integer.valueOf(rand.nextInt()));
        user.setPassword("1234");
        consumer.setUser(user);

        Problem p = given()
        .basePath("/users/signup/consumers")
        .port(8080)
        .contentType("application/json")
        .body(consumer)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(400)
        .extract().body().as(Problem.class);


        assertEquals("Validation error", p.getTitle());
        assertEquals("The provided user was not successfuly validated", p.getDetail());
 
    }

    @Test
    public void signupAlreadyExistingUser(){
        Consumer consumer = new Consumer();
        Random rand = new Random();
        consumer.setName("name_" + Integer.valueOf(rand.nextInt()));
        consumer.setLastname("lastname_" + + Integer.valueOf(rand.nextInt()));
        consumer.setDni("00000000A");
        consumer.setEmail(Integer.valueOf(rand.nextInt()) + "@gmail.com");
        User user = new User();
        user.setUsername("josito");
        user.setPassword("1234");
        consumer.setUser(user);

        Problem p = given()
        .basePath("/users/signup/consumers")
        .port(8080)
        .contentType("application/json")
        .body(consumer)
        .when()
        .post().prettyPeek()
        .then()
        .statusCode(400)
        .extract().body().as(Problem.class);

        assertEquals("Already existing user", p.getTitle());
        assertEquals("I wasn't possible to create an user because of username replication on the data.", p.getDetail());


    }


}
