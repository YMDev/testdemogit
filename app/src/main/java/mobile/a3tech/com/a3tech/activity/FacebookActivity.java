package mobile.a3tech.com.a3tech.activity;

import java.util.Arrays;
import java.util.List;


import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.WebDialog;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.Constant;

public class FacebookActivity extends Activity {
    protected List<String> PERMISSIONS= Arrays.asList(new String[]{"basic_info", "email"});
    String caption;
    String description;
    String name;
    
    String src = "";
    String strEmail;
    String strFirstName;
    String strLocation;
    LoginButton login_button ;
    String facebookid ;
    boolean  sessionExpired ;   
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
  
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(R.layout.facebook);
        Bundle b = getIntent().getExtras();
      
        if (b != null) {
            this.src = b.getString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC);
           
        }
        if (this.src.equals(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGIN)||this.src.equals(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_SPLASH) ) {
        	
        	AccessToken facebookAccessToken = AccessToken.getCurrentAccessToken();
        	if(facebookAccessToken != null){
        	        sessionExpired = facebookAccessToken.isExpired();
        	}else{
        	        sessionExpired = true;
        	}
        	if(sessionExpired==false){
        		
        		LoginManager.getInstance().logOut();
        	}
        	
        	callbackManager=CallbackManager.Factory.create();
        	
        	login_button= (LoginButton)findViewById(R.id.login_button);
        	
        	login_button.setReadPermissions("public_profile", "email");
        	
        	login_button.performClick();
        	
        	login_button.setPressed(true);
        
        	login_button.invalidate();
        	
        	login_button.registerCallback(callbackManager, mCallBack);
        	
        	login_button.setPressed(false); 
        	
        	login_button.invalidate();
        	
        	
        }
       
        if (this.src.equals(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGOUT)) {
        	LoginManager.getInstance().logOut();
             setResult(-1, new Intent());
             finish();
           
        }
        if (this.src.equals(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_DETAIL)) {
        	AccessToken facebookAccessToken = AccessToken.getCurrentAccessToken();
        	if(facebookAccessToken != null){
        	        sessionExpired = facebookAccessToken.isExpired();
        	}else{
        	        sessionExpired = true;
        	}
        	if(sessionExpired==false){
        		callbackManager=CallbackManager.Factory.create();
        		ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentTitle(this.name)
             
                .setContentDescription(
                		this.description)
                .setContentUrl(Uri.parse("http://play.google.com/store/apps/details?id=ma.khodara"))
                .setImageUrl(Uri.parse("http://oostadee.com/images/256_OOSTADEE.png"))
                .build();
	        	 shareDialog = new ShareDialog(this);
	             shareDialog.registerCallback(
	                     callbackManager,
	                     shareCallback);
	             shareDialog.show(linkContent);
	            
        	}else{
        		 Intent returnIntent = new Intent();
        	        returnIntent.putExtra("info", "5");
        	        setResult(-1, returnIntent);
        	        finish();
        		
        	}
        }
    }
    
  
    
  
    
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
        	 Intent returnIntent = new Intent();
             returnIntent.putExtra("info", "3");
             FacebookActivity.this.setResult(-1, returnIntent);
             FacebookActivity.this.finish();
        }

        @Override
        public void onError(FacebookException error) {
        	Intent returnIntent = new Intent();
             returnIntent.putExtra("info", "4");
             FacebookActivity.this.setResult(-1, returnIntent);
             FacebookActivity.this.finish();
        }

        @Override
        public void onSuccess(Sharer.Result result) {
        	Intent returnIntent = new Intent();
        	  if (result.getPostId() != null) {
                  returnIntent.putExtra("info", "1");
                  FacebookActivity.this.setResult(-1, returnIntent);
                  FacebookActivity.this.finish();
                  return;
              }
              
              returnIntent.putExtra("info", "2");
              FacebookActivity.this.setResult(-1, returnIntent);
              FacebookActivity.this.finish();
        }
    };
    
    
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(
                    loginResult.getAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                        	
                                try {
                                    Intent returnIntent = new Intent();
                	                returnIntent.putExtra("nom", object.getString("first_name").toString());
                	                returnIntent.putExtra("prenom", object.getString("last_name").toString());
                	                returnIntent.putExtra("email", object.getString("email").toString());
                	                returnIntent.putExtra("facebookIdentifiant",  object.getString("id").toString());
                	                returnIntent.putExtra("resultCode", "OK");
                	                FacebookActivity.this.setResult(-1, returnIntent);
                	                FacebookActivity.this.finish();
 
                                }catch (Exception e){
                                	Intent returnIntent = new Intent();
                	                returnIntent.putExtra("resultCode", "NOK");
                	                returnIntent.putExtra("info", e.getMessage());
                	                FacebookActivity.this.setResult(-1, returnIntent);
                	                FacebookActivity.this.finish();
                                }
                        }
 
                    });
           
            
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,first_name,last_name");
            request.setParameters(parameters);
            request.executeAsync();
        }
 
        @Override
        public void onCancel() {
        	Toast.makeText(FacebookActivity.this,"Canceled !!!!!", Toast.LENGTH_LONG).show();
        	
            
        }
 
        @Override
        public void onError(FacebookException e) {
        
            Toast.makeText(FacebookActivity.this,e.toString(), Toast.LENGTH_LONG).show();
        }
    };
 

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
   
    
    protected void postFacebook(Activity activity) {


    }
    
}
