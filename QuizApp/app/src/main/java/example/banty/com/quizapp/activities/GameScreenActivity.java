package example.banty.com.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import example.banty.com.quizapp.R;
import example.banty.com.quizapp.model.Question;

public class GameScreenActivity extends AppCompatActivity {

    public static final String INTENT_QUESTIONS_DATA = "intent.questions.data";
    public static final String TAG = GameScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main_screen);

        Intent intentReceived = getIntent();
        if (intentReceived != null) {
            ArrayList<Question> questionArrayList = intentReceived.getParcelableArrayListExtra(INTENT_QUESTIONS_DATA);
            Log.d(TAG, "onCreate: number of question received = " + questionArrayList.size());
        }
    }
}
