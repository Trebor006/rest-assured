import com.google.gson.Gson;
import entities.Employee;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

public class RestAssuredTest {

    @Test
    public void getEmployeesTest() {
        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured.when().get("/employees");
        response.then().log().body();
    }

    @Test
    public void getEmployeeByIdTest() {
        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().pathParams("id", "3")
                .when().get("/employee/{id}");
        response.then().log().body();
    }

    @Test
    public void getEmployeesAndValidateStatusCodeTest() {
        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured.when().get("/employees");
        response.then().log().body();

        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(0));
    }

    @Test
    public void getEmployeeByIdValidateDataTest() {
        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().pathParams("id", "3")
                .when().get("/employee/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(3));
        response.then().assertThat().body("data.id", Matchers.equalTo(3));
        response.then().assertThat().body("data.employee_name", Matchers.equalTo("Ashton Cox"));
        response.then().assertThat().body("data.employee_salary", Matchers.equalTo(86000));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been fetched."));
    }

    @Test
    public void postCreateTest() {
        Employee employee = Employee.builder()
                .name("Robert Cabrera")
                .salary("651")
                .age("25")
                .build();
        Gson gson = new Gson();
        String data = gson.toJson(employee);

        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(data)
                .when().post("/create");
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("size()", Matchers.equalTo(3));
        response.then().assertThat().body("data.age", Matchers.equalTo(employee.getAge()));
        response.then().assertThat().body("data.name", Matchers.equalTo(employee.getName()));
        response.then().assertThat().body("data.salary", Matchers.equalTo(employee.getSalary()));
        response.then().assertThat().body("data.id", Matchers.not(""));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been added."));
    }

    @Test
    public void putUpdateTest() {
        Integer id = 4;
        Employee employee = Employee.builder()
                .name("Robert Cabrera")
                .salary("651")
                .age("25")
                .build();
        Gson gson = new Gson();
        String data = gson.toJson(employee);

        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().contentType(ContentType.JSON).accept(ContentType.JSON).body(data)
                .pathParams("id", id)
                .when().put("/update/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("size()", Matchers.equalTo(3));
        response.then().assertThat().body("data.age", Matchers.equalTo(employee.getAge()));
        response.then().assertThat().body("data.name", Matchers.equalTo(employee.getName()));
        response.then().assertThat().body("data.salary", Matchers.equalTo(employee.getSalary()));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been updated."));
    }

    @Test
    public void deleteTest() {
        Integer id = 2;

        RestAssured.baseURI = "https://dummy.restapiexample.com/api/v1";
        Response response = RestAssured
                .given().pathParams("id", id)
                .when().delete("/delete/{id}");
        response.then().log().body();
        response.then().assertThat().statusCode(200);

        response.then().assertThat().body("size()", Matchers.equalTo(3));
        response.then().assertThat().body("data", Matchers.equalTo(String.valueOf(id)));
        response.then().assertThat().body("message", Matchers.equalTo("Successfully! Record has been deleted"));
    }
}
