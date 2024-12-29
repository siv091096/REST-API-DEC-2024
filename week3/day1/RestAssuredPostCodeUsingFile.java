package week3.day1;

import java.io.File;
import io.restassured.RestAssured;

public class RestAssuredPostCodeUsingFile {

	public static void main(String[] args) {
		
        String url = "https://dev202621.service-now.com/api/now/table/{tableName}";
		
		RestAssured.given()
		           .auth()
		           .basic("admin", "RxbDVA*v4!f8")
		           .pathParam("tableName", "incident")
		           .header("Content-Type", "application/json")
		           .log().all() // Request Log
		           .when()
		           .body(new File("src/main/resources/Incident-Data.json"))
		           .post(url)
		           .then()
		           .log().all() // Response Log
		           .assertThat()
		           .statusCode(201);

	}

}
