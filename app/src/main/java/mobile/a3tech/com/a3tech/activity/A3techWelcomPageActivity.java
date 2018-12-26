package mobile.a3tech.com.a3tech.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techWelcomHomeFragment;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.utils.ValidationPatternUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;

public class A3techWelcomPageActivity extends BaseActivity implements A3techWelcomHomeFragment.OnFragmentInteractionListener {

    public static final String TAG_ADD_MISSION_FROM_WELCOM = "TAG_ADD_MISSION_FROM_WELCOM";
    public static final int REQ_ADD_MISSION_FROM_WELCOM = 9175;
    public static final String TAG_WELCOM_ACCOUNT = "TAG_WELCOM_ACCOUNT";
    public static final String TAG_WELCOM_BROWSE = "TAG_WELCOM_BROWSE";
    public static final String KEY_WELCOM_BROWSE_OR_ACCOUNT = "KEY_WELCOM_BROWSE_OR_ACCOUNT";
    RelativeLayout networkOn, networkDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_welcom_page_activity);
        networkDown = findViewById(R.id.network_down);
        networkOn = findViewById(R.id.network_on);
        saveUserNewLocation();
        onNetworkUp();
        /*installListener();*/
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(A3techWelcomPageActivity.this, new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e("KKKKKKKKK : newToken", newToken);
                A3techUser connectedUser = PreferencesValuesUtils.getConnectedUser(A3techWelcomPageActivity.this);
                if (connectedUser != null && connectedUser.getId() != null) {
                    connectedUser.setFcmId(newToken);
                    UserManager.getInstance().updateUser(connectedUser, new DataLoadCallback() {
                        @Override
                        public void dataLoaded(Object data, int method, int typeOperation) {
                            A3techUser userAjour = (A3techUser)data;
                            PreferencesValuesUtils.setConnectedUser(A3techWelcomPageActivity.this,userAjour);
                        }

                        @Override
                        public void dataLoadingError(int errorCode) {

                        }
                    });
                }

            }
        });
    }

    protected void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.fragment_frame, fragment);
        t.addToBackStack(fragment.getClass().getName());
        t.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        RipplePulseLayout mPulsator = (RipplePulseLayout) findViewById(R.id.layout_ripplepulse);
        if (mPulsator != null) mPulsator.startRippleAnimation();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    public void startAddMission() {
        Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techAddMissionActivity.class);
        intentAddMissionn.putExtra(TAG_ADD_MISSION_FROM_WELCOM, Boolean.TRUE);
        startActivityForResult(intentAddMissionn, REQ_ADD_MISSION_FROM_WELCOM);
    }

    @Override
    public void startBrowse() {
        Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techMissionListeActivity.class);
        startActivity(intentAddMissionn);
    }

    @Override
    public void startAccount() {
        Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techAccountActivity.class);
        startActivity(intentAddMissionn);
    }

    @Override
    public void startBrowseEents() {
        Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techMissionTimeLineActivity.class);
        startActivity(intentAddMissionn);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }


    BroadcastReceiver broadcastReceiver;

    private void installListener() {

        if (broadcastReceiver == null) {

            broadcastReceiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {

                    Bundle extras = intent.getExtras();

                    NetworkInfo info = (NetworkInfo) extras
                            .getParcelable("networkInfo");

                    NetworkInfo.State state = info.getState();
                    Log.d("KKKKKKKK NET", info.toString() + " "
                            + state.toString());

                    if (state == NetworkInfo.State.CONNECTED) {
                        onNetworkUp();
                        /* A3techCustomToastDialog.createToastDialog(A3techHomeActivity.this, getString(R.string.connexion_retablie), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_INFO);*/

                    } else {
                        A3techCustomToastDialog.createSnackBar(A3techWelcomPageActivity.this, getString(R.string.network_error_text), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                        onNetworkDown();
                    }

                }
            };

            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(broadcastReceiver, intentFilter);
        }
    }

    private void onNetworkDown() {
        networkDown.setVisibility(View.VISIBLE);
        networkOn.setVisibility(View.GONE);

    }

    private void onNetworkUp() {
        networkDown.setVisibility(View.GONE);
        networkOn.setVisibility(View.VISIBLE);
        setFragment(A3techWelcomHomeFragment.newInstance(null, null));
    }

    GPSTracker gps;

    private void saveUserNewLocation() {

        if (!PermissionsStuffs.canAccessLocation(getActivity())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PermissionsStuffs.INITIAL_PERMS, PermissionsStuffs.INITIAL_REQUEST);
            }
        } else {
            gps = new GPSTracker(A3techWelcomPageActivity.this);
            if (gps.canGetLocation()) {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                try {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    String userConnectedMail = prefs.getString("identifiant", "");
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
                    editor.putString(A3techHomeActivity.TAG_CONNECTED_USER_LATITUDE, String.valueOf(latitude));
                    editor.putString(A3techHomeActivity.TAG_CONNECTED_USER_LONGETUDE, String.valueOf(longitude));
                    editor.commit();
                    UserManager.getInstance().saveUserNewLocation(String.valueOf(latitude), String.valueOf(longitude), userConnectedMail, new DataLoadCallback() {
                        @Override
                        public void dataLoaded(Object data, int method, int typeOperation) {
                            //OK
                        }

                        @Override
                        public void dataLoadingError(int errorCode) {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                gps.showSettingsAlert();
            }
        }


    }

    public static String KEY_IS_AFTER_ADD_MISSION = "ISAFTERADDMISSION";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ADD_MISSION_FROM_WELCOM && resultCode == Activity.RESULT_OK) {
            Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techMissionListeActivity.class);
            intentAddMissionn.putExtra(KEY_IS_AFTER_ADD_MISSION, true);
            startActivity(intentAddMissionn);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case PermissionsStuffs.LOCATION_REQUEST:
                if (PermissionsStuffs.canAccessLocation(getActivity())) {
                    saveUserNewLocation();
                } else {
                    // bzzzt();
                }
                break;
        }
    }
}
