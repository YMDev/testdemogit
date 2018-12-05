package mobile.a3tech.com.a3tech.activity;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.content.res.AppCompatResources;
import android.view.View;
import android.view.ViewGroup;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techMissionsListeAdapter;
import mobile.a3tech.com.a3tech.adapter.A3techProfilFragmentAdapter;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;

public class A3techMissionListeActivity extends AppCompatActivity implements A3techMissionsHomeFragment.OnFragmentInteractionListener{



    private TabLayout tabLayouMissions;
    private ViewPager viewPagerMissions;
    private A3techMissionsListeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_mission_liste_activity);
        viewPagerMissions = (ViewPager) findViewById(R.id.viewpager_missions);
        tabLayouMissions = (TabLayout) findViewById(R.id.tabs_mission);
        setTabBG(R.drawable.tab_left_select, R.drawable.tab_right_unselect);
        adapter = new A3techMissionsListeAdapter(getSupportFragmentManager(),
                A3techMissionListeActivity.this);
        viewPagerMissions.setAdapter(adapter);

        tabLayouMissions.post(new Runnable() {
            @Override
            public void run() {
                tabLayouMissions.setupWithViewPager(viewPagerMissions);
            }
        });
        tabLayouMissions.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerMissions.setCurrentItem(tab.getPosition());
                if (tabLayouMissions.getSelectedTabPosition() == 0) {
                    setTabBG(R.drawable.tab_left_select, R.drawable.tab_right_unselect);
                } else {
                    setTabBG(R.drawable.tab_left_unselect, R.drawable.tab_right_select);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /*setfragment(a3techmissionshomefragment.newinstance(null,null));*/
    }

    /*protected void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.frame_liste_missions, fragment);
        t.addToBackStack(fragment.getClass().getName());
        t.commit();
    }
*/
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setTabBG(int tab1, int tab2) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ViewGroup tabStrip = (ViewGroup) tabLayouMissions.getChildAt(0);
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

}
