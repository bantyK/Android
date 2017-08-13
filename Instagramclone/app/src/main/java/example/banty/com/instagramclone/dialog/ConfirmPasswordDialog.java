package example.banty.com.instagramclone.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.banty.com.instagramclone.R;

/**
 * Created by banty on 13/8/17.
 */

public class ConfirmPasswordDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = "ConfirmPasswordDialog";

    public interface OnConfirmPasswordListener {
        void onConfirmPassword(String password);
    }

    OnConfirmPasswordListener passwordListener;

    //Dialog widgets
    private TextView cancelTextView, confirmTextView;
    private EditText passwordEditText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_password, container, false);

        setUpUI(view);

        return view;
    }

    private void setUpUI(View view) {
        cancelTextView = (TextView) view.findViewById(R.id.dialog_cancel);
        confirmTextView = (TextView) view.findViewById(R.id.dialog_confirm);
        passwordEditText = (EditText) view.findViewById(R.id.confirm_password);

        cancelTextView.setOnClickListener(this);
        confirmTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        int viewID = view.getId();

        switch(viewID) {
            case R.id.dialog_cancel:
                getDialog().dismiss();
                break;

            case R.id.dialog_confirm:
                String password = passwordEditText.getText().toString();
                if(password != null || !password.isEmpty()) {
                    passwordListener.onConfirmPassword(password);
                    getDialog().dismiss();
                }
                else
                    Toast.makeText(getActivity(), "Enter a valid password..", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            passwordListener = (OnConfirmPasswordListener) getTargetFragment();
        }catch (ClassCastException cce) {
            Log.e(TAG,cce.getMessage());
        }
    }
}
