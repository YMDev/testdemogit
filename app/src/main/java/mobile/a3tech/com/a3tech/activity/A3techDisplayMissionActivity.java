package mobile.a3tech.com.a3tech.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.ImagesStuffs;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.A3techDialogAddReview;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techDisplayMissionActivity extends AppCompatActivity implements A3techDialogAddReview.AddReviewParam {


    public static final String TAG_MISSION_SELECTED_FROM_HOME_ACTIVITY = "TAG_SELECTED_MISION";
    private Mission selectedMission;
    private Avis missionReview;

    @BindView(R.id.toolbar_display_mission)
    android.support.v7.widget.Toolbar toolbarDisplayMission;
    @BindView(R.id.title_mission)
    TextView titreMission;
    @BindView(R.id.dis_statut_mission)
    TextView statutMission;
    @BindView(R.id.dis_description_mission)
    ExpandableTextView descriptionMission;
    @BindView(R.id.dis_cout_mission)
    TextView montantMission;
    @BindView(R.id.dis_temps_mission)
    TextView tempsMission;
    @BindView(R.id.dis_value_category_mission)
    TextView categoryMission;
    @BindView(R.id.dis_commentaire_mission)
    TextView commentaire;
    @BindView(R.id.avatare_technicien)
    ImageView avatare;

    @BindView(R.id.name_tech)
    TextView nameTech;
    @BindView(R.id.rating_nbr)
    TextView rating;
    @BindView(R.id.rating_tech)
    RatingBar ratingbar;
    @BindView(R.id.rating_nbr_text)
    TextView nbrReviews;
    @BindView(R.id.adresse_alpha)
    TextView adresse;
    @BindView(R.id.distance)
    TextView distanceEnKm;

    @BindView(R.id.dis_date_mission)
    TextView dateLieuIntervention;

    @BindView(R.id.dis_adresse_mission)
    TextView adresseIntervention;

    @BindView(R.id.rating_mission)
    RatingBar ratingMission;
    @BindView(R.id.container_action_add_rating_mission)
    RelativeLayout containerAddReview;

    @BindView(R.id.container_rating_mission)
    RelativeLayout containerDisplayReview;
    @BindView(R.id.edit_review_mission)
    ImageView btnEditReview;

    @BindView(R.id.action_demande_add_review_mission)
    TextView actionAddReview;

    @BindView(R.id.dis_action_mission)
    TextView actionMission;

    @BindView(R.id.container_actions_mission)
    RelativeLayout containerActions;

    @BindView(R.id.container_two_action_mission)
    RelativeLayout containerTwoActions;


    @BindView(R.id.container_actions_valider_mission)
    RelativeLayout containerActionsValider;

    @BindView(R.id.container_actions_anuller_mission)
    RelativeLayout containerActionAnnuler;

    @BindView(R.id.dis_action_annuler_mission)
    TextView actionAnnulerMission;

    @BindView(R.id.dis_action_valider_clo_mission)
    TextView actionValiderMission;

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

    private void initCompo() {
        if (selectedMission == null) {
            return;
        }
        titreMission.setText(selectedMission.getTitre());
        if (StringUtils.isBlank(selectedMission.getCatDescription())) {
            descriptionMission.setVisibility(View.GONE);
        } else {
            descriptionMission.setVisibility(View.VISIBLE);
        }
        descriptionMission.setText(selectedMission.getCatDescription());
        if (selectedMission.getCategoryMission() != null) {
            categoryMission.setText(selectedMission.getCategoryMission().getLibelle());
        }

        if (selectedMission.getTechnicien() != null) {
            nameTech.setText(selectedMission.getTechnicien().getNom());
            adresse.setText(selectedMission.getTechnicien().getAdresse());
            rating.setText(selectedMission.getTechnicien().getRating());
            ratingbar.setNumStars(5);
            ratingbar.setRating(Float.valueOf(selectedMission.getTechnicien().getRating()));
            nbrReviews.setText(selectedMission.getTechnicien().getNbr());
        }


        if (StringUtils.isNoneBlank(selectedMission.getDateIntervention())) {
            String dateAdresseIntervention = "";
            Date dateIntervention = DateStuffs.stringToDate(selectedMission.getDateIntervention(), DateStuffs.TIME_FORMAT);
            if (dateIntervention != null) {
                String dateInterventionAlphaSimple = DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, dateIntervention);
                String timeIntervention = DateStuffs.dateToString(DateStuffs.HOURS_MINUTES_FORMAT, dateIntervention);
                dateAdresseIntervention = " " + dateInterventionAlphaSimple + " " + getString(R.string.a_date_time) + " " + timeIntervention;
                dateLieuIntervention.setText(dateAdresseIntervention);
            }
        }


        if (StringUtils.isNoneBlank(selectedMission.getAdresse())) {
            adresseIntervention.setText(selectedMission.getAdresse());
        }

        if (selectedMission.getReviewMission() != null && StringUtils.isNoneBlank(selectedMission.getReviewMission().getNote())) {
            ratingMission.setRating(Float.valueOf(selectedMission.getReviewMission().getNote()));
            commentaire.setText(selectedMission.getReviewMission().getAvantage());
        } else {
            ratingMission.setVisibility(View.GONE);
            commentaire.setText("");

        }

        actionAddReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A3techDialogAddReview.createProfileDialog(A3techDisplayMissionActivity.this, null);
            }
        });

        btnEditReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (missionReview != null) {
                    A3techDialogAddReview.createProfileDialog(A3techDisplayMissionActivity.this, missionReview);
                }
            }
        });
        avatare.setImageBitmap(ImagesStuffs.getProfileDefaultPicture(A3techDisplayMissionActivity.this, selectedMission.getTechnicien().getNom()));
        actionRefreshStatutMission();
        actionMissionSetup();

    }


    private void actionMissionSetup() {
        if(StringUtils.isBlank(selectedMission.getStatut()) || selectedMission.getStatut().equals(Mission.STATUT_CREEE) || selectedMission.getStatut().equals(Mission.STATUT_VALIDEE)){
            containerTwoActions.setVisibility(View.VISIBLE);
            containerActions.setVisibility(View.GONE);
            actionAnnulerMission.setText(getString(R.string.annuler_mission));
            actionValiderMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }else {
            containerTwoActions.setVisibility(View.GONE);
            containerActions.setVisibility(View.VISIBLE);
        }
        containerActionsValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                if (selectedMission.getStatut().equals(Mission.STATUT_CREEE)) {
                    selectedMission.setStatut(Mission.STATUT_VALIDEE);
                } else if (selectedMission.getStatut().equals(Mission.STATUT_VALIDEE)) {
                    if(selectedMission.getReviewMission() == null || StringUtils.isBlank(selectedMission.getReviewMission().getNote())){
                        //pas de review pour cette mission, demander d'ajouter un review
                        SimpleDialog.build()
                                .title(R.string.please_add_review)
                                .msg(R.string.please_add_review_content)
                                .show(A3techDisplayMissionActivity.this);
                        return;
                    }
                    selectedMission.setStatut(Mission.STATUT_CLOTUREE);
                    btnEditReview.setVisibility(View.GONE);
                }
                actionRefreshStatutMission();
            }
        });

        containerActionAnnuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                selectedMission.setStatut(Mission.STATUT_ANNULEE);
                btnEditReview.setVisibility(View.GONE);
                slidefromRightToLeft(containerActionAnnuler);
                slidefromLeftToRight(containerActionsValider);
                actionRefreshStatutMission();
            }
        });


        containerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                if (selectedMission.getStatut().equals(Mission.STATUT_CREEE)) {
                    selectedMission.setStatut(Mission.STATUT_VALIDEE);
                } else if (selectedMission.getStatut().equals(Mission.STATUT_VALIDEE)) {
                    if(selectedMission.getReviewMission() == null || StringUtils.isBlank(selectedMission.getReviewMission().getNote())){
                        //pas de review pour cette mission, demander d'ajouter un review
                        SimpleDialog.build()
                                .title(R.string.please_add_review)
                                .msg(R.string.please_add_review_content)
                                .show(A3techDisplayMissionActivity.this);
                        return;
                    }
                    selectedMission.setStatut(Mission.STATUT_CLOTUREE);
                    btnEditReview.setVisibility(View.GONE);
                }
                actionRefreshStatutMission();
            }
        });

    }
    public void slidefromRightToLeft(View view) {

        view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_to_left));
        view.setVisibility(View.GONE);
    }
    public void slidefromLeftToRight(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.slide_to_right));
        view.setVisibility(View.GONE);
    }
    private void doZoomEffect(View view)
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.zoom_scall);
        a.reset();
        view.clearAnimation();
        view.startAnimation(a);
    }


    private void actionRefreshStatutMission() {
        if (selectedMission == null) return;

        if (StringUtils.isBlank(selectedMission.getStatut())) {
            selectedMission.setStatut(Mission.STATUT_CREEE);
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        if (selectedMission.getStatut().equals(Mission.STATUT_CLOTUREE) || selectedMission.getStatut().equals(Mission.STATUT_ANNULEE)) {
            containerActions.setVisibility(View.GONE);
        } else {
            containerActions.setVisibility(View.VISIBLE);
        }

        if (selectedMission.getStatut().equals(Mission.STATUT_CREEE)) {
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        if (selectedMission.getStatut().equals(Mission.STATUT_VALIDEE)) {
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        if (selectedMission.getStatut().equals(Mission.STATUT_ANNULEE)) {
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        statutMission.setText(selectedMission.getStatut());
        doZoomEffect(statutMission);
    }


    private String getActionLabelFromStatut(String statut) {
        if (statut.equals(Mission.STATUT_CREEE)) {
            return getString(R.string.valider_mission);
        }
        if (statut.equals(Mission.STATUT_VALIDEE)) {
            return getString(R.string.cloturer_mission);
        }

        return Mission.STATUT_CREEE;
    }

    @Override
    public void actionsubmitt(Avis review) {
        missionReview = review;
        selectedMission.setReviewMission(review);
        containerAddReview.setVisibility(View.GONE);
        containerDisplayReview.setVisibility(View.VISIBLE);
        ratingMission.setVisibility(View.VISIBLE);
        ratingMission.setRating(Float.valueOf(review.getNote()));
        commentaire.setText(review.getAvantage());
        btnEditReview.setVisibility(View.VISIBLE);
        A3techCustomToastDialog.createToastDialog(getContext(), "Merci", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
    }

    @Override
    public Context getContext() {
        return A3techDisplayMissionActivity.this;
    }
}
