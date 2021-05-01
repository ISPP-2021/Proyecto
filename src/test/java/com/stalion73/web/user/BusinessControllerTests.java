package com.stalion73.web.user;
import java.util.Random;
import java.util.Set;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import com.stalion73.model.User;
import com.stalion73.model.Consumer;
import com.stalion73.model.Option;
import com.stalion73.model.Servise;
import com.stalion73.model.Supplier;
import com.stalion73.model.Booking;
import com.stalion73.model.Business;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.given;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.springframework.http.HttpHeaders;


import org.springframework.hateoas.mediatype.problem.Problem;
import org.testng.annotations.Test;

public class BusinessControllerTests {

    HttpHeaders headers = new HttpHeaders();

    @BeforeMethod
    public void loginToken(){
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
        
        this.headers.add("Authorization", usr.getToken());
    }

    @AfterMethod
    public void logout(){
        given().queryParam("username", "josito")
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
    public void all(){
        List<Business> businesses = new ArrayList<>();
        businesses = given()
            .basePath("/business")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get().prettyPeek()
            .then()
            .statusCode(200)
            .extract().body().as(businesses.getClass());
        
        assertThat(businesses.size()).isGreaterThan(0);
    }
    @Test
    public void one(){
        Integer id = 1;
        given().pathParam("id", id)
            .basePath("/business/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get().prettyPeek()
            .then()
            .statusCode(200);
    }

    
    
}
