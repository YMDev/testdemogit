package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AproposFragment extends Activity {
   
    AlertDialog alertDialog;
    
    
    RelativeLayout idSettings_relativeLayoutCGU ;
    RelativeLayout idSettings_relativeLayoutCGV ;
    RelativeLayout idSettings_relativeLayoutFAQ ;
    LinearLayout idSetting_linearLayoutRetour ;
    
    
	private OnClickListener alertValiderListener = new OnClickListener() {
		public void onClick(View v) {
			alertDialog.dismiss();
		}
	};
  
    

   
  
    
    private OnClickListener cgulistener = new OnClickListener() {
    	 public void onClick(View v) {
    		 Handler handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						Builder builder = new Builder(AproposFragment.this);
						LayoutInflater inflater = AproposFragment.this.getLayoutInflater();
						View content = inflater.inflate(R.layout.conditions_dialog_box, null);
						WebView idConditions_webviewContent = (WebView) content.findViewById(R.id.idConditions_webviewContent);
						LinearLayout idCondition_relativeLayoutValider = (LinearLayout) content.findViewById(R.id.idCondition_relativeLayoutValider);
						((TextView) content.findViewById(R.id.idCondition_textviewValider)).setText(getString(R.string.txtCondition_textviewFermer));
						idCondition_relativeLayoutValider.setOnClickListener(alertValiderListener);
						builder.setView(content);
						idConditions_webviewContent.loadUrl(Constant.CGU_URL_FR);
						alertDialog = builder.create();
						alertDialog.show();
					}
				});
    	 }
    };
    
    private OnClickListener cgvlistener = new OnClickListener() {
   	 public void onClick(View v) {
   		 Handler handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						Builder builder = new Builder(AproposFragment.this);
						LayoutInflater inflater = AproposFragment.this.getLayoutInflater();
						View content = inflater.inflate(R.layout.conditions_dialog_box, null);
						WebView idConditions_webviewContent = (WebView) content.findViewById(R.id.idConditions_webviewContent);
						LinearLayout idCondition_relativeLayoutValider = (LinearLayout) content.findViewById(R.id.idCondition_relativeLayoutValider);
						((TextView) content.findViewById(R.id.idCondition_textviewValider)).setText(getString(R.string.txtCondition_textviewFermer));
						idCondition_relativeLayoutValider.setOnClickListener(alertValiderListener);
						builder.setView(content);
						idConditions_webviewContent.loadUrl(Constant.CGV_URL_FR);
						alertDialog = builder.create();
						alertDialog.show();
					}
				});
   	 }
   };
   
   private OnClickListener faqlistener = new OnClickListener() {
  	 public void onClick(View v) {
  		 Handler handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						Builder builder = new Builder(AproposFragment.this);
						LayoutInflater inflater = AproposFragment.this.getLayoutInflater();
						View content = inflater.inflate(R.layout.conditions_dialog_box, null);
						WebView idConditions_webviewContent = (WebView) content.findViewById(R.id.idConditions_webviewContent);
						LinearLayout idCondition_relativeLayoutValider = (LinearLayout) content.findViewById(R.id.idCondition_relativeLayoutValider);
						((TextView) content.findViewById(R.id.idCondition_textviewValider)).setText(getString(R.string.txtCondition_textviewFermer));
						idCondition_relativeLayoutValider.setOnClickListener(alertValiderListener);
						builder.setView(content);
						idConditions_webviewContent.loadUrl(Constant.FAQ_URL_FR);
						alertDialog = builder.create();
						alertDialog.show();
					}
				});
  	 }
  };
    
   
    
    
 

    protected void onCreate(Bundle savedInstanceState) {
  		super.onCreate(savedInstanceState);
  		requestWindowFeature(1);
  		setContentView(R.layout.setting_fragment);
  	
  		idSetting_linearLayoutRetour = (LinearLayout) findViewById(R.id.idSetting_linearLayoutRetour);
  	    idSettings_relativeLayoutCGU = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutCGU);
  	    idSettings_relativeLayoutCGV = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutCGV);
  	    idSettings_relativeLayoutFAQ = (RelativeLayout) findViewById(R.id.idSettings_relativeLayoutFAQ);
  	    idSettings_relativeLayoutCGU.setOnClickListener(cgulistener);
  	    idSettings_relativeLayoutCGV.setOnClickListener(cgvlistener);
    	idSettings_relativeLayoutFAQ.setOnClickListener(faqlistener);
       
      
      
        idSetting_linearLayoutRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();				
			}
		});
       
    }

    


   

    
}
