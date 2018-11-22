package mobile.a3tech.com.a3tech.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techWelcomHomeFragment;
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

        installListener();
        networkDown.setVisibility(View.GONE);
        networkOn.setVisibility(View.VISIBLE);

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
        if(mPulsator != null) mPulsator.startRippleAnimation();
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
        startActivity(intentAddMissionn);
    }

    @Override
    public void startBrowse() {
        Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techHomeActivity.class);
        intentAddMissionn.putExtra(TAG_ADD_MISSION_FROM_WELCOM, Boolean.TRUE);
        intentAddMissionn.putExtra(KEY_WELCOM_BROWSE_OR_ACCOUNT, TAG_WELCOM_BROWSE);
        startActivity(intentAddMissionn);
    }

    @Override
    public void startAccount() {
        Intent intentAddMissionn = new Intent(A3techWelcomPageActivity.this, A3techHomeActivity.class);
        intentAddMissionn.putExtra(TAG_ADD_MISSION_FROM_WELCOM, Boolean.TRUE);
        intentAddMissionn.putExtra(KEY_WELCOM_BROWSE_OR_ACCOUNT, TAG_WELCOM_ACCOUNT);
        startActivityForResult(intentAddMissionn, REQ_ADD_MISSION_FROM_WELCOM);
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
                        A3techCustomToastDialog.createToastDialog(A3techWelcomPageActivity.this, getString(R.string.network_error_text), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
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

}
