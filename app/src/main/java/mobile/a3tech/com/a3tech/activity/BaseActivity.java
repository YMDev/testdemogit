package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.NetworkUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;

public class BaseActivity extends AppCompatActivity {
/*    @Override
    protected void onPause() {
        super.onPause();
        if (!NetworkUtils.isNetworkAvailable(BaseActivity.this)) {
            A3techCustomToastDialog.createToastDialog(BaseActivity.this, getString(R.string.common_google_play_services_network_error_text), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
            return;
        }

    }*/

    /*@Override*/
    /*protected void onResume() {
        super.onResume();
        if (!NetworkUtils.isNetworkAvailable(BaseActivity.this)) {
            A3techCustomToastDialog.createToastDialog(BaseActivity.this, getString(R.string.common_google_play_services_network_error_text), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
            return;
        }
        // ...
    }*/
}