package resources;

public enum BookerAPIEnum {

    createBooking("/booking"),
        createTokenAPI("/auth"),
            updateBooking("/booking/{id}"),
    bookingById("/booking/{id}"),
    partialUpdateBooking("/booking/{id}");
    private String resource;


    BookerAPIEnum(String resource){
        this.resource=resource;
    }


    public String getResource(){
        return resource;
    }

}
