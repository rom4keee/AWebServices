import API.Config;
import API.Create;
import API.Update;
import API.Users;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;



class ApiTests {
    static String endpoint = "https://reqres.in";

    @BeforeAll
    static void setUp() {
        Config.applyConfig(Config.setBaseUri(endpoint));
    }

    @Test
   void VerifyAllUserData() {
        given()
                .when().get("/api/users/")
                .then().log().body()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("data.id", notNullValue())
                .body("data.email", notNullValue())
                .body("data.first_name", notNullValue())
                .body("data.last_name", notNullValue())
                .body("data.avatar", notNullValue());
    }

    public String formattedTime() {
        return ZonedDateTime
                .now()
                .format(DateTimeFormatter
                        .ofPattern("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH)
                        .withZone(ZoneOffset.UTC));
    }

    @Test
    void verifyPerPageParameter() {
        int numberOfUsersPerPage = given()
                .when().get("/api/users?page=1&per_page=2")
                .then().extract().body().jsonPath().get("per_page");

        List<Users> users = given()
                .when()
                .get("/api/users?page=1&per_page=2")
                .then().log().body()
                .extract().body().jsonPath().getList("data", Users.class);

        assertEquals(numberOfUsersPerPage, users.size());
    }

    @Test
    void verifyUserIsUpdate() {
        given().body(new Update("morpheus", "zion resident"))
                .when().put("/api/users/2")
                .then().log().body().assertThat().statusCode(200).body("job", equalTo("zion resident"));
    }

    @Test
    void verifyUserIsDelete() {
        given()
                .when().delete("/api/users/2")
                .then().assertThat().statusCode(204);
    }

    @Test
    void verifyAllUsersAreReturned() {
        int totalNumberOfUsers = given()
                .when().get("/api/users?page=2")
                .then().extract().body().jsonPath().get("total");

        List<Users> users = given()
                .when()
                .get("/api/users?page=1&per_page=12")
                .then().log().body()
                .extract().body().jsonPath().getList("data", Users.class);

        assertEquals(totalNumberOfUsers, users.size());
    }
}
