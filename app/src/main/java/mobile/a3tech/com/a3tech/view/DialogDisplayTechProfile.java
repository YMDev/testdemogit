package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureUtils;
import android.os.Bundle;
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
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.SphericalUtil;

public class DialogDisplayTechProfile {

    public static String SRC_FROM_DIALOGUE_DISPLAY_TECH = "SRC_FROM_DIALOGUE_DISPLAY_TECH";
    public static int REQUEST_DISPLAY_TECH_FROM_DIALOGUE = 3221;
	
	public static Dialog createProfileDialog(final Context mContext, final User user, final Mission mission, final boolean isFromAddmission) {
		final  Dialog progressDialog = new Dialog(mContext);
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
        Button hireTech = content.findViewById(R.id.select_tech);
        displayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent mainIntent = new Intent(mContext, A3techViewEditProfilActivity.class);
                String src_action ="";
                int req = 0;
                if(isFromAddmission){
                    src_action = SimpleAdapterTechnicien.SRC_FROM_HOME_ADD_MISSION;
                    req = SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_MISSION;
                }else{
                    src_action = SimpleAdapterTechnicien.SRC_FROM_HOME_BROWSE_TECH;
                    req = SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_HOME;
                }
                bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_DIALOGUE_DISPLAY_TECH);
                bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(user));
                bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                mainIntent.putExtras(bundle);
                ((Activity)mContext).startActivityForResult(mainIntent, req);

                progressDialog.dismiss();
            }
        });
        hireTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mission.setTechnicien(user);
                int req = 0;
                if(isFromAddmission){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH,new Gson().toJson(mission));
                    ((Activity)mContext).setResult(Activity.RESULT_OK, resultIntent);
                    ((Activity)mContext).finish();
                }else{
                    ((A3techDisplayTechniciensListeActivity)mContext).nextStepAfterSelectTechnicien(mission);
                }

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

}
