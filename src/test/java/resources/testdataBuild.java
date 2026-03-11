package resources;

import pojo.BookingDates;
import pojo.createBookingPayload;

public class testdataBuild {

    createBookingPayload c= new createBookingPayload();


    public createBookingPayload createBookingReq(String firstname, String lastname, int totalprice, boolean depositPaid2, String checkin, String checkout, String additionalneeds){


        c.setFirstname(firstname);
        c.setLastname(lastname);
        c.setTotalprice(totalprice);
        c.setDepositpaid(depositPaid2);
        c.setAdditionalneeds(additionalneeds);
        BookingDates d=new BookingDates();
        d.setCheckin(checkin);
        d.setCheckout(checkout);
        c.setBookingDates(d);

        return c;



    }

    public String updateBookingReq(String lastname, String additionalneeds){

        return "{\n" +
                "    \"lastname\" : \"" + lastname + "\",\n" +
                "    \"additionalneeds\" : \"" + additionalneeds + "\"\n" +
                "}";

    }

}
