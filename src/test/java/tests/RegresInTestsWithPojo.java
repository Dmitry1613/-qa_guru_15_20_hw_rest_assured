package tests;

import models.pojo.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;

public class RegresInTestsWithPojo {

    @Test
    @DisplayName("Update user")
    void updateUser() {

        UpdateUserPojo data = new UpdateUserPojo();
        data.setName("morpheus");
        data.setJob("zion resident");

        UpdateUserPojoResponse responce = given()
                .filter(withCustomTemplates())
                .log().uri()
                .contentType(JSON)
                .body(data)
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(UpdateUserPojoResponse.class);

        assertThat(responce.getName()).isEqualTo("morpheus");
        assertThat(responce.getJob()).isEqualTo("zion resident");
        assertThat(responce.getUpdatedAt()).isNotNull();
    }

    @Test
    @DisplayName("User creation")
    void createUser() {
        UserCreationPojo data = new UserCreationPojo();
        data.setName("morpheus");
        data.setJob("leader");

        UserCreationResponsePojo response = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().as(UserCreationResponsePojo.class);
        assertThat(response.getJob()).isEqualTo("leader");
        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    @DisplayName("Successful registration")
    void userRegister() {
        RegistrationPositivePojo data = new RegistrationPositivePojo();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");
        RegistrationPositiveResponsePojo response = given()
                .filter(withCustomTemplates())
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(RegistrationPositiveResponsePojo.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }

    @Test
    @DisplayName("Login successful")
    void loginPositive() {

        LoginPositivePojo body = new LoginPositivePojo();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginPositiveResponsePojo response = given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().as(LoginPositiveResponsePojo.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Login unsuccessful")
    void loginNegative() {

        LoginNegativePojo data = new LoginNegativePojo();
        data.setEmail("peter@klaven");

        LoginNegativeResponsePojo responce = given()
                .filter(withCustomTemplates())
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .extract().as(LoginNegativeResponsePojo.class);

        assertThat(responce.getError()).isEqualTo("Missing password");
    }
}

