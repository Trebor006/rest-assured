import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import util.TestUrls;

public class BaseTest {

    @BeforeEach
    public void setHeader(){
        RestAssured.baseURI = TestUrls.BASE_URL;
    }
}
