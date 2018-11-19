package mobile.a3tech.com.a3tech.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gauravbhola.ripplepulsebackground.RipplePulseLayout;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techWelcomHomeFragment;

public class A3techWelcomPageActivity extends BaseActivity implements A3techWelcomHomeFragment.OnFragmentInteractionListener{

    public static final String TAG_ADD_MISSION_FROM_WELCOM = "TAG_ADD_MISSION_FROM_WELCOM";
    public static final int REQ_ADD_MISSION_FROM_WELCOM = 9175;
    public static final String TAG_WELCOM_ACCOUNT = "TAG_WELCOM_ACCOUNT";
    public static final String TAG_WELCOM_BROWSE = "TAG_WELCOM_BROWSE";
    public static final String KEY_WELCOM_BROWSE_OR_ACCOUNT = "KEY_WELCOM_BROWSE_OR_ACCOUNT";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_welcom_page_activity);
        setFragment(A3techWelcomHomeFragment.newInstance(null, null));
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
         RipplePulseLayout mPulsator = (RipplePulseLayout ) findViewById(R.id.layout_ripplepulse);
        mPulsator.startRippleAnimation();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void startAddMission() {
        Intent intentAddMissionn  = new Intent(A3techWelcomPageActivity.this, A3techAddMissionActivity.class);
        intentAddMissionn.putExtra(TAG_ADD_MISSION_FROM_WELCOM,Boolean.TRUE);
        startActivity(intentAddMissionn);
    }

    @Override
    public void startBrowse() {
        Intent intentAddMissionn  = new Intent(A3techWelcomPageActivity.this, A3techHomeActivity.class);
        intentAddMissionn.putExtra(TAG_ADD_MISSION_FROM_WELCOM,Boolean.TRUE);
        intentAddMissionn.putExtra(KEY_WELCOM_BROWSE_OR_ACCOUNT,TAG_WELCOM_BROWSE);
        startActivity(intentAddMissionn);
    }

    @Override
    public void startAccount() {
        Intent intentAddMissionn  = new Intent(A3techWelcomPageActivity.this, A3techHomeActivity.class);
        intentAddMissionn.putExtra(TAG_ADD_MISSION_FROM_WELCOM,Boolean.TRUE);
        intentAddMissionn.putExtra(KEY_WELCOM_BROWSE_OR_ACCOUNT,TAG_WELCOM_ACCOUNT);
        startActivityForResult(intentAddMissionn,REQ_ADD_MISSION_FROM_WELCOM);
    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
            this.finishAffinity();
    }
}
