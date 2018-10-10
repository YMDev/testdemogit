package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.lang3.ClassUtils;

import java.util.Date;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.DateStuffs;

public class A3techDialogAddReview {

    public static String SRC_FROM_DIALOGUE_DISPLAY_TECH = "SRC_FROM_DIALOGUE_DISPLAY_TECH";
    public static int REQUEST_DISPLAY_TECH_FROM_DIALOGUE = 3221;
	public static Dialog createProfileDialog(final AddReviewParam param) {
		final  Dialog progressDialog = new Dialog(param.getContext());
        progressDialog.show();
		LayoutInflater inflater =((Activity)param.getContext()).getLayoutInflater();
    	View content =inflater.inflate(R.layout.a3tech_add_rating_dialogue, null);
		final RatingBar mRatingBar = (RatingBar) content.findViewById(R.id.ratingBar);
		final TextView mRatingScale = (TextView) content.findViewById(R.id.rating_comment);
		final  EditText mFeedback = (EditText) content.findViewById(R.id.comment_to_insert);
		Button mSendFeedback = (Button) content.findViewById(R.id.btnSubmit);


		mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
			@Override
			public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
				mRatingScale.setText(String.valueOf(v));
				switch ((int) ratingBar.getRating()) {
					case 1:
						mRatingScale.setText("Non satisfait");
						break;
					case 2:
						mRatingScale.setText("besoin d'amelioration");
						break;
					case 3:
						mRatingScale.setText("Bien");
						break;
					case 4:
						mRatingScale.setText("Trés bien");
						break;
					case 5:
						mRatingScale.setText("Super, travail parfait");
						break;
					default:
						mRatingScale.setText("");
				}
			}
		});

		mSendFeedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Avis avis  = new Avis();
				avis.setDate_creation(DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT, new Date()));
				avis.setNote(String.valueOf(mRatingBar.getRating()));
				avis.setAvantage(mFeedback.getText().toString());
				param.actionsubmitt(avis);
				progressDialog.dismiss();
			}
		});
    	progressDialog.setContentView(content);
		progressDialog.setCancelable(true);
		Window window = progressDialog.getWindow();
		window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
        return progressDialog;
    }

	public interface AddReviewParam {
		void actionsubmitt(Avis review);
		Context getContext();
	}
}
