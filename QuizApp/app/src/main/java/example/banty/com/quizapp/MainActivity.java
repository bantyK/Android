package example.banty.com.quizapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import example.banty.com.quizapp.model.ApiResponse;
import example.banty.com.quizapp.model.Question;
import example.banty.com.quizapp.retrofit.ApiClient;
import example.banty.com.quizapp.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ApiResponse> call = apiService.getQuestions(10,9,"easy","multiple");

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d(TAG, "onResponse: response = " + response.body());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: error = " + t.getMessage());
            }
        });
    }
}
