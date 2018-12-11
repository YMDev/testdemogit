package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.NetworkUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;

public class BaseActivity extends AppCompatActivity implements View.OnFocusChangeListener {
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

    public BaseActivity getActivity() {
        return BaseActivity.this;
    }

    public void hideKeyboard(View view) {
        if(view == null) return;
        Activity act = getActivity();
        if(act == null) return;
        InputMethodManager inputMethodManager =(InputMethodManager)act.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            hideKeyboard(v);
        }
    }

}