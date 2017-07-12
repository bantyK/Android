package example.banty.com.quizapp.model;

import com.google.gson.annotations.SerializedName;

public class Question {
    @SerializedName("category")
    private String category;

    @SerializedName("type")
    private String type;

    @SerializedName("difficulty")
    private String difficulty;

    @SerializedName("question")
    private String question;

    @SerializedName("correct_answer")
    private String correctAns;

    @SerializedName("incorrect_answers")
    private String[] inCorrectAns;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public String[] getInCorrectAns() {
        return inCorrectAns;
    }

    public void setInCorrectAns(String[] inCorrectAns) {
        this.inCorrectAns = inCorrectAns;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
