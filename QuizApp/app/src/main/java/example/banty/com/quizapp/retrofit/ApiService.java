package example.banty.com.quizapp.retrofit;

import java.util.List;

import example.banty.com.quizapp.model.ApiResponse;
import example.banty.com.quizapp.model.Question;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("api.php/{amount}/{category}/{difficulty}/{type}")
    Call<ApiResponse> getQuestions(@Query("amount") int amount, @Query("category") int category, @Query("difficulty") String difficulty, @Query("type") String type);

}
