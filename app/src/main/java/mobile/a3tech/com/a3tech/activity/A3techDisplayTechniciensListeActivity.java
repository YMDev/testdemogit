package mobile.a3tech.com.a3tech.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techAffecterTechnicienFragment;
import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechInMapFragment;
import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechniciensFragment;
import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechniciensPArentFragment;
import mobile.a3tech.com.a3tech.fragment.A3techPostMissionFragment;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.utils.ValidationPatternUtils;
import mobile.a3tech.com.a3tech.view.A3techDialogFilterTechniciens;

public class A3techDisplayTechniciensListeActivity extends BaseActivity implements A3techDialogFilterTechniciens.OnAppliquerPerimetre, A3techDisplayTechniciensFragment.OnFragmentInteractionListener, A3techPostMissionFragment.OnFragmentInteractionListener, A3techDisplayTechniciensPArentFragment.OnFragmentInteractionListener, A3techDisplayTechInMapFragment.OnFragmentInteractionListener, A3techAffecterTechnicienFragment.OnFragmentInteractionListener {

    public static final String TAG_MISSION_TO_SAVE_FROM_BROWS_TECH = "TAG_MISSION_TO_SAVE_FROM_BROWS_TECH";
    public static final String TAG_CATEGORY_FOR_RELOADING = "TAG_CATEGORY_FOR_RELOADING";
    private FrameLayout framePostMission;
    private ProgressBar progressPostMission;
    private ImageView backAction, filterTechniciens;
    private RecyclerView recyclerViewTechnicien;
    private Toolbar toolbarSelectTechnicien;
    private Categorie categorieSelected;
    private A3techMission missionSelected;

    private Boolean isFromSearchActivity = Boolean.FALSE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_display_techniciens_liste_activity);
        if (!getSelectedMissionTechFromRecherche()) {
            getSelectedCategory();
            initiInterfaceActivity();
        } else {
            isFromSearchActivity = Boolean.TRUE;
        }

    }

    private A3techDisplayTechniciensPArentFragment fragmentToDispl;

    private void initiInterfaceActivity() {
        bindComponents();
        updateAppbarLayout(0);
        getSupportActionBar().setElevation(0);
        progressBarchangeSmouthly(50);
        fragmentToDispl = A3techDisplayTechniciensPArentFragment.newInstance(categorieSelected);
        setFragment(fragmentToDispl, false, false);
    }

    private Integer getPerimetresRechercheTechnicien() {
        return PreferencesValuesUtils.getPermietreRechercheTechniciens(A3techDisplayTechniciensListeActivity.this);
    }

    private void setPerimetreRechercheTechnicien(Integer perimNew) {
        PreferencesValuesUtils.setPermietreRechercheTechniciens(A3techDisplayTechniciensListeActivity.this, perimNew);
    }

    private void bindComponents() {
        framePostMission = findViewById(R.id.frame_add_mission);
        toolbarSelectTechnicien = findViewById(R.id.toolbar_select_tech);
        progressPostMission = findViewById(R.id.progress_add_mission);
        filterTechniciens = findViewById(R.id.filter_techniciens);
        filterTechniciens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker(A3techDisplayTechniciensListeActivity.this);
                if (gps.canGetLocation()) {
                    A3techDialogFilterTechniciens.displayFilterDialogue(A3techDisplayTechniciensListeActivity.this, getPerimetresRechercheTechnicien());
                } else {
                    gps.showSettingsAlert();
                }
                if(PermissionsStuffs.canAccessLocation(A3techDisplayTechniciensListeActivity.this)){
                    A3techDialogFilterTechniciens.displayFilterDialogue(A3techDisplayTechniciensListeActivity.this, getPerimetresRechercheTechnicien());
                }else{
                    SimpleDialog.build().title("Autorisation location est nécessaire").msg("pour afficher la liste des techniciens sur Map, il faut autoriser l'app à accéder à votre position").show(A3techDisplayTechniciensListeActivity.this);
                }

            }
        });
       /* backAction = findViewById(R.id.back_action);
        backAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/


    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            updateProgress();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    private void updateAppbarLayout(int position) {
        switch (position) {
            case 0:
                toolbarSelectTechnicien.setVisibility(View.VISIBLE);
                if (categorieSelected != null) {
                    ((TextView) toolbarSelectTechnicien.findViewById(R.id.big_title)).setText(categorieSelected.getLibelle());
                    ((TextView) toolbarSelectTechnicien.findViewById(R.id.big_sub_title)).setText(getResources().getString(R.string.find_best_tech));
                }
                findViewById(R.id.filter_techniciens).setVisibility(View.VISIBLE);
                setSupportActionBar(toolbarSelectTechnicien);
                break;
            case 1:
                if (missionSelected != null && missionSelected.getCategorie() != null) {
                    ((TextView) toolbarSelectTechnicien.findViewById(R.id.big_title)).setText(getResources().getString(R.string.save_mission));
                    ((TextView) toolbarSelectTechnicien.findViewById(R.id.big_sub_title)).setText(getResources().getString(R.string.complete_mission_informations));
                }
                findViewById(R.id.filter_techniciens).setVisibility(View.GONE);
                actionToolbar(false);
                break;
            case 3:

                break;
        }
        getSupportActionBar().setElevation(0);
    }

    private void updateProgress() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_add_mission);
        if (f instanceof A3techPostMissionFragment) {
            progressBarchangeSmouthly(50);
            updateAppbarLayout(0);
        } else if (f instanceof A3techDisplayTechniciensFragment) {
            progressBarchangeSmouthly(0);
            updateAppbarLayout(0);
        }
    }

    protected void setFragment(Fragment fragment, boolean withAnim, boolean back) {
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        if (withAnim) {
            if (!back) {
                t.setCustomAnimations(R.animator.slide_in_left,
                        R.animator.slide_in_right, R.animator.slide_in_right_back, R.animator.slide_in_left_back);
            } else {
                t.setCustomAnimations(R.animator.slide_in_right_back,
                        R.animator.slide_in_left_back, 0, 0);
            }
        }
        t.replace(R.id.frame_add_mission, fragment);
        if (fragment instanceof A3techPostMissionFragment) {
            t.addToBackStack(fragment.getClass().getName());
        }
        t.commit();
    }

    private void progressBarchangeSmouthly(int progress) {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            // will update the "progress" propriety of seekbar until it reaches progress
            ObjectAnimator animation = ObjectAnimator.ofInt(progressPostMission, "progress", progress);
            animation.setDuration(1000); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            progressPostMission.setProgress(progress); // no animation on Gingerbread or lower
    }

    private void getSelectedCategory() {
        String jsonMyObject = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            jsonMyObject = b.getString(A3techHomeActivity.TAG_CATEGORY_SELECTED_FROM_HOME_ACTIVITY);
        }
        if (jsonMyObject != null) {
            categorieSelected = new Gson().fromJson(jsonMyObject, Categorie.class);
        }

    }


    public static final int RESULT_RELOAD = 3213;

    public void finishActivityToBeReloaded() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(TAG_CATEGORY_FOR_RELOADING, new Gson().toJson(categorieSelected));
        setResult(RESULT_RELOAD, resultIntent);
        finish();
    }

    private Boolean getSelectedMissionTechFromRecherche() {
        String jsonMyObject = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            jsonMyObject = b.getString(A3techSearchMissionsResultsActivity.ARG_MISSION_TECH_SELECTED_FROM_SEARCH);
        }
        if (jsonMyObject != null) {
            missionSelected = new Gson().fromJson(jsonMyObject, A3techMission.class);
            if (missionSelected != null && missionSelected.getTechnicien() != null) {
                bindComponents();
                nextStepAfterSelectTechnicien(missionSelected);
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    public void nextStepAfterSelectTechnicien(A3techMission mission) {
        missionSelected = mission;
        // next step add mission informations
        progressBarchangeSmouthly(100);
        setFragment(A3techPostMissionFragment.newInstance(missionSelected), true, false);
        findViewById(R.id.filter_techniciens).setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_HOME): {
                if (resultCode == Activity.RESULT_OK) {
                    String jsonMission = data.getStringExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH);
                    missionSelected = new Gson().fromJson(jsonMission, A3techMission.class);
                    nextStepAfterSelectTechnicien(missionSelected);
                }
                break;
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void actionToolbar(boolean hide) {
        if (hide) {
            getSupportActionBar().hide();
            findViewById(R.id.filter_techniciens).setVisibility(View.GONE);
        } else {
            getSupportActionBar().show();
            findViewById(R.id.filter_techniciens).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void backAction() {

    }

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techPostMissionFragment.ACTION_SAVE_MISSION_INFO_FROM_TECH:
                if (isFromSearchActivity) {
                    A3techMission mission = (A3techMission) data;
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(TAG_MISSION_TO_SAVE_FROM_BROWS_TECH, new Gson().toJson(mission));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                    break;
                } else {
                    A3techMission mission = (A3techMission) data;
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(TAG_MISSION_TO_SAVE_FROM_BROWS_TECH, new Gson().toJson(mission));
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                    break;
                }

        }
    }

    @Override
    public void appliquerPerimetre(Integer perim) {
        // refresh liste tech with permi
        setPerimetreRechercheTechnicien(perim);
        if(fragmentToDispl != null){
            fragmentToDispl.notifyPerimetreChanged(perim);
        }
    }

    @Override
    public Context getContext() {
        return A3techDisplayTechniciensListeActivity.this;
    }


}
