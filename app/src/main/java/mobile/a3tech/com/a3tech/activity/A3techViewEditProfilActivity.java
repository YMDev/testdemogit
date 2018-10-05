package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techProfilFragmentAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techProfilInformationFragment;
import mobile.a3tech.com.a3tech.fragment.A3techReviewsFragment;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.view.CircleImageView;

public class A3techViewEditProfilActivity extends AppCompatActivity  implements AppBarLayout.OnOffsetChangedListener, A3techProfilInformationFragment.OnFragmentInteractionListener, A3techReviewsFragment.OnFragmentInteractionListener {
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.72f;
    private static final float PERCENTAGE_TO_SHOW_TOOLBAR  = 0.7f;
    private static final float PERCENTAGE_TO_HIDE_CIRCLE_IMAGE  = 0.5f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;
    private static final int ALPHA_ANIMATIONS_DURATION_TOOL              = 400;
    public static final String ARG_USER_OBJECT = "ARG_USER_OBJECT";
    public static final String ARG_SRC_ACTION = "ARG_SRC_ACTION";
    public static final String ARG_MISSION_OBJECT = "ARG_MISSION_OBJECT";

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private boolean mIsTheToolbarVisible = false;
    private boolean mIsTheCircleVisible = true;

    private LinearLayout mTitleContainer;
    private TextView mTitle;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private TabLayout tabLayouProfil;
    private ViewPager viewPagerProfil;

    private ImageView backBtn;
    private TextView userNamePname;
    private Boolean isFromHomeAccount = false;
    private Boolean isFromAddMission = false;
    private Boolean isFromBrowseTech = false;
    private LinearLayout validationContainer;
    private Button btnValidationSelection;
    User userToDisplay;
    Mission mission;
    private de.hdodenhof.circleimageview.CircleImageView circleImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonMyObject = null;
        String jsonMyObjectMission = null;
        String srcAction = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            jsonMyObject = b.getString(ARG_USER_OBJECT);
            srcAction = b.getString(ARG_SRC_ACTION);
            jsonMyObjectMission = b.getString(ARG_MISSION_OBJECT);
        }
        if(jsonMyObject != null){
            userToDisplay = new Gson().fromJson(jsonMyObject, User.class);
        }
        if(jsonMyObjectMission != null){
            mission = new Gson().fromJson(jsonMyObjectMission, Mission.class);
        }else{
            mission = new Mission();
        }
        if(srcAction != null && srcAction.equalsIgnoreCase(A3techHomeActivity.ACTION_FROM_A3techHomeActivity)){
            isFromHomeAccount = true;
            isFromBrowseTech = false;
            isFromAddMission = false;
        }else  if(srcAction != null && srcAction.equalsIgnoreCase(SimpleAdapterTechnicien.SRC_FROM_HOME_ADD_MISSION)){
            isFromAddMission = true;
            isFromHomeAccount = false;
            isFromBrowseTech = false;
        } else if(srcAction != null && srcAction.equalsIgnoreCase(SimpleAdapterTechnicien.SRC_FROM_HOME_BROWSE_TECH)){
            isFromBrowseTech = true;
            isFromHomeAccount = false;
            isFromAddMission = false;
        }


        setContentView(R.layout.a3tech_activity_view_edit_profil);

        bindActivity();
        viewPagerProfil.setAdapter(new A3techProfilFragmentAdapter(getSupportFragmentManager(),
                A3techViewEditProfilActivity.this, userToDisplay));

        tabLayouProfil.post(new Runnable() {
            @Override
            public void run() {
                tabLayouProfil.setupWithViewPager(viewPagerProfil);
            }
        });
        mAppBarLayout.addOnOffsetChangedListener(this);
        setTabBG(R.drawable.tab_left_select,R.drawable.tab_right_unselect);
        tabLayouProfil.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerProfil.setCurrentItem(tab.getPosition());
                if(tabLayouProfil.getSelectedTabPosition()==0) {
                    setTabBG(R.drawable.tab_left_select,R.drawable.tab_right_unselect);
                }
                else {
                    setTabBG(R.drawable.tab_left_unselect,R.drawable.tab_right_select);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        startAlphaAnimation(mTitle, 0, View.INVISIBLE);

    }

    private void bindActivity() {
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitle          = (TextView) findViewById(R.id.main_textview_title);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_linearlayout_title);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_appbar);
        viewPagerProfil = (ViewPager) findViewById(R.id.viewpager_profile_detail);
        tabLayouProfil = (TabLayout) findViewById(R.id.tabs_profil);
        circleImage = findViewById(R.id.avatare_user_cicle);
        userNamePname = findViewById(R.id.user_name_pname_detail);
        userNamePname.setText(userToDisplay.getNom()+" "+userToDisplay.getPrenom() );
        backBtn = findViewById(R.id.back_home_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               A3techViewEditProfilActivity.this.finish();
            }
        });
        validationContainer = findViewById(R.id.validation_selection_container);
        btnValidationSelection = findViewById(R.id.validate_selection);
        if(isFromHomeAccount){
            validationContainer.setVisibility(View.GONE);
        }else{
            validationContainer.setVisibility(View.VISIBLE);
        }

        btnValidationSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mission.setTechnicien(userToDisplay);
                Intent resultIntent = new Intent();
                resultIntent.putExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH, new Gson().toJson(mission));
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
        handleAlphaOnToolbar(percentage);
        handleAlphaOnCircleImage(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }

        } else {

            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleContainerVisible = false;
            }

        } else {

            if (!mIsTheTitleContainerVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    private void handleAlphaOnToolbar(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TOOLBAR) {
            if(mIsTheToolbarVisible) {
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);
                mIsTheToolbarVisible = false;
            }

        } else {

            if (!mIsTheToolbarVisible) {
                startAlphaAnimation(mToolbar, ALPHA_ANIMATIONS_DURATION_TOOL, View.INVISIBLE);
                mIsTheToolbarVisible = true;
            }
        }
    }


    private void handleAlphaOnCircleImage(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_CIRCLE_IMAGE) {
            if(mIsTheCircleVisible) {
                startAlphaAnimation(circleImage, ALPHA_ANIMATIONS_DURATION_TOOL, View.INVISIBLE);
                mIsTheCircleVisible = false;
            }

        } else {

            if (!mIsTheCircleVisible) {
                startAlphaAnimation(circleImage, ALPHA_ANIMATIONS_DURATION_TOOL, View.VISIBLE);
                mIsTheCircleVisible = true;
            }
        }
    }
    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE)
                ? new AlphaAnimation(0f, 1f)
                : new AlphaAnimation(1f, 0f);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);

    }

    private void setTabBG(int tab1, int tab2){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayouProfil.getChildAt(0);
            View tabView1 = tabStrip.getChildAt(0);
            View tabView2 = tabStrip.getChildAt(1);
            if (tabView1 != null) {
                int paddingStart = tabView1.getPaddingStart();
                int paddingTop = tabView1.getPaddingTop();
                int paddingEnd = tabView1.getPaddingEnd();
                int paddingBottom = tabView1.getPaddingBottom();
                ViewCompat.setBackground(tabView1, AppCompatResources.getDrawable(tabView1.getContext(), tab1));
                ViewCompat.setPaddingRelative(tabView1, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }

            if (tabView2 != null) {
                int paddingStart = tabView2.getPaddingStart();
                int paddingTop = tabView2.getPaddingTop();
                int paddingEnd = tabView2.getPaddingEnd();
                int paddingBottom = tabView2.getPaddingBottom();
                ViewCompat.setBackground(tabView2, AppCompatResources.getDrawable(tabView2.getContext(), tab2));
                ViewCompat.setPaddingRelative(tabView2, paddingStart, paddingTop, paddingEnd, paddingBottom);
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
