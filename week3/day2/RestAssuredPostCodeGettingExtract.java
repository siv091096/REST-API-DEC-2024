package week3.day2;

import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RestAssuredPostCodeGettingExtract {
	
	public String url = "https://dev202621.service-now.com/api/now/table/{tableName}";
	
	IncidentRequestPayLoad request_payload = new IncidentRequestPayLoad();
    
	@DataProvider()
	public String[][] CreateTestData(){
		
		return new String[][] {
			
			{"Create a New Record 1 Using POJO Class", "New Record 1 - POJO Class"}
			
		};
		
	}
	
	@Test(dataProvider = "CreateTestData")
    public void CreateNewIncident(String Description, String ShortDescription) {
    	   
    	   request_payload.setDescription(Description);
    	   request_payload.setShort_description(ShortDescription);
    	   
    	   String SysId = RestAssured.given()
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
           .statusCode(201)
           .statusLine(Matchers.containsString("Created"))
           .contentType(ContentType.JSON)
           .time(Matchers.lessThan(5000L))
           .extract()
           .jsonPath()
           .getString("result.sys_id");
    	   
    	   System.out.println("System Id: "+SysId);

	}
		
        
}
