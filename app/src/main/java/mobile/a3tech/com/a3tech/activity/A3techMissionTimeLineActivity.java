package mobile.a3tech.com.a3tech.activity;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techHomeAccountFragment;
import mobile.a3tech.com.a3tech.fragment.A3techNotificationHomeFragment;

public class A3techMissionTimeLineActivity extends BaseActivity implements A3techNotificationHomeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_mission_time_line_activity);
        setFragment(A3techNotificationHomeFragment.newInstance(null, null));
    }

    protected void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_time_line, fragment);
        t.addToBackStack(fragment.getClass().getName());
        t.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
