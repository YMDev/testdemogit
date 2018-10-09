package mobile.a3tech.com.a3tech.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public class A3techBaseFragment extends Fragment implements View.OnFocusChangeListener {
    public void hideKeyboard(View view) {
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
