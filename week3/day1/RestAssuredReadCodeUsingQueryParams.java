package week3.day1;

import io.restassured.RestAssured;

public class RestAssuredReadCodeUsingQueryParams {

	public static void main(String[] args) {
		
        String url = "https://dev202621.service-now.com/api/now/table/{tableName}";
		
		RestAssured.given()
		           .auth()
		           .basic("admin", "RxbDVA*v4!f8")
		           .pathParam("tableName", "incident")
		           .queryParam("sysparm_limit", 10)
		           .queryParam("sysparm_fields", "number,sys_id,short_description,active")
		           .log().all() // Request Log
		           .when()
		           .get(url)
		           .then()
		           .log().all() // Response Log
		           .assertThat()
		           .statusCode(200);

	}

}
