package week3.day1;

import io.restassured.RestAssured;

public class RestAssuredDeleteCode {

	public static void main(String[] args) {
		
        String url = "https://dev202621.service-now.com/api/now/table/{tableName}/{sysId}";
		
		RestAssured.given()
		           .auth()
		           .basic("admin", "RxbDVA*v4!f8")
		           .pathParam("tableName", "incident")
		           .pathParam("sysId", "d6f055a283625210260ff3a6feaad3a2")
		           .header("Content-Type", "application/json")
		           .log().all() // Request Log
		           .when()
		           .delete(url)
		           .then()
		           .log().all() // Response Log
		           .assertThat()
		           .statusCode(204);

	}

}
