package tests;

import models.lombok.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.LoginSpecs.*;
import static specs.RegistrationSpecs.RegistrationPositiveRequestSpec;
import static specs.RegistrationSpecs.RegistrationPositiveResponseSpec;
import static specs.UpdateUserSpecs.UpdateUserRequestSpec;
import static specs.UpdateUserSpecs.UpdateUserResponseSpec;
import static specs.UserCreationSpecs.userCreationRequestSpec;
import static specs.UserCreationSpecs.userCreationResponseSpec;

public class RegresInTestsWithLombokAndSpecs {

    @Test
    @DisplayName("Update user")
    void updateUser() {

        UpdateUserLombok data = new UpdateUserLombok();
        data.setName("morpheus");
        data.setJob("zion resident");

        UpdateUserLombokResponce response = given()
                .spec(UpdateUserRequestSpec)
                .body(data)
                .when()
                .put()
                .then()
                .spec(UpdateUserResponseSpec)
                .extract().as(UpdateUserLombokResponce.class);

        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getJob()).isEqualTo("zion resident");
    }

    @Test
    @DisplayName("User creation")
    void createUser() {
        UserCreationLombok data = new UserCreationLombok();
        data.setName("morpheus");
        data.setJob("leader");

        UserCreationResponseLombok response = given()
                .spec(userCreationRequestSpec)
                .body(data)
                .when()
                .post()
                .then()
                .spec(userCreationResponseSpec)
                .extract().as(UserCreationResponseLombok.class);
        assertThat(response.getJob()).isEqualTo("leader");
        assertThat(response.getName()).isEqualTo("morpheus");
        assertThat(response.getCreatedAt()).isNotNull();
        assertThat(response.getId()).isNotNull();
    }

    @Test
    @DisplayName("Successful registration")
    void userRegister() {
        RegistrationPositiveLombok data = new RegistrationPositiveLombok();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");
        RegistrationPositiveResponseLombok response = given()
                .spec(RegistrationPositiveRequestSpec)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .spec(RegistrationPositiveResponseSpec)
                .extract().as(RegistrationPositiveResponseLombok.class);
        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
        assertThat(response.getId()).isEqualTo("4");
    }

    @Test
    @DisplayName("Login successful")
    void loginPositive() {

        LoginPositiveLombok body = new LoginPositiveLombok();
        body.setEmail("eve.holt@reqres.in");
        body.setPassword("cityslicka");

        LoginPositiveResponseLombok response = given()
                .spec(loginPositiveRequestSpec)
                .body(body)
                .when()
                .post()
                .then()
                .spec(loginPositiveResponseSpec)
                .extract().as(LoginPositiveResponseLombok.class);

        assertThat(response.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Login unsuccessful")
    void loginNegative() {

        LoginNegativeLombok data = new LoginNegativeLombok();
        data.setEmail("peter@klaven");

        LoginNegativeResponceLombok response = given()
                .spec(loginNegativeRequestSpec)
                .body(data)
                .when()
                .post()
                .then()
                .spec(loginNegativeResponseSpec)
                .extract().as(LoginNegativeResponceLombok.class);

        assertThat(response.getError()).isEqualTo("Missing password");
    }
}

