/*
package mobile.a3tech.com.a3tech.activity;

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
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techLoginPagerviewerAdapter;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techLoginActivityoldV extends BaseActivity implements DataLoadCallback {

	AlertDialog alertDialog;
	static int requestKey = 3422;
	ProgressDialog dialog = null;

	EditText idLoginDialog_editTextMail;
	EditText idLoginDialog_editTextPassword;
	TextView idLoginDialog_textViewPasswordForgot;
	Button idLogin_buttonAddContact;
	Button btn_create_account;
	LinearLayout idLogin_linearLayoutAnnuler;
	LinearLayout idLogin_linearLayoutCreateAccount;
	RelativeLayout createAccountContainer;

	LinearLayout idLogin_linearLayoutLogin;
	LinearLayout idLogin_linearLayoutValider;
	EditText idPasswordForgetDialog_editTextEmail;
	TextView signinAction;
	LinearLayout idPasswordForget_linearLayoutAnnuler;
	LinearLayout idPasswordForget_linearLayoutValider;
	RelativeLayout idLogin_linearLayoutAnonyme;
	String password ;

	AnimationDrawable rocketAnimation;


	*/
/*
	First view pager of login screen
	 *//*

    private CoordinatorLayout rootView;
    private ViewPager mViewPager;
    ImageView zero, one, two;
    ImageView[] indicators;
    int lastLeftValue = 0;
    RelativeLayout mCoordinator;
    int page = 0;   //  to track page position

    */
/*
    End of view pager
     *//*


	private OnClickListener faceBookListener = new OnClickListener() {
		public void onClick(View v) {
			Intent mainIntent = new Intent(A3techLoginActivityoldV.this,
					FacebookActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC,
					Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGIN);
			mainIntent.putExtras(bundle);
			A3techLoginActivityoldV.this.startActivityForResult(mainIntent,
					requestKey);
		}
	};

	private OnClickListener addContactListener = new OnClickListener() {
		public void onClick(View v) {
			*/
/*A3techLoginActivity.this.startActivity(new Intent(A3techLoginActivity.this,
					InscriptionActivity.class));*//*

			A3techLoginActivityoldV.this.startActivity(new Intent(A3techLoginActivityoldV.this,
					A3techCreateAccountActivity.class));
			A3techLoginActivityoldV.this.finish();
		}
	};

	private OnClickListener passWordForgetListener = new OnClickListener() {

		public void onClick(View v) {

			new Handler().post(new Runnable() {

				@Override
				public void run() {
					try {
						A3techLoginActivityoldV.this.alertDialog.dismiss();
					} catch (Exception e) {
					}
					Builder builder = new Builder(A3techLoginActivityoldV.this);
					View content = A3techLoginActivityoldV.this.getLayoutInflater()
							.inflate(R.layout.password_forget_dialog, null);
					A3techLoginActivityoldV.this.idPasswordForgetDialog_editTextEmail = (EditText) content
							.findViewById(R.id.idPasswordForgetDialog_editTextEmail);
					A3techLoginActivityoldV.this.idPasswordForget_linearLayoutAnnuler = (LinearLayout) content
							.findViewById(R.id.idPasswordForget_linearLayoutAnnuler);
					A3techLoginActivityoldV.this.idPasswordForget_linearLayoutValider = (LinearLayout) content
							.findViewById(R.id.idPasswordForget_linearLayoutValider);
					builder.setTitle(A3techLoginActivityoldV.this
							.getString(R.string.txtLogin_title_dialog_password_forget));
					A3techLoginActivityoldV.this.idPasswordForget_linearLayoutValider
							.setOnClickListener(A3techLoginActivityoldV.this.validerForgetListener);
					A3techLoginActivityoldV.this.idPasswordForget_linearLayoutAnnuler
							.setOnClickListener(A3techLoginActivityoldV.this.annulerListener);
					builder.setView(content);
					A3techLoginActivityoldV.this.alertDialog = builder.create();
					A3techLoginActivityoldV.this.alertDialog.show();

				}
			});
		}
	};

	private OnClickListener loginListener = new OnClickListener() {

		public void onClick(View v) {
			A3techLoginActivityoldV.this.startActivity(new Intent(A3techLoginActivityoldV.this,
                    A3techSignInActivity.class));
			A3techLoginActivityoldV.this.finish();
		*/
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
			});*//*

		}
	};

	private OnClickListener validerForgetListener = new OnClickListener() {

		public void onClick(View v) {
			String email = A3techLoginActivityoldV.this.idPasswordForgetDialog_editTextEmail
					.getText().toString();
			if (email.length() == 0
					|| !A3techLoginActivityoldV.this.isValidEmailAddress(email)) {
				Toast.makeText(
						A3techLoginActivityoldV.this.getApplicationContext(),
						A3techLoginActivityoldV.this
								.getString(R.string.txtPasswordForgetDialog_textViewPasswordForgot),
						Toast.LENGTH_SHORT).show();
				return;
			}
			A3techLoginActivityoldV.this.dialog = CustomProgressDialog
					.createProgressDialog(
							A3techLoginActivityoldV.this,
							A3techLoginActivityoldV.this
									.getString(R.string.txtMenu_dialogChargement));
			UserManager.getInstance().initPassword(email,
					A3techLoginActivityoldV.this.generatePassword(), A3techLoginActivityoldV.this);
		}
	};

	private OnClickListener connecterListener = new OnClickListener() {

		public void onClick(View v) {
			A3techLoginActivityoldV.this.connecter();
		}
	};

	private OnClickListener annulerListener = new OnClickListener() {

		public void onClick(View v) {
			A3techLoginActivityoldV.this.alertDialog.dismiss();
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.a3tech_login_activity);
		btn_create_account = findViewById(R.id.btn_create_account);
		btn_create_account.setOnClickListener(this.addContactListener);
		signinAction = findViewById(R.id.signin_action_login);
		signinAction.setOnClickListener(this.loginListener);
		*/
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
*//*

		iniPager();
	}

	private void iniPager(){
        zero = (ImageView) findViewById(R.id.intro_indicator_0);
        one = (ImageView) findViewById(R.id.intro_indicator_1);
        two = (ImageView) findViewById(R.id.intro_indicator_2);
        mCoordinator = (RelativeLayout) findViewById(R.id.main_content);
        indicators = new ImageView[]{zero, one, two};
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
		final int[] listeBack = new int[]{R.drawable.a3tech_back_4, R.drawable.a3tech_back_4, R.drawable.a3tech_back_4};
        mViewPager.setAdapter(new A3techLoginPagerviewerAdapter(this,listeBack));
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
			password = facebookIdentifiant ;
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
		char[] allowedCharacters = new char[] { 'a', 'b', 'c', '1', '2', '3',
				'4' };
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
		case Constant.KEY_USER_MANAGER_LOGIN */
/* 4 *//*
:
		case Constant.KEY_USER_MANAGER_LOGIN_FB */
/* 35 *//*
:
			A3techUser user = (A3techUser) data;
			Editor editor = PreferenceManager.getDefaultSharedPreferences(
					getApplicationContext()).edit();
			editor.putString("MyCredentials", user.getEmail());
			editor.putString("pseudo", user.getPseudo());
            editor.putString(PreferencesValuesUtils.KEY_CONNECTED_USER_NAME, user.getNom());
			editor.putString("identifiant", user.getId()+"");
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
			mainIntent.putExtra("nbr", user.getNbrMission()+"");

			startActivity(mainIntent);
			finish();
			break;
		case Constant.KEY_USER_MANAGER_INIT_PASSWORD */
/* 6 *//*
:
			this.dialog.dismiss();
			this.alertDialog.dismiss();
			Toast.makeText(getApplicationContext(),
					R.string.txtPasswordForgot_msg_password_initilise,
					Toast.LENGTH_SHORT).show();
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
			A3techLoginActivityoldV.this.runOnUiThread(new Runnable() {
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
}
*/
