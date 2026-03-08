package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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


    //create booking API

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
        }


        System.out.println("Response received: " + response.asString());
    }

    @Then("the response status should be {int}")
    public void the_response_status_should_be(Integer expectedStatusCode) throws IOException {

        assertEquals((int) expectedStatusCode, response.getStatusCode());
    }


    @Then("the booking id is stored")
    public void the_booking_id_is_stored(){
        String bookingId = getJsonPath(response, "bookingid");
        System.out.println("Booking ID: " + bookingId);
    }


    // authorisation API


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
            String token = getJsonPath(authRes, "token");
            System.out.println("Token: " + token);
        }

}
