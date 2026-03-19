package stepDefinitions;


import io.cucumber.java.Before;

public class hooks {

    @Before("@DeleteBooking")
    public void beforeDeleteBookingScenario() {

        stepDefiniton s=new stepDefiniton();
        if(stepDefiniton.bookingId==null){

            s.the_user_calls_with_request("createBooking","POST");
            s.the_booking_id_is_stored();
            s.the_user_calls_with_request("bookingById", "GET");

        }
    }

}
