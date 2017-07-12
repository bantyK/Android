package example.banty.com.quizapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import example.banty.com.quizapp.model.ApiResponse;
import example.banty.com.quizapp.model.SpinnerData;
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
        setContentView(R.layout.layout_main_screen);
        initSpinner();

        getDataFromAPI();
    }

    private void initSpinner() {
        String[] difficulties = SpinnerData.getDifficultySpinnerData();
        setUpSpinner(difficulties, (MaterialBetterSpinner) findViewById(R.id.difficulty_spinner));

        String[] types = SpinnerData.getTypeSpinnerData();
        setUpSpinner(types,(MaterialBetterSpinner) findViewById(R.id.type_spinner));

        String [] categories = SpinnerData.getCategorySpinnerData();
        setUpSpinner(categories,(MaterialBetterSpinner) findViewById(R.id.category_spinner));


    }

    private void setUpSpinner(String[] spinnerData, MaterialBetterSpinner spinner) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, spinnerData);
        spinner.setAdapter(arrayAdapter);
    }

    private void getDataFromAPI() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ApiResponse> call = apiService.getQuestions(10, 9, "easy", "multiple");

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
