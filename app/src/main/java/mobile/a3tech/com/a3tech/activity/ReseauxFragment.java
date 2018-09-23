package mobile.a3tech.com.a3tech.activity;

import java.util.Locale;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class ReseauxFragment extends Activity implements DataLoadCallback {
   
    AlertDialog alertDialog;
    
  
    int city;
    String connectedUser = "";
    String password = "" ;
   
    
    
   
    LinearLayout idChangementPassword_linearLayoutAnnuler;
    LinearLayout idChangementPassword_linearLayoutValider;
    EditText idNousEcrireDialog_editTextMessage;
    LinearLayout idNousEcrire_linearLayoutAnnuler;
    LinearLayout idNousEcrire_linearLayoutValider;
    
    RelativeLayout idSettings_relativeLayoutNousEcrire;
    RelativeLayout idSettings_relativeLayoutNousSuivreFacebook;
    RelativeLayout idSettings_relativeLayoutNousSuivreGooglePlay;
    RelativeLayout idSettings_relativeLayoutNousSuivreTwitter;
   
   
    
   
    LinearLayout idSetting_linearLayoutRetour ;
   
    
    ProgressDialog progressDialog = null;

  



    


    private OnClickListener alertAnnulerListener =new OnClickListener() {
        public void onClick(View v) {
            alertDialog.dismiss();
        }
    };

  
    private OnClickListener deconnectionListener =new OnClickListener() {
        public void onClick(View v) {
            String conMode = PreferenceManager.getDefaultSharedPreferences(ReseauxFragment.this).getString("conMode", "");
            ReseauxFragment.this.deconnexion();
            if (conMode.equals("facebook")) {
                Intent mainIntent = new Intent(ReseauxFragment.this, FacebookActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC, Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGOUT);
                mainIntent.putExtras(bundle);
                startActivityForResult(mainIntent,  Constant.SPLASH_FACEBOOK_REQUEST_CODE_LOGOUT);
                return;
            }
            startActivity(new Intent(ReseauxFragment.this, LoginActivity.class));
            finish();
        }
    };

   
    private OnClickListener twitterListener = new OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://twitter.com/arakhod")));
        }
    };

   
    private OnClickListener facebookListener =new OnClickListener() {
     
        public void onClick(View v) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://www.facebook.com/arakhod")));
        }
    };

    private OnClickListener googlePlayListener = new OnClickListener() {
        public void onClick(View v) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=ma.arakhod")));
        }
    };
    
    private OnClickListener nousEcrireListener = new OnClickListener() {

    	 public void onClick(View v) {
    		 new Handler().post(new Runnable() {
					public void run() {
						 Builder builder = new Builder(ReseauxFragment.this);
			                View content = getLayoutInflater().inflate(R.layout.nous_ecrire_dialog, null);
			                builder.setTitle(getString(R.string.txtTitle_dialog_nous_contacter));
			                idNousEcrireDialog_editTextMessage = (EditText) content.findViewById(R.id.idNousEcrireDialog_editTextMessage);
			                idNousEcrire_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idNousEcrire_linearLayoutAnnuler);
			                idNousEcrire_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idNousEcrire_linearLayoutValider);
			                idNousEcrire_linearLayoutValider.setOnClickListener(ValiderNousContacterListener);
			                idNousEcrire_linearLayoutAnnuler.setOnClickListener(annulerListener);
			                builder.setView(content);
			                alertDialog = builder.create();
			                alertDialog.show();
					}
    		 });
    	 }
       
    };
    
    private OnClickListener ValiderNousContacterListener = new OnClickListener() {
        public void onClick(View v) {
            String question = idNousEcrireDialog_editTextMessage.getText().toString();
            progressDialog = CustomProgressDialog.createProgressDialog(ReseauxFragment.this, getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().contactSupport(connectedUser, question,password, ReseauxFragment.this);
            alertDialog.dismiss();
        }
    };
    
    
    
 
    
    private OnClickListener annulerListener = new OnClickListener() {
        public void onClick(View v) {
            alertDialog.hide();
        }
    };
   

    protected void onCreate(Bundle savedInstanceState) {
  		super.onCreate(savedInstanceState);
  		requestWindowFeature(1);
  		setContentView(R.layout.reseaux_fragment);
  		boolean z;
  		idSetting_linearLayoutRetour = (LinearLayout) findViewById(R.id.idSetting_linearLayoutRetour);
        idSettings_relativeLayoutNousEcrire = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutNousEcrire);
        idSettings_relativeLayoutNousSuivreFacebook = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutNousSuivreFacebook);
        idSettings_relativeLayoutNousSuivreTwitter = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutNousSuivreTwitter);
        idSettings_relativeLayoutNousSuivreGooglePlay = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutNousSuivreGooglePlay);
       
       
        idSettings_relativeLayoutNousEcrire.setOnClickListener(nousEcrireListener);
        idSettings_relativeLayoutNousSuivreFacebook.setOnClickListener(facebookListener);
        idSettings_relativeLayoutNousSuivreTwitter.setOnClickListener(twitterListener);
        idSettings_relativeLayoutNousSuivreGooglePlay.setOnClickListener(googlePlayListener);
       
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        connectedUser = prefs.getString("identifiant", "");
        password = prefs.getString("password", "");
       
        
      
        idSetting_linearLayoutRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
       
    }

    

    public void deconnexion() {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        editor.putString("MyCredentials", "");
        editor.putString("facebookId", "");
        editor.putString("conMode", "");
        editor.putString("ApplicationLanguage", "");
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        GCMRegistrar.unregister(this);
        editor.commit();
    }

    public void dataLoaded(Object data, int method, int typeOperation) {
        switch (method) {
            case Constant.KEY_USER_MANAGER_CONTACT_SUPPORT /*16*/:
                Toast.makeText(getApplicationContext(), getString(R.string.txtNousContacter_msg_QuestionEnvoyeeSucces), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                break;
            case Constant.KEY_USER_MANAGER_CHANGE_PASSWORD /*17*/:
                Toast.makeText(getApplicationContext(), getString(R.string.txtChangementPassword_msg_PasswordChangeSucces), Toast.LENGTH_SHORT).show();
                this.progressDialog.dismiss();
                break;
            case Constant.KEY_USER_MANAGER_DESABONNER_USER /*36*/:
                Toast.makeText(getApplicationContext(), getString(R.string.txtSettings_msgCompteDesactive), Toast.LENGTH_SHORT).show();
                deconnexion();
                String conMode = PreferenceManager.getDefaultSharedPreferences(this).getString("conMode", "");
                deconnexion();
                if (conMode.equals("facebook")) {
                    Intent mainIntent = new Intent(this, FacebookActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC, Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGOUT);
                    mainIntent.putExtras(bundle);
                    startActivityForResult(mainIntent, Constant.SPLASH_FACEBOOK_REQUEST_CODE_LOGOUT);
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                }
                progressDialog.dismiss();
            default:
        }
    }

    public void dataLoadingError(int errorCode) {
        if (errorCode == 10) {
            try {
                Toast.makeText(getApplicationContext(), getString(R.string.txtChangementPassword_msgCompteInexistant), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (errorCode == 1) {
            Toast.makeText(getApplicationContext(), getString(R.string.txtChangementPassword_msgInternetInexistant), Toast.LENGTH_SHORT).show();
        }
        if (errorCode == 20) {
            Toast.makeText(getApplicationContext(), getString(R.string.txtChangementPassword_msgPasswordInexistant), Toast.LENGTH_SHORT).show();
        }
        this.progressDialog.dismiss();
    }

    public void dataLoadingError(String error) {
    }
}
