package resources;

public enum BookerAPIEnum {

    createBooking("/booking"),
        createTokenAPI("/auth"),
            updateBooking("/booking"),
    partialUpdateBooking("/booking");
    private String resource;


    BookerAPIEnum(String resource){
        this.resource=resource;
    }


    public String getResource(){
        return resource;
    }

}
