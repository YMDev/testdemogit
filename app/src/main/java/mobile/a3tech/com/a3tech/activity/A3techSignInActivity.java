package mobile.a3tech.com.a3tech.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.gson.Gson;

import java.util.HashMap;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.fragment.A3techChooseSignInOption;
import mobile.a3tech.com.a3tech.fragment.A3techSignInEmailFragment;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
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
            case A3techChooseSignInOption.ACTION_SIGN_IN_PAR_FB:
                Intent mainIntent = new Intent(A3techSignInActivity.this,
                        FacebookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC,
                        Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGIN);
                mainIntent.putExtras(bundle);
                A3techSignInActivity.this.startActivityForResult(mainIntent,
                        A3techChooseSignInOption.REQUEST_KEY_CONNECT_BY_FACEBOOK);
                break;
            case A3techSignInEmailFragment.ACTION_CONNEXION:
                dialog = CustomProgressDialog.createProgressDialog(A3techSignInActivity.this,
                        getString(R.string.txtMenu_dialogChargement));
                 HashMap hashdata = (HashMap)data;
                 String username = String.valueOf(hashdata.get("EMAIL"));
                 password = String.valueOf(hashdata.get("PASS"));
                 connecter(username, password);
                 break;
            case A3techChooseSignInOption.ACTION_RESET_PASS:
        }
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            startActivity(new Intent(A3techSignInActivity.this,
                    A3techLoginActivity.class));
            finish();
        }
    }
    @Override
    public void dataLoaded(Object data, int method, int typeOperation) {
        A3techUser user = (A3techUser) data;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext()).edit();
        Intent mainIntent = new Intent(this, A3techHomeActivity.class);
        switch (method) {

            case Constant.KEY_USER_MANAGER_LOGIN /* 4 */:
                editor.putString("identifiant", user.getId()+"");
                editor.putString("facebookId", user.getFacebookId());
                editor.putString("password",password);
                editor.putString("pseudo", user.getPseudo());
                editor.putString("conMode", "application");
                editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson(user));
                editor.commit();

                mainIntent.putExtra("nomPrenom",user.getPrenom()
                        + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + user.getNom());
                mainIntent.putExtra("nbr", user.getNbrMission());

                startActivity(mainIntent);
                dialog.dismiss();
                finish();

                break;
            case Constant.KEY_USER_MANAGER_LOGIN_FB /* 35 */:
                editor.putString("MyCredentials", user.getEmail());
                editor.putString("pseudo", user.getPseudo());
                editor.putString("identifiant", user.getId()+"");
                editor.putString("password", password);
                editor.putString("facebookId", user.getFacebookId());
                editor.putString("checkphone", user.getTelephone());
                editor.putString("conMode", method == 4 ? "application"
                        : "facebook");
                editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson(user));
                editor.commit();

                mainIntent.putExtra("nomPrenom",
                        user.getPrenom()
                                + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
                                + user.getNom());
                mainIntent.putExtra("nbr", user.getNbrMission());
                startActivity(mainIntent);
                dialog.dismiss();
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
        A3techCustomToastDialog.createToastDialog(A3techSignInActivity.this,getString(R.string.probleme_technique),Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
        this.dialog.dismiss();
    }
}
