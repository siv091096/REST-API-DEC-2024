package week3.day2;

import io.restassured.RestAssured;

public class RestAssuredPostCodeUsingObject {
	
	public static void main(String[] args) {
		
        String url = "https://dev202621.service-now.com/api/now/table/{tableName}";
        
        // POJO -> Plain Old Java Object
        IncidentRequestPayLoad request_payload = new IncidentRequestPayLoad();
        request_payload.setDescription("Create a New Record Using POJO Class");
        request_payload.setShort_description("New Record - POJO Class");
		
        	
        RestAssured.given()
	               .auth()
	               .basic("admin", "RxbDVA*v4!f8")
	               .pathParam("tableName", "incident")
	               .header("Content-Type", "application/json")
	               .log().all() // Request Log
	               .when()
	               .body(request_payload)
	               .post(url)
	               .then()
	               .log().all() // Response Log
	               .assertThat()
	               .statusCode(201);
		
	}
		
        
}
