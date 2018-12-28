package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techLoginPagerviewerAdapter;
import mobile.a3tech.com.a3tech.exception.FirebaseException;
import mobile.a3tech.com.a3tech.images.Image;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.utils.ValidationPatternUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomSnackbar;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techLoginActivity extends BaseActivity implements DataLoadCallback, SimpleDialog.OnDialogResultListener {

    AlertDialog alertDialog;
    static int requestKey = 3422;
    ProgressDialog dialog = null;

    EditText idLoginDialog_editTextMail;
    EditText idLoginDialog_editTextPassword;
    TextView idLoginDialog_textViewPasswordForgot;
    Button startConnexion;
    TextView btn_create_account, btn_password_forgotten;
    LinearLayout idLogin_linearLayoutAnnuler;
    LinearLayout idLogin_linearLayoutCreateAccount;
    RelativeLayout createAccountContainer;

    LinearLayout idLogin_linearLayoutLogin;
    LinearLayout idLogin_linearLayoutValider;
    EditText idPasswordForgetDialog_editTextEmail;
    ImageView idPasswordForget_linearLayoutAnnuler;
    Button idPasswordForget_linearLayoutValider;
    RelativeLayout idLogin_linearLayoutAnonyme;
    ImageView facebookSignin, googlePlusSignin, twetterSignin;
    String password;

    FirebaseAuth mAuth;

    /*
    First view pager of login screen
     */
    private CoordinatorLayout rootView;
    private ViewPager mViewPager;
    ImageView zero, one, two;
    ImageView[] indicators;
    int lastLeftValue = 0;
    RelativeLayout mCoordinator;
    int page = 0;   //  to track page position

    /*
    End of view pager
     */

    private OnClickListener faceBookListener = new OnClickListener() {
        public void onClick(View v) {
            Intent mainIntent = new Intent(A3techLoginActivity.this,
                    FacebookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC,
                    Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGIN);
            mainIntent.putExtras(bundle);
            A3techLoginActivity.this.startActivityForResult(mainIntent,
                    requestKey);
        }
    };

    private OnClickListener addContactListener = new OnClickListener() {
        public void onClick(View v) {
			/*A3techLoginActivity.this.startActivity(new Intent(A3techLoginActivity.this,
					InscriptionActivity.class));*/
            A3techLoginActivity.this.startActivity(new Intent(A3techLoginActivity.this,
                    A3techCreateAccountActivity.class));
            A3techLoginActivity.this.finish();
        }
    };

    private OnClickListener passWordForgetListener = new OnClickListener() {

        public void onClick(View v) {

            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    try {
                        if (A3techLoginActivity.this.alertDialog != null)
                            A3techLoginActivity.this.alertDialog.dismiss();
                    } catch (Exception e) {
                    }
                    Builder builder = new Builder(A3techLoginActivity.this);
                    View content = A3techLoginActivity.this.getLayoutInflater()
                            .inflate(R.layout.password_forget_dialog, null);
                    A3techLoginActivity.this.idPasswordForgetDialog_editTextEmail = (EditText) content
                            .findViewById(R.id.idPasswordForgetDialog_editTextEmail);
                    A3techLoginActivity.this.idPasswordForget_linearLayoutAnnuler = (ImageView) content
                            .findViewById(R.id.close_dialog);
                    A3techLoginActivity.this.idPasswordForget_linearLayoutValider = (Button) content
                            .findViewById(R.id.valider_forgot_password);
                    A3techLoginActivity.this.idPasswordForget_linearLayoutValider
                            .setOnClickListener(A3techLoginActivity.this.validerForgetListener);
                    A3techLoginActivity.this.idPasswordForget_linearLayoutAnnuler
                            .setOnClickListener(A3techLoginActivity.this.annulerListener);
                    builder.setView(content);

                    A3techLoginActivity.this.alertDialog = builder.create();
                    A3techLoginActivity.this.alertDialog.show();

                }
            });
        }
    };

    private OnClickListener loginListener = new OnClickListener() {

        public void onClick(View v) {
            A3techLoginActivity.this.startActivity(new Intent(A3techLoginActivity.this,
                    A3techSignInActivity.class));
            A3techLoginActivity.this.finish();
		/*	new Handler().post(new Runnable() {

				@Override
				public void run() {
					Builder builder = new Builder(A3techLoginActivity.this);
					View content = A3techLoginActivity.this.getLayoutInflater()
							.inflate(R.layout.login_dialog, null);
					builder.setTitle(A3techLoginActivity.this
							.getString(R.string.txtLogin_title_dialog_login));
					A3techLoginActivity.this.idLoginDialog_textViewPasswordForgot = (TextView) content
							.findViewById(R.id.idLoginDialog_textViewPasswordForgot);
					A3techLoginActivity.this.idLoginDialog_editTextMail = (EditText) content
							.findViewById(R.id.idLoginDialog_editTextMail);
					A3techLoginActivity.this.idLoginDialog_editTextPassword = (EditText) content
							.findViewById(R.id.idLoginDialog_editTextPassword);
					A3techLoginActivity.this.idLogin_linearLayoutAnnuler = (LinearLayout) content
							.findViewById(R.id.idLogin_linearLayoutAnnuler);
					A3techLoginActivity.this.idLogin_linearLayoutValider = (LinearLayout) content
							.findViewById(R.id.idLogin_linearLayoutValider);
					SpannableString spanString = new SpannableString(
							A3techLoginActivity.this
									.getString(R.string.txtLoginDialog_textViewPasswordForgot));
					spanString.setSpan(new UnderlineSpan(), 0,
							spanString.length(), 0);
					A3techLoginActivity.this.idLoginDialog_textViewPasswordForgot
							.setText(spanString);
					A3techLoginActivity.this.idLoginDialog_textViewPasswordForgot
							.setOnClickListener(A3techLoginActivity.this.passWordForgetListener);
					A3techLoginActivity.this.idLogin_linearLayoutValider
							.setOnClickListener(A3techLoginActivity.this.connecterListener);
					A3techLoginActivity.this.idLogin_linearLayoutAnnuler
							.setOnClickListener(A3techLoginActivity.this.annulerListener);
					builder.setView(content);
					A3techLoginActivity.this.alertDialog = builder.create();
					A3techLoginActivity.this.alertDialog.show();

				}
			});*/
        }
    };
    AlertDialog okMsgDialog = null;
    private OnClickListener validerForgetListener = new OnClickListener() {

        public void onClick(View v) {
            hideKeyboard(A3techLoginActivity.this.idPasswordForgetDialog_editTextEmail);
            final String email = A3techLoginActivity.this.idPasswordForgetDialog_editTextEmail
                    .getText().toString();
            if (email.length() == 0
                    || !A3techLoginActivity.this.isValidEmailAddress(email)) {
                A3techLoginActivity.this.idPasswordForgetDialog_editTextEmail.setError(getString(R.string.txtPasswordForgetDialog_textViewPasswordForgot));
                A3techLoginActivity.this.idPasswordForgetDialog_editTextEmail.requestFocus();
                return;
            }

            FirebaseAuth auth = FirebaseAuth.getInstance();

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                if (A3techLoginActivity.this.dialog != null)
                                    A3techLoginActivity.this.dialog.dismiss();
                                if (A3techLoginActivity.this.alertDialog != null)
                                    A3techLoginActivity.this.alertDialog.dismiss();

                                Builder builder = new Builder(A3techLoginActivity.this);
                                View content = A3techLoginActivity.this.getLayoutInflater()
                                        .inflate(R.layout.mail_verification_sent_dialog, null);
                                content.findViewById(R.id.ok_close).setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (okMsgDialog != null) okMsgDialog.dismiss();
                                    }
                                });
                                ((TextView) content.findViewById(R.id.header_email_val_label)).setText(email);
                                builder.setView(content);
                                okMsgDialog = builder.create();
                                okMsgDialog.show();

                            } else {
                                A3techCustomToastDialog.createSnackBar(A3techLoginActivity.this, FirebaseException.getExceptionMessage(A3techLoginActivity.this,((FirebaseAuthException) task.getException())), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_INFO);
                            }
                        }
                    });
          /*  A3techLoginActivity.this.dialog = CustomProgressDialog
                    .createProgressDialog(
                            A3techLoginActivity.this,
                            A3techLoginActivity.this
                                    .getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().initPassword(email,
                    A3techLoginActivity.this.generatePassword(), A3techLoginActivity.this);*/
        }
    };

    private OnClickListener connecterListener = new OnClickListener() {

        public void onClick(View v) {
            A3techLoginActivity.this.connecter();
        }
    };

    private OnClickListener annulerListener = new OnClickListener() {

        public void onClick(View v) {
            hideKeyboard(A3techLoginActivity.this.idPasswordForgetDialog_editTextEmail);
            A3techLoginActivity.this.alertDialog.dismiss();
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        requestWindowFeature(1);
        setContentView(R.layout.a3tech_login_activity_v2);
        btn_create_account = findViewById(R.id.create_account);
        btn_password_forgotten = findViewById(R.id.forgot_password);
        btn_create_account.setOnClickListener(this.addContactListener);
        idLoginDialog_editTextMail = (EditText) findViewById(R.id.input_username_log_in);
        idLoginDialog_editTextPassword = (EditText) findViewById(R.id.input_password_log_in);
        startConnexion = findViewById(R.id.btn_start_connexion);
        facebookSignin = findViewById(R.id.facebook_signin_btn);
        googlePlusSignin = findViewById(R.id.gplus_signin_btn);
        twetterSignin = findViewById(R.id.twitter_signin_btn);
        facebookSignin.setOnClickListener(faceBookListener);
        btn_password_forgotten.setOnClickListener(passWordForgetListener);
        startConnexion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboard(idLoginDialog_editTextMail);
                hideKeyboard(idLoginDialog_editTextPassword);
                if (!ValidationPatternUtils.isValideEmail(idLoginDialog_editTextMail.getText().toString())) {
                    idLoginDialog_editTextMail.setError(getString(R.string.error_email_not_valide));
                    return;
                }

                if (idLoginDialog_editTextPassword.getText() == null || idLoginDialog_editTextPassword.getText().toString().equals("")) {
                    idLoginDialog_editTextPassword.setError(getString(R.string.error_invalid_password));
                    return;
                }
                dialog = CustomProgressDialog.createProgressDialog(A3techLoginActivity.this,
                        getString(R.string.txtMenu_dialogChargement));
                String email = idLoginDialog_editTextMail.getText().toString();
                password = idLoginDialog_editTextPassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(A3techLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    Log.w("TAG", "signInWithEmail:failed", task.getException());
                                    A3techCustomToastDialog.createSnackBar(A3techLoginActivity.this, FirebaseException.getExceptionMessage(A3techLoginActivity.this,((FirebaseAuthException) task.getException())), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                                    dialog.dismiss();
                                } else {
                                    checkIfEmailVerified();
                                }
                                // ...
                            }
                        });
            }
        });
		/*this.idLogin_linearLayoutLogin = (LinearLayout) findViewById(R.id.idLogin_linearLayoutLogin);
		this.idLogin_linearLayoutCreateAccount = (LinearLayout) findViewById(R.id.idLogin_linearLayoutCreateAccount);
		this.idLogin_linearLayoutFacebook = (RelativeLayout) findViewById(R.id.idLogin_linearLayoutFacebook);
		this.idLogin_linearLayoutFacebook
				.setOnClickListener(this.faceBookListener);
		this.idLogin_linearLayoutLogin.setOnClickListener(this.loginListener);
		this.idLogin_linearLayoutCreateAccount
				.setOnClickListener(this.addContactListener);
		idLogin_linearLayoutAnonyme = (RelativeLayout) findViewById(R.id.idLogin_linearLayoutAnonyme);
		idLogin_linearLayoutAnonyme.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(A3techLoginActivity.this, NavigationMain.class);

				startActivity(mainIntent);
				finish();

			}
		});
*/
        /*iniPager();*/
    }

    private void checkIfEmailVerified() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {
            final Intent mainIntent = new Intent(this, A3techWelcomPageActivity.class);
            // user is verified, so you can finish this activity or send user to activity which you want.
            final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
                    getApplicationContext()).edit();
            //TODO getUser
            UserManager.getInstance().getProfil(user.getEmail(), password, new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    A3techUser userconnectedFromDB = (A3techUser) data;
                    if(userconnectedFromDB != null && userconnectedFromDB.getPassword() != password){
                        //update user password
                        editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson((A3techUser) data));
                        editor.putString("identifiant", userconnectedFromDB.getEmail() + "");
                        editor.putString("password", password);
                        editor.putString("pseudo", userconnectedFromDB.getPseudo());
                        editor.putString("conMode", "application");
                        editor.commit();
                        startActivity(mainIntent);
                        finish();
                        if (dialog != null) dialog.dismiss();
                    }else{
                        editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson((A3techUser) data));
                        editor.putString("identifiant", userconnectedFromDB.getEmail() + "");
                        editor.putString("password", password);
                        editor.putString("pseudo", user.getDisplayName());
                        editor.putString("conMode", "application");
                        editor.commit();
                        startActivity(mainIntent);
                        finish();
                        if (dialog != null) dialog.dismiss();
                    }
                }

                @Override
                public void dataLoadingError(int errorCode) {
                    if (dialog != null) dialog.dismiss();
                    // A3techCustomToastDialog.createToastDialog(A3techLoginActivity.this, getString(R.string.error_connexion_server), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
                  if(errorCode == Constant.ERROR_USER_NOT_FOUND){

                  }else if(errorCode == Constant.ERROR_USER_DISABLED){

                  }else{
                      final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) A3techLoginActivity.this
                              .findViewById(android.R.id.content)).getChildAt(0);
                      Snackbar
                              .make(viewGroup, getString(R.string.error_connexion_server),
                                      Snackbar.LENGTH_LONG)
                              .show();
                  }


                }
            });


        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            //restart this activity
           // A3techCustomToastDialog.createSnackBar(A3techLoginActivity.this, getString(R.string.error_auth_email_not_verified), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            SimpleDialog.build().msg(R.string.mail_non_verifie).neg(R.string.cancel).theme(R.style.SimpleDialogThemeProfile).show(A3techLoginActivity.this, "MAIL_VERIF");
            if (dialog != null) dialog.dismiss();
        }
    }

    private void iniPager() {
        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        mCoordinator = (RelativeLayout) findViewById(R.id.main_content);
        indicators = new ImageView[]{zero, one, two};
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        final int[] listeBack = new int[]{R.drawable.a3tech_back_4, R.drawable.a3tech_back_4, R.drawable.a3tech_back_4};
        mViewPager.setAdapter(new A3techLoginPagerviewerAdapter(this, listeBack));
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setClipChildren(false);
        mViewPager.setCurrentItem(page);
        updateIndicators(page);
        mViewPager.setClipToPadding(false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                page = position;
                updateIndicators(page);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);

    }

    void updateIndicators(int position) {
        for (int i = 0; i < indicators.length; i++) {
            indicators[i].setBackgroundResource(
                    i == position ? R.drawable.indicator_selected : R.drawable.indicator_unselected
            );
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != requestKey
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
        new Builder(this).setTitle("Error")
                .setMessage(data.getStringExtra("info"))
                .setPositiveButton("OK", null).show();
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());

        Locale locale2 = new Locale(prefs.getString("ApplicationLanguage",
                Constant.LANGUAGE_FRENSH));
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        getBaseContext().getResources().updateConfiguration(config2,
                getBaseContext().getResources().getDisplayMetrics());
    }

    private String generatePassword() {
        char[] allowedCharacters = new char[]{'a', 'b', 'c', '1', '2', '3',
                '4'};
        SecureRandom random = new SecureRandom();
        StringBuffer password = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            password.append(allowedCharacters[random
                    .nextInt(allowedCharacters.length)]);
        }
        return password.toString();
    }

    private boolean isValidEmailAddress(String emailAddress) {
        return Pattern
                .compile(
                        "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+.([a-zA-Z])+([a-zA-Z])+",
                        Pattern.CASE_INSENSITIVE).matcher(emailAddress).matches();
    }

    private void connecter() {
        String login = this.idLoginDialog_editTextMail.getText().toString();
        password = this.idLoginDialog_editTextPassword.getText()
                .toString();
        if (login.length() == 0 || !isValidEmailAddress(login)
                || password.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.txtInscription_msgInformationCorrectes),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        this.dialog = CustomProgressDialog.createProgressDialog(this,
                getString(R.string.txtMenu_dialogChargement));
        UserManager.getInstance().login(login, password, this);
    }

    public void dataLoaded(Object data, int method, int i) {
        switch (method) {
            case Constant.KEY_USER_MANAGER_LOGIN /* 4 */:
            case Constant.KEY_USER_MANAGER_LOGIN_FB /* 35 */:
                A3techUser user = (A3techUser) data;
                Editor editor = PreferenceManager.getDefaultSharedPreferences(
                        getApplicationContext()).edit();
                editor.putString("MyCredentials", user.getEmail());
                editor.putString("pseudo", user.getPseudo());
                editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_NAME, user.getNom());
                editor.putString("identifiant", user.getId() + "");
                editor.putString("password", password);
                editor.putString("facebookId", user.getFacebookId());
                editor.putString("checkphone", user.getTelephone());
                editor.putString("conMode", method == 4 ? "application"
                        : "facebook");
                editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, new Gson().toJson(user));
                editor.commit();
                this.dialog.dismiss();
                Intent mainIntent = new Intent(this, NavigationMain.class);
                mainIntent.putExtra("nomPrenom",
                        user.getPrenom()
                                + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR
                                + user.getNom());
                mainIntent.putExtra("nbr", user.getNbrMission() + "");

                startActivity(mainIntent);
                finish();
                break;
            case Constant.KEY_USER_MANAGER_INIT_PASSWORD /* 6 */:
                if (this.dialog != null) this.dialog.dismiss();
                if (this.alertDialog != null) this.alertDialog.dismiss();
                A3techCustomToastDialog.createSnackBar(A3techLoginActivity.this, getResources().getString(R.string.txtPasswordForgot_msg_password_initilise), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_INFO);
                break;
            default:
        }
    }

    public void dataLoadingError(int errorCode) {
        try {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.txtLogin_msg_erreur_connexion_login_password_incorrect,
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dialog.dismiss();
    }

    public void dataLoadingError(String error) {
        this.dialog = CustomProgressDialog.createProgressDialog(this, error);
    }

    private class SliderTimer extends TimerTask {

        @Override
        public void run() {
            A3techLoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewPager.getCurrentItem() < 2) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    } else {
                        mViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }

    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        if (dialogTag.equals("MAIL_VERIF")) {
            if (which == BUTTON_POSITIVE) {
                sendVerificationEmail();
            } else {
                FirebaseAuth.getInstance().signOut();
            }
        }
        return false;
    }


    private void sendVerificationEmail() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) return;
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            dialog.dismiss();
                            // email sent
                           /* A3techCustomToastDialog.createSnackBar(A3techLoginActivity.this, getString(R.string.email_sent), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);*/
                            // after email is sent just logout the user and finish this activity
                            AlertDialog.Builder builder = new AlertDialog.Builder(A3techLoginActivity.this);
                            View content = A3techLoginActivity.this.getLayoutInflater()
                                    .inflate(R.layout.mail_verification_sent_dialog, null);
                            content.findViewById(R.id.ok_close).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (okMsgDialog != null) okMsgDialog.dismiss();
                                    FirebaseAuth.getInstance().signOut();
                                }
                            });
                            ((TextView) content.findViewById(R.id.header_email_val_label)).setText( user.getEmail());
                            builder.setView(content);
                            okMsgDialog = builder.create();
                            okMsgDialog.setCancelable(false);
                            okMsgDialog.show();

                        } else {
                            dialog.dismiss();
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            A3techCustomToastDialog.createSnackBar(A3techLoginActivity.this, getString(R.string.probleme_technique_create_account_email_not_sent), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
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
