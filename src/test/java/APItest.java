import API.Config;
import API.Update;
import API.Users;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


class APItest {
    static String endpoint = "https://reqres.in";

    @BeforeAll
    static void setUp() {
        Config.applyConfig(Config.setBaseUri(endpoint));
    }

    @Test
    void testAllUsers() {
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
    @Test
    public void testSingleUserPage() {
                 given()
                .when()
                .get("/api/users/2")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.first_name", equalTo("Janet"))
                .body("data.last_name", equalTo("Weaver"))
                .body("data.avatar", equalTo("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    public void testPerParameter() {
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
    public void testUserIsUpdate() {
        given().body(new Update("morpheus", "zion resident"))
                .when().put("/api/users/2")
                .then().log().body().assertThat().statusCode(200).body("job", equalTo("zion resident"));
    }

    @Test
    public void testUserIsDelete() {
        given()
                .when().delete("/api/users/2")
                .then().assertThat().statusCode(204);
    }

    @Test
    public void testAllUsersAreReturned() {
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
