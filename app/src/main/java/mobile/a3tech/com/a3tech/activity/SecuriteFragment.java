package mobile.a3tech.com.a3tech.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class SecuriteFragment extends Activity implements DataLoadCallback {
   
    AlertDialog alertDialog;
    String connectedUser = "";
    String password = "" ;
   
    
    
    EditText idChangementPasswordDialog_editTextConfirmPassword;
    EditText idChangementPasswordDialog_editTextNewPassword;
    EditText idChangementPasswordDialog_editTextPassword;
    LinearLayout idChangementPassword_linearLayoutAnnuler;
    LinearLayout idChangementPassword_linearLayoutValider;
 
    LinearLayout idNousEcrire_linearLayoutAnnuler;
    LinearLayout idNousEcrire_linearLayoutValider;
    RelativeLayout idSettings_relativeLayoutMotPasse;
   
    
    RelativeLayout idSettings_relativeLayoutSeDesabonner;
   
    
  
    ProgressDialog progressDialog = null;
    LinearLayout idSetting_linearLayoutRetour ;
   String newpassword ;
    
    

  
   


 

    
    private OnClickListener desabonnerListener =new OnClickListener() {
        public void onClick(View v) {
            Builder builder = new Builder(SecuriteFragment.this);
            View content = getLayoutInflater().inflate(R.layout.message_dialog, null);
            LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
            LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutValider);
            ((TextView) content.findViewById(R.id.idMessageDialog_textView)).setText(R.string.txtSettings_msgConfirmDesabonner);
            idMessageDialog_linearLayoutValider.setOnClickListener(alertValiderDesabonementListener);
            idMessageDialog_linearLayoutAnnuler.setOnClickListener(alertAnnulerListener);
            builder.setTitle(getString(R.string.txtSettings_textAlertTitleSignalerDesabonnement));
            builder.setView(content);
            alertDialog = builder.create();
            alertDialog.show();
        }
    };

   
    private OnClickListener alertValiderDesabonementListener =new OnClickListener() {
        public void onClick(View v) {
            progressDialog = CustomProgressDialog.createProgressDialog(SecuriteFragment.this, getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().desabonnerUser(connectedUser,password, SecuriteFragment.this);
            alertDialog.dismiss();
        }
    };

    private OnClickListener alertAnnulerListener =new OnClickListener() {
        public void onClick(View v) {
            alertDialog.dismiss();
        }
    };

  
 
   
  
 
    
    private OnClickListener changePasswordListener = new OnClickListener() {
    	public void onClick(View v) {
   		 new Handler().post(new Runnable() {
					public void run() {
						 Builder builder = new Builder(SecuriteFragment.this);
			                View content = getLayoutInflater().inflate(R.layout.changement_password_dialog, null);
			                builder.setTitle(getString(R.string.txtTitle_dialog_changement_password));
			                idChangementPasswordDialog_editTextPassword = (EditText) content.findViewById(R.id.idChangementPasswordDialog_editTextPassword);
			                idChangementPasswordDialog_editTextNewPassword = (EditText) content.findViewById(R.id.idChangementPasswordDialog_editTextNewPassword);
			                idChangementPasswordDialog_editTextConfirmPassword = (EditText) content.findViewById(R.id.idChangementPasswordDialog_editTextConfirmPassword);
			                LinearLayout idChangementPassword_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idChangementPassword_linearLayoutAnnuler);
			                ((LinearLayout) content.findViewById(R.id.idChangementPassword_linearLayoutValider)).setOnClickListener(ValiderchangePasswordListener);
			                idChangementPassword_linearLayoutAnnuler.setOnClickListener(annulerListener);
			                builder.setView(content);
			               alertDialog = builder.create();
			                alertDialog.show();
					}
   		 	});
    	}
    };
    
    
	
	 public void deconnexion() {
	        Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
	        editor.putString("MyCredentials", "");
	        editor.putString("facebookId", "");
	        editor.putString("conMode", "");
	        editor.putString("ApplicationLanguage", "");
	        editor.putString("identifiant", "");
	        editor.putString("password", "");
	        
	        GCMRegistrar.checkDevice(this);
	        GCMRegistrar.checkManifest(this);
	        GCMRegistrar.unregister(this);
	        editor.commit();
	    }
    
    private OnClickListener ValiderchangePasswordListener = new OnClickListener() {
        public void onClick(View v) {
            String oldPassword = idChangementPasswordDialog_editTextPassword.getText().toString();
            String newPassword = idChangementPasswordDialog_editTextNewPassword.getText().toString();
            newpassword = idChangementPasswordDialog_editTextNewPassword.getText().toString();
            String confirmPassword = idChangementPasswordDialog_editTextConfirmPassword.getText().toString();
            if (oldPassword.length() == 0 || newPassword.length() == 0 || !newPassword.equals(confirmPassword)) {
                Toast.makeText(getApplicationContext(), getString(R.string.txtChangementPassword_msgSaisirPasswordCorrect), Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog = CustomProgressDialog.createProgressDialog(SecuriteFragment.this, getString(R.string.txtMenu_dialogChargement));
            UserManager.getInstance().changePassword(oldPassword, newPassword, connectedUser, SecuriteFragment.this);
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
  		setContentView(R.layout.securite_fragment);
  		
  		idSetting_linearLayoutRetour = (LinearLayout) findViewById(R.id.idSetting_linearLayoutRetour);     
        idSettings_relativeLayoutMotPasse = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutMotPasse);
        idSettings_relativeLayoutMotPasse.setOnClickListener(this.changePasswordListener);       
        idSettings_relativeLayoutSeDesabonner = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutSeDesabonner);
        idSettings_relativeLayoutSeDesabonner.setOnClickListener(desabonnerListener);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        connectedUser = prefs.getString("identifiant", "");
        password = prefs.getString("password", "");
        String conMode = prefs.getString("conMode", "application");  
        if (conMode.equals("application")) {
            idSettings_relativeLayoutMotPasse.setVisibility(View.VISIBLE);
        }else{
        	idSettings_relativeLayoutMotPasse.setVisibility(View.GONE);
        }
        idSetting_linearLayoutRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
       
    }

    

  

    public void dataLoaded(Object data, int method, int typeOperation) {
        switch (method) {
           
            case Constant.KEY_USER_MANAGER_CHANGE_PASSWORD /*17*/:
                Toast.makeText(getApplicationContext(), getString(R.string.txtChangementPassword_msg_PasswordChangeSucces), Toast.LENGTH_SHORT).show();
                Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
    	      
    	        editor.putString("password", newpassword);
    	        
    	        
    	        editor.commit();
                this.progressDialog.dismiss();
                break;
            case Constant.KEY_USER_MANAGER_DESABONNER_USER /*36*/:
                Toast.makeText(getApplicationContext(), getString(R.string.txtSettings_msgCompteDesactive), Toast.LENGTH_SHORT).show();
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
