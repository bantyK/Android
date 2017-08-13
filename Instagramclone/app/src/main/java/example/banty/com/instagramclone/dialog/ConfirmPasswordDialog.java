package example.banty.com.instagramclone.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import example.banty.com.instagramclone.R;

/**
 * Created by banty on 13/8/17.
 */

public class ConfirmPasswordDialog extends DialogFragment {

    private static final String TAG = "ConfirmPasswordDialog";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_confirm_password, container, false);

        return view;
    }
}
