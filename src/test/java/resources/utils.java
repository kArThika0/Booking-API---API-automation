package resources;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class utils {

    public String getJsonPath(Response response, String key)
    {
        String resp=response.asString();
        JsonPath   js = new JsonPath(resp);
        return js.get(key).toString();
    }

    public static String getGlobalValue(String key) throws IOException
    {
        Properties prop =new Properties();
        FileInputStream fis =new FileInputStream("E:\\Road to SDET - Materials\\BDD_Framwork_restful_booker\\src\\test\\java\\resources\\global.properties");
        prop.load(fis);
        return prop.getProperty(key);

    }


    public RequestSpecification requestSpec() throws IOException {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseUrl")).setContentType(ContentType.JSON)
                .build();
        return req;

    }

    public ResponseSpecification responseSpec(){
        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).build();
        return res;

    }


}
