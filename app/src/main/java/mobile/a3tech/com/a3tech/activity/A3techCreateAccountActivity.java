package mobile.a3tech.com.a3tech.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.util.HashMap;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techAddEmailFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAddPasswordFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAddUserNameFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelecteAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techStep1CreatAccountFragment;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.A3techUserType;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techCreateAccountActivity extends BaseActivity implements A3techSelecteAccountFragment.OnFragmentInteractionListener, A3techStep1CreatAccountFragment.OnFragmentInteractionListener, A3techAddUserNameFragment.OnFragmentInteractionListener, A3techAddEmailFragment.OnFragmentInteractionListener, A3techAddPasswordFragment.OnFragmentInteractionListener, DataLoadCallback {
    public static final int DEST_HOME = 0;
    public static final int DEST_SELECT_ACCOUNT = 1;
    public static final int DEST_TYPE_USERNAME = 2;
    public static final int DEST_TYPE_EMAIL = 3;
    public static final int DEST_TYPE_PASS = 4;

    ProgressBar progressbarAccountCreation;
    FrameLayout frameView;
    A3techUser account;
    ProgressDialog dialog = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        account = new A3techUser();
        setContentView(R.layout.a3tech_create_account_activity);
        frameView = findViewById(R.id.frame_create_account);
        progressbarAccountCreation = findViewById(R.id.progress_creation_account);
        setFragment(new A3techStep1CreatAccountFragment(), false, false);
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
        t.replace(R.id.frame_create_account, fragment);
        t.addToBackStack(fragment.getClass().getName());
        t.commit();
    }

    private void progressBarchangeSmouthly(int progress) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(progressbarAccountCreation, "progress", progress);
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            progressbarAccountCreation.setProgress(progress); // no animation on Gingerbread or lower
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
            updateProgress();
        } else {
            super.onBackPressed();
            startActivity(new Intent(A3techCreateAccountActivity.this,
                    A3techLoginActivity.class));
            finish();
        }
    }

    private void updateProgress() {
        Fragment f = getFragmentManager().findFragmentById(R.id.frame_create_account);
        if (f instanceof A3techSelecteAccountFragment) {
            progressBarchangeSmouthly(0);
        } else if (f instanceof A3techAddUserNameFragment) {
            progressBarchangeSmouthly(20);
        } else if (f instanceof A3techStep1CreatAccountFragment) {
            super.onBackPressed();
        } else if (f instanceof A3techAddEmailFragment) {
            progressBarchangeSmouthly(40);
        }else if (f instanceof A3techAddPasswordFragment) {
            progressBarchangeSmouthly(60);
        }


    }

    @Override
    public void backAction(int dest) {
        switch (dest) {
            case DEST_HOME:
                setFragment(new A3techStep1CreatAccountFragment(), true, true);
                progressBarchangeSmouthly(0);
                break;
            case DEST_SELECT_ACCOUNT:
                setFragment(new A3techSelecteAccountFragment(), true, true);
                progressBarchangeSmouthly(20);
                break;
            case DEST_TYPE_USERNAME:
                setFragment(new A3techAddUserNameFragment(), true, true);
                progressBarchangeSmouthly(40);
                break;
            default:
                // finish();
        }
    }

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techSelecteAccountFragment.ACTION_SELECT_ACCOUNT:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                if (((Integer) data) == A3techSelecteAccountFragment.ACCOUNT_HIRE) {
                    account.setTypeUser(A3techUserType.CLIENT);
                } else if (((Integer) data) == A3techSelecteAccountFragment.ACCOUNT_WORK) {
                    account.setTypeUser(A3techUserType.TECHNICIEN);
                }
                setFragment(new A3techAddUserNameFragment(), true, false);
                break;
            case A3techAddUserNameFragment.ACTION_TYPE_USERNAME:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                account.setNom(String.valueOf(data));
                setFragment(new A3techAddEmailFragment(), true, false);
                break;
            case A3techAddEmailFragment.ACTION_TYPE_EMAIL:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                HashMap mapData  = (HashMap)data;
                account.setEmail(String.valueOf(mapData.get("EMAIL")));
                account.setTelephone(String.valueOf(mapData.get("PHONE")));
                setFragment(new A3techAddPasswordFragment(), true, false);
                break;
            case A3techAddPasswordFragment.ACTION_TYPE_PASS:
                 progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                account.setPassword(String.valueOf(data));
                Log.d("email_inserted", "actionNext: "+account.getEmail()+"  :: "+account.getNom());
                dialog = CustomProgressDialog.createProgressDialog(
                      A3techCreateAccountActivity.this,
                        getString(R.string.txtMenu_dialogChargement));
                UserManager
                        .getInstance()
                        .createAccount(
                                account.getNom(),
                                account.getPrenom(),
                                account.getEmail(),
                                account.getPassword(),
                                "",
                                "",
                                new StringBuilder(String.valueOf( account.getPrenom()))
                                        .append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR)
                                        .append(account.getNom().substring(0, 1)).toString(),
                                A3techCreateAccountActivity.this);

                break;

        }
    }

    @Override
    public void actionNext(Integer typeAction) {
        switch (typeAction) {
            case A3techStep1CreatAccountFragment.ACTION_NEXT_CREATE:
                progressBarchangeSmouthly(progressbarAccountCreation.getProgress() + 20);
                setFragment(new A3techSelecteAccountFragment(), true, false);
                break;
            case A3techStep1CreatAccountFragment.ACTION_NEXT_FACEBOOK:
                break;

        }

    }

    @Override
    public void dataLoaded(Object data, int method, int typeOperation) {
        A3techUser user = (A3techUser) data;
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                getApplicationContext()).edit();
        editor.putString("identifiant", user.getId()+"");
        editor.putString("facebookId", user.getFacebookId());
        editor.putString("password", account.getPassword());
        editor.putString("pseudo", user.getPseudo());
        editor.putString("conMode", "application");
        editor.commit();
        dialog.dismiss();
        Intent mainIntent = new Intent(this, A3techWelcomPageActivity.class);
        mainIntent.putExtra("nomPrenom", this.account.getPrenom()
                + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + this.account.getNom());
        mainIntent.putExtra("nbr", user.getNbrMission()+"");
        startActivity(mainIntent);
        finish();
    }

    @Override
    public void dataLoadingError(int errorCode) {
        A3techCustomToastDialog.createToastDialog(A3techCreateAccountActivity.this,getString(R.string.probleme_technique),Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
        this.dialog.dismiss();
    }
}
