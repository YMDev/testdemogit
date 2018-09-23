package mobile.a3tech.com.a3tech.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gcm.GCMRegistrar;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions.Builder;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.NavigationAdapter;
import mobile.a3tech.com.a3tech.data.NavigationList;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.Menus;
import mobile.a3tech.com.a3tech.utils.Title;
import mobile.a3tech.com.a3tech.view.CircleImageView;

public class NavigationMain extends AppCompatActivity implements DataLoadCallback {
    public static Boolean inBackground  = Boolean.valueOf(true);
    CircleImageView ImgDrawer;
    String connectedUser;
    String password ;
    private int counterItemDownloads;
    private ActionBarDrawerToggleCompat drawerToggle;
    private EvaluationViewPagerFragment evaluationFragment;
    private AssociationsFragment associationsFragment;
    String idMissionNotif = "";
    String idOffreNotif = "";
    String isMessageNotif = "" ;
    
    ImageLoader imageLoader;
    private int lastPosition = 5;
    private DrawerLayout layoutDrawer;
    private LinearLayout linearDrawer;
    private ListView listDrawer;
    
    LocationManager locationManager;
    private GCMReceiver mGCMReceiver;
    private IntentFilter mOnRegisteredFilter;
    private MesServiceViewPagerFragment mesServiceViewPagerFragment;
    private NavigationAdapter navigationAdapter;
    String nbr;
    String nomPrenom;
   
    DisplayImageOptions options = new Builder().cacheInMemory().cacheOnDisc().build();
    private ParametrageFragment parametrageFragment;
   
    
    TextView tituloDrawer;
    private RelativeLayout userDrawer;
    String userIdNotif = "";
    
    private ServicesFragment xListViewActivity;
    private ServicesFragment xListViewActivity1;
    public  AlertDialog alertDialog;

    public static NavigationMain instance ;
    
    GPSTracker gps;
    
    private LocationListener locationListener =new  LocationListener() {
        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }

        public void onProviderEnabled(String arg0) {
        }

        public void onProviderDisabled(String arg0) {
        }

        public void onLocationChanged(Location location) {
            UserManager.getInstance().saveProfil(connectedUser, "", "", "", "", "", String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), "", null, null, null, null, null, null,null,password, NavigationMain.this);
        }
    };

   
    private OnClickListener userOnClick =new OnClickListener() {   
        public void onClick(View v) {
            layoutDrawer.closeDrawer(linearDrawer);
        }
    };

    private class DrawerItemClickListener implements OnItemClickListener {
        private DrawerItemClickListener() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
            setLastPosition(posicao);
            setFragmentList(lastPosition);
            layoutDrawer.closeDrawer(linearDrawer);
        }
    }

    private class GCMReceiver extends BroadcastReceiver {
        private GCMReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            UserManager.getInstance().saveProfil(connectedUser, "", "", "", "", "", "", "", intent.getStringExtra(Constant.FIELD_REGISTRATION_ID), null, null, null, null, null, null,null,password, NavigationMain.this);
        }
    }

    private class ActionBarDrawerToggleCompat extends ActionBarDrawerToggle {
        public ActionBarDrawerToggleCompat(Activity mActivity, DrawerLayout mDrawerLayout) {
            super(mActivity, mDrawerLayout, R.drawable.ic_action_navigation_drawer, R.string.drawer_open, R.string.drawer_close);
        }

        public void onDrawerClosed(View view) {
            supportInvalidateOptionsMenu();
        }

        public void onDrawerOpened(View drawerView) {
            navigationAdapter.notifyDataSetChanged();
            supportInvalidateOptionsMenu();
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this ;
        getSupportActionBar().setIcon((int) R.drawable.ic_launcher);
        setContentView((int) R.layout.navigation_main);
        ImgDrawer = (CircleImageView) findViewById(R.id.ImgDrawer);
        tituloDrawer = (TextView) findViewById(R.id.tituloDrawer);
        imageLoader = ImageLoader.getInstance();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        nomPrenom = getIntent().getStringExtra("nomPrenom");
        nbr = getIntent().getStringExtra("nbr");
        tituloDrawer.setText(nomPrenom);
        idMissionNotif = getIntent().getStringExtra("idMissionNotif");
        idOffreNotif = getIntent().getStringExtra("idOffreNotif");
        userIdNotif = getIntent().getStringExtra("userIdNotif");
        isMessageNotif = getIntent().getStringExtra("isMessageNotif");
        connectedUser = prefs.getString("identifiant", "");
        password = prefs.getString("password", "");
        imageLoader.displayImage(new StringBuilder(Constant.CHECK_EDU_PICTURES_URL).append(connectedUser).append(".jpg").toString(), ImgDrawer, options);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        listDrawer = (ListView) findViewById(R.id.listDrawer);
        linearDrawer = (LinearLayout) findViewById(R.id.linearDrawer);
        layoutDrawer = (DrawerLayout) findViewById(R.id.layoutDrawer);
        userDrawer = (RelativeLayout) findViewById(R.id.userDrawer);
        userDrawer.setOnClickListener(userOnClick);
        if (listDrawer != null) {
            navigationAdapter = NavigationList.getNavigationAdapter(this);
        }
        listDrawer.setAdapter(navigationAdapter);
        try{
        	navigationAdapter.updateItem(5, Integer.parseInt(nbr));
        }catch(Exception e){
        	navigationAdapter.updateItem(5, 0);
        }
        
        
        gps = new GPSTracker(NavigationMain.this);
        if(gps.canGetLocation()){             
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            try {
            	  UserManager.getInstance().saveProfil(connectedUser, "", "", "", "", "", String.valueOf(latitude), String.valueOf(longitude), "", null, null, null, null, null, null,null,password, NavigationMain.this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            gps.showSettingsAlert();
        }
           
        
        
        listDrawer.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggleCompat(this, layoutDrawer);
        layoutDrawer.setDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        locationManager = (LocationManager) getSystemService("location");
        Criteria myCriteria = new Criteria();
        myCriteria.setAccuracy(1);
        try{
            locationManager.requestLocationUpdates(locationManager.getBestProvider(myCriteria, true), 0, 2000.0f, locationListener);
        }catch (Exception e){
            e.printStackTrace();
        }
        
        mGCMReceiver = new GCMReceiver();
        mOnRegisteredFilter = new IntentFilter();
        mOnRegisteredFilter.addAction(Constant.ACTION_ON_REGISTERED);
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals("")) {
            GCMRegistrar.register(this, Constant.SENDER_ID);
        } else {
            UserManager.getInstance().saveProfil(connectedUser, "", "", "", "", "", "", "", regId, null, null, null, null, null, null,null,password, this);
        }
        if (savedInstanceState != null) {
            setLastPosition(savedInstanceState.getInt(Constant.LAST_POSITION));
            getInstanceFragments(savedInstanceState, lastPosition);
            return;
        }
        setLastPosition(lastPosition);
        setFragmentList(lastPosition);
    }
    
    
  


    public void onPause() {
        super.onPause();
        unregisterReceiver(mGCMReceiver);
    }

    protected void onStop() {
        super.onStop();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
        if (requestCode == 1 && resultCode == -1 && data.getStringExtra("result").equals("OK")) {
        	 FragmentManager fragmentManager = getSupportFragmentManager();
        	 fragmentManager
				.beginTransaction()
				.replace(R.id.content_frame,
						xListViewActivity = new ServicesFragment()).commitAllowingStateLoss();
        	
            
        }
     
        if (requestCode == 100) {
            getSupportFragmentManager().findFragmentById(R.id.content_frame).onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == 101) {
            getSupportFragmentManager().findFragmentById(R.id.content_frame).onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
        if (requestCode == Constant.SPLASH_FACEBOOK_REQUEST_CODE_LOGOUT) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constant.LAST_POSITION, lastPosition);
        setInstanceFragments(outState, lastPosition);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case Menus.HOME /*16908332*/:
                if (layoutDrawer.isDrawerOpen(linearDrawer)) {
                    layoutDrawer.closeDrawer(linearDrawer);
                    return true;
                }
                layoutDrawer.openDrawer(linearDrawer);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        hideMenus(menu, lastPosition);
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
        inBackground = Boolean.valueOf(true);
    }

    protected void onResume() {
        inBackground = Boolean.valueOf(false);
        registerReceiver(mGCMReceiver, mOnRegisteredFilter);
        if (!(idMissionNotif == null || idMissionNotif.equals(""))) {
            Intent mainIntent = new Intent(this, DetailServiceViewPagerFragment.class);
            Bundle bundle = new Bundle();
            bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID, userIdNotif);
            bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_OFFRE_ID, idOffreNotif);
            bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_ID, idMissionNotif);
            bundle.putString(Constant.KEY_EXTRA_DETAIL_MESSAGE_ID, isMessageNotif);
            idMissionNotif = "";
            mainIntent.putExtras(bundle);
            startActivityForResult(mainIntent, 101);
          
			
        }
        super.onResume();
    }

    public void setTitleActionBar(CharSequence informacao) {
        getSupportActionBar().setTitle(informacao);
    }

    public void setSubtitleActionBar(CharSequence informacao) {
        getSupportActionBar().setSubtitle(informacao);
    }

    public void setIconActionBar(int icon) {
        getSupportActionBar().setIcon(icon);
    }

    public void setLastPosition(int posicao) {
        lastPosition = posicao;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    public void onBackPressed() {
        if (lastPosition != 5) {
            setLastPosition(5);
            setFragmentList(lastPosition);
            return;
        }
        imageLoader.clearMemoryCache();
        imageLoader.clearDiscCache();
        finish();
        //super.onBackPressed();
    }
    


    private void setFragmentList(int posicao) {
        FragmentManager fragmentManager = getSupportFragmentManager();
      
        switch (posicao) {
        	case 0 /*mon compte*/:
        		break;
            case 1 /*1 mon profil*/:
            	if(connectedUser.equals("")){
            		showConnect();
            	}else{
	            	fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame,
							parametrageFragment = new ParametrageFragment()).commit();
            	}
               
               
                break;
           
            case 2 /*3 mes taches*/:
            	if(connectedUser.equals("")){
            		showConnect();
            	}else{
	            	fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame,
							mesServiceViewPagerFragment = new MesServiceViewPagerFragment()).commit();
            	}
               
                break;
            case 3 /*4 mes evaluations*/:
            	if(connectedUser.equals("")){
            		showConnect();
            	}else{
	            	fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame,
							evaluationFragment = new EvaluationViewPagerFragment()).commit();
            	}
                break;
          
            case 4 /*Mes recherches*/:
        		break;
            case 5 /*7 liste des echanges*/:
            	xListViewActivity = new ServicesFragment();
            	Bundle bundle = new Bundle();
                bundle.putString(Constant.TYPE_TRANSACTION, String.valueOf(Constant.TRANSACTION_TROC));
            	xListViewActivity.setArguments(bundle);
            	fragmentManager
				.beginTransaction()
				.replace(R.id.content_frame,
						xListViewActivity).commit();
                break;
            case 6 /*8 voeux associatifs*/:
            	if(connectedUser.equals("")){
            		showConnect();
            	}else{
	            	xListViewActivity1 = new ServicesFragment();
	            	Bundle bdle = new Bundle();
	            	bdle.putString(Constant.TYPE_TRANSACTION, String.valueOf(Constant.TRANSACTION_DON));
	            	xListViewActivity1.setArguments(bdle);
	            	fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame,
							xListViewActivity1).commit();
            	}
                break;
            case 7 /*ASSOCIATION*/:

                
                break;
            case 8 /*10 annuaire*/:
            	if(connectedUser.equals("")){
            		showConnect();
            	}else{
	            	fragmentManager
					.beginTransaction()
					.replace(R.id.content_frame,
							associationsFragment = new AssociationsFragment()).commit();
            	}
            	
            
                break;
        }
        navigationAdapter.resetarCheck();
        setTitleFragments(lastPosition);
        navigationAdapter.setChecked(posicao, true);
    }
    
    private void showConnect(){
    	  AlertDialog.Builder builder = new AlertDialog.Builder(NavigationMain.this);
          View content = getLayoutInflater().inflate(R.layout.message_dialog, null);
          LinearLayout idMessageDialog_linearLayoutAnnuler = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutAnnuler);
          LinearLayout idMessageDialog_linearLayoutValider = (LinearLayout) content.findViewById(R.id.idMessageDialog_linearLayoutValider);
          ((TextView) content.findViewById(R.id.idMessageDialog_textView)).setText(R.string.txtDetailSrvc_textAlertTitleConnectApp);
          idMessageDialog_linearLayoutValider.setOnClickListener(alertValiderConnetctListener);
          idMessageDialog_linearLayoutAnnuler.setOnClickListener(alertAnnulerListener);
          builder.setTitle(getString(R.string.txtDetailSrvc_textAlertTitleAppConnectApp));
          builder.setView(content);
          alertDialog = builder.create();
          alertDialog.show();
    }
 
    private OnClickListener alertValiderConnetctListener = new OnClickListener() {
        public void onClick(View v) {
            alertDialog.dismiss();
            Intent mainIntent = new Intent(NavigationMain.this, LoginActivity.class);

			startActivity(mainIntent);
			finish();
        }
    };
    
    private OnClickListener alertAnnulerListener = new OnClickListener() {
        public void onClick(View v) {
            alertDialog.dismiss();
        }
    };

    private void hideMenus(Menu menu, int posicao) {
        boolean drawerOpen = layoutDrawer.isDrawerOpen(linearDrawer);
        switch (posicao) {
        	case 5:
            case 6:
            	if(getSupportFragmentManager().findFragmentById(R.id.content_frame) instanceof ServicesFragment){
            		
	            	menu.findItem(Menus.ADD).setVisible(!drawerOpen);
	    			menu.findItem(Menus.SEARCH).setVisible(!drawerOpen);
	    			menu.findItem(Menus.SSEARCH).setVisible(!drawerOpen);
	    			menu.findItem(Menus.REFRESH).setVisible(!drawerOpen);
	    			
	    			menu.findItem(Menus.MORE).setVisible(!drawerOpen);
	    			menu.findItem(Menus.LIST).setVisible(false);
            	}else{
            		menu.findItem(Menus.LIST).setVisible(!drawerOpen);
            		menu.findItem(Menus.ADD).setVisible(false);
            		menu.findItem(Menus.SEARCH).setVisible(false);
	    			menu.findItem(Menus.SSEARCH).setVisible(false);
	    			menu.findItem(Menus.REFRESH).setVisible(false);
	    			
	    			menu.findItem(Menus.MORE).setVisible(false);
            	}
    			break;
        }
    }

    private void setInstanceFragments(Bundle savedInstanceState, int posicao) {
        FragmentManager manager = getSupportFragmentManager();
        switch (posicao) {
            case 1 /*1*/:
                manager.putFragment(savedInstanceState, Constant.FRAGMENT_PROFIL, parametrageFragment);
                break;
           
            case 2 /*3*/:
                manager.putFragment(savedInstanceState, Constant.FRAGMENT_MES_SERVICES, mesServiceViewPagerFragment);
                break;
            case 3 /*4*/:
                manager.putFragment(savedInstanceState, Constant.FRAGMENT_EVALUATION, evaluationFragment);
                break;
          
            case 5 /*7*/:
                manager.putFragment(savedInstanceState, Constant.FRAGMENT_SERVICES, xListViewActivity);
                break;
            case 6 /*7*/:
                manager.putFragment(savedInstanceState, Constant.FRAGMENT_DONS, xListViewActivity1);
                break;
            case 8 /*10*/:
                manager.putFragment(savedInstanceState, Constant.FRAGMENT_HELP, associationsFragment);
                break;
            default:
        }
    }

    private void getInstanceFragments(Bundle savedInstanceState, int posicao) {
        FragmentManager manager = getSupportFragmentManager();
        switch (posicao) {
            case 1 /*1*/:
                parametrageFragment = (ParametrageFragment) manager.getFragment(savedInstanceState, Constant.FRAGMENT_PROFIL);
                break;
          
            case 2 /*3*/:
                mesServiceViewPagerFragment = (MesServiceViewPagerFragment) manager.getFragment(savedInstanceState, Constant.FRAGMENT_MES_SERVICES);
                break;
            case 3 /*4*/:
                evaluationFragment = (EvaluationViewPagerFragment) manager.getFragment(savedInstanceState, Constant.FRAGMENT_EVALUATION);
                break;
           
            case 5 /*7*/:
                xListViewActivity = (ServicesFragment) manager.getFragment(savedInstanceState, Constant.FRAGMENT_SERVICES);
                break;
            case 6 /*7*/:
                xListViewActivity1 = (ServicesFragment) manager.getFragment(savedInstanceState, Constant.FRAGMENT_DONS);
                break;
            case 8 /*10*/:
                associationsFragment = (AssociationsFragment) manager.getFragment(savedInstanceState, Constant.FRAGMENT_HELP);
                break;
        }
        navigationAdapter.resetarCheck();
        setTitleFragments(lastPosition);
        navigationAdapter.setChecked(posicao, true);
    }

    private void setTitleFragments(int position) {
        setIconActionBar(Title.iconNavigation[position]);
        setSubtitleActionBar(Title.getTitleItem(this, position));
    }

    public int getCounterItemDownloads() {
        return counterItemDownloads;
    }

    public void setCounterItemDownloads(int value) {
        counterItemDownloads = value;
    }

    public void dataLoaded(Object data, int method, int typeOperation) {
    }

    public void dataLoadingError(int errorCode) {
    	
    }

    
}
