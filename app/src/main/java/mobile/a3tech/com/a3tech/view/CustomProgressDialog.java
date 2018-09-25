package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;

public class CustomProgressDialog {
	
	public static ProgressDialog createProgressDialog(Context mContext,String text) {
        ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
		LayoutInflater inflater =((Activity)mContext).getLayoutInflater();
    	View content =inflater.inflate(R.layout.custom_progressdialog, null);
    	/*TextView  myTitle =  (TextView) content.findViewById(R.id.idTextViewDialog);
    	myTitle.setText(text);*/
    	progressDialog.setContentView(content);
    	progressDialog.setIndeterminate(true);
		progressDialog.setCancelable(false);
        return progressDialog;
    }

}
