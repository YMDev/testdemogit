package mobile.a3tech.com.a3tech.view;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techMissionListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.activity.A3techWelcomPageActivity;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techReviewMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.A3techUserService;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.utils.SphericalUtil;

public class DialogDisplayTechProfile {

    public static String SRC_FROM_DIALOGUE_DISPLAY_TECH = "SRC_FROM_DIALOGUE_DISPLAY_TECH";
    public static int REQUEST_DISPLAY_TECH_FROM_DIALOGUE = 3221;

    public static Dialog createProfileDialog(final Context mContext, final A3techUser user, final A3techMission mission, final boolean isFromAddmission) {
        final Dialog progressDialog = new Dialog(mContext);
        progressDialog.show();
        LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
        View content = inflater.inflate(R.layout.a3tech_dialogue_profile_user, null);
        ImageView avatare = (ImageView) content.findViewById(R.id.avatare_technicien);

        if(user.getId_photo_profil() != null){
            Picasso.get().load("").centerCrop().into(avatare);
        }else{
            final LetterTileProvider tileProvider = new LetterTileProvider(mContext);
            final Bitmap letterTile = tileProvider.getLetterTile(user.getNom(), user.getNom(), 129, 129);
            avatare.setImageBitmap(letterTile);
        }
        StringBuilder nomSb = new StringBuilder();
        if(user.getNom() != null){
            nomSb.append(user.getNom().toUpperCase());
            nomSb.append(" ");
        }
        if(user.getPrenom() != null){
            nomSb.append(user.getPrenom().toUpperCase().substring(0, 1) + ".");
        }
        TextView username = content.findViewById(R.id.name_tech);
        username.setText(nomSb);
        TextView ratingValue = content.findViewById(R.id.rating_nbr);
       // ratingValue.setText(user.getRating() + "");
        RatingBar ratingBar = content.findViewById(R.id.rating_tech);
        ratingBar.setNumStars(5);
        //ratingBar.setRating(Float.valueOf(user.getRating()));
        TextView tatingnbrVam = content.findViewById(R.id.rating_nbr_text);
       // tatingnbrVam.setText(user.getNbrReview()+" "+mContext.getResources().getString(R.string.avis));

        getRationUser(user,ratingValue,ratingBar, tatingnbrVam,mContext);
        TextView adresse = content.findViewById(R.id.adresse_alpha);
        adresse.setText(user.getAdresse());
        TextView distance = content.findViewById(R.id.distance);
        distance.setText(getDistance(user, mContext));
        Button displayProfile = content.findViewById(R.id.display_profile);
        Button hireTech = content.findViewById(R.id.select_tech);
        displayProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent mainIntent = new Intent(mContext, A3techViewEditProfilActivity.class);
                String src_action = "";
                int req = 0;
                if (isFromAddmission) {
                    src_action = SimpleAdapterTechnicien.SRC_FROM_HOME_ADD_MISSION;
                    req = SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_MISSION;
                } else {
                    src_action = SimpleAdapterTechnicien.SRC_FROM_HOME_BROWSE_TECH;
                    req = SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_HOME;
                }
                bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_DIALOGUE_DISPLAY_TECH);
                bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(user));
                bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(mission));
                mainIntent.putExtras(bundle);
                ((Activity) mContext).startActivityForResult(mainIntent, req);
                progressDialog.dismiss();
            }
        });
        hireTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mission.setTechnicien(user);
                if (isFromAddmission) {
                    addMission(mission, mContext);
                } else {
                    ((A3techDisplayTechniciensListeActivity) mContext).nextStepAfterSelectTechnicien(mission);
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


    private static void getRationUser(A3techUser user, final TextView ratingView, final RatingBar ratingbar,final TextView ratingViewNbr,  final Context context){
        UserManager.getInstance().getUserReviews(String.valueOf(user.getId()), "", new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                List<A3techReviewMission> reviews = (List) data;
                if(reviews != null && reviews.size() != 0){
                    Double ratingCalcule = 0d;
                    for (A3techReviewMission rev: reviews
                         ) {
                        if (rev != null) {
                            ratingCalcule = ratingCalcule + rev.getRating();
                        }
                    }
                    ratingbar.setRating(Double.valueOf(ratingCalcule / reviews.size()).floatValue());
                    ratingView.setText(String.valueOf(String.format("%.02f", ratingCalcule / reviews.size())));
                    ratingViewNbr.setText(reviews.size()+" "+context.getResources().getString(R.string.avis));
                }else {
                    if(ratingbar != null) ratingbar.setRating(0f);
                    if(ratingView != null) ratingView.setText(0+"");
                    if(ratingViewNbr != null)  ratingViewNbr.setText(0+" "+context.getResources().getString(R.string.avis));

                }
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if(ratingbar != null) ratingbar.setRating(0f);
                if(ratingView != null) ratingView.setText(0+"");
                if(ratingViewNbr != null)  ratingViewNbr.setText(0+" "+context.getResources().getString(R.string.avis));
            }
        });
    }
    private static void addMission(final A3techMission missionn ,final Context msContext) {
       final ProgressDialog dialogwaiting  = CustomProgressDialog.createProgressDialog(msContext,"");
        MissionManager.getInstance().createMission(missionn, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
//mission created
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                A3techMission missionSaved = (A3techMission) data;
                Intent resultIntent = new Intent();
                resultIntent.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH, gson.toJson(missionn));
                 if(dialogwaiting != null) dialogwaiting.dismiss();
                ((A3techAddMissionActivity) msContext).setResult(Activity.RESULT_OK, resultIntent);
                ((A3techAddMissionActivity) msContext).finish();
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if(dialogwaiting != null) dialogwaiting.dismiss();
                A3techCustomToastDialog.createSnackBar(msContext, msContext.getResources().getString(R.string.mission_error_creation), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }
    private static  String getDistance(A3techUser user ,Context context){
        if (user.getLatitude() == 0d && user.getLongetude() == 0d) {
            return "N/A" + " KM";
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String latidude = prefs.getString(A3techHomeActivity.TAG_CONNECTED_USER_LATITUDE, "0");
        String longetide = prefs.getString(A3techHomeActivity.TAG_CONNECTED_USER_LONGETUDE, "0");
        Double distance = SphericalUtil.computeDistanceBetween(new LatLng(Double.valueOf(latidude), Double.valueOf(longetide)), new LatLng(user.getLatitude(), user.getLongetude()));
        final Double distanceUser = Math.round((distance / 1000) * 100.0) / 100.0;
         return distanceUser + " KM";
    }
}
