package stepDefinitions;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.createBookingPayload;
import resources.BookerAPIEnum;
import resources.testdataBuild;
import resources.utils;
import sun.security.timestamp.TSResponse;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class stepDefiniton extends utils {
    RequestSpecification req;
    ResponseSpecification res;
    boolean depositPaid2;
    Response response;



    testdataBuild data = new testdataBuild();

    @Given("a payload with booking details {string} {string} {int} {string} {string} {string} {string}")
    public void a_payload_with_booking_details(String firstname,
                                               String lastname,
                                               int totalprice,
                                               String depositpaid,
                                               String checkin,
                                               String checkout,
                                               String needs) throws IOException {

       depositPaid2 = Boolean.parseBoolean(depositpaid);
        req =  given().log().all().spec(requestSpec()).body(data.createBookingReq(firstname,lastname,totalprice,depositPaid2,checkin,checkout,needs));
    }


    @When("the user calls {string} with {string} request")
    public void the_user_calls_with_request(String resource, String method) {
       //constructor of BookerAPIEnum will ge called passing the resource value
        BookerAPIEnum resourceAPI = BookerAPIEnum.valueOf(resource);
        System.out.println(resourceAPI.getResource());
        //req.when().post("/booking").

        if(method.equalsIgnoreCase("POST"))
            response = req.when().post(resourceAPI.getResource());
        else if(method.equalsIgnoreCase("GET"))
            response = req.when().get(resourceAPI.getResource());
    }


    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expectedStatusCode) {

        assertEquals(response.getStatusCode(),200);
        String actualStatusCode= getJsonPath(response,"status");
        assertEquals(actualStatusCode,expectedStatusCode);


    }
    @Then("the booking id is stored")
    public void the_booking_id_is_stored() {

        String bookingid = getJsonPath(response,"bookingid");

    }



}
