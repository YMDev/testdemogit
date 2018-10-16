package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.A3techReviewMission;

public class A3techDialogChooseMissionCancelORReport {

    public static String SRC_FROM_DIALOGUE_DISPLAY_TECH = "SRC_FROM_DIALOGUE_DISPLAY_TECH";
    public static int REQUEST_DISPLAY_TECH_FROM_DIALOGUE = 3221;
	public static final int ACTION_CANCEL = 1;
	public static final int ACTION_REPORT = 2;
	public static Dialog createProfileDialog(final InteractionActivityInterface param) {
		final  Dialog progressDialog = new Dialog(param.getContext());
        progressDialog.show();
		LayoutInflater inflater =((Activity)param.getContext()).getLayoutInflater();
    	View content =inflater.inflate(R.layout.a3tech_choose_cancel_or_report_mission_dialogue, null);

    	RelativeLayout cancelContaner = content.findViewById(R.id.cancel_mission_container);
		RelativeLayout reportContaner = content.findViewById(R.id.container_report_mission);

		cancelContaner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(param != null){
					param.actionsubmitt(ACTION_CANCEL);
                    progressDialog.dismiss();
				}
			}
		});

		reportContaner.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(param != null){
					param.actionsubmitt(ACTION_REPORT);
                    progressDialog.dismiss();
				}
			}
		});
    	progressDialog.setContentView(content);
		progressDialog.setCancelable(true);
		Window window = progressDialog.getWindow();
		window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
		window.setBackgroundDrawableResource(android.R.color.transparent);
        return progressDialog;
    }

	public interface InteractionActivityInterface {
		void actionsubmitt(int typeAction);
		Context getContext();
	}
}
