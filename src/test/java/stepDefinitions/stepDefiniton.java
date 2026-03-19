package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.authorisationResponse;
import resources.BookerAPIEnum;
import resources.testdataBuild;
import resources.utils;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class stepDefiniton extends utils {


    RequestSpecification req;
    Response response;
    boolean depositPaid2;
    testdataBuild data = new testdataBuild();
    RequestSpecification authReq;
    Response authRes;
    static String token;
    static String bookingId;


    //create booking API   POST

    @Given("a payload with booking details {string} {string} {int} {string} {string} {string} {string}")
    public void a_payload_with_booking_details(String firstname,
                                               String lastname,
                                               int totalprice,
                                               String depositpaid,
                                               String checkin,
                                               String checkout,
                                               String needs) throws IOException {
        // convert deposit from string to boolean
        depositPaid2 = Boolean.parseBoolean(depositpaid);

        // build the request with payload
        req = given()
                .log().all()
                .spec(requestSpec())
                .body(data.createBookingReq(firstname, lastname, totalprice, depositPaid2, checkin, checkout, needs));

        System.out.println("Request body prepared: " + data.createBookingReq(firstname, lastname, totalprice, depositPaid2, checkin, checkout, needs));
    }

    @When("the user calls {string} with {string} request")
    public void the_user_calls_with_request(String resource, String method) {
        BookerAPIEnum resourceAPI = BookerAPIEnum.valueOf(resource);
        System.out.println("Calling API: " + resourceAPI.getResource());

        if (method.equalsIgnoreCase("POST")) {
            response = req.when().post(resourceAPI.getResource());
        } else if (method.equalsIgnoreCase("GET")) {
            response = req.when().get(resourceAPI.getResource());
        } else if (method.equalsIgnoreCase("PUT")) {
            response = req.when().put(resourceAPI.getResource());
        } else if (method.equalsIgnoreCase("PATCH")) {
            response = req.when().patch(resourceAPI.getResource());
        } else if ( method.equalsIgnoreCase("DELETE")) {
            response = req.when().delete(resourceAPI.getResource());
        }

        System.out.println("Response received: " + response.asString());
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expectedStatusCode) throws IOException {

        assertEquals((int) expectedStatusCode, response.getStatusCode());
    }


    @Then("the booking id is stored")
    public void the_booking_id_is_stored(){
         bookingId = getJsonPath(response, "bookingid");
        System.out.println("Booking ID: " + bookingId);
    }


    // authorisation  Auth


    @Given("there are credentials {string} and {string}")
    public void there_are_credentials_and(String username, String password) throws IOException {
        authReq = given()
                .spec(requestSpec()) // clean base spec
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
        System.out.println("Auth request prepared: " + authReq);
    }

    @When("the user calls the authAPI {string} with {string} request")
    public void call_auth_api(String resource, String method) {
        BookerAPIEnum resourceAPI = BookerAPIEnum.valueOf(resource);

        if (method.equalsIgnoreCase("POST")) {
            authRes = authReq.when().post(resourceAPI.getResource());
        }

        System.out.println("Auth response: " + authRes);

    }
    @Then("the token generated is stored")
    public void store_token() {
        //authorisationResponse a=new authorisationResponse(authRes);
        //token=a.getToken();


    token = getJsonPath(authRes, "token");
       System.out.println("Token: " + token);

    }


    //updating the created booking PUT,GET
    @Given("the user updates the booking with {string} {string} {int} {string} {string} {string} {string} using the generated token")
    public void the_user_updates_the_booking_with_using_the_generated_token(String firstname,
                                                                            String lastname,
                                                                            int totalprice,
                                                                            String depositpaid,
                                                                            String checkin,
                                                                            String checkout,
                                                                            String needs) throws IOException {
        Boolean depostiPaid3 = Boolean.parseBoolean(depositpaid);

        // Make sure bookingId is not null
        if (bookingId == null || bookingId.isEmpty()) {
            throw new RuntimeException("Booking ID is not set. Create a booking first!");
        }

        req = given() .log().all() .spec(requestSpec()) .pathParam("id", bookingId)
                .header("Cookie","token=" + token) .body(data.createBookingReq(firstname, lastname, totalprice, depostiPaid3, checkin, checkout, needs));


        // Response updateResponse =req.when().put("/booking/{id}"). then().statusCode(200).extract().response();
        //System.out.println ("The update response is"+updateResponse.asString());


    }


    @Then("the response field {string} should be {string}")
    public void the_response_field_should_be(String actualFirstName, String expectedFirstName) {
        actualFirstName = getJsonPath(response,"firstname");
        assertEquals(actualFirstName,expectedFirstName);

    }

//Partially update the request PATCH,GET

    @Given("the user prepares partial update payload with {string} and {string}")
    public void the_user_prepares_partial_update_payload_with_and(String lastname, String additionalneeds) throws IOException {

        req =given().spec(requestSpec()).pathParam("id", bookingId)
                .header("Cookie","token=" + token).body(data.updateBookingReq(lastname,additionalneeds));

    }
    @Then("the response fields should have {string} {string} same as {string} {string}")
    public void the_response_fields_should_have_same_as(String actualLastName, String actualAdditionalNeeds, String expectedLastname, String expectedAdditionalneeds) {

        actualLastName = getJsonPath(response,"lastname");
        actualAdditionalNeeds=getJsonPath(response,"additionalneeds");
        assertEquals(actualLastName,expectedLastname);
        assertEquals(actualAdditionalNeeds,expectedAdditionalneeds);



    }


    //Delete booking
    @Given("the user has bookingid")
    public void the_user_has_bookingid() throws IOException {
        req = given().spec(requestSpec()).pathParam("id",bookingId).header("Cookie","token=" + token);
    }
    @Then("the response should have {string}")
    public void the_response_should_have(String expectedResponse) {

        String actualResponse=response.getBody().asString();
        assertEquals(actualResponse, expectedResponse);

    }


}
