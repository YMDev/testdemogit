package mobile.a3tech.com.a3tech.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.CaseFormat;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;

public class SplashActivity extends Activity implements DataLoadCallback {
    ImageView animationLoginImage;
    String conMode;
    String connectedUser;
    String password;
    String idMissionNotif;
    String idOffreNotif;
    AnimationDrawable rocketAnimation;
    String userIdNotif;
    String isMessageNotif ;
    String versionname ;
    User usern ;

    static int requExce=  34232;

    public SplashActivity() {
        this.idMissionNotif = "";
        this.idOffreNotif = "";
        this.userIdNotif = "";
        this.isMessageNotif= "" ;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_activity);
        
        try {
	        PackageInfo info = getPackageManager().getPackageInfo("mobile.a3tech.com.a3tech", PackageManager.GET_SIGNATURES);
	        for (android.content.pm.Signature signature : info.signatures) {
	            MessageDigest md = MessageDigest.getInstance("SHA");
	            md.update(signature.toByteArray());
	            Log.d("KeyHash======>:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
	        }
	    } catch (NameNotFoundException e) {
	        Log.e("KeyHash:", e.toString());
	    } catch (NoSuchAlgorithmException e) {
	        Log.e("KeyHash:", e.toString());
	    }
        setVersionInfo();
        
        
        
        this.idMissionNotif = getIntent().getStringExtra("idMissionNot");
        this.userIdNotif = getIntent().getStringExtra("userIdNot");
        this.idOffreNotif = getIntent().getStringExtra("idOffreNot");
        this.isMessageNotif = getIntent().getStringExtra("isMessageNot");
        this.animationLoginImage = (ImageView) findViewById(R.id.animationLoginImage);
        this.animationLoginImage.setBackgroundResource(R.drawable.rocket_thrust);
        this.rocketAnimation = (AnimationDrawable) this.animationLoginImage.getBackground();
        this.rocketAnimation.start();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.conMode = prefs.getString("conMode", "");
        this.connectedUser = prefs.getString("identifiant", "");
        this.password = prefs.getString("password", "");
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.txtSplash_messageCheckConnexion), Toast.LENGTH_SHORT).show();
            finish();
        } else if (this.conMode.equals("facebook")) {
            Intent mainIntent = new Intent(this, FacebookActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC, Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_SPLASH);
            mainIntent.putExtras(bundle);
            startActivityForResult(mainIntent, requExce);
        } else if (this.conMode.length() == 0) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            UserManager.getInstance().getProfil(this.connectedUser,password, this);
        }
        ((TextView) findViewById(R.id.idLogin_textViewlogan)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/harlowsi.ttf"));
        ((TextView) findViewById(R.id.idLogin_textViewlogan)).setText(Html.fromHtml(getString(R.string.txtLogin_textViewlogan)));
        
        
        
//          ASyncCheck;
//        checker.setSuccessfulChecksRequired(2);
//        checker.start();
       
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != Constant.SPLASH_FACEBOOK_REQUEST_CODE_SPLASH || resultCode != -1) {
            return;
        }
        if (data.getStringExtra("resultCode").equals("OK")) {
            String nom = data.getStringExtra("nom");
            String prenom = data.getStringExtra("prenom");
            String email = data.getStringExtra("email");
            password = data.getStringExtra("facebookIdentifiant");
            UserManager.getInstance().loginfb(nom, prenom, data.getStringExtra("facebookIdentifiant"), email, this);
            return;
        }
        new Builder(this).setTitle("Error").setMessage(data.getStringExtra("info")).setPositiveButton("OK", null).show();
    }

    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Locale locale2 = new Locale(prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH));
        Locale.setDefault(locale2);
        Configuration config2 = new Configuration();
        config2.locale = locale2;
        getBaseContext().getResources().updateConfiguration(config2, getBaseContext().getResources().getDisplayMetrics());
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            return false;
        }
        return true;
    }

    public void dataLoaded(Object data, int method, int typeOperation) {
    	switch (method) {
		case Constant.KEY_USER_MANAGER_LOGIN_FB:
		case Constant.KEY_USER_MANAGER_GET_PROFIL:
			usern = (User) data;
	        UserManager.getInstance().getVersion(SplashActivity.this);
			break;
		case Constant.KEY_USER_GET_VERSION:
			String version  =(String)data ;
			 Intent mainIntent = new Intent(this, NavigationMain.class);
			if(versionname.equals(version)){
				mainIntent.putExtra("nomPrenom", usern.getPrenom() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + usern.getNom());
		        mainIntent.putExtra("nbr", usern.getNbrServiceEmis());
		        mainIntent.putExtra("idMissionNotif", this.idMissionNotif);
		        mainIntent.putExtra("userIdNotif", this.userIdNotif);
		        mainIntent.putExtra("idOffreNotif", this.idOffreNotif);
		        mainIntent.putExtra("isMessageNotif", this.isMessageNotif);
		       
		        Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
		        editor.putString("MyCredentials", usern.getEmail());
		        editor.putString("pseudo", usern.getPseudo());
		        editor.putString("identifiant", usern.getIdentifiant());
		        editor.putString("password", password);
		        editor.putString("facebookId", usern.getFacebookId());
		        editor.putString("checkphone", usern.getCheckphone());
		        editor.putString("mode", usern.getMode());
		        editor.commit();
		        startActivity(mainIntent);
		        finish();
			}else{
				Toast.makeText(SplashActivity.this, "Veuillez proceder � la mise � jour de votre application", Toast.LENGTH_LONG).show();
				 startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=ma.khodara")));
				 finish();
			}

		}
       
    }
    
    private void setVersionInfo() {
 	   // int versionCode = -1;
 	    try {
 	        PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
 	        versionname = packageInfo.versionName;
 	       // versionCode = packageInfo.versionCode;
 	    } catch (NameNotFoundException e) {
 	        e.printStackTrace();
 	    }

 	}

    public void dataLoadingError(int errorCode) {
    }

    public void dataLoadingError(String error) {
    }

	
}
