package example.banty.com.quizapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import example.banty.com.quizapp.R;
import example.banty.com.quizapp.model.Question;

public class GameScreenActivity extends BaseActivity implements View.OnClickListener {

    public static final String INTENT_QUESTIONS_DATA = "intent.questions.data";
    public static final String TAG = GameScreenActivity.class.getSimpleName();
    private ArrayList<Question> questionArrayList;
    private TextView questionText;
    private Button answerOptionA, answerOptionB, answerOptionC, answerOptionD;
    private String correctAnswer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen_layout);
        initUIElements();
        Intent intentReceived = getIntent();
        if (intentReceived != null) {
            questionArrayList = intentReceived.getParcelableArrayListExtra(INTENT_QUESTIONS_DATA);
        }

        presentQuestionOnUI(questionArrayList.get(0));
    }

    private void initUIElements() {
        questionText = (TextView) findViewById(R.id.question_text);
        answerOptionA = (Button) findViewById(R.id.btn_option_A);
        answerOptionB = (Button) findViewById(R.id.btn_option_B);
        answerOptionC = (Button) findViewById(R.id.btn_option_C);
        answerOptionD = (Button) findViewById(R.id.btn_option_D);

        answerOptionA.setOnClickListener(this);
        answerOptionB.setOnClickListener(this);
        answerOptionC.setOnClickListener(this);
        answerOptionD.setOnClickListener(this);
    }

    private void presentQuestionOnUI(Question question) {
        questionText.setText(convertHTMLString(question.getQuestion()));
        answerOptionA.setText(convertHTMLString(question.getCorrectAns()));
        answerOptionB.setText(convertHTMLString(question.getInCorrectAns().get(0)));
        answerOptionC.setText(convertHTMLString(question.getInCorrectAns().get(1)));
        answerOptionD.setText(convertHTMLString(question.getInCorrectAns().get(2)));

        this.correctAnswer = question.getCorrectAns();
    }

    @Override
    public void onClick(View view) {
        Button answerOptionClicked = (Button) view;

        if (answerOptionClicked.getText().toString().equals(correctAnswer)) {
            Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
        }
    }

    private Spanned convertHTMLString(String htmlText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(htmlText);
        }
    }
}
