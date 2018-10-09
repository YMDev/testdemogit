package mobile.a3tech.com.a3tech.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techDisplayMissionActivity extends AppCompatActivity {


    public static final String  TAG_MISSION_SELECTED_FROM_HOME_ACTIVITY = "TAG_SELECTED_MISION";
    private Mission selectedMission;
    @BindView(R.id.toolbar_display_mission)
    android.support.v7.widget.Toolbar toolbarDisplayMission;
    @BindView(R.id.title_mission) TextView titreMission;
    @BindView(R.id.dis_statut_mission) TextView statutMission;
    @BindView(R.id.dis_description_mission) ExpandableTextView descriptionMission;
    @BindView(R.id.dis_cout_mission) TextView montantMission;
    @BindView(R.id.dis_temps_mission) TextView tempsMission;
    @BindView(R.id.dis_value_category_mission) TextView categoryMission;
    @BindView(R.id.dis_commentaire_mission) TextView commentaire;
    @BindView(R.id.avatare_technicien)
    ImageView avatare;

    @BindView(R.id.name_tech) TextView nameTech;
    @BindView(R.id.rating_nbr) TextView rating;
    @BindView(R.id.rating_tech)
    RatingBar ratingbar;
    @BindView(R.id.rating_nbr_text) TextView nbrReviews;
    @BindView(R.id.adresse_alpha) TextView adresse;
    @BindView(R.id.distance) TextView distanceEnKm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_display_mission_activity);
        ButterKnife.bind(this);
        getSelectedMission();
        initCompo();
    }

    private void getSelectedMission() {
        String jsonMyObject = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            jsonMyObject = b.getString(A3techDisplayMissionActivity.TAG_MISSION_SELECTED_FROM_HOME_ACTIVITY);
        }
        if (jsonMyObject != null) {
            selectedMission = new Gson().fromJson(jsonMyObject, Mission.class);
        }
    }

    private void initCompo(){
        if(selectedMission == null){
            return;
        }
        titreMission.setText(selectedMission.getTitre());
        descriptionMission.setText(selectedMission.getCatDescription());
        categoryMission.setText(selectedMission.getCategoryMission().getLibelle());
        nameTech.setText(selectedMission.getTechnicien().getNom());
        adresse.setText(selectedMission.getTechnicien().getAdresse());
        rating.setText(selectedMission.getTechnicien().getRating());
        ratingbar.setNumStars(5);
        ratingbar.setRating(Float.valueOf(selectedMission.getTechnicien().getRating()));
        nbrReviews.setText(selectedMission.getTechnicien().getNbr());
    }
}
