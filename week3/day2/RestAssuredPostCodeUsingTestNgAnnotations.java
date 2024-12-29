package week3.day2;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;

public class RestAssuredPostCodeUsingTestNgAnnotations {
	
	public String url = "https://dev202621.service-now.com/api/now/table/{tableName}";
	
	IncidentRequestPayLoad request_payload = new IncidentRequestPayLoad();
    
	@DataProvider()
	public String[][] CreateTestData(){
		
		return new String[][] {
			
			{"Create a New Record 1 Using POJO Class", "New Record 1 - POJO Class"},
			{"Create a New Record 2 Using POJO Class", "New Record 2 - POJO Class"}
			
		};
		
	}
	
	@Test(dataProvider = "CreateTestData")
    public void CreateNewIncident(String Description, String ShortDescription) {
    	   
    	   request_payload.setDescription(Description);
    	   request_payload.setShort_description(ShortDescription);
    	   
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
