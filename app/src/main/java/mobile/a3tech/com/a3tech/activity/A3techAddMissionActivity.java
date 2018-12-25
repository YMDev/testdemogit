package mobile.a3tech.com.a3tech.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang3.BooleanUtils;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techSelectMissionCategoryAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techAddEmailFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAddPasswordFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAddUserNameFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAffecterTechnicienFragment;
import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechInMapFragment;
import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechniciensPArentFragment;
import mobile.a3tech.com.a3tech.fragment.A3techPostMissionFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelectCategoryMissionFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelecteAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techStep1CreatAccountFragment;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.A3techDialogFilterTechniciens;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techAddMissionActivity extends BaseActivity implements A3techSelectCategoryMissionFragment.OnFragmentInteractionListener, A3techPostMissionFragment.OnFragmentInteractionListener, A3techAffecterTechnicienFragment.OnFragmentInteractionListener, A3techDisplayTechniciensPArentFragment.OnFragmentInteractionListener, A3techDisplayTechInMapFragment.OnFragmentInteractionListener {


    private FrameLayout framePostMission;
    private AppBarLayout appBarLayoutPostMission;
    private Toolbar toolbarPostMission;
    private Toolbar toolbarSelectCategoryMission;
    private Toolbar toolbarAffecterTech;
    private Toolbar toolbarVisible;
    private ProgressBar progressPostMission;
    private ImageView backAction, filterTech;
    A3techDisplayTechniciensPArentFragment fragmentMap;
    public static final String TAG_RESULT_FROM_SELECT_TECH = "TAG_RESULT_FROM_SELECT_TECH";
    public static final String TAG_RESULT_FROM_ADD_MISSION_WELCOM = "TAG_RESULT_FROM_ADD_MISSION_WELCOM";
    public static final String TAG_RESULT_OBJECT_ADD_MISSION_WELCOM = "TAG_RESULT_OBJECT_ADD_MISSION_WELCOM";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_add_mission_activity);
        new InitActivityTask(A3techAddMissionActivity.this).execute();

    }

    private void initiInterfaceActivity() {
        bindComponents();
        updateAppbarLayout(0);
        getSupportActionBar().setElevation(0);
        setFragment(A3techSelectCategoryMissionFragment.newInstance(null, null), false, false);
        getExtratData();
    }

    Boolean isFromWelcom = Boolean.FALSE;

    private void getExtratData() {
        Bundle b = getIntent().getExtras();
        if (b != null) {
            Boolean isFromWelcoms = b.getBoolean(A3techWelcomPageActivity.TAG_ADD_MISSION_FROM_WELCOM);
            if (BooleanUtils.isTrue(isFromWelcoms)) {
                isFromWelcom = Boolean.TRUE;
            } else {
                isFromWelcom = Boolean.FALSE;
            }
        }
    }


    private void bindComponents() {
        framePostMission = findViewById(R.id.frame_add_mission);
        appBarLayoutPostMission = findViewById(R.id.appbar_add_mission);
        toolbarPostMission = findViewById(R.id.toolbar_post_mission);
        toolbarSelectCategoryMission = findViewById(R.id.toolbar_select_category);
        toolbarAffecterTech = findViewById(R.id.toolbar_affect_tech);
        progressPostMission = findViewById(R.id.progress_add_mission);
        backAction = findViewById(R.id.back_action);
        backAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        filterTech = findViewById(R.id.filter_techniciens);
        filterTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker gps = new GPSTracker(A3techAddMissionActivity.this);
                if (gps.canGetLocation()) {
                    A3techDialogFilterTechniciens.displayFilterDialogue(new A3techDialogFilterTechniciens.OnAppliquerPerimetre() {
                        @Override
                        public void appliquerPerimetre(Integer perim) {
                            PreferencesValuesUtils.setPermietreRechercheTechniciens(A3techAddMissionActivity.this, perim);
                            if (fragmentMap != null) {
                                fragmentMap.notifyPerimetreChanged(perim);
                            }
                        }

                        @Override
                        public Context getContext() {
                            return A3techAddMissionActivity.this;
                        }
                    }, PreferencesValuesUtils.getPermietreRechercheTechniciens(A3techAddMissionActivity.this));
                } else {
                    gps.showSettingsAlert();
                }
            }
        });

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
        t.addToBackStack(fragment.getClass().getName());
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

    private void updateProgress() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.frame_add_mission);
        if (f instanceof A3techSelectCategoryMissionFragment) {
            progressBarchangeSmouthly(0);
            updateAppbarLayout(0);
        } else if (f instanceof A3techPostMissionFragment) {
            progressBarchangeSmouthly(0);
            updateAppbarLayout(0);
        } else if (f instanceof A3techDisplayTechniciensPArentFragment) {
            progressBarchangeSmouthly(50);
            updateAppbarLayout(1);
            ((A3techDisplayTechniciensPArentFragment) f).deleteMapInFragment();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void actionToolbar(boolean hide) {
        if (getSupportActionBar() == null) return;
        if (hide) {
            toolbarVisible.animate()
                    .translationY(-toolbarVisible.getHeight())
                    .alpha(0.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toolbarVisible.setVisibility(View.GONE);
                        }
                    });
        } else {
            toolbarVisible.setVisibility(View.VISIBLE);
            toolbarVisible.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toolbarVisible.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }


    Categorie categorieSelected;

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techSelectCategoryMissionFragment.ACTION_SELECT_CATEGORY_MISSION:
                //Categorie Mission selectionnée
                categorieSelected = (Categorie) data;
                updateAppbarLayout(1);
                setFragment(A3techPostMissionFragment.newInstance(categorieSelected), true, false);
                progressBarchangeSmouthly(50);
                break;
            case A3techPostMissionFragment.ACTION_SAVE_MISSION_INFO:
                //Categorie Mission selectionnée
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressBarchangeSmouthly(100);
                    }
                });
                A3techMission mission = (A3techMission) data;
                updateAppbarLayout(3);
                ((TextView) toolbarAffecterTech.findViewById(R.id.big_title)).setText(mission.getCategorie().getLibelle());
                // avant d'introduire buttom tabs : setFragment(A3techAffecterTechnicienFragment.newInstance(mission), true, false);
                fragmentMap = A3techDisplayTechniciensPArentFragment.newInstance(mission);
                setFragment(fragmentMap, true, false);
                break;
        }
    }

    @Override
    public void backAction() {
        onBackPressed();
    }


    @SuppressLint("RestrictedApi")
    private void updateAppbarLayout(int position) {

        switch (position) {
            case 0:
                showToolbar(toolbarSelectCategoryMission);
                hideToolbar(toolbarPostMission);
                hideToolbar(toolbarAffecterTech);
                setSupportActionBar(toolbarSelectCategoryMission);
                toolbarVisible = toolbarSelectCategoryMission;
                break;
            case 1:
                hideToolbar(toolbarPostMission);
                hideToolbar(toolbarSelectCategoryMission);
                hideToolbar(toolbarAffecterTech);
                setSupportActionBar(toolbarPostMission);
                toolbarVisible = toolbarPostMission;
                break;
            case 3:
                hideToolbar(toolbarAffecterTech);
                hideToolbar(toolbarPostMission);
                hideToolbar(toolbarSelectCategoryMission);
                setSupportActionBar(toolbarAffecterTech);
                toolbarVisible = toolbarAffecterTech;
                break;
        }
        getSupportActionBar().setElevation(0);
    }


    public class InitActivityTask extends AsyncTask<Void, Void, Boolean> {
        private Context activity;
        private ProgressDialog pd;

        public InitActivityTask(Context activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            pd = CustomProgressDialog.createProgressDialog(
                    activity,
                    "");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            initiInterfaceActivity();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            pd.dismiss();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_MISSION): {
                if (resultCode == Activity.RESULT_OK) {
                   /*if (isFromWelcom){
                       Intent intentAddMissionn  = new Intent(A3techAddMissionActivity.this, A3techWelcomPageActivity.class);
                       String jsonMission = data.getStringExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH);
                       A3techMission mission = new Gson().fromJson(jsonMission, A3techMission.class);
                       intentAddMissionn.putExtra(A3techAddMissionActivity.TAG_RESULT_OBJECT_ADD_MISSION_WELCOM,jsonMission);
                       intentAddMissionn.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_ADD_MISSION_WELCOM,true);
                       startActivityForResult(intentAddMissionn,A3techWelcomPageActivity.REQ_ADD_MISSION_FROM_WELCOM);
                       Log.i("KKKKKKKKKKKKKKKKKKKKKK", "Recupération resultat add mission activity");
                       finish();
                   }else{
                       String jsonMission = data.getStringExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH);
                       A3techMission mission = new Gson().fromJson(jsonMission, A3techMission.class);
                       Intent resultIntent = new Intent();
                       resultIntent.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH,jsonMission);
                       setResult(Activity.RESULT_OK, resultIntent);
                       finish();
                   }*/
                    String jsonMission = data.getStringExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH);
                    A3techMission mission = new Gson().fromJson(jsonMission, A3techMission.class);
                    addMission(mission);
                }
                break;
            }
        }
    }

    private void addMission(A3techMission mission) {
        final ProgressDialog dialogwaiting  = CustomProgressDialog.createProgressDialog(getActivity(),"");
        MissionManager.getInstance().createMission(mission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
//mission created
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                A3techMission missionSaved = (A3techMission) data;
                Intent resultIntent = new Intent();
                resultIntent.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH, new Gson().toJson(missionSaved));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                if(dialogwaiting != null) dialogwaiting.dismiss();
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if(dialogwaiting != null) dialogwaiting.dismiss();
                A3techCustomToastDialog.createSnackBar(A3techAddMissionActivity.this, getString(R.string.mission_error_creation), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
            }
        });
    }

    public void hideToolbar(Boolean hide) {

        if (getSupportActionBar() == null) return;
        if (hide) {
            toolbarVisible.animate()
                    .translationY(-toolbarVisible.getHeight())
                    .alpha(0.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toolbarVisible.setVisibility(View.GONE);
                        }
                    });
        } else {
            toolbarVisible.setVisibility(View.VISIBLE);
            toolbarVisible.animate()
                    .translationY(0)
                    .alpha(1.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            toolbarVisible.setVisibility(View.VISIBLE);
                        }
                    });
        }
    }

    private void showToolbar(final Toolbar tool) {
        tool.animate()
                .translationY(0)
                .alpha(1.0f)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        tool.setVisibility(View.VISIBLE);
                    }
                });

    }

    private void hideToolbar(final Toolbar tool) {
        if (tool != null && tool.getVisibility() == View.VISIBLE) {
            tool.animate()
                    .translationY(-toolbarVisible.getHeight())
                    .alpha(0.0f)
                    .setDuration(200)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            tool.setVisibility(View.GONE);
                        }
                    });
        }

    }
}
