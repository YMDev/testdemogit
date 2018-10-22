package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import mobile.a3tech.com.a3tech.R;

public class A3techWaitingDialog {
	
	public static ProgressDialog createProgressDialog(Context mContext,String text) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
		LayoutInflater inflater =((Activity)mContext).getLayoutInflater();
		progressDialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
		View content =inflater.inflate(R.layout.a3tech_waiting_dialogue_splach, null);
    	/*TextView  myTitle =  (TextView) content.findViewById(R.id.idTextViewDialog);
    	myTitle.setText(text);*/
    	progressDialog.setContentView(content);
    	progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
		progressDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.MATCH_PARENT);
        return progressDialog;
    }

}
