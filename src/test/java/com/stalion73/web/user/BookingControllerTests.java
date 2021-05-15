package com.stalion73.web.user;

import java.util.Random;
import java.util.List;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.stalion73.model.User;
import com.stalion73.model.Consumer;
import com.stalion73.model.Booking;


import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static io.restassured.RestAssured.given;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import org.springframework.http.HttpHeaders;


import org.springframework.hateoas.mediatype.problem.Problem;
import org.testng.annotations.Test;

public class BookingControllerTests {
    
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
        List<Booking> bookings = new ArrayList<>();
        bookings = given()
            .basePath("/bookings")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get().prettyPeek()
            .then()
            .statusCode(200)
            .extract().body().as(bookings.getClass());
        
        assertThat(bookings.size()).isGreaterThan(0);
    }

    @Test
    public void one(){
        Integer id = 2;
        given().pathParam("id", id)
            .basePath("/bookings/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(headers)
            .when()
            .get().prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    public void create() throws ParseException{
        Booking booking = new Booking();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("3000-05-14 15:23");
        booking.setBookDate(date);
        Integer id = 5;

        given().pathParam("id", id)
            .basePath("/bookings/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(booking)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    public void createAutomated() throws ParseException{
        Booking booking = new Booking();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("3000-05-14 15:23");
        booking.setBookDate(date);
        Integer id = 1;

        given().pathParam("id", id)
            .basePath("/bookings/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(booking)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    public void createBindingErrors() throws ParseException{
        Booking booking = new Booking();
        Date date = new Date();
        booking.setBookDate(date);
        Integer id = 1;

        Problem p = given().pathParam("id", id)
            .basePath("/bookings/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(booking)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(400)
            .extract().body().as(Problem.class);

        assertEquals("Error de validación", p.getTitle());
        assertEquals("La reserva no se ha podido validar correctamente.", p.getDetail());

    }

    @Test
    public void createBadTime() throws ParseException{
        Booking booking = new Booking();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2021-05-14 22:23");
        booking.setBookDate(date);
        Integer id = 1;

        Problem p = given().pathParam("id", id)
            .basePath("/bookings/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(booking)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(400)
            .extract().body().as(Problem.class);

        assertEquals("Error de validación", p.getTitle());
        assertEquals("La reserva no se ha podido validar correctamente.", p.getDetail());
    }

    @Test
    public void createNonExistent() throws ParseException{
        Booking booking = new Booking();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = format.parse("2021-05-14 15:23");
        booking.setBookDate(date);
        Integer id = Integer.MAX_VALUE;

        Problem p = given().pathParam("id", id)
            .basePath("/bookings/{id}")
            .port(8080)
            .contentType("application/json")
            .headers(this.headers)
            .body(booking)
            .when()
            .post().prettyPeek()
            .then()
            .statusCode(400)
            .extract().body().as(Problem.class);

        assertEquals("Error de validación", p.getTitle());
        assertEquals("La reserva no se ha podido validar correctamente.", p.getDetail());
    }

    @Test
    public void cancel(){
        Integer id = 4;

        given().pathParam("id", id)
            .basePath("/bookings/{id}/cancel")
            .port(8080)
            .headers(this.headers)
            .when()
            .delete().prettyPeek()
            .then()
            .statusCode(200);
    }

    @Test
    public void cancelMethodNotAllowed(){
        Integer id = 16;

        Problem p = given().pathParam("id", id)
            .basePath("/bookings/{id}/cancel")
            .port(8080)
            .headers(this.headers)
            .when()
            .delete().prettyPeek()
            .then()
            .statusCode(405)
            .extract().body().as(Problem.class);

        assertEquals("Cancelación no permitida", p.getTitle());

    }

    @Test
    public void cancelNotOwned(){
        Integer id = 3;

        Problem p = given().pathParam("id", id)
            .basePath("/bookings/{id}/cancel")
            .port(8080)
            .headers(this.headers)
            .when()
            .delete().prettyPeek()
            .then()
            .statusCode(403)
            .extract().body().as(Problem.class);
        
        assertEquals("Servicio innaccesible", p.getTitle());
        assertEquals("Este servicio no está en la lista de servicios disponibles.", p.getDetail());
    }

    @Test
    public void deleteGood(){
        List<Booking> priorBookings = new ArrayList<>();
        priorBookings = given()
        .basePath("/bookings")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(priorBookings.getClass());

        Integer priorSize = priorBookings.size();
        assertThat(priorSize).isGreaterThan(0);

        Random randomGenerator = new Random();
        Integer rand =  randomGenerator.nextInt(priorSize);

        // Integer bookingId = priorBookings.get(rand);


        given().pathParam("id", 4)
        .basePath("/bookings/{id}")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .delete().prettyPeek()
        .then()
        .statusCode(204);

        List<Booking> postBookings = new ArrayList<>();
        postBookings = given()
        .basePath("/bookings")
        .port(8080)
        .contentType("application/json")
        .headers(headers)
        .when()
        .get().prettyPeek()
        .then()
        .statusCode(200)
        .extract().body().as(postBookings.getClass());

        Integer postSize = postBookings.size();
        assertThat(priorSize).isGreaterThan(postSize);
    }

    @Test
    public void deleteIneffectedId(){
       
    Problem p =  given().pathParam("id", Integer.MAX_VALUE)
        .basePath("/bookings/{id}")
        .port(8080)
        .contentType("application/json")
        .headers(this.headers)
        .when()
        .delete().prettyPeek()
        .then()
        .statusCode(404)
        .extract().body().as(Problem.class);

        assertEquals("Reserva incorrecta", p.getTitle());
        assertEquals("La reserva no existe.", p.getDetail());
        
    }
}
