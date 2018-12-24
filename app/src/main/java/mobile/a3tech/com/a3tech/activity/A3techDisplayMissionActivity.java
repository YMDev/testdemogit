package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABShape;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.form.Input;
import eltos.simpledialogfragment.form.SimpleFormDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.NotificationsManager;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techMissionStatut;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.model.A3techNotificationType;
import mobile.a3tech.com.a3tech.model.A3techReviewMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.ImagesStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.A3techDialogAddReview;
import mobile.a3tech.com.a3tech.view.A3techDialogChooseMissionCancelORReport;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

public class A3techDisplayMissionActivity extends BaseActivity implements A3techDialogAddReview.AddReviewParam, SimpleDialog.OnDialogResultListener, RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {


    public static final String TAG_MISSION_SELECTED_FROM_HOME_ACTIVITY = "TAG_SELECTED_MISION";
    public static final String CONFIRMATION_VALIDATION_DIALOG = "CONFIRMATION_VALIDATION_DIALOG";
    public static final String CONFIRMATION_VALIDATION_CANCEL_DIALOG = "CONFIRMATION_VALIDATION_CANCEL_DIALOG";
    public static final String CONFIRMATION_VALIDATION_REPORT_DIALOG = "CONFIRMATION_VALIDATION_REPORT_DIALOG";
    public static final String CONFIRMATION_VALIDATION_CLOTURE_DIALOG = "CONFIRMATION_VALIDATION_CLOTURE_DIALOG";

    public static final String SRC_FROM_DISPLAY_MISSION = "DISPLAY_MISSION";
    public static final int REQUEST_DISPLAY_TECH_FROM_DISPLAY_MISSION = 532;
    private static final String TAG_START_MISSION =  "TAG_START_MISSION";
    private static final String TAG_PAUSE_MISSION =  "TAG_PAUSE_MISSION";


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

    @BindView(R.id.container_item_tech)
    RelativeLayout layoutTechnicien;

    @BindView(R.id.progress_montant_mission)
    ProgressBar progressMontantMission;

    @BindView(R.id.progress_time_mission)
    ProgressBar progresstimeMission;


    @BindView(R.id.activity_main_rfal)
    RapidFloatingActionLayout rfaLayout;
    @BindView(R.id.activity_main_rfab)
    RapidFloatingActionButton rfaBtn;
    RapidFloatingActionHelper rfabHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_display_mission_activity);
        ButterKnife.bind(this);
        getSelectedMission();
        initCompo();
        initMontantDureeMissionCloturee();
        initFabEventsAction();
        initReviewMission();
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

    private void initReviewMission(){
        if(selectedMission != null){
            MissionManager.getInstance().getMissionReview(selectedMission, new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    missionReview = (A3techReviewMission) data;
                    containerAddReview.setVisibility(View.GONE);
                    containerDisplayReview.setVisibility(View.VISIBLE);
                    ratingMission.setVisibility(View.VISIBLE);
                    ratingMission.setRating(Float.valueOf(missionReview.getRating()));
                    commentaire.setText(missionReview.getCommentaire());
                    btnEditReview.setVisibility(View.VISIBLE);
                }

                @Override
                public void dataLoadingError(int errorCode) {

                }
            });
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
        if (selectedMission.getCategorie() != null) {
            categoryMission.setText(selectedMission.getCategorie().getLibelle());
        }

        if (selectedMission.getTechnicien() != null) {
            StringBuilder nomSb = new StringBuilder();
            if(selectedMission.getTechnicien().getNom() != null){
                nomSb.append(selectedMission.getTechnicien().getNom().toUpperCase());
                nomSb.append(" ");
            }
            if(selectedMission.getTechnicien().getPrenom() != null){
                nomSb.append(selectedMission.getTechnicien().getPrenom().toUpperCase().substring(0, 1) + ".");
            }

            nameTech.setText(nomSb);
            adresse.setText(selectedMission.getTechnicien().getAdresse());
           //rating.setText(selectedMission.getTechnicien().getRating() + "");
            ratingbar.setNumStars(5);
            //ratingbar.setRating(Float.valueOf(selectedMission.getTechnicien().getRating()));
            nbrReviews.setText(selectedMission.getTechnicien().getNbrReview() + " "+getResources().getString(R.string.avis));
            getRationUser(selectedMission.getTechnicien(),rating,ratingbar, getActivity());
        }


        if (selectedMission.getDateIntervention() != null) {
            Date dateIntervention = selectedMission.getDateIntervention();
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


        layoutTechnicien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainIntent = new Intent(A3techDisplayMissionActivity.this, A3techViewEditProfilActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, SRC_FROM_DISPLAY_MISSION);
                bundle.putString(A3techViewEditProfilActivity.ARG_MISSION_OBJECT, new Gson().toJson(selectedMission));
                bundle.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(selectedMission.getTechnicien()));
                mainIntent.putExtras(bundle);
                startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_DISPLAY_MISSION);
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
    private  void getRationUser(A3techUser user, final TextView ratingView, final RatingBar ratingbar, final Context context){
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
                    if(ratingbar != null) ratingbar.setRating(Double.valueOf(ratingCalcule / reviews.size()).floatValue());
                    if(ratingView != null) ratingView.setText(String.valueOf(ratingCalcule / reviews.size()));
                }else {
                    if(ratingbar != null) ratingbar.setRating(0f);
                    if(ratingView != null) ratingView.setText(0);

                }
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if(ratingView != null) ratingView.setText("0");
                if(ratingbar != null)  ratingbar.setRating(0);
            }
        });
    }
    private void initMontantDureeMissionCloturee() {
        if (selectedMission != null && selectedMission.getStatut() != null && selectedMission.getStatut().equals(A3techMissionStatut.CLOTUREE)) {
            doCalculeDureeMission();
            doCalculeMontantMission();
        }
    }

    private void actionMissionSetup() {

        containerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                if (selectedMission.getStatut() == null) {
                    selectedMission.setStatut(A3techMissionStatut.CREE);
                }
                if (selectedMission.getStatut().getId() == A3techMissionStatut.CREE.getId()) {
                    selectedMission.setStatut(A3techMissionStatut.VALIDEE);
                } else {  //if (selectedMission.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()) {
                    if (missionReview == null) {
                        //pas de review pour cette mission, demander d'ajouter un review
                        SimpleDialog.build()
                                .title(R.string.please_add_review)
                                .msg(R.string.please_add_review_content)
                                .icon(R.drawable.a3tech_review_technicien)
                                .show(A3techDisplayMissionActivity.this);
                        return;
                    }
                    clotureMission();
                  /*  selectedMission.setStatut(A3techMissionStatut.CLOTUREE);
                    btnEditReview.setVisibility(View.GONE);*/
                }

                MissionManager.getInstance().updateMission(selectedMission, new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        actionRefreshStatutMission();
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {

                    }
                });

            }
        });

        cancelMissionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
               /* SimpleDialog.build().title(R.string.please_add_review).cancelable(true).pos("ok").neg("no").msg("confirm cancel mission").icon(R.drawable.a3tech_cancel_mission).show(A3techDisplayMissionActivity.this);
                selectedMission.setStatut(A3techMissionStatut.ANNULEE);
                btnEditReview.setVisibility(View.GONE);
                actionRefreshStatutMission();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        doZoomEffect(statutMission);
                    }
                });*/
                //TODO verify if mission not expired
                final ProgressDialog dialogWaitingCancel = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
                MissionManager.getInstance().missionCanBeReportedOrCanceled(selectedMission, new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {

                        Boolean canBe = (Boolean) data;
                        if (canBe != null && canBe) {
                            cancelOrReportMissionDialogue();
                        } else {
                            A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this, "Cannot be canceled or reported", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                        }

                        dialogWaitingCancel.dismiss();
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {

                    }
                });


            }
        });


        containerActions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedMission == null) return;
                if (selectedMission.getStatut().getId() == A3techMissionStatut.CREE.getId()) {
                    validateMission();
                } else {
                    //if (selectedMission.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()) {
                    if (missionReview == null) {
                        //pas de review pour cette mission, demander d'ajouter un review
                        SimpleDialog.build()
                                .title(R.string.please_add_review)
                                .msg(R.string.please_add_review_content)
                                .show(A3techDisplayMissionActivity.this);
                        return;
                    }
                    clotureMission();
                }
                /*actionRefreshStatutMission();*/
            }
        });

    }


    private void validateMission() {
        if (selectedMission != null) {
            SimpleDialog.build().title(getString(R.string.valider_mission_title_dialogue)).msg(R.string.valider_mission_msg_dialog).neg(R.string.cancel).pos(R.string.oui_valider).show(A3techDisplayMissionActivity.this, CONFIRMATION_VALIDATION_DIALOG);
        }
    }


    private void cancelMission() {
        if (selectedMission != null) {
            SimpleDialog.build().title(getString(R.string.valider__cancel_mission_title_dialogue)).msg(R.string.valider_cancel_mission_msg_dialog).neg(R.string.cancel).pos(R.string.oui_valider).show(A3techDisplayMissionActivity.this, CONFIRMATION_VALIDATION_CANCEL_DIALOG);
        }
    }

    private void reporterMission() {
        if (selectedMission != null) {
            SimpleDialog.build().title(getString(R.string.valider_report_mission_title_dialogue)).msg(R.string.valider_reporter_mission_msg_dialog).neg(R.string.cancel).pos(R.string.oui_valider).show(A3techDisplayMissionActivity.this, CONFIRMATION_VALIDATION_REPORT_DIALOG);
        }
    }


    private void clotureMission() {
        if (selectedMission != null) {
            SimpleDialog.build().title(getString(R.string.valider_cloture_mission_title_dialogue)).msg(R.string.valider_cloturer_mission_msg_dialog).neg(R.string.cancel).pos(R.string.oui_valider).show(A3techDisplayMissionActivity.this, CONFIRMATION_VALIDATION_CLOTURE_DIALOG);
        }
    }


    private void cancelOrReportMissionDialogue() {

        A3techDialogChooseMissionCancelORReport.createProfileDialog(new A3techDialogChooseMissionCancelORReport.InteractionActivityInterface() {

            @Override
            public void actionsubmitt(int typeAction) {
                switch (typeAction) {
                    case A3techDialogChooseMissionCancelORReport.ACTION_CANCEL:
                        //cancel mission
                        cancelMission();
                        break;
                    case A3techDialogChooseMissionCancelORReport.ACTION_REPORT:
                        //report
                        reporterMission();
                        break;
                }
            }

            @Override
            public Context getContext() {
                return A3techDisplayMissionActivity.this;
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

        MissionManager.getInstance().addReview(missionReview, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                missionReview = (A3techReviewMission) data;
                containerAddReview.setVisibility(View.GONE);
                containerDisplayReview.setVisibility(View.VISIBLE);
                ratingMission.setVisibility(View.VISIBLE);
                ratingMission.setRating(Float.valueOf(missionReview.getRating()));
                commentaire.setText(missionReview.getCommentaire());
                btnEditReview.setVisibility(View.VISIBLE);
                A3techCustomToastDialog.createSnackBar(getContext(), getResources().getString(R.string.thank_you), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                A3techCustomToastDialog.createSnackBar(getContext(), getResources().getString(R.string.error_review_not_created), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
            }
        });


    }

    @Override
    public Context getContext() {
        return A3techDisplayMissionActivity.this;
    }


    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {

        if (CONFIRMATION_VALIDATION_DIALOG.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation
                    validationDemande();
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }
            return true;
        }


        if (CONFIRMATION_VALIDATION_CANCEL_DIALOG.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation
                    validationCancelDemande();
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }
            return true;
        }

        if (CONFIRMATION_VALIDATION_REPORT_DIALOG.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation
                    validationReportDemande();
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }
            return true;
        }

        if (CONFIRMATION_VALIDATION_CLOTURE_DIALOG.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation
                    validationCloturetDemande();
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }
            return true;
        }

        if (TAG_ADD_DESC_DIALOG.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation
                    validationAjoutDescriptionDemande(extras.getString(TAG_ADD_DESC_INPUT));
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }

            return true;
        }
        if (TAG_START_MISSION.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation

                    validationStartMission();
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }
            return true;
        }

        if (TAG_PAUSE_MISSION.equals(dialogTag)) {
            switch (which) {
                case BUTTON_POSITIVE:
                    // validation

                    validationPauseMission();
                    break;
                case BUTTON_NEGATIVE:
                    // nothing to do
                    break;
                // ...
            }
            return true;
        }
        return false;
    }

    private void validationAjoutDescriptionDemande(String value) {
        //
        A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this,value, Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
    }

    private void validationDemande() {
        final ProgressDialog dialogueWaiting = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
        selectedMission.setStatut(A3techMissionStatut.VALIDEE);
        //Save Mission
        MissionManager.getInstance().updateMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this, "Mission Validée avec succès !", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                notificiationValidationDemande(dialogueWaiting);
                actionRefreshStatutMission();
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
                A3techCustomToastDialog.createSnackBar(getActivity(), getString(R.string.error_validation_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            }
        });

    }


    // todo same as validation for cancel or report, cloture, ...

    /**
     * Permet d'enregistrer un Evenement de Validation de la Mission.
     *
     * @param dialogueWaiting
     */
    private void notificiationValidationDemande(final ProgressDialog dialogueWaiting) {
        //TODO builder un commenaire + discreption
        String commentaire = "";
        if (selectedMission.getCategorie() != null) {
            commentaire = "Demande validée pour une Mission en  " + selectedMission.getCategorie().getLibelle() + "";
        } else {
            commentaire = "Demande validée";
        }

        A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                null, selectedMission, A3techNotificationType.VALIDATION_MISSION, commentaire, getString(R.string.libelle_creatio_mission));
        NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                dialogueWaiting.dismiss();
                A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.mission_validee), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
                // A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }

    /**
     * Permet d'enregistrer un Evenement d'Annulation de la Mission.
     *
     * @param dialogueWaiting
     */
    private void notificiationAnnulationDemande(final ProgressDialog dialogueWaiting) {
        //TODO builder un commenaire + discreption
        String commentaire = "Demande Annulée pour motif :   " + selectedMission.getMotifRejet() + "";
        A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                null, selectedMission, A3techNotificationType.ANNULATION_MISSION, commentaire, getString(R.string.libelle_annulation_mission));
        NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                dialogueWaiting.dismiss();
                A3techCustomToastDialog.createSnackBar(getActivity(), getString(R.string.libelle_annulation_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
                // A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }


    /**
     * Permet d'enregistrer un Evenement d'Annulation de la Mission.
     *
     * @param dialogueWaiting
     */
    private void notificiationReportDemande(final ProgressDialog dialogueWaiting) {
        //TODO builder un commenaire + discreption
        String commentaire = "Demande Reportée pour motif :   " + selectedMission.getMotifReport() + "";
        A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                null, selectedMission, A3techNotificationType.REPORTATION_MISSION, commentaire, getString(R.string.libelle_report_mission));
        NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                dialogueWaiting.dismiss();
                A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.libelle_report_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
                // A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }


    /**
     * Permet d'enregistrer un Evenement d'Annulation de la Mission.
     *
     * @param dialogueWaiting
     */
    private void notificiationClotureDemande(final ProgressDialog dialogueWaiting) {
        //TODO builder un commenaire + discreption
        String commentaire = "Demande Achevée et clôturée";
        if (selectedMission.getReviewMission() != null) {
            if (selectedMission.getReviewMission().getCommentaire() != null) {
                commentaire = commentaire + " ,le Client est " + getRatingClientMood(selectedMission.getReviewMission().getRating());
                commentaire = commentaire + " \n ..." + selectedMission.getReviewMission().getCommentaire();
            }
        }
        A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                null, selectedMission, A3techNotificationType.CLOTURE_MISSION, commentaire, getString(R.string.libelle_cloture_mission));
        NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                dialogueWaiting.dismiss();
                A3techCustomToastDialog.createSnackBar(getActivity(), getString(R.string.libelle_cloture_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
                // A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }

    private void notificiationDemarrageMission(final ProgressDialog dialogueWaiting) {
        //TODO builder un commenaire + discreption
        String commentaire = "Mission Démarée";

        A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                null, selectedMission, A3techNotificationType.DEMARRAGE_MISSION, commentaire, getString(R.string.libelle_demarrage_mission));
        NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                dialogueWaiting.dismiss();
                A3techCustomToastDialog.createSnackBar(getActivity(), getString(R.string.libelle_demarrage_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
                // A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }




    private String getRatingClientMood(Float rating) {
        switch (Math.round(rating)) {
            case 1:
                return " est très décu du travail effectué";
            case 2:
                return " n'est pas satisfait du travail effectué";
            case 3:
                return " pense que le travail a besoin d'amélioration";
            case 4:
                return " a aimé le travail effectué";
            case 5:
                return " est très content du travail";
        }
        return "";
    }




    private void validationReportDemande() {
        final ProgressDialog dialogueWaiting = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
        selectedMission.setStatut(A3techMissionStatut.REPORTEE);
        //Save Mission
        MissionManager.getInstance().updateMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                A3techCustomToastDialog.createToastDialog(A3techDisplayMissionActivity.this, "Mission Reportée avec succès !", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                notificiationReportDemande(dialogueWaiting);
                actionRefreshStatutMission();
                initFabEventsAction();
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
            }
        });

    }

    private void validationCancelDemande() {
        final ProgressDialog dialogueWaiting = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
        selectedMission.setStatut(A3techMissionStatut.ANNULEE);
        //Save Mission
        MissionManager.getInstance().updateMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this, "Mission Annulée avec succès !", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                notificiationAnnulationDemande(dialogueWaiting);
                actionRefreshStatutMission();
                initFabEventsAction();

            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
            }
        });

    }

    private void validationCloturetDemande() {
        final ProgressDialog dialogueWaiting = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
        //Save Mission
        MissionManager.getInstance().cloturerMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                /* dialogueWaiting.dismiss();*/

                selectedMission = (A3techMission) data;
                initFabEventsAction();
                doZoomEffect(statutMission);
                //calcule montant
                doCalculeMontantMission();
                //calcule Duree
                doCalculeDureeMission();
                actionRefreshStatutMission();
                btnEditReview.setVisibility(View.GONE);
                containerActions.setVisibility(View.GONE);

                A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this, "Mission Cloturée avec succès !", Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                //notificiationClotureDemande(dialogueWaiting);
                dialogueWaiting.dismiss();
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
            }
        });

    }



    private void validationStartMission() {
        final ProgressDialog dialogueWaiting = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
        //Save Mission
        MissionManager.getInstance().startMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                /* dialogueWaiting.dismiss();*/
                selectedMission = (A3techMission)data;
                actionRefreshStatutMission();
                initFabEventsAction();
                doZoomEffect(statutMission);
                //calcule montant

                A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this, getResources().getString(R.string.mission_started), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                notificiationClotureDemande(dialogueWaiting);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
            }
        });
    }

    private void validationPauseMission() {
        final ProgressDialog dialogueWaiting = CustomProgressDialog.createProgressDialog(A3techDisplayMissionActivity.this, "");
        //Save Mission
        MissionManager.getInstance().pauseMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                /* dialogueWaiting.dismiss();*/
                selectedMission = (A3techMission)data;
                actionRefreshStatutMission();
                initFabEventsAction();
                doZoomEffect(statutMission);
                //calcule montant

                A3techCustomToastDialog.createSnackBar(A3techDisplayMissionActivity.this, getResources().getString(R.string.mission_en_pause), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_SUCESS);
                notificiationClotureDemande(dialogueWaiting);
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogueWaiting.dismiss();
            }
        });
    }


    private void doCalculeMontantMission() {
        progressMontantMission.setVisibility(View.VISIBLE);
        montantMission.setVisibility(View.GONE);
        MissionManager.getInstance().calculeMontantMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                final Double montant = (Double) data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        montantMission.setText(montant + " MAD");
                        progressMontantMission.setVisibility(View.GONE);
                        montantMission.setVisibility(View.VISIBLE);
                        //rdoZoomEffect(montantMission);
                    }
                });
            }

            @Override
            public void dataLoadingError(int errorCode) {
                montantMission.setText("N/A " + "");
            }
        });
    }

    private void doCalculeDureeMission() {
        progresstimeMission.setVisibility(View.VISIBLE);
        tempsMission.setVisibility(View.GONE);
        MissionManager.getInstance().calculeDureeMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                final String duree = (String) data;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tempsMission.setText(duree + "");
                        doZoomEffect(tempsMission);
                        progresstimeMission.setVisibility(View.GONE);
                        tempsMission.setVisibility(View.VISIBLE);
                    }
                });
            }

            @Override
            public void dataLoadingError(int errorCode) {
                tempsMission.setText("N/A " + "");
            }
        });
    }


    private void initFabEventsAction() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(A3techDisplayMissionActivity.this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        RFACLabelItem cancelmenu = new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.cancel))
                .setResId(R.drawable.a3tech_annuler_mission_action)
                .setIconNormalColor(getResources().getColor(R.color.colorPrimary))
                .setIconPressedColor(getResources().getColor(R.color.colorPrimaryDark))
                .setLabelColor(getResources().getColor(R.color.colorPrimary))
                .setWrapper(0)
                ;
        RFACLabelItem reportMenu = new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.report_mission))
                .setResId(R.drawable.a3tech_report_mission_action)
                .setIconNormalColor(getResources().getColor(R.color.colorPrimary))
                .setIconPressedColor(getResources().getColor(R.color.colorPrimaryDark))
                .setLabelColor(getResources().getColor(R.color.colorPrimary))
                .setWrapper(1);


        RFACLabelItem stopMenu = new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.stop_mission))
                .setResId(R.drawable.a3tech_stop_mission_action)
                .setIconNormalColor(getResources().getColor(R.color.colorPrimary))
                .setIconPressedColor(getResources().getColor(R.color.colorPrimaryDark))
                .setLabelColor(getResources().getColor(R.color.colorPrimary))
                .setLabelSizeSp(14)
                .setWrapper(2);

        RFACLabelItem startMenu = new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.start_mission))
                .setResId(R.drawable.a3tech_start_mission)
                .setIconNormalColor(getResources().getColor(R.color.colorPrimary))
                .setIconPressedColor(getResources().getColor(R.color.colorPrimaryDark))
                .setLabelColor(getResources().getColor(R.color.colorPrimary))
                .setWrapper(3);

        if(selectedMission.getStatut() != null){
            if(selectedMission.getStatut().getId() == A3techMissionStatut.CREE.getId()){
                items.add(cancelmenu);

                items.add(
                        reportMenu
                );
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.VALIDEE.getId()){
                items.add(cancelmenu);

                items.add(
                        reportMenu
                );
                items.add(
                        startMenu
                );
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.DEMARREE.getId()){

                items.add(
                        stopMenu
                );
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.EN_PAUSE.getId()){

                items.add(
                        startMenu
                );
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.REPORTEE.getId()){

                items.add(
                        cancelmenu
                );
                items.add(
                        reportMenu
                );
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.REJETEE.getId()){

                items.clear();
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.ANNULEE.getId()){
                items.clear();
            }
            else  if(selectedMission.getStatut().getId() == A3techMissionStatut.CLOTUREE.getId()){
                items.clear();
            }
        }

        if(items == null || items.size() == 0){
            rfaBtn.setVisibility(View.GONE);
        }else {
            rfaBtn.setVisibility(View.VISIBLE);
            rfaContent
                    .setItems(items)
                    .setIconShadowRadius(RFABTextUtil.dip2px(A3techDisplayMissionActivity.this, 5))
                    .setIconShadowColor(0xff888888)
                    .setIconShadowDy(RFABTextUtil.dip2px(A3techDisplayMissionActivity.this, 5))
            ;
            rfabHelper = new RapidFloatingActionHelper(
                    A3techDisplayMissionActivity.this,
                    rfaLayout,
                    rfaBtn,
                    rfaContent
            ).build();
        }

    }

    private static final String TAG_ADD_DESC_DIALOG = "TADD";
    private static final String TAG_ADD_DESC_INPUT = "DESC";
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch ((Integer)item.getWrapper()){
            case 0:
                //annuler
                break;
            case 1:
                // Reporter
                break;
            case 2:
                //Pause
                SimpleDialog.build().title(R.string.pause_mission).msg(R.string.msg_pause_mission).pos(R.string.ok_label).neg(R.string.cancel).show(A3techDisplayMissionActivity.this, TAG_PAUSE_MISSION);
                break;
            case 3:
                //demarer
                SimpleDialog.build().title(R.string.start_mission).msg(R.string.msg_start_mission).pos(R.string.ok_label).neg(R.string.cancel).show(A3techDisplayMissionActivity.this, TAG_START_MISSION);
                break;

        }
        rfabHelper.toggleContent();
      /*  SimpleFormDialog.build().title(item.getLabel()).cancelable(true)
                .fields(Input.plain(TAG_ADD_DESC_INPUT).required(true)
                        .hint(R.string.hint_add_more_details_event)
                        .inputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE))
                .show(getSupportFragmentManager(), TAG_ADD_DESC_DIALOG);
        rfabHelper.toggleContent();*/
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch ((Integer)item.getWrapper()){
            case 0:
                //annuler
                break;
            case 1:
                // Reporter
                break;
            case 2:
                //Pause
                SimpleDialog.build().title(R.string.pause_mission).msg(R.string.msg_pause_mission).pos(R.string.ok_label).neg(R.string.cancel).show(A3techDisplayMissionActivity.this, TAG_PAUSE_MISSION);
                break;
            case 3:
                //demarer
                SimpleDialog.build().title(R.string.start_mission).msg(R.string.msg_start_mission).pos(R.string.ok_label).neg(R.string.cancel).show(A3techDisplayMissionActivity.this, TAG_START_MISSION);
                break;

        }
        rfabHelper.toggleContent();
    }
}


