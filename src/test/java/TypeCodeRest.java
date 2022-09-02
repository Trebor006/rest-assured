import com.google.gson.Gson;
import entities.Post;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import util.TestUrls;

public class TypeCodeRest extends BaseTest{

    @Test
    public void testGetAllPost(){
        //   GET /posts
        Response response = RequestHelper.get(TestUrls.GET_POST_URL);
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.not(""));
    }

    @Test
    public void testGetPostById(){
        //   GET /posts/1
        Integer id = 1;
        Response response = RequestHelper.getById(TestUrls.GET_POST_BY_ID_URL, String.valueOf(id));

        response.then().log().body();
        response.then().assertThat().body("size()", Matchers.equalTo(4));
        response.then().assertThat().body("userId", Matchers.greaterThan(0));
        response.then().assertThat().body("id", Matchers.equalTo(id));
        response.then().assertThat().body("title", Matchers.not(""));
        response.then().assertThat().body("body", Matchers.not(""));
    }

    @Test
    public void testCreatePost(){
        //  POST /posts
        Post post = Post.builder().userId(333).id(333).title("The satanic bible").body("We are working on it").build();
        Gson gson = new Gson();
        String data = gson.toJson(post);

        Response response = RequestHelper.post(TestUrls.POST_POST_URL, data);

        response.then().log().body();
        response.then().assertThat().statusCode(201);
        response.then().assertThat().body("size()", Matchers.equalTo(4));
        response.then().assertThat().body("userId", Matchers.equalTo(post.getUserId()));
        response.then().assertThat().body("id", Matchers.greaterThan(0));
        response.then().assertThat().body("title", Matchers.equalTo(post.getTitle()));
        response.then().assertThat().body("body", Matchers.equalTo(post.getBody()));
    }

    @Test
    public void testUpdatePost(){
        //    PUT /posts/1

        Integer id = 1;
        Post post = Post.builder().userId(333).title("The satanic bible").body("We are working on it").build();
        Gson gson = new Gson();
        String data = gson.toJson(post);

        Response response = RequestHelper.put(TestUrls.PUT_POST_URL, String.valueOf(id), data);

        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(4));
        response.then().assertThat().body("userId", Matchers.equalTo(post.getUserId()));
        response.then().assertThat().body("id", Matchers.equalTo(id));
        response.then().assertThat().body("title", Matchers.equalTo(post.getTitle()));
        response.then().assertThat().body("body", Matchers.equalTo(post.getBody()));

    }

    @Test
    public void testDeletePost(){
        //    DELETE /posts/1
        Integer id = 2;
        Response response = RequestHelper.delete(TestUrls.DELETE_POST_URL, String.valueOf(id));
        response.then().log().body();
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body("size()", Matchers.equalTo(0));
    }
}
