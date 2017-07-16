package example.banty.com.quizapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import example.banty.com.quizapp.R;
import example.banty.com.quizapp.model.Question;

public class GameScreenActivity extends BaseActivity implements View.OnClickListener {

    public static final String INTENT_QUESTIONS_DATA = "intent.questions.data";
    public static final String TAG = GameScreenActivity.class.getSimpleName();
    final Handler handler = new Handler();
    private ArrayList<Question> questionArrayList;
    private TextView questionText;
    private Button answerOptionA, answerOptionB, answerOptionC, answerOptionD;
    private ImageView correctAnsIndicatorImage;
    private String correctAnswer;
    private int currentQuestionIndex = 0;
    private int score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_screen_layout);
        initUIElements();
        Intent intentReceived = getIntent();
        if (intentReceived != null) {
            questionArrayList = intentReceived.getParcelableArrayListExtra(INTENT_QUESTIONS_DATA);
        }

        presentQuestionOnUI(questionArrayList.get(currentQuestionIndex));
    }

    private void initUIElements() {
        questionText = (TextView) findViewById(R.id.question_text);
        answerOptionA = (Button) findViewById(R.id.btn_option_A);
        answerOptionB = (Button) findViewById(R.id.btn_option_B);
        answerOptionC = (Button) findViewById(R.id.btn_option_C);
        answerOptionD = (Button) findViewById(R.id.btn_option_D);

        correctAnsIndicatorImage = (ImageView) findViewById(R.id.answer_image_indicator);

        answerOptionA.setOnClickListener(this);
        answerOptionB.setOnClickListener(this);
        answerOptionC.setOnClickListener(this);
        answerOptionD.setOnClickListener(this);
    }

    private void presentQuestionOnUI(Question question) {
        questionText.setText(convertHTMLString(question.getQuestion()));
        if (question.getType().equals("multiple")) {
            //multiple option questions
            setUpMultipleAnswerButtons(question.getCorrectAns(), question.getInCorrectAns());
        } else {
            //True/False type questions
            setUpBooleanAnswerButtons(question.getCorrectAns(), question.getInCorrectAns());
        }
        this.correctAnswer = question.getCorrectAns();
    }

    private void setUpBooleanAnswerButtons(String correctAns, ArrayList<String> inCorrectAns) {
        int correctAnswerOption = new Random().nextInt(2) + 1;

        //only require two buttons so hiding the last two buttons
        answerOptionC.setVisibility(View.GONE);
        answerOptionD.setVisibility(View.GONE);

        answerOptionA.setText("True");
        answerOptionB.setText("False");
    }

    private void setUpMultipleAnswerButtons(String correctAnswer, ArrayList<String> incorrectAnswerList) {
        int correctAnswerOption = new Random().nextInt(4) + 1;
        Log.d(TAG, "setUpMultipleAnswerButtons: correct button = " + correctAnswerOption);

        if (correctAnswerOption == 1) {
            answerOptionA.setText(correctAnswer);
            answerOptionB.setText(incorrectAnswerList.get(0));
            answerOptionC.setText(incorrectAnswerList.get(1));
            answerOptionD.setText(incorrectAnswerList.get(2));
        } else if (correctAnswerOption == 2) {
            answerOptionB.setText(correctAnswer);
            answerOptionA.setText(incorrectAnswerList.get(0));
            answerOptionC.setText(incorrectAnswerList.get(1));
            answerOptionD.setText(incorrectAnswerList.get(2));
        } else if (correctAnswerOption == 3) {
            answerOptionC.setText(correctAnswer);
            answerOptionA.setText(incorrectAnswerList.get(0));
            answerOptionB.setText(incorrectAnswerList.get(1));
            answerOptionD.setText(incorrectAnswerList.get(2));
        } else {
            answerOptionD.setText(correctAnswer);
            answerOptionA.setText(incorrectAnswerList.get(0));
            answerOptionB.setText(incorrectAnswerList.get(1));
            answerOptionC.setText(incorrectAnswerList.get(2));
        }
    }

    @Override
    public void onClick(View view) {
        Button answerOptionClicked = (Button) view;

        if (answerOptionClicked.getText().toString().equals(correctAnswer)) {
            Toast.makeText(this, "Correct Answer", Toast.LENGTH_SHORT).show();
            final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.right_answer);
            mediaPlayer.start();
            correctAnsIndicatorImage.setImageDrawable(this.getResources().getDrawable(R.drawable.green_tick));
            correctAnsIndicatorImage.setVisibility(View.VISIBLE);
            score++;
        } else {
            Toast.makeText(this, "Incorrect Answer", Toast.LENGTH_SHORT).show();
            final MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.wrong_answer);
            mediaPlayer.start();
            correctAnsIndicatorImage.setImageDrawable(this.getResources().getDrawable(R.drawable.red_cross));
            correctAnsIndicatorImage.setVisibility(View.VISIBLE);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                correctAnsIndicatorImage.setVisibility(View.GONE);

                //increase the current index and display the next question
                currentQuestionIndex += 1;
                if (currentQuestionIndex < questionArrayList.size())
                    presentQuestionOnUI(questionArrayList.get(currentQuestionIndex));
                else {
                    Toast.makeText(GameScreenActivity.this, "Game over..", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onClick: Total score = " + score);
                    unregisterAllButtons();
                }
            }
        }, 500);
    }

    private void unregisterAllButtons() {
        answerOptionA.setOnClickListener(null);
        answerOptionB.setOnClickListener(null);
        answerOptionC.setOnClickListener(null);
        answerOptionD.setOnClickListener(null);
    }

    private Spanned convertHTMLString(String htmlText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(htmlText);
        }
    }

    @Override
    public void onBackPressed() {
        if (currentQuestionIndex < questionArrayList.size())
            showCloseDialog();
        else
            finish();
    }

    private void showCloseDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Exit Game")
                .setMessage("You will lose all your progress")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
