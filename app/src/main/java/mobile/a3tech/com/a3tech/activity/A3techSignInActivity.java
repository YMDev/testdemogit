package mobile.a3tech.com.a3tech.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techChooseSignInOption;
import mobile.a3tech.com.a3tech.fragment.A3techSignInEmailFragment;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techSignInActivity extends AppCompatActivity implements DataLoadCallback, A3techChooseSignInOption.OnFragmentInteractionListener, A3techSignInEmailFragment.OnFragmentInteractionListener {


    Button resetPassword;
    Button facebookLogIn;
    Button emailLogIn;
    TextView signin;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_sign_in_activity);
        resetPassword = findViewById(R.id.reset_password);
        facebookLogIn = findViewById(R.id.btnfacebookConnection);
        emailLogIn = findViewById(R.id.btn_connect_email);
        signin = findViewById(R.id.signin_action);
        setFragment(new A3techChooseSignInOption(), true, false);
    }

    protected void setFragment(Fragment fragment, boolean withAnim, boolean back) {
        FragmentTransaction t = getFragmentManager().beginTransaction();
        if (withAnim) {
            if (!back) {
                t.setCustomAnimations(R.animator.slide_in_left,
                        R.animator.slide_in_right, R.animator.slide_in_right_back, R.animator.slide_in_left_back);
            } else {
                t.setCustomAnimations(R.animator.slide_in_right_back,
                        R.animator.slide_in_left_back, 0, 0);
            }
        }
        t.replace(R.id.frame_sign_in_account, fragment);
        t.addToBackStack(fragment.getClass().getName());
        t.commit();
    }


    private void connecter(String login, String password) {


        this.dialog = CustomProgressDialog.createProgressDialog(this,
                getString(R.string.txtMenu_dialogChargement));
        UserManager.getInstance().login(login, password, this);
    }




    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techChooseSignInOption.ACTION_SIGN_IN_PAR_EMAIL:
            setFragment(new A3techSignInEmailFragment(),true,false);
            break;
            case A3techSignInEmailFragment.ACTION_CONNEXION:
                HashMap hashdata = (HashMap)data;
                String username = String.valueOf(hashdata.get("EMAIL"));
                String password = String.valueOf(hashdata.get("PASS"));

        }
    }

    @Override
    public void dataLoaded(Object data, int method, int typeOperation) {

    }

    @Override
    public void dataLoadingError(int errorCode) {

    }
}
