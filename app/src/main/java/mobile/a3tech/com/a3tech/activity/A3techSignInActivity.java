package mobile.a3tech.com.a3tech.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.util.HashMap;
import java.util.regex.Pattern;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techChooseSignInOption;
import mobile.a3tech.com.a3tech.fragment.A3techSignInEmailFragment;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techSignInActivity extends AppCompatActivity implements DataLoadCallback, A3techChooseSignInOption.OnFragmentInteractionListener, A3techSignInEmailFragment.OnFragmentInteractionListener {


    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_sign_in_activity);
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



String password;
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
                 password = String.valueOf(hashdata.get("PASS"));
                 connecter(username, password);
        }
    }

    @Override
    public void dataLoaded(Object data, int method, int typeOperation) {
        User user = (User) data;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext()).edit();
        Intent mainIntent = new Intent(this, A3techHomeActivity.class);
        switch (method) {

            case Constant.KEY_USER_MANAGER_LOGIN /* 4 */:
                this.dialog.dismiss();
                editor.putString("identifiant", user.getIdentifiant());
                editor.putString("facebookId", user.getFacebookId());
                editor.putString("password",password);
                editor.putString("pseudo", user.getPseudo());
                editor.putString("mode", user.getMode());
                editor.putString("conMode", "application");
                editor.commit();
                dialog.dismiss();
                mainIntent.putExtra("nomPrenom",user.getPrenom()
                        + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + user.getNom());
                mainIntent.putExtra("nbr", user.getNbrServiceEmis());
                startActivity(mainIntent);
                finish();
                break;
            case Constant.KEY_USER_MANAGER_LOGIN_FB /* 35 */:
                editor.putString("MyCredentials", user.getEmail());
                editor.putString("pseudo", user.getPseudo());
                editor.putString("identifiant", user.getIdentifiant());
                editor.putString("password", password);
                editor.putString("facebookId", user.getFacebookId());
                editor.putString("checkphone", user.getCheckphone());
                editor.putString("mode", user.getMode());
                editor.putString("conMode", method == 4 ? "application"
                        : "facebook");
                editor.commit();
                this.dialog.dismiss();

                mainIntent.putExtra("nomPrenom",
                        user.getPrenom()
                                + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
                                + user.getNom());
                mainIntent.putExtra("nbr", user.getNbrServiceEmis());
                startActivity(mainIntent);
                finish();
                break;
            case Constant.KEY_USER_MANAGER_INIT_PASSWORD /* 6 */:
                this.dialog.dismiss();
                Toast.makeText(getApplicationContext(),
                        R.string.txtPasswordForgot_msg_password_initilise,
                        Toast.LENGTH_SHORT).show();
                break;
            default:
        }
    }

    @Override
    public void dataLoadingError(int errorCode) {

    }
}
