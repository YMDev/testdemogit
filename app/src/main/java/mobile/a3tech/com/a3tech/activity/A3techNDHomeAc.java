package mobile.a3tech.com.a3tech.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toolbar;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.fragment.A3techMissionsHomeFragment;
import mobile.a3tech.com.a3tech.view.MenuItem;

public class A3techNDHomeAc extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, A3techMissionsHomeFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a3tech_nd_home_v2);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        replaceServerFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_bt_server) {
            replaceServerFragment();
        } else if (id == R.id.menu_bt_client) {
            replaceClientFragment();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void replaceServerFragment() {
        setTitle("Bluetooth Server");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new A3techMissionsHomeFragment();
        ft.replace(R.id.content_main, fragment);
        ft.commit();
    }

    void replaceClientFragment() {
        setTitle("Bluetooth Client");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment fragment = new A3techMissionsHomeFragment();
        ft.replace(R.id.content_main, fragment);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull android.view.MenuItem item) {
        return false;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}