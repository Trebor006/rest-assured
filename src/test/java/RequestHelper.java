import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class RequestHelper {

    public static Response get(String url){
        return RestAssured.when().get(url);
    }

    public static Response getById(String url, String id){
        return RestAssured
                .given().pathParams("id", id)
                .when().get(url);
    }

    public static Response post(String url, String payload){
        return RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .when().post(url);
    }

    public static Response put(String url, String id, String payload){
        return RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(payload)
                .pathParams("id", id)
                .when().put(url);
    }
    public static Response delete(String url, String id){
        return RestAssured
                .given().pathParams("id", id)
                .when().delete(url);
    }
}
