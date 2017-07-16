package example.banty.com.quizapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import example.banty.com.quizapp.R;
import example.banty.com.quizapp.adapter.SummaryListAdapter;
import example.banty.com.quizapp.model.Question;


public class GameoverScreen extends BaseActivity {

    public static final String INTENT_KEY_SCORE = "intent.key.source";
    public static final String INTENT_KEY_USER_ANSWERS = "intent.key.user.source";
    public static final String INTENT_KEY_QUESTIONS = "intent.key.questions";

    //Variables to store intent values
    private ArrayList<Question> questions;
    private ArrayList<String> userAnswers;
    private String score;

    //UI elements
    private TextView scoreTextView;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_game_over);
        getDataFromIntent();
        initUIElements();
    }

    private void initUIElements() {
        scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText(score + "/" + questions.size());
        listView = (ListView) findViewById(R.id.game_summary_list);
        displayList();
    }

    private void displayList() {
        SummaryListAdapter adapter = new SummaryListAdapter(this, questions, userAnswers);
        listView.setAdapter(adapter);
    }

    public void getDataFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            questions = intent.getParcelableArrayListExtra(INTENT_KEY_QUESTIONS);
            userAnswers = intent.getStringArrayListExtra(INTENT_KEY_USER_ANSWERS);
            score = intent.getStringExtra(INTENT_KEY_SCORE);
        }
    }
}
