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
import mobile.a3tech.com.a3tech.model.Avis;

public class A3techDialogAddReview {

    public static String SRC_FROM_DIALOGUE_DISPLAY_TECH = "SRC_FROM_DIALOGUE_DISPLAY_TECH";
    public static int REQUEST_DISPLAY_TECH_FROM_DIALOGUE = 3221;
	public static Dialog createProfileDialog(final AddReviewParam param, A3techReviewMission review) {
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
		if(review != null){
			mRatingBar.setRating(Float.valueOf(review.getRating()));
			mFeedback.setText(review.getCommentaire());
		}


		mSendFeedback.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				A3techReviewMission avis  = new A3techReviewMission();
				avis.setDateEdition(Calendar.getInstance().getTime());
				avis.setDateEvaluation(Calendar.getInstance().getTime());
				avis.setRating(mRatingBar.getRating());
				avis.setCommentaire(mFeedback.getText().toString());
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
		void actionsubmitt(A3techReviewMission review);
		Context getContext();
	}
}
