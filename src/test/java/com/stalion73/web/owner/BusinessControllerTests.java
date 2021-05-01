package com.stalion73.web.owner;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
import com.stalion73.model.BusinessType;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.given;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.testng.annotations.Test;


public class BusinessControllerTests {

    HttpHeaders headers = new HttpHeaders();

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

    @Test
    public void createGood(){
        Business business = new Business();
        business.setName("name_1");
        business.setAddress("description_1");
        Servise servise = new Servise();
        servise.setDescription("sfajfal");
        servise.setName("sfajfal");
        business.addServise(servise);
        business.setAutomatedAccept(true);
        business.setCloseTime(LocalTime.of(10, 0, 0));
        business.setOpenTime(LocalTime.of(21, 0, 0));
        Option option = new Option();
        option.setDepositTimeLimit(1);
        option.setAutomatedAccept(true);
        business.setOption(option);
        business.setBusinessType(BusinessType.HAIRDRESSER);

        given()
            .basePath("/business")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(business)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(201);
    }

    @Test
    public void createBad(){

        Business business = new Business();
        business.setName("name_1");
        // business.setAddress("description_1");
        Servise servise = new Servise();
        servise.setDescription("sfajfal");
        servise.setName("sfajfal");
        business.addServise(servise);
        business.setAutomatedAccept(true);
        business.setCloseTime(LocalTime.of(10, 0, 0));
        business.setOpenTime(LocalTime.of(21, 0, 0));
        Option option = new Option();
        option.setDepositTimeLimit(1);
        option.setAutomatedAccept(true);
        business.setOption(option);
        business.setBusinessType(BusinessType.HAIRDRESSER);

        given()
            .basePath("/business")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(business)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(400);

    }

    @Test
    public void deleteGood(){
        List<Business> priorBusiness = new ArrayList<>();
        priorBusiness = given()
        .basePath("/business")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(priorBusiness.getClass());

        Integer priorSize = priorBusiness.size();
        assertThat(priorSize).isGreaterThan(0);

       

        given().pathParam("id", 1)
        .basePath("/business/{id}")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .delete().prettyPeek()
        .then()
        .statusCode(204);

        List<Business> postBusiness = new ArrayList<>();
        postBusiness = given()
        .basePath("/business")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(postBusiness.getClass());

        Integer postSize = postBusiness.size();
        assertThat(priorSize).isGreaterThan(postSize);
    }

    @Test
    public void deleteBad(){
        Integer id = Integer.MAX_VALUE;

        Problem p = given().pathParam("id", id)
        .basePath("/business/{id}")
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
    
    
}
