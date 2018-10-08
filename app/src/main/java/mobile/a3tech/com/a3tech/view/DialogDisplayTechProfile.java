package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.utils.SphericalUtil;

public class DialogDisplayTechProfile {
	
	public static Dialog createProfileDialog(final Context mContext, final User user) {
		Dialog progressDialog = new Dialog(mContext);
        progressDialog.show();
		LayoutInflater inflater =((Activity)mContext).getLayoutInflater();
    	View content =inflater.inflate(R.layout.a3tech_dialogue_profile_user, null);
    	 ImageView avatare =  (ImageView) content.findViewById(R.id.avatare_technicien);
		avatare.setImageDrawable(mContext.getDrawable(R.drawable.photo_login_1));
		TextView username =  content.findViewById(R.id.name_tech);
		username.setText(user.getNom()+" "+user.getPrenom().substring(0,1).toUpperCase()+".");
		TextView ratingValue =  content.findViewById(R.id.rating_nbr);
		ratingValue.setText(user.getRating());
		RatingBar ratingBar =  content.findViewById(R.id.rating_tech);
		ratingBar.setNumStars(5);
		ratingBar.setRating(Float.valueOf(user.getRating()));
		TextView tatingnbrVam =  content.findViewById(R.id.rating_nbr_text);
		TextView adresse =  content.findViewById(R.id.adresse_alpha);
		adresse.setText(user.getAdresse());
		TextView distance =  content.findViewById(R.id.distance);
		distance.setText("23 km de distance");
		TextView categoryTech =  content.findViewById(R.id.category_tech);
		categoryTech.setText("Technicien Category");
        Button displayProfile =  content.findViewById(R.id.display_profile);
        displayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(mContext, A3techViewEditProfilActivity.class);
                mainIntent.putExtra(A3techViewEditProfilActivity.ARG_USER_OBJECT,new Gson().toJson(user));
                ((Activity) mContext).startActivityForResult(mainIntent,A3techHomeActivity.REQUEST_START_DISPLAY_TECH_ACTIVITY );

            }
        });
    	progressDialog.setContentView(content);
		progressDialog.setCancelable(true);
		Window window = progressDialog.getWindow();
		window.setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);
        return progressDialog;
    }

}
