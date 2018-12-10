package mobile.a3tech.com.a3tech.activity;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.regex.Pattern;



import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class LoginActivity extends Activity implements DataLoadCallback {

	AlertDialog alertDialog;
	ImageView animationLoginImage;
	static int requestKey = 3422;
	ProgressDialog dialog = null;

	EditText idLoginDialog_editTextMail;
	EditText idLoginDialog_editTextPassword;
	TextView idLoginDialog_textViewPasswordForgot;
	Button idLogin_buttonAddContact;
	Button idLogin_buttonLogin;
	LinearLayout idLogin_linearLayoutAnnuler;
	LinearLayout idLogin_linearLayoutCreateAccount;
	RelativeLayout idLogin_linearLayoutFacebook;
	LinearLayout idLogin_linearLayoutLogin;
	LinearLayout idLogin_linearLayoutValider;
	EditText idPasswordForgetDialog_editTextEmail;
	LinearLayout idPasswordForget_linearLayoutAnnuler;
	LinearLayout idPasswordForget_linearLayoutValider;
	RelativeLayout idLogin_linearLayoutAnonyme;
	String password ;

	AnimationDrawable rocketAnimation;

	private OnClickListener faceBookListener = new OnClickListener() {
		public void onClick(View v) {
			Intent mainIntent = new Intent(LoginActivity.this,
					FacebookActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC,
					Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGIN);
			mainIntent.putExtras(bundle);
			LoginActivity.this.startActivityForResult(mainIntent,
					requestKey);
		}
	};

	private OnClickListener addContactListener = new OnClickListener() {
		public void onClick(View v) {
			LoginActivity.this.startActivity(new Intent(LoginActivity.this,
					InscriptionActivity.class));
			LoginActivity.this.finish();
		}
	};

	private OnClickListener passWordForgetListener = new OnClickListener() {

		public void onClick(View v) {

			/*new Handler().post(new Runnable() {

				@Override
				public void run() {
					try {
						LoginActivity.this.alertDialog.dismiss();
					} catch (Exception e) {
					}
					Builder builder = new Builder(LoginActivity.this);
					View content = LoginActivity.this.getLayoutInflater()
							.inflate(R.layout.password_forget_dialog, null);
					LoginActivity.this.idPasswordForgetDialog_editTextEmail = (EditText) content
							.findViewById(R.id.idPasswordForgetDialog_editTextEmail);
					LoginActivity.this.idPasswordForget_linearLayoutAnnuler = (LinearLayout) content
							.findViewById(R.id.close_dialog);
					LoginActivity.this.idPasswordForget_linearLayoutValider = (LinearLayout) content
							.findViewById(R.id.validerForgetListener);
					builder.setTitle(LoginActivity.this
							.getString(R.string.txtLogin_title_dialog_password_forget));
					LoginActivity.this.idPasswordForget_linearLayoutValider
							.setOnClickListener(LoginActivity.this.validerForgetListener);
					LoginActivity.this.idPasswordForget_linearLayoutAnnuler
							.setOnClickListener(LoginActivity.this.annulerListener);
					builder.setView(content);
					LoginActivity.this.alertDialog = builder.create();
					LoginActivity.this.alertDialog.show();

				}
			});*/
		}
	};

	private OnClickListener loginListener = new OnClickListener() {

		public void onClick(View v) {

			new Handler().post(new Runnable() {

				@Override
				public void run() {
					Builder builder = new Builder(LoginActivity.this);
					View content = LoginActivity.this.getLayoutInflater()
							.inflate(R.layout.login_dialog, null);
					builder.setTitle(LoginActivity.this
							.getString(R.string.txtLogin_title_dialog_login));
					LoginActivity.this.idLoginDialog_textViewPasswordForgot = (TextView) content
							.findViewById(R.id.idLoginDialog_textViewPasswordForgot);
					LoginActivity.this.idLoginDialog_editTextMail = (EditText) content
							.findViewById(R.id.idLoginDialog_editTextMail);
					LoginActivity.this.idLoginDialog_editTextPassword = (EditText) content
							.findViewById(R.id.idLoginDialog_editTextPassword);
					LoginActivity.this.idLogin_linearLayoutAnnuler = (LinearLayout) content
							.findViewById(R.id.idLogin_linearLayoutAnnuler);
					LoginActivity.this.idLogin_linearLayoutValider = (LinearLayout) content
							.findViewById(R.id.idLogin_linearLayoutValider);
					SpannableString spanString = new SpannableString(
							LoginActivity.this
									.getString(R.string.txtLoginDialog_textViewPasswordForgot));
					spanString.setSpan(new UnderlineSpan(), 0,
							spanString.length(), 0);
					LoginActivity.this.idLoginDialog_textViewPasswordForgot
							.setText(spanString);
					LoginActivity.this.idLoginDialog_textViewPasswordForgot
							.setOnClickListener(LoginActivity.this.passWordForgetListener);
					LoginActivity.this.idLogin_linearLayoutValider
							.setOnClickListener(LoginActivity.this.connecterListener);
					LoginActivity.this.idLogin_linearLayoutAnnuler
							.setOnClickListener(LoginActivity.this.annulerListener);
					builder.setView(content);
					LoginActivity.this.alertDialog = builder.create();
					LoginActivity.this.alertDialog.show();

				}
			});
		}
	};

	private OnClickListener validerForgetListener = new OnClickListener() {

		public void onClick(View v) {
			String email = LoginActivity.this.idPasswordForgetDialog_editTextEmail
					.getText().toString();
			if (email.length() == 0
					|| !LoginActivity.this.isValidEmailAddress(email)) {
				Toast.makeText(
						LoginActivity.this.getApplicationContext(),
						LoginActivity.this
								.getString(R.string.txtPasswordForgetDialog_textViewPasswordForgot),
						Toast.LENGTH_SHORT).show();
				return;
			}
			LoginActivity.this.dialog = CustomProgressDialog
					.createProgressDialog(
							LoginActivity.this,
							LoginActivity.this
									.getString(R.string.txtMenu_dialogChargement));
			UserManager.getInstance().initPassword(email,
					LoginActivity.this.generatePassword(), LoginActivity.this);
		}
	};

	private OnClickListener connecterListener = new OnClickListener() {

		public void onClick(View v) {
			LoginActivity.this.connecter();
		}
	};

	private OnClickListener annulerListener = new OnClickListener() {

		public void onClick(View v) {
			LoginActivity.this.alertDialog.dismiss();
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.login_activity);
		this.animationLoginImage = (ImageView) findViewById(R.id.animationLoginImage);
		this.idLogin_linearLayoutLogin = (LinearLayout) findViewById(R.id.idLogin_linearLayoutLogin);
		this.idLogin_linearLayoutCreateAccount = (LinearLayout) findViewById(R.id.idLogin_linearLayoutCreateAccount);
		this.idLogin_linearLayoutFacebook = (RelativeLayout) findViewById(R.id.idLogin_linearLayoutFacebook);
		this.idLogin_linearLayoutFacebook
				.setOnClickListener(this.faceBookListener);
		this.animationLoginImage
				.setBackgroundResource(R.drawable.rocket_thrust);
		this.rocketAnimation = (AnimationDrawable) this.animationLoginImage
				.getBackground();
		this.rocketAnimation.start();
		this.idLogin_linearLayoutLogin.setOnClickListener(this.loginListener);
		this.idLogin_linearLayoutCreateAccount
				.setOnClickListener(this.addContactListener);
		((TextView) findViewById(R.id.idLogin_textViewlogan))
				.setTypeface(Typeface.createFromAsset(getAssets(),
						"fonts/harlowsi.ttf"));
		idLogin_linearLayoutAnonyme = (RelativeLayout) findViewById(R.id.idLogin_linearLayoutAnonyme);
		idLogin_linearLayoutAnonyme.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mainIntent = new Intent(LoginActivity.this, NavigationMain.class);

				startActivity(mainIntent);
				finish();

			}
		});
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
		case Constant.KEY_USER_MANAGER_LOGIN /* 4 */:
		case Constant.KEY_USER_MANAGER_LOGIN_FB /* 35 */:
			User user = (User) data;
			Editor editor = PreferenceManager.getDefaultSharedPreferences(
					getApplicationContext()).edit();
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
			Intent mainIntent = new Intent(this, NavigationMain.class);
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
}
