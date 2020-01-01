package pageobject;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.springframework.stereotype.Component;
import pojo.Employee;

import static com.jayway.restassured.RestAssured.given;

@Component
public class SampleGet {

    public Response doGetResponse(String endpoint,String queryParam, String queryText){
        return given().log().all().queryParam(queryParam,queryText).accept(ContentType.JSON).when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
    }

    public Response doGetResponse(String endpoint){
        return given().log().all().accept(ContentType.JSON).when().get(endpoint).then().contentType(ContentType.JSON).extract().response();
    }
    public Response doGetResponse(String endPoint, String pathParam) {
        return given().pathParam("resource",pathParam).log().all().when().get(endPoint).then().extract().response();
    }

    public Response doBasicAuthentication(String endpoint, String userName, String password){
        return given().auth().preemptive().basic(userName,password).when().get(endpoint).then().extract().response();
    }

    public Response doPostGenerateAuthToken(String endpoint, String clientId, String clientSecret) {
        return given().formParameter("client_id",clientId).formParameter("client_secret",clientSecret).formParameter("grant_type","client_credentials").when().post(endpoint).then().extract().response();
    }

    public Response doPostAuthenticationWithOAuth2(String endpoint, String token,String pathParam) {
        return given().auth().oauth2(token).pathParam("User_ID",pathParam).when().post(endpoint).then().extract().response();
    }

    public Response doPostAuthenticationWithBody(String endpoint, Employee employee) {
        return given().log().all().when().body(employee).post(endpoint).then().extract().response();
    }
}
