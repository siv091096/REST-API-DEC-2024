package week3.day2;

import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class RestAssuredCRUDOperationsUsingChainRequest {
	
    public String url = "https://dev202621.service-now.com/api/now/table/{tableName}";
	
	IncidentRequestPayLoad request_payload = new IncidentRequestPayLoad();
	
	String SysId;
    
	@DataProvider()
	public String[][] CreateTestData(){
		
		return new String[][] {
			
			{"Create a New Record Using POJO Class", "New Record - POJO Class"}
			
		};
		
	}
	
	@DataProvider()
	public String[][] UpdateTestData(){
		
		return new String[][] {
			
			{"Update a New Record Using POJO Class - Put", "New Record Update - POJO Class"}
			
		};
		
	}
	
	//CREATE OPERATION
	@Test(dataProvider = "CreateTestData", priority = 1)
    public void CreateNewIncident(String Description, String ShortDescription) {
    	   
    	   request_payload.setDescription(Description);
    	   request_payload.setShort_description(ShortDescription);
    	   
    	   SysId = RestAssured.given()
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
	
	//READ OPERATION
	@Test(priority = 2)
    public void ReadCreatedIncident() {
    	   
    	   RestAssured.given()
           .auth()
           .basic("admin", "RxbDVA*v4!f8")
           .pathParam("tableName", "incident")
           .pathParam("sys_id", SysId)
           .log().all() // Request Log
           .when()
           .get(url+"/{sys_id}")
           .then()
           .log().all() // Response Log
           .assertThat()
           .statusCode(200)
           .statusLine(Matchers.containsString("OK"))
    	   .body("result.sys_id", Matchers.equalTo(SysId));

	}
	
	//UPDATE OPERATION
	@Test(dataProvider = "UpdateTestData", priority = 3)
    public void UpdateCreatedIncident(String Description, String ShortDescription) {
		
	   request_payload.setDescription(Description);
 	   request_payload.setShort_description(ShortDescription);
    	   
    	   RestAssured.given()
           .auth()
           .basic("admin", "RxbDVA*v4!f8")
           .pathParam("tableName", "incident")
           .pathParam("sys_id", SysId)
           .header("Content-Type", "application/json")
           .log().all() // Request Log
           .when()
           .body(request_payload)
           .put(url+"/{sys_id}")
           .then()
           .log().all() // Response Log
           .assertThat()
           .statusCode(200)
           .statusLine(Matchers.containsString("OK"))
           .body("result.sys_id", Matchers.equalTo(SysId));

	}
	
	//DELETE OPERATION
	@Test(priority = 4)
    public void DeleteCreatedIncident() {
    	   
    	   RestAssured.given()
           .auth()
           .basic("admin", "RxbDVA*v4!f8")
           .pathParam("tableName", "incident")
           .pathParam("sys_id", SysId)
           .log().all() // Request Log
           .when()
           .delete(url+"/{sys_id}")
           .then()
           .log().all() // Response Log
           .assertThat()
           .statusCode(204)
           .statusLine(Matchers.containsString("No Content"));

	}
	
	//CONFIRMATION OF DELETE OPERATION
	@Test(priority = 5)
    public void ConfirmtheDeletionOfCreatedIncident() {
    	   
    	   RestAssured.given()
           .auth()
           .basic("admin", "RxbDVA*v4!f8")
           .pathParam("tableName", "incident")
           .pathParam("sys_id", SysId)
           .log().all() // Request Log
           .when()
           .get(url+"/{sys_id}")
           .then()
           .log().all() // Response Log
           .assertThat()
           .statusCode(404)
           .statusLine(Matchers.containsString("Not Found"));

	}

}
