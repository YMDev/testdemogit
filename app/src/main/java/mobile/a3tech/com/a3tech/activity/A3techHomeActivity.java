package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.BottomBarAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techHomeAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techHomeBrowseTechFragment;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;
import mobile.a3tech.com.a3tech.fragment.A3techNotificationHomeFragment;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.NotificationsManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.model.A3techNotificationType;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.DummyFragment;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTest;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.NoSwipePager;

public class A3techHomeActivity extends AppCompatActivity implements A3techHomeAccountFragment.OnFragmentInteractionListener, A3techMissionsHomeFragment.OnFragmentInteractionListener, A3techHomeBrowseTechFragment.OnFragmentInteractionListener, A3techNotificationHomeFragment.OnFragmentInteractionListener, SimpleAdapterTest.OnDeconnexion {
    private final int[] colors = {R.color.white, R.color.white, R.color.white, R.color.white};
    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;
    private boolean notificationVisible = false;
    private Categorie categorieSelected;
    AppBarLayout appBarHome;
    Toolbar toolMission;
    Toolbar toolEchange;
    Toolbar toolAccount;
    Toolbar toolBrowse;
    TextView nomPrenomUser;
    ImageView userAvatar;
    A3techUser connectedUser;

    public static final String ACTION_FROM_A3techHomeActivity = "A3techHomeActivity";
    public static final String TAG_CATEGORY_SELECTED_FROM_HOME_ACTIVITY = "TAG_RESULT_FROM_SELECT_TECH";
    public static final int REQUEST_START_DISPLAY_TECH_ACTIVITY = 255;
    public static final int REQ_SEARCH = 897;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_home_activity);
        connectedUser = PreferencesValuesUtils.getConnectedUser(A3techHomeActivity.this);
        Log.i("KKKKKKKKKKKKKKKKKKKKKK", "initialisation Home Activity");
        new InitActivityTask(A3techHomeActivity.this).execute();

    }


    GPSTracker gps;
    public static final String TAG_CONNECTED_USER_LATITUDE = "UserConnectedLatitude";
    public static final String TAG_CONNECTED_USER_LONGETUDE = "UserConnectedLongetude";

    private void updateUserConnectedLocation() {

        //TODO request permission
        gps = new GPSTracker(A3techHomeActivity.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            try {
                //  mobile.a3tech.com.a3tech.manager.UserManager.getInstance().saveProfil(connectedUser, "", "", "", "", "", String.valueOf(latitude), String.valueOf(longitude), "", null, null, null, null, null, null,null,password, NavigationMain.this);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                editor.putString(TAG_CONNECTED_USER_LATITUDE, String.valueOf(latitude));
                editor.putString(TAG_CONNECTED_USER_LONGETUDE, String.valueOf(longitude));
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            gps.showSettingsAlert();
        }
    }


    private void getListeOFTechToDisplayPhone() {

    }

    private void initiInterfaceActivity() {
        setupViewPager();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUserConnectedLocation();
            }
        });
        toolMission = findViewById(R.id.toolbar_mission);
        toolEchange = findViewById(R.id.toolbar_echange);
        toolAccount = findViewById(R.id.toolbar_account);
        toolBrowse = findViewById(R.id.toolbar_browse);


        setupToolbarAccount();

        appBarHome = findViewById(R.id.appbar_home);


        bottomNavigation = (AHBottomNavigation) findViewById(R.id.home_bottom_navigation);
        setupBottomNavBehaviors();
        setupBottomNavStyle();
        //createFakeNotification();
        addBottomNavigationItems();
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


    A3techMission selectedMission;
    Boolean isFromWelcom = Boolean.FALSE;

    private void getSelectedMission() {
        String jsonMyObject = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            jsonMyObject = b.getString(A3techAddMissionActivity.TAG_RESULT_OBJECT_ADD_MISSION_WELCOM);
            isFromWelcom = b.getBoolean(A3techAddMissionActivity.TAG_RESULT_FROM_ADD_MISSION_WELCOM);

        }
        if (jsonMyObject != null) {
            selectedMission = new Gson().fromJson(jsonMyObject, A3techMission.class);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    traitementDisplayMissionCreated();
                }
            });
        } else {
            hundleMessageFromWelcomPage();

        }


    }

    private void hundleMessageFromWelcomPage() {
        String typeAffichageFromWelcom = null;
        Bundle b = getIntent().getExtras();
        if (b != null) {
            isFromWelcom = b.getBoolean(A3techWelcomPageActivity.TAG_ADD_MISSION_FROM_WELCOM);
            typeAffichageFromWelcom = b.getString(A3techWelcomPageActivity.KEY_WELCOM_BROWSE_OR_ACCOUNT);
        }
        if (isFromWelcom && typeAffichageFromWelcom != null) {
            if (typeAffichageFromWelcom.equals(A3techWelcomPageActivity.TAG_WELCOM_ACCOUNT)) {
                bottomNavigation.setCurrentItem(3);
                updateAppbarLayout(3);
            } else if (typeAffichageFromWelcom.equals(A3techWelcomPageActivity.TAG_WELCOM_BROWSE)) {
                bottomNavigation.setCurrentItem(2);
                updateAppbarLayout(2);
            }
        } else {
            Log.i("KKKKKKKKKKKKKKKKKKKKKK", "initialisation fragment 0");
            bottomNavigation.setCurrentItem(0);
            updateAppbarLayout(0);
        }
    }


    private void traitementDisplayMissionCreated() {
        //TODO display progress bar, save mission,whene finish saving, dismiss progress bar

        /*((A3techMissionsHomeFragment) pagerAdapter.getItem(1)).addMissionToLise(selectedMission);*/
        //TODO create mission
        final ProgressDialog dialogWaiting = CustomProgressDialog.createProgressDialog(getActivity(), "");
        MissionManager.getInstance().createMission(selectedMission, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                //TODO commentaire a reconstituer.
                String commentaire = "Demande créée pour une Mission en  " + selectedMission.getCategoryMission().getLibelle() + "";
                A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                        null, selectedMission, A3techNotificationType.CREATION_MISSION, commentaire, getString(R.string.libelle_creatio_mission));
                NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.mission_cree), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
                            }
                        });
                        bottomNavigation.setCurrentItem(1);
                        updateAppbarLayout(1);
                        dialogWaiting.dismiss();
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {
                        dialogWaiting.dismiss();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
                            }
                        });
                    }
                });
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dialogWaiting.dismiss();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
                    }
                });
            }
        });
    }

    private void updateAppbarLayout(int position) {
        switch (position) {
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
        pagerAdapter.addFragments(A3techNotificationHomeFragment.newInstance(null, null));
        pagerAdapter.addFragments(A3techMissionsHomeFragment.newInstance(null, null));
        pagerAdapter.addFragments(A3techHomeBrowseTechFragment.newInstance(null, null));
        pagerAdapter.addFragments(A3techHomeAccountFragment.newInstance(null, null));
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(pagerAdapter);
    }

    /* @Override
     public void startActivity(Intent intent) {
         // check if search intent
         if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
             if (bottomNavigation.getCurrentItem() == 0) {
                 intent.putExtra(A3techSearchMissionsResultsActivity.TYPE_SRC_PARAM, A3techNotificationHomeFragment.THIS_FRAGMENT);
             } else if (bottomNavigation.getCurrentItem() == 2)  {
                 intent.putExtra(A3techSearchMissionsResultsActivity.TYPE_SRC_PARAM, A3techHomeBrowseTechFragment.THIS_FRAGMENT_2);
             }

         }
         super.startActivity(intent);
     }*/
    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            // handles a search query
            handleIntent(intent);
        }
    }

    /**
     * Avatar + action clique toolbar to display user profil.
     */
    private void setupToolbarAccount() {
        // Acount fragment toolbar init
        toolAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String connectedUser = prefs.getString("identifiant", "");
                String password = prefs.getString("password", "");

                mobile.a3tech.com.a3tech.manager.UserManager.getInstance().getProfil(connectedUser, password, new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        A3techUser user = (A3techUser) data;
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (connectedUser != null && StringUtils.isNoneBlank(connectedUser.getNom())) {
                    final LetterTileProvider tileProvider = new LetterTileProvider(A3techHomeActivity.this);
                    final Bitmap letterTile = tileProvider.getLetterTile(connectedUser.getNom(), connectedUser.getNom(), 155, 155);
                    userAvatar.setImageBitmap(letterTile);
                }

            }
        });

        String jsonUSer = PreferencesValuesUtils.getPreferenceStringByParam(A3techHomeActivity.this, PreferencesValuesUtils.KEY_CONNECTED_USER_GSON, "");
        if (StringUtils.isNoneBlank(jsonUSer)) {
            A3techUser connectedUSer = new Gson().fromJson(jsonUSer, A3techUser.class);
            if (connectedUSer != null) {
                if (StringUtils.isNoneBlank(connectedUSer.getId_photo_profil())) {
                    //getUSer Avatare
                    //TODO set avatare from user picture
                    //ImageManager.getInstance().getString()
                }
            }
        }
        nomPrenomUser = findViewById(R.id.user_name_pname);
        String nameConnectedUser = "";
        if (connectedUser != null && connectedUser.getNom() != null && connectedUser.getPrenom() != null) {
            nameConnectedUser = connectedUser.getNom() + " " + connectedUser.getPrenom().substring(0, 1) + ".";
        }
        nomPrenomUser.setText(nameConnectedUser);

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


    private void prepareElementToolbar() {

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


        if (bottomNavigation.getCurrentItem() == 1 || bottomNavigation.getCurrentItem() == 3) {
            searchItem.setVisible(false);
        } else {
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

    private void handleIntent(Intent intent) {
        // Get the intent, verify the action and get the query
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            // manually launch the real search activity
            final Intent searchIntent = new Intent(getApplicationContext(),
                    A3techSearchMissionsResultsActivity.class);
            // add query to the Intent Extras
            searchIntent.putExtra(SearchManager.QUERY, query);
            searchIntent.setAction(Intent.ACTION_SEARCH);
            if (bottomNavigation.getCurrentItem() == 0) {
                searchIntent.putExtra(A3techSearchMissionsResultsActivity.TYPE_SRC_PARAM, A3techNotificationHomeFragment.THIS_FRAGMENT);
            } else if (bottomNavigation.getCurrentItem() == 2) {
                searchIntent.putExtra(A3techSearchMissionsResultsActivity.TYPE_SRC_PARAM, A3techHomeBrowseTechFragment.THIS_FRAGMENT_2);
            }
            startActivityForResult(searchIntent, REQ_SEARCH);
        }
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
                categorieSelected = (Categorie) data;
                Intent mainIntent = new Intent(this, A3techDisplayTechniciensListeActivity.class);
                mainIntent.putExtra(A3techHomeActivity.TAG_CATEGORY_SELECTED_FROM_HOME_ACTIVITY, new Gson().toJson(categorieSelected));
                startActivityForResult(mainIntent, REQUEST_START_DISPLAY_TECH_ACTIVITY);
                break;

        }
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQ_SEARCH):
            case (REQUEST_START_DISPLAY_TECH_ACTIVITY): {
                if (resultCode == A3techDisplayTechniciensListeActivity.RESULT_RELOAD) {
                    //Reload
                    String cateSelected = data.getStringExtra(A3techDisplayTechniciensListeActivity.TAG_CATEGORY_FOR_RELOADING);
                    if (cateSelected != null) {
                        Categorie category = new Gson().fromJson(cateSelected, Categorie.class);
                        if (category != null) {
                            categorieSelected = category;
                            Intent mainIntent = new Intent(this, A3techDisplayTechniciensListeActivity.class);
                            mainIntent.putExtra(A3techHomeActivity.TAG_CATEGORY_SELECTED_FROM_HOME_ACTIVITY, new Gson().toJson(category));
                            startActivityForResult(mainIntent, REQUEST_START_DISPLAY_TECH_ACTIVITY);
                        }
                    }

                }
                if (resultCode == Activity.RESULT_OK) {
                    String jsonMission = data.getStringExtra(A3techDisplayTechniciensListeActivity.TAG_MISSION_TO_SAVE_FROM_BROWS_TECH);
                    final A3techMission mission = new Gson().fromJson(jsonMission, A3techMission.class);
                    //TODO display progress bar, save mission,whene finish saving, dismiss progress bar
                    bottomNavigation.setCurrentItem(1);
                    ((A3techMissionsHomeFragment) pagerAdapter.getItem(1)).addMissionToLise(mission);
                    //TODO create mission
                    final ProgressDialog dialogWaiting = CustomProgressDialog.createProgressDialog(getActivity(), "");
                    MissionManager.getInstance().createMission(mission, new DataLoadCallback() {
                        @Override
                        public void dataLoaded(Object data, int method, int typeOperation) {
                            //TODO commentaire a reconstituer.
                            String commentaire = "";
                            if (mission.getCategoryMission() != null) {
                                commentaire = "Demande créée pour une Mission en  " + mission.getCategoryMission().getLibelle() + "";
                            } else if (mission.getTechnicien().getCategorie() != null) {
                                commentaire = "Demande créée pour une Mission en  " + mission.getTechnicien().getCategorie().getLibelle() + "";
                            }
                            A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                                    null, mission, A3techNotificationType.CREATION_MISSION, commentaire, getString(R.string.libelle_creatio_mission));
                            NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
                                @Override
                                public void dataLoaded(Object data, int method, int typeOperation) {
                                    A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.mission_cree), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
                                    dialogWaiting.dismiss();
                                }

                                @Override
                                public void dataLoadingError(int errorCode) {
                                    dialogWaiting.dismiss();
                                    A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
                                }
                            });
                        }

                        @Override
                        public void dataLoadingError(int errorCode) {
                            dialogWaiting.dismiss();
                            A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
                        }
                    });
                    //A3techCustomToastDialog.createToastDialog(A3techHomeActivity.this, "Mission created with success", Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_WARNING);
                    //A3techCustomToastDialog.createToastDialog(A3techHomeActivity.this, "Mission created with success", Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_ERROR);
                }
                break;
            }
        }
    }

    @Override
    public void deconnexion() {
        Intent mainIntent = new Intent(A3techHomeActivity.this, A3techLoginActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private class InitActivityTask extends AsyncTask<Void, Void, Boolean> {
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
        }

        @Override
        protected Boolean doInBackground(Void... arg0) {
            initiInterfaceActivity();
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (pd != null) pd.dismiss();
            getSelectedMission();
        }
    }

    @Override
    public void onBackPressed() {
       /* if (bottomNavigation.getCurrentItem() == 0) {
            super.onBackPressed();
           // this.finishAffinity();
            finish();
        } else {
            bottomNavigation.setCurrentItem(0);
        }*/
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private Activity getActivity() {
        return A3techHomeActivity.this;
    }

}
