package API;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Config {

    private Config() {
        throw new IllegalStateException("Utility class");
    }
    public static RequestSpecification setBaseUri(String uri) {
        return new RequestSpecBuilder()
                .setBaseUri(uri)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static void applyConfig(RequestSpecification endpoint) {
        RestAssured.requestSpecification = endpoint;
    }
}
