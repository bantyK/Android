package example.banty.com.instagramclone.activities.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import example.banty.com.instagramclone.R;
import example.banty.com.instagramclone.activities.BaseActivity;

public class AccountSettingActivity extends BaseActivity {
    private static final String TAG = "AccountSettingActivity";
    private View.OnClickListener backArrowClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        ImageView backArrow = (ImageView) findViewById(R.id.iv_back_arrow);
        backArrow.setOnClickListener(backArrowClicked);

        setUpSettingList();

    }

    private void setUpSettingList() {
        Log.d(TAG, "setUpSettingList: initializing the account setting list");
        ListView accountOptionListview = (ListView) findViewById(R.id.lv_settings);

        ArrayList<String> settingOptions = new ArrayList<>();
        settingOptions.add(getString(R.string.edit_profile));
        settingOptions.add(getString(R.string.sign_out));

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,settingOptions);
        accountOptionListview.setAdapter(adapter);
    }
}
