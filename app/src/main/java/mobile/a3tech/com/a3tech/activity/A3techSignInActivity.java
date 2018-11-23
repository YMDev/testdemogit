package mobile.a3tech.com.a3tech.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import java.util.HashMap;

import eltos.simpledialogfragment.SimpleDialog;
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

public class A3techSignInActivity extends BaseActivity implements DataLoadCallback, A3techChooseSignInOption.OnFragmentInteractionListener, A3techSignInEmailFragment.OnFragmentInteractionListener, SimpleDialog.OnDialogResultListener {


    Dialog dialog;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
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
                setFragment(new A3techSignInEmailFragment(), true, false);
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
                HashMap hashdata = (HashMap) data;
                String email = String.valueOf(hashdata.get("EMAIL"));
                password = String.valueOf(hashdata.get("PASS"));
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Log.d("TAG", "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w("TAG", "signInWithEmail:failed", task.getException());
                                    A3techCustomToastDialog.createToastDialog(A3techSignInActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);

                                    dialog.dismiss();

                                } else {
                                    checkIfEmailVerified();
                                }
                                // ...
                            }
                        });
                /*connecter(username, password);*/
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
        Intent mainIntent = new Intent(this, A3techWelcomPageActivity.class);
        switch (method) {

            case Constant.KEY_USER_MANAGER_LOGIN /* 4 */:
                editor.putString("identifiant", user.getId() + "");
                editor.putString("facebookId", user.getFacebookId());
                editor.putString("password", password);
                editor.putString("pseudo", user.getPseudo());
                editor.putString("conMode", "application");
                editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson(user));
                editor.commit();

                mainIntent.putExtra("nomPrenom", user.getPrenom()
                        + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + user.getNom());
                mainIntent.putExtra("nbr", user.getNbrMission());

                startActivity(mainIntent);
                if (dialog != null) dialog.dismiss();
                finish();

                break;
            case Constant.KEY_USER_MANAGER_LOGIN_FB /* 35 */:
                editor.putString("MyCredentials", user.getEmail());
                editor.putString("pseudo", user.getPseudo());
                editor.putString("identifiant", user.getId() + "");
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
                if (dialog != null) dialog.dismiss();
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
        A3techCustomToastDialog.createToastDialog(A3techSignInActivity.this, getString(R.string.probleme_technique), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
        this.dialog.dismiss();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != A3techChooseSignInOption.REQUEST_KEY_CONNECT_BY_FACEBOOK
                || resultCode != -1) {
            return;
        }
        if (data.getStringExtra("resultCode").equals("OK")) {
            String nom = data.getStringExtra("nom");
            String prenom = data.getStringExtra("prenom");
            String email = data.getStringExtra("email");
            String facebookIdentifiant = data
                    .getStringExtra("facebookIdentifiant");
            password = facebookIdentifiant;
            this.dialog = CustomProgressDialog.createProgressDialog(this,
                    getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().loginfb(nom, prenom, facebookIdentifiant,
                    email, this);
            return;
        }
        new AlertDialog.Builder(this).setTitle("Error")
                .setMessage(data.getStringExtra("info"))
                .setPositiveButton("OK", null).show();
    }


    private void checkIfEmailVerified() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {
            final Intent mainIntent = new Intent(this, A3techWelcomPageActivity.class);
            // user is verified, so you can finish this activity or send user to activity which you want.
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                    getApplicationContext()).edit();

            //TODO getUser
            UserManager.getInstance().login(user.getEmail(), password, new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson((A3techUser)data));
                    editor.putString("identifiant", user.getProviderId() + "");
                    editor.putString("password", password);
                    editor.putString("pseudo", user.getDisplayName());
                    editor.putString("conMode", "application");
                    editor.commit();
                    startActivity(mainIntent);
                    finish();
                    Toast.makeText(A3techSignInActivity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    if (dialog != null) dialog.dismiss();

                }

                @Override
                public void dataLoadingError(int errorCode) {
                    if (dialog != null) dialog.dismiss();
                }
            });


        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            //restart this activity
            A3techCustomToastDialog.createToastDialog(A3techSignInActivity.this, getString(R.string.error_auth_email_not_verified), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            SimpleDialog.build().msg(R.string.mail_non_verifie).pos(R.string.verify).neg(R.string.cancel).theme(R.style.SimpleDialogThemeProfile).show(A3techSignInActivity.this,"MAIL_VERIF");
            if (dialog != null) dialog.dismiss();
        }
    }

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        if(dialogTag.equals("MAIL_VERIF")){
            if(which == BUTTON_POSITIVE){
                sendVerificationEmail();
            }else{
                FirebaseAuth.getInstance().signOut();
            }
        }
        return false;
    }


    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null) return;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            // email sent
                            A3techCustomToastDialog.createToastDialog(A3techSignInActivity.this,getString(R.string.email_sent),Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                            // after email is sent just logout the user and finish this activity
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(A3techSignInActivity.this, A3techLoginActivity.class));
                            finish();
                        }
                        else
                        {
                            dialog.dismiss();
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            A3techCustomToastDialog.createToastDialog(A3techSignInActivity.this,getString(R.string.probleme_technique_create_account_email_not_sent),Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }
}
