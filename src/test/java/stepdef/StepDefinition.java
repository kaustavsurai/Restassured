package stepdef;

import com.jayway.restassured.response.Response;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import pageobject.SampleGet;
import pojo.Dao;
import pojo.Employee;
import pojo.JsonFormatter;

import java.io.IOException;
import java.util.List;

public class StepDefinition extends AbstractDefinition {

    @Autowired
    private SampleGet sampleGet;

    @Autowired
    private Dao dao;

    @Autowired
    private JsonFormatter jsonFormatter;

    Response response;
    List<Employee> employees;
    private String token;

    @Given("I hit api {string} with query parameter {string} query parameter value {string}  path parameter {string}")
    public void i_hit_api_with_query_parameter_query_parameter_value(String endPoint, String queryParam, String queryText,String pathParam) {
        if(StringUtils.isNotBlank(queryParam) && StringUtils.isNotBlank(queryText))
            response=sampleGet.doGetResponse(endPoint, queryParam, queryText);
        else if (StringUtils.isNotBlank(pathParam)) response=sampleGet.doGetResponse(endPoint,pathParam);
        else response=sampleGet.doGetResponse(endPoint);
    }

    @Given("I hit api {string} with username {string} password {string}")
    public void i_hit_api_with_username_password(String endPoint, String userName, String password) {
        response = sampleGet.doBasicAuthentication(endPoint, userName, password);
    }

    @Then("validate Response code is {string}")
    public void validate_Response_code_is(String string) {
        Assert.assertEquals("validate response code",Integer.parseInt(string),response.getStatusCode());
    }

    @Then("validate Total Number of Record is {string}")
    public void validate_Total_Number_of_Record_is(String string) {
        response.then().assertThat().body("MRData.CircuitTable.Circuits", Matchers.hasSize(Integer.parseInt(string)));
    }

    @Then("validate {string} is not present")
    public void validate_is_not_present(String string) {
        response.then().assertThat().body("MRData.CircuitTable.Circuits.Location.country",Matchers.not(Matchers.hasItem(string)));
    }


    @Then("verify longitude is {string} when locality is {string}")
    public void verify_longitude_is_when_locality_is(String longitude, String locality) {
        Assert.assertEquals(longitude,response.jsonPath().get("MRData.CircuitTable.Circuits.Location.find {locality='"+locality+"'}.long"));
    }

    @Then("verify circuit id is {string} when locality is {string}")
    public void verify_circuit_id_is_when_locality_is(String circuitId, String locality) {
        String str = response.jsonPath().get("MRData.CircuitTable.Circuits.Location.find {locality = '"+locality+"'}").toString();
        Assert.assertEquals(circuitId,response.jsonPath().get("MRData.CircuitTable.Circuits.find {Location = '"+str+"'}.circuitId").toString());
    }

    @Then("Verify Json Schema {string}")
    public void verify_Json_Schema(String fileName) {
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(fileName));
    }

    @Then("I verify Response Header server is {string}")
    public void i_verify_Response_Header_server_is(String header) {
        Assert.assertEquals(header,response.getHeader("Server"));
    }

    @Given("I hit api {string} with Clientid {string} ClientSecret {string}")
    public void i_hit_api_with_Clientid_ClientSecret(String endpoint, String clientId, String clientSecret) {
        response =sampleGet.doPostGenerateAuthToken(endpoint,clientId,clientSecret);
    }

    @Then("I receive Json Token")
    public void i_receive_Json_Token() {
        token = response.jsonPath().get("access_token");
    }

    @Given("I hit api {string} with token and path parameter {string}")
    public void i_hit_api_with_token(String endpoint,String pathParam) {
        response = sampleGet.doPostAuthenticationWithOAuth2(endpoint,token,pathParam);
    }

    @Given("I run {string}")
    public void i_run(String query) {
        employees = dao.getEmployee(query);
    }


    @When("I hit api {string} post with json body")
    public void i_hit_api_post_with_json_body(String endpoint) {
        for(Employee employee:employees){
           response =sampleGet.doPostAuthenticationWithBody(endpoint,employee);
        }
    }

















}
