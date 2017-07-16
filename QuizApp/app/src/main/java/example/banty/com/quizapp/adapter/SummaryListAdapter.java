package example.banty.com.quizapp.adapter;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import example.banty.com.quizapp.R;
import example.banty.com.quizapp.model.Question;

public class SummaryListAdapter extends BaseAdapter {
    private final ArrayList<Question> questions;
    private final ArrayList<String> userAnswerList;
    private final Context context;

    public SummaryListAdapter(Context context, ArrayList<Question> correctAnswerList, ArrayList<String> userAnswerList) {
        this.context = context;
        this.questions = correctAnswerList;
        this.userAnswerList = userAnswerList;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return questions.get(position);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.summary_list_item, parent, false);

        ((TextView) view.findViewById(R.id.list_item_question)).setText(convertHTMLString(questions.get(position).getQuestion()));
        ((TextView) view.findViewById(R.id.list_item_correct_answer)).setText(convertHTMLString(questions.get(position).getCorrectAns()));
        ((TextView) view.findViewById(R.id.list_item_user_answer)).setText(convertHTMLString(userAnswerList.get(position)));

        return view;

    }

    private Spanned convertHTMLString(String htmlText) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(htmlText, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(htmlText);
        }
    }
}
