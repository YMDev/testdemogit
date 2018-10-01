package mobile.a3tech.com.a3tech.activity;

import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techSelectMissionCategoryAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techAddEmailFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAddPasswordFragment;
import mobile.a3tech.com.a3tech.fragment.A3techAddUserNameFragment;
import mobile.a3tech.com.a3tech.fragment.A3techPostMissionFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelectCategoryMissionFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelecteAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techStep1CreatAccountFragment;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techAddMissionActivity extends AppCompatActivity implements A3techSelectCategoryMissionFragment.OnFragmentInteractionListener,A3techPostMissionFragment.OnFragmentInteractionListener {


    private FrameLayout framePostMission;
    private AppBarLayout appBarLayoutPostMission;
    private Toolbar toolbarPostMission;
    private Toolbar toolbarSelectCategoryMission;
    private ProgressBar progressPostMission;
    private ImageView backAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_add_mission_activity);
        bindComponents();
        updateAppbarLayout(0);
        setFragment(A3techSelectCategoryMissionFragment.newInstance(null, null), true, false);

    }


    private void bindComponents() {
        framePostMission = findViewById(R.id.frame_add_mission);
        appBarLayoutPostMission = findViewById(R.id.appbar_add_mission);
        toolbarPostMission = findViewById(R.id.toolbar_post_mission);
        toolbarSelectCategoryMission = findViewById(R.id.toolbar_select_category);
        progressPostMission = findViewById(R.id.progress_add_mission);
        backAction = findViewById(R.id.back_action);
        backAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    protected void setFragment(Fragment fragment, boolean withAnim, boolean back) {
        FragmentTransaction t = getFragmentManager().beginTransaction();
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
            animation.setDuration(500); // 0.5 second
            animation.setInterpolator(new DecelerateInterpolator());
            animation.start();
        } else
            progressPostMission.setProgress(progress); // no animation on Gingerbread or lower
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
            updateProgress();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    private void updateProgress() {
            progressBarchangeSmouthly(0);
           updateAppbarLayout(0);
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techSelectCategoryMissionFragment.ACTION_SELECT_CATEGORY_MISSION:
                //Categorie Mission selectionnée
                progressBarchangeSmouthly(100);
                Categorie categorieSelected = (Categorie)data;
                updateAppbarLayout(1);
                setFragment(A3techPostMissionFragment.newInstance(null, null), true, false);
                break;
        }
    }


    private void updateAppbarLayout(int position){
        switch (position){
            case 0:
                toolbarSelectCategoryMission.setVisibility(View.VISIBLE);
                toolbarPostMission.setVisibility(View.GONE);
                setSupportActionBar(toolbarSelectCategoryMission);
                break;
            case 1:
                toolbarPostMission.setVisibility(View.VISIBLE);
                toolbarSelectCategoryMission.setVisibility(View.GONE);
                setSupportActionBar(toolbarPostMission);
                break;
        }
    }

}
