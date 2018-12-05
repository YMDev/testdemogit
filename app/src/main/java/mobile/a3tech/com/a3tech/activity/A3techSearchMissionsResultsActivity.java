package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eltos.simpledialogfragment.SimpleDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techHomeBrowseTechFragment;
import mobile.a3tech.com.a3tech.fragment.A3techNotificationHomeFragment;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.SimpleAdapterNotifications;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class A3techSearchMissionsResultsActivity extends BaseActivity {


    public static final String TYPE_SRC_PARAM = "SRC_ACTION_SEARCH";
    public static final String ARG_MISSION_TECH_SELECTED_FROM_SEARCH = "ARG_MISSION_TECH_SELECTED_FROM_SEARCH";
    public static final String TAG_FROM_SEARCH = "SEARCH_ACT";
    public static final int REQUEST_DISPLAY_TECH_FROM_SEARCH = 3219;

    String keyWord = "";
    String premium = "";

    int distance = -1;
    int limit = 10;
    private static final int PAGE_SIZE = 20;
    int start = 0;
    int end = PAGE_SIZE - 1;

    A3techMission missionSelected;
    Boolean isFromBrowseTech = Boolean.FALSE;
    Boolean isFromEvenements = Boolean.FALSE;

    @BindView(R.id.recycle_techniciens_results_container)
    RelativeLayout containerTechniciens;

    @BindView(R.id.recycle_evenets_results_container)
    RelativeLayout containerEvents;

    @BindView(R.id.recycle_results_techniciens)
    RecyclerView recycleResultsTechniciens;
    @BindView(R.id.recycle_results_revents)
    RecyclerView recycleResultsEvents;

    @BindView(R.id.toolbar_display_search_results)
    Toolbar toolbarResults;

    @BindView(R.id.big_title)
    TextView titleToolbarResults;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_search_results_activity);
        ButterKnife.bind(this);
        handleIntent(getIntent());
        if (!PermissionsStuffs.canAccessLocation(getActivity())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PermissionsStuffs.INITIAL_PERMS, PermissionsStuffs.INITIAL_REQUEST);
            }
        }else
        gpsGetLocation();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            String sourceAction = intent.getStringExtra(TYPE_SRC_PARAM);
            if (sourceAction != null) {
                if (sourceAction.equals(A3techHomeBrowseTechFragment.THIS_FRAGMENT_2)) {
                    isFromBrowseTech = Boolean.TRUE;
                } else if (sourceAction.equals(A3techNotificationHomeFragment.THIS_FRAGMENT)) {
                    isFromEvenements = Boolean.TRUE;
                }
                displayRecycleResults(query);
            }

        }
    }

    private void displayRecycleResults(String query) {
        if (StringUtils.isNoneBlank(query)) {
            titleToolbarResults.setText(query);
            if (isFromEvenements) {
                containerEvents.setVisibility(View.VISIBLE);
                containerTechniciens.setVisibility(View.GONE);

                // search EventsMission
                searchMissionEvents(query);
            } else if (isFromBrowseTech) {
                containerEvents.setVisibility(View.GONE);
                containerTechniciens.setVisibility(View.VISIBLE);

                searchTechniciens(query);
            }
        }

    }


    private void searchMissionEvents(String query) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(A3techSearchMissionsResultsActivity.this);
        String connectedUser = prefs.getString("identifiant", "");
        String password = prefs.getString("password", "");
        String lang = prefs.getString("ApplicationLanguage",
                Constant.LANGUAGE_FRENSH);

        final ProgressDialog dd = CustomProgressDialog.createProgressDialog(A3techSearchMissionsResultsActivity.this, "");
        MissionManager.getInstance().filtreMission(lang, connectedUser,
                query, "", "",
                Constant.LOAD_DATA_FINISH, String.valueOf(0),
                String.valueOf(limit), connectedUser, null, premium, password, 0, 0,
                new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        List<A3techMission> listeRetour = (List<A3techMission>) data;
                        SimpleAdapterNotifications adapter = new SimpleAdapterNotifications(getActivity(), listeRetour, (A3techSearchMissionsResultsActivity) getActivity());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                        recycleResultsEvents.setLayoutManager(mLayoutManager);
                        recycleResultsEvents.setItemAnimator(new DefaultItemAnimator());
                        recycleResultsEvents.setAdapter(adapter);
                        dd.dismiss();
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {
                        dd.dismiss();
                    }
                });

    }

    SimpleAdapterTechnicien adapter = null;

    List listeTechToDisplay = new ArrayList();
    GPSTracker gps;
    Double userLatitude;
    Double userLongetude;
    private void gpsGetLocation() {
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            userLatitude = gps.getLatitude();
            userLongetude = gps.getLongitude();
            /*float zoomLevel = 12.0f; //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLatitude, userLongetude), zoomLevel));*/
        } else {
            gps.showSettingsAlert();
            userLatitude = 0d;
            userLongetude = 0d;
        }
    }
    private void searchTechniciens(final String query) {
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycleResultsTechniciens.setLayoutManager(mLayoutManager);
        adapter = new SimpleAdapterTechnicien(getActivity(), listeTechToDisplay, getActivity(), new A3techMission(), recycleResultsTechniciens);
        gpsGetLocation();
        adapter.setLongitude(userLongetude);
        adapter.setLatitude(userLatitude);
        recycleResultsTechniciens.setItemAnimator(new DefaultItemAnimator());
        recycleResultsTechniciens.setAdapter(adapter);

        // load the first page of users (PAGE_SIZE to controle number of users to load)
        //getListOFTechToDisplay(query, start, end, CustomProgressDialog.createProgressDialog(getActivity(), ""));
        adapter.setOnLoadMoreListener(new SimpleAdapterTechnicien.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                /*   adapter.notifyItemInserted(adapter.getListObject().size() - 1);*/
                // notify adapter to display progress bar
                recycleResultsTechniciens.post(new Runnable() {
                    public void run() {
                        adapter.getListObject().add(null);
                        adapter.notifyItemInserted(adapter.getListObject().size() - 1);
                        // load the next page
                        getListOFTechToDisplay(query, start, end, null);
                        start = start + PAGE_SIZE;
                        end = start + PAGE_SIZE - 1;

                    }
                });


            }
        });
    }

    private void getListOFTechToDisplay(final String query, final int start, final int end, final ProgressDialog dd) {


        UserManager.getInstance().fetchTechnicien(query, start, end, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                List<A3techUser> reslisteOfTechToDisplay = (List<A3techUser>) data;
                //whene loading next page, delete progress bar
                if (dd == null) {
                    adapter.getListObject().remove(adapter.getListObject().size() - 1);
                    adapter.notifyItemRemoved(adapter.getListObject().size());

                }

                // adding each element to recycle
                if (reslisteOfTechToDisplay != null && reslisteOfTechToDisplay.size() != 0) {
                    for (A3techUser userTmp : reslisteOfTechToDisplay
                            ) {
                        if (userTmp != null) {
                            adapter.getListObject().add(userTmp);
                            adapter.notifyItemInserted(adapter.getListObject().size());
                        }

                       /* recycleResultsTechniciens.post(new Runnable() {
                            public void run() {
                                adapter.getListObject().remove(adapter.getListObject().size() - 1);
                                adapter.notifyItemRemoved(adapter.getListObject().size());
                            }
                        });*/
                    }
                }
                adapter.setLoaded();

               /* getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });*/
                if (dd != null) {
                    dd.dismiss();
                }
                return;
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if (dd != null) dd.dismiss();
                if (dd == null) {
                    adapter.getListObject().remove(adapter.getListObject().size() - 1);
                    adapter.notifyItemRemoved(adapter.getListObject().size());
                }
            }
        });
    }



    public A3techSearchMissionsResultsActivity() {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (SimpleAdapterTechnicien.REQUEST_DISPLAY_TECH_FROM_HOME): {
                if (resultCode == Activity.RESULT_OK) {
                    String jsonMission = data.getStringExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH);
                    // mission avec le technicien comme attribut
                    missionSelected = new Gson().fromJson(jsonMission, A3techMission.class);
                    // display Display tech Liste Activity and show next Step
                    // nextStepAfterSelectTechnicien(missionSelected);
                    Intent mainIntent = new Intent(A3techSearchMissionsResultsActivity.this, A3techDisplayTechniciensListeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(ARG_MISSION_TECH_SELECTED_FROM_SEARCH, new Gson().toJson(missionSelected));
                    mainIntent.putExtras(bundle);
                    startActivityForResult(mainIntent, REQUEST_DISPLAY_TECH_FROM_SEARCH);
                }
                break;
            }

            case (REQUEST_DISPLAY_TECH_FROM_SEARCH): {
                if (data != null) {
                    String jsonMission = data.getStringExtra(A3techDisplayTechniciensListeActivity.TAG_MISSION_TO_SAVE_FROM_BROWS_TECH);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(A3techDisplayTechniciensListeActivity.TAG_MISSION_TO_SAVE_FROM_BROWS_TECH, jsonMission);
                    resultIntent.putExtra(TAG_FROM_SEARCH, Boolean.TRUE);
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                }
                break;
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case PermissionsStuffs.INITIAL_REQUEST:
                if (PermissionsStuffs.canAccessLocation(getActivity())) {
                    ///doCameraThing();
                    Intent in = getActivity().getIntent();
                    getActivity().finish();
                    getActivity().startActivity(in);
                } else {
                    /*SimpleDialog.build().title("Autorisation location est nécessaire").msg("pour afficher la liste des techniciens sur Map, il faut autoriser l'app à accéder à votre position").show(gets,"TAG");*/
                }
                break;


        }
    }
}
