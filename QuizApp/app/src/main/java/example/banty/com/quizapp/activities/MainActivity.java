package example.banty.com.quizapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.ArrayList;

import example.banty.com.quizapp.R;
import example.banty.com.quizapp.model.ApiResponse;
import example.banty.com.quizapp.model.Question;
import example.banty.com.quizapp.model.SpinnerData;
import example.banty.com.quizapp.retrofit.ApiClient;
import example.banty.com.quizapp.retrofit.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    //UI Elements
    private Button startQuizButton;
    private EditText numberOfQuestionEditText;
    private MaterialBetterSpinner categorySpinner, difficultySpinner, typeSpinner;

    //To store the values selected from spinners
    private String difficultySelected = "";
    private int categorySelected;
    private String typeSelected = "";
    private ProgressDialog progressDialog;
    private AdapterView.OnItemClickListener categoryItemSelected = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            categorySelected = SpinnerData.getCategoryHashMap().get(parent.getAdapter().getItem(position));
            Log.d(TAG, "onItemClick: category spinner selected : " + categorySelected);
        }
    };
    private AdapterView.OnItemClickListener typeItemSelected = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            typeSelected = parent.getAdapter().getItem(position).equals("Multiple Choice") ? "multiple" : "boolean";
            Log.d(TAG, "onItemClick: category spinner selected : " + typeSelected);
        }
    };
    private AdapterView.OnItemClickListener difficultyItemSelected = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            difficultySelected = ((String) parent.getAdapter().getItem(position)).toLowerCase();
            Log.d(TAG, "onItemClick: category spinner selected : " + difficultySelected);
        }
    };

    private View.OnClickListener startButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (wrongInput()) {
                Log.d(TAG, "onClick: Wrong input");
                Toast.makeText(MainActivity.this, "Invalid input.. please provide all the information", Toast.LENGTH_LONG).show();
            } else {
                int numberOfQuestions = Integer.parseInt(numberOfQuestionEditText.getText().toString());
                getDataFromAPI(numberOfQuestions, categorySelected, difficultySelected, typeSelected);
            }
        }
    };


    private boolean wrongInput() {
        return TextUtils.isEmpty(numberOfQuestionEditText.getText().toString()) ||
                TextUtils.isEmpty(difficultySelected) ||
                TextUtils.isEmpty(typeSelected) ||
                categorySelected == -1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIElements();
        initSpinner();
    }

    private void initUIElements() {
        startQuizButton = (Button) findViewById(R.id.start_button);
        numberOfQuestionEditText = (EditText) findViewById(R.id.et_number_questions);
        categorySpinner = (MaterialBetterSpinner) findViewById(R.id.category_spinner);
        difficultySpinner = (MaterialBetterSpinner) findViewById(R.id.difficulty_spinner);
        typeSpinner = (MaterialBetterSpinner) findViewById(R.id.type_spinner);

        //Set up progressbar
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");

        startQuizButton.setOnClickListener(startButtonClickListener);
        categorySpinner.setOnItemClickListener(categoryItemSelected);
        typeSpinner.setOnItemClickListener(typeItemSelected);
        difficultySpinner.setOnItemClickListener(difficultyItemSelected);
    }

    private void initSpinner() {
        String[] difficulties = SpinnerData.getDifficultySpinnerData();
        setUpSpinner(difficulties, difficultySpinner);

        String[] types = SpinnerData.getTypeSpinnerData();
        setUpSpinner(types, typeSpinner);

        String[] categories = SpinnerData.getCategorySpinnerData();
        setUpSpinner(categories, categorySpinner);


    }

    private void setUpSpinner(String[] spinnerData, MaterialBetterSpinner spinner) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, spinnerData);
        spinner.setAdapter(arrayAdapter);
    }

    private void getDataFromAPI(int numberOfQuestions, int categoryId, String difficulty, String type) {
        progressDialog.show();

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<ApiResponse> call = apiService.getQuestions(numberOfQuestions, categoryId, difficulty, type);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                hideProgressBar();
                Log.d(TAG, "onResponse: response = " + response.body());
                ArrayList<Question> questionList = response.body().getQuestions();
                Intent gameActivityIntent = new Intent(MainActivity.this, GameScreenActivity.class);
                gameActivityIntent.putParcelableArrayListExtra(GameScreenActivity.INTENT_QUESTIONS_DATA, questionList);
                startActivity(gameActivityIntent);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: error = " + t.getMessage());
            }
        });
    }

    private void hideProgressBar() {
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

}
