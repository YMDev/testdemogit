package mobile.a3tech.com.a3tech.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techMissionStatut;
import mobile.a3tech.com.a3tech.model.A3techReviewMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.ImagesStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.A3techDialogAddReview;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techDisplayMissionActivity extends AppCompatActivity implements A3techDialogAddReview.AddReviewParam {


    public static final String TAG_MISSION_SELECTED_FROM_HOME_ACTIVITY = "TAG_SELECTED_MISION";
    private A3techMission selectedMission;
    private A3techReviewMission missionReview;

    @BindView(R.id.toolbar_display_mission)
    android.support.v7.widget.Toolbar toolbarDisplayMission;
    @BindView(R.id.back_action)
    ImageView backAction;
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
    @BindView(R.id.cancel_mission_layout)
    LinearLayout cancelMissionLayout;

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
            selectedMission = new Gson().fromJson(jsonMyObject, A3techMission.class);
        }
    }

    private void initCompo() {
        if (selectedMission == null) {
            return;
        }

        setSupportActionBar(toolbarDisplayMission);

        backAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        titreMission.setText(selectedMission.getTitre());
        if (StringUtils.isBlank(selectedMission.getDescriptionMission())) {
            descriptionMission.setVisibility(View.GONE);
        } else {
            descriptionMission.setVisibility(View.VISIBLE);
        }
        descriptionMission.setText(selectedMission.getDescriptionMission());
        if (selectedMission.getCategoryMission() != null) {
            categoryMission.setText(selectedMission.getCategoryMission().getLibelle());
        }

        if (selectedMission.getTechnicien() != null) {
            nameTech.setText(selectedMission.getTechnicien().getNom());
            adresse.setText(selectedMission.getTechnicien().getAdresse());
            rating.setText(selectedMission.getTechnicien().getRating()+"");
            ratingbar.setNumStars(5);
            ratingbar.setRating(Float.valueOf(selectedMission.getTechnicien().getRating()));
            nbrReviews.setText(selectedMission.getTechnicien().getNbrReview()+"");
        }


        if (selectedMission.getDateIntervention() != null) {
            Date dateIntervention = new Date(selectedMission.getDateIntervention());
            String dateAdresseIntervention = "";
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

        if (selectedMission.getReviewMission() != null) {
            ratingMission.setRating(Float.valueOf(selectedMission.getReviewMission().getRating()));
            commentaire.setText(selectedMission.getReviewMission().getCommentaire());
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                doZoomEffect(statutMission);
                doZoomEffect(tempsMission);
                doZoomEffect(montantMission);
            }
        });
    }


    private void actionMissionSetup() {

        containerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                if(selectedMission.getStatut() == null){
                    selectedMission.setStatut(A3techMissionStatut.CREE);
                }
                if (selectedMission.getStatut().getId() == A3techMissionStatut.CREE.getId()) {
                    selectedMission.setStatut(A3techMissionStatut.VALIDEE);
                } else if (selectedMission.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()) {
                    if (selectedMission.getReviewMission() == null) {
                        //pas de review pour cette mission, demander d'ajouter un review
                        SimpleDialog.build()
                                .title(R.string.please_add_review)
                                .msg(R.string.please_add_review_content)
                                .icon(R.drawable.a3tech_review_technicien)
                                .show(A3techDisplayMissionActivity.this);
                        return;
                    }
                    selectedMission.setStatut(A3techMissionStatut.CLOTUREE);
                    btnEditReview.setVisibility(View.GONE);
                }
                actionRefreshStatutMission();
            }
        });

        cancelMissionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                SimpleDialog.build().title(R.string.please_add_review).cancelable(true).pos("ok").neg("no").msg("confirm cancel mission").icon(R.drawable.a3tech_cancel_mission).show(A3techDisplayMissionActivity.this);
                selectedMission.setStatut(A3techMissionStatut.ANNULEE);
                btnEditReview.setVisibility(View.GONE);
                actionRefreshStatutMission();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doZoomEffect(statutMission);
                    }
                });
            }
        });


        containerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                if (selectedMission.getStatut().getId() == A3techMissionStatut.CREE.getId()) {
                    selectedMission.setStatut(A3techMissionStatut.VALIDEE);
                } else  if (selectedMission.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()) {
                    if (selectedMission.getReviewMission() == null) {
                        //pas de review pour cette mission, demander d'ajouter un review
                        SimpleDialog.build()
                                .title(R.string.please_add_review)
                                .msg(R.string.please_add_review_content)
                                .show(A3techDisplayMissionActivity.this);
                        return;
                    }
                    selectedMission.setStatut(A3techMissionStatut.CLOTUREE);
                    btnEditReview.setVisibility(View.GONE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            doZoomEffect(statutMission);
                        }
                    });
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

    private void doZoomEffect(View view) {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.zoom_scall);
        a.reset();
        view.clearAnimation();
        view.startAnimation(a);
    }


    private void actionRefreshStatutMission() {
        if (selectedMission == null) return;

        if (selectedMission.getStatut() == null) {
            selectedMission.setStatut(A3techMissionStatut.CREE);
            actionMission.setText(selectedMission.getStatut().getDiscreptionEnum());
        }

        if (selectedMission.getStatut().getId() == A3techMissionStatut.CREE.getId()) {
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        if (selectedMission.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()) {
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        if (selectedMission.getStatut().getId() == A3techMissionStatut.ANNULEE.getId()) {
            actionMission.setText(getActionLabelFromStatut(selectedMission.getStatut()));
        }

        statutMission.setText(selectedMission.getStatut().getDiscreptionEnum());


    }


    private String getActionLabelFromStatut(A3techMissionStatut statut) {
        if (statut.getId() == A3techMissionStatut.CREE.getId()) {
            return getString(R.string.valider_mission);
        }
        if (statut.getId() == A3techMissionStatut.VALIDEE.getId()) {
            return getString(R.string.cloturer_mission);
        }

        return "";
    }

    @Override
    public void actionsubmitt(A3techReviewMission review) {
        missionReview = review;
        missionReview.setMission(selectedMission);
        missionReview.setUserTechnicien(selectedMission.getTechnicien());
        missionReview.setUserClient(PreferencesValuesUtils.getConnectedUser(A3techDisplayMissionActivity.this));
        selectedMission.setReviewMission(review);
        containerAddReview.setVisibility(View.GONE);
        containerDisplayReview.setVisibility(View.VISIBLE);
        ratingMission.setVisibility(View.VISIBLE);
        ratingMission.setRating(Float.valueOf(review.getRating()));
        commentaire.setText(review.getCommentaire());
        btnEditReview.setVisibility(View.VISIBLE);
        A3techCustomToastDialog.createToastDialog(getContext(), "Merci", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
    }

    @Override
    public Context getContext() {
        return A3techDisplayMissionActivity.this;
    }


}
