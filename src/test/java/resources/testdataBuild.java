package resources;

import pojo.BookingDates;
import pojo.createBookingPayload;

public class testdataBuild {




    public createBookingPayload createBookingReq(String firstname, String lastname, int totalprice, boolean depositPaid2, String checkin, String checkout, String additionalneeds){

        createBookingPayload c= new createBookingPayload();
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
}
