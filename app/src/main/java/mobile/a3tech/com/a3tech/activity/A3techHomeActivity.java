package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.UserManager;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.BottomBarAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techAffecterTechnicienFragment;
import mobile.a3tech.com.a3tech.fragment.A3techDisplayTechniciensFragment;
import mobile.a3tech.com.a3tech.fragment.A3techHomeAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techHomeBrowseTechFragment;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;
import mobile.a3tech.com.a3tech.fragment.A3techPostMissionFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelectCategoryMissionFragment;
import mobile.a3tech.com.a3tech.fragment.A3techSelecteAccountFragment;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.DummyFragment;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.NoSwipePager;

public class A3techHomeActivity extends AppCompatActivity implements A3techHomeAccountFragment.OnFragmentInteractionListener, A3techMissionsHomeFragment.OnFragmentInteractionListener, A3techHomeBrowseTechFragment.OnFragmentInteractionListener {
    private final int[] colors = {R.color.white, R.color.white, R.color.white, R.color.white};
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;
    private boolean notificationVisible = false;
    private Categorie categorieSelected;
    AppBarLayout appBarHome ;
    Toolbar toolMission;
    Toolbar toolEchange;
    Toolbar toolAccount;
    Toolbar toolBrowse;
    TextView nomPrenomUser;
    ImageView userAvatar;

    public static final String ACTION_FROM_A3techHomeActivity = "A3techHomeActivity";
    public static final String TAG_CATEGORY_SELECTED_FROM_HOME_ACTIVITY = "TAG_RESULT_FROM_SELECT_TECH";
    public static final int REQUEST_START_DISPLAY_TECH_ACTIVITY = 255;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_home_activity);

        new InitActivityTask(A3techHomeActivity.this).execute();

    }


    private void initiInterfaceActivity(){
        setupViewPager();
        toolMission = findViewById(R.id.toolbar_mission);
        toolEchange = findViewById(R.id.toolbar_echange);
        toolAccount = findViewById(R.id.toolbar_account);
        toolBrowse = findViewById(R.id.toolbar_browse);
        toolAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String connectedUser = prefs.getString("identifiant", "");
                String password = prefs.getString("password", "");

                mobile.a3tech.com.a3tech.manager.UserManager.getInstance().getProfil(connectedUser, password, new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        User user = (User)data;
                        Bundle bExtra = new Bundle();
                        bExtra.putString(A3techViewEditProfilActivity.ARG_SRC_ACTION, A3techHomeActivity.ACTION_FROM_A3techHomeActivity);
                        bExtra.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(user));
                        Intent mainIntent = new Intent(A3techHomeActivity.this, A3techViewEditProfilActivity.class);
                        mainIntent.putExtras(bExtra);
                        startActivity(mainIntent);
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {

                    }
                });

            }
        });

        userAvatar = findViewById(R.id.photo_user);
        nomPrenomUser = findViewById(R.id.user_name_pname);

        nomPrenomUser.setText(getIntent().getStringExtra("nomPrenom"));
        appBarHome = findViewById(R.id.appbar_home);
        updateAppbarLayout(0);

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.home_bottom_navigation);
        setupBottomNavBehaviors();
        setupBottomNavStyle();
        //createFakeNotification();
        addBottomNavigationItems();
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected)
                    viewPager.setCurrentItem(position);

                updateAppbarLayout(position);
                // remove notification badge
                int lastItemPos = bottomNavigation.getItemsCount() - 1;
                if (notificationVisible && position == lastItemPos)
                    bottomNavigation.setNotification(new AHNotification(), lastItemPos);
                return true;
            }
        });
    }

    private void updateAppbarLayout(int position){
        switch (position){
            case 0:
                toolEchange.setVisibility(View.VISIBLE);
                toolMission.setVisibility(View.GONE);
                toolAccount.setVisibility(View.GONE);
                toolBrowse.setVisibility(View.GONE);
                appBarHome.setBackgroundColor(getResources().getColor(R.color.white));
                setSupportActionBar(toolEchange);
                break;
            case 1:
                toolMission.setVisibility(View.VISIBLE);
                toolEchange.setVisibility(View.GONE);
                toolAccount.setVisibility(View.GONE);
                toolBrowse.setVisibility(View.GONE);
                appBarHome.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                setSupportActionBar(toolMission);
                break;
            case 2:
                toolMission.setVisibility(View.GONE);
                toolEchange.setVisibility(View.GONE);
                toolAccount.setVisibility(View.GONE);
                toolBrowse.setVisibility(View.VISIBLE);
                appBarHome.setBackgroundColor(getResources().getColor(R.color.white));
                setSupportActionBar(toolBrowse);
                break;
            case 3:
                toolMission.setVisibility(View.GONE);
                toolEchange.setVisibility(View.GONE);
                toolAccount.setVisibility(View.VISIBLE);
                toolBrowse.setVisibility(View.GONE);
                appBarHome.setBackgroundColor(getResources().getColor(R.color.white));
                setSupportActionBar(toolAccount);
                break;
        }
     }
    private void setupViewPager() {
        viewPager = (NoSwipePager) findViewById(R.id.home_view_pager_navigation);
        viewPager.setPagingEnabled(false);
        pagerAdapter = new BottomBarAdapter(getSupportFragmentManager());
        pagerAdapter.addFragments(createFragment(colors[0]));
        pagerAdapter.addFragments(A3techMissionsHomeFragment.newInstance(null,null));
        pagerAdapter.addFragments(A3techHomeBrowseTechFragment.newInstance(null,null));
        pagerAdapter.addFragments(A3techHomeAccountFragment.newInstance(null,null));
        viewPager.setAdapter(pagerAdapter);
    }

    @NonNull
    private Fragment createFragment(int color) {
        DummyFragment fragment = new DummyFragment();
        fragment.setArguments(passFragmentArguments(fetchColor(color)));
        return fragment;
    }

    @NonNull
    private Bundle passFragmentArguments(int color) {
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        return bundle;
    }

    private void createFakeNotification() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AHNotification notification = new AHNotification.Builder()
                        .setText("1")
                        .setBackgroundColor(Color.YELLOW)
                        .setTextColor(Color.BLACK)
                        .build();
                // Adding notification to last item.

                bottomNavigation.setNotification(notification, bottomNavigation.getItemsCount() - 1);

                notificationVisible = true;
            }
        }, 1000);
    }


    public void setupBottomNavBehaviors() {
//        bottomNavigation.setBehaviorTranslationEnabled(false);

        /*
        Before enabling this. Change MainActivity theme to MyTheme.TranslucentNavigation in
        AndroidManifest.
        Warning: Toolbar Clipping might occur. Solve this by wrapping it in a LinearLayout with a top
        View of 24dp (status bar size) height.
         */
        bottomNavigation.setTranslucentNavigationEnabled(false);
    }

    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.
        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.accent_500));
        bottomNavigation.setInactiveColor(fetchColor(R.color.semi_transparent));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(getResources().getColor(R.color.colorPrimary),
                fetchColor(R.color.semi_transparent));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }


    /**
     * Adds (items) {@link AHBottomNavigationItem} to {@link AHBottomNavigation}
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.home_tab_1, R.drawable.ic_action_echange, colors[0]);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.home_tab_2, R.drawable.a3tech_missions_icon, colors[1]);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.home_tab_3, R.drawable.a3tech_search_icon, colors[2]);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.home_tab_4, R.drawable.a3tech_account_icon, colors[3]);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
    }


    private void prepareElementToolbar(){

    }

    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(this, color);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.a3tech_menu_echange, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);


        if(bottomNavigation.getCurrentItem() == 1  || bottomNavigation.getCurrentItem() == 3) {
            searchItem.setVisible(false);
        } else{
            searchItem.setVisible(true);
        }
        SearchManager searchManager = (SearchManager) A3techHomeActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(A3techHomeActivity.this.getComponentName()));
        }

        // look at https://stackoverflow.com/questions/27378981/how-to-use-searchview-in-toolbar-android
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void backAction() {

    }

    @Override
    public void actionNext(Integer typeAction, Object data) {
        switch (typeAction) {
            case A3techHomeBrowseTechFragment.ACTION_SELECT_CATEGORY_BROWSE_TECH:
                categorieSelected = (Categorie)data;
                 Intent mainIntent = new Intent(this, A3techDisplayTechniciensListeActivity.class);
                 mainIntent.putExtra(A3techHomeActivity.TAG_CATEGORY_SELECTED_FROM_HOME_ACTIVITY,new Gson().toJson(categorieSelected));
                 startActivityForResult(mainIntent,REQUEST_START_DISPLAY_TECH_ACTIVITY );
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQUEST_START_DISPLAY_TECH_ACTIVITY): {
                if (resultCode == Activity.RESULT_OK) {
                    String jsonMission = data.getStringExtra(A3techDisplayTechniciensListeActivity.TAG_MISSION_TO_SAVE_FROM_BROWS_TECH);
                    Mission mission = new Gson().fromJson(jsonMission, Mission.class);
                    //TODO display progress bar, save mission,whene finish saving, dismiss progress bar
                  if(onActivityInteractionMissionListener != null){
                      onActivityInteractionMissionListener.onAddElementToList(mission);
                  }
                    bottomNavigation.setCurrentItem(1);
                }
                break;
            }
        }
    }

    private class InitActivityTask extends AsyncTask<Void, Void, Boolean> {
        private Context activity;
        private ProgressDialog pd;

        public InitActivityTask(Context activity) {
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
           pd  = CustomProgressDialog.createProgressDialog(
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
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
            this.finishAffinity();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onActivityInteractionMissionListener = null;
    }

    OnActivityInteractionListener onActivityInteractionMissionListener;

    public OnActivityInteractionListener getOnActivityInteractionMissionListener() {
        return onActivityInteractionMissionListener;
    }

    public void setOnActivityInteractionMissionListener(OnActivityInteractionListener onActivityInteractionMissionListener) {
        this.onActivityInteractionMissionListener = onActivityInteractionMissionListener;
    }

    public interface OnActivityInteractionListener {
        void onAddElementToList(Mission mission);
    }
}
