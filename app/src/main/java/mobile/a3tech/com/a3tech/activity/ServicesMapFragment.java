package mobile.a3tech.com.a3tech.activity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringTokenizer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.ServicesAdapter;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class ServicesMapFragment  extends Fragment implements DataLoadCallback, OnMapReadyCallback {
    String connectedUser = "";
    String password = "" ;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    int distance = 5;
    Button idSrvcMap_buttonSearchAroundMe;
   
    GoogleMap idSrvcMap_map;
    String keyWord = "";
    String premium = "" ;
    String lang;
    double latitude;
    int limit = 50;
    double longitude;
    ProgressDialog progressDialog = null;
    String typeTransaction;
    GPSTracker gps;
    int type = -1 ;
   
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
    String services = "";
    int start = 0;
    public ImageLoader imageLoader;
	
	DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory().cacheOnDisc()
    .showStubImage(R.drawable.logo_portrait)
    .showImageForEmptyUri(R.drawable.logo_portrait)
    .showImageOnFail(R.drawable.logo_portrait)
    .resetViewBeforeLoading()
    .build();
   
   
    private OnClickListener searchAroundMeListener =new OnClickListener() {
        public void onClick(View v) {
            progressDialog = CustomProgressDialog.createProgressDialog(ServicesMapFragment.this.getActivity(), getString(R.string.txtMenu_dialogChargement));
            MissionManager.getInstance().filtreMission(lang, connectedUser, keyWord, String.valueOf(distance), services, 0, String.valueOf(start), String.valueOf(limit), connectedUser,"0",premium,password,0,type, ServicesMapFragment.this);
        }
    };

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.idSrvcMap_map = googleMap;
        idSrvcMap_map.clear();
        if(gps.canGetLocation()){
            latitude = gps.getLatitude();
            longitude =gps.getLongitude() ;
            idSrvcMap_map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 5.0f));
        }else{
            gps.showSettingsAlert();
        }
        // idSrvcMap_map.setMyLocationEnabled(true);
        idSrvcMap_map.setInfoWindowAdapter(new CustomInfoWindowAdapter());
    }


    private class CustomInfoWindowAdapter implements InfoWindowAdapter {
        private View myContentsView;
        public CustomInfoWindowAdapter() {
            this.myContentsView = getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
        }

        public View getInfoContents(Marker marker) {
            marker.hideInfoWindow();
            marker.showInfoWindow();
            return null;
        }

        public View getInfoWindow(Marker marker) {
            this.myContentsView = getActivity().getLayoutInflater().inflate(R.layout.info_window, null);
           
            ImageView mission_row_profil_picto = (ImageView) this.myContentsView.findViewById(R.id.mission_row_profil_picto);
            TextView mission_row_description = (TextView) this.myContentsView.findViewById(R.id.mission_row_description);
            TextView mission_row_posted_forme = (TextView) this.myContentsView.findViewById(R.id.mission_row_posted_forme);
            StringTokenizer st2 = new StringTokenizer(marker.getSnippet(), "||");
            final String identifiant = (String) st2.nextElement();
            String url = (String) st2.nextElement();
            String article = (String) st2.nextElement();
            String objet = (String) st2.nextElement();
            String typeTransaction = (String) st2.nextElement();
            final String originator = (String)st2.nextElement();
            final String statut =(String) st2.nextElement();
            imageLoader.displayImage(new StringBuilder(
					Constant.CHECK_EDU_IMAGES_URL).append(url)
					.toString(), mission_row_profil_picto, options);
            mission_row_description.setText(Html.fromHtml(article));
        	String forme="";
			if(typeTransaction.equals("0")){
				forme = objet!=null? objet:"";
			}else{
				forme =getActivity().getString(R.string.txt_mission_row_khod_ajr);
			}
			mission_row_posted_forme.setText(forme);
			idSrvcMap_map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
				
				@Override
				public void onInfoWindowClick(Marker arg0) {
					
					Intent mainIntent = new Intent(getActivity(),
							DetailServiceViewPagerFragment.class);
					Bundle bundle = new Bundle();
					bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_STATUT,
							statut);
					bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID,
							originator);
					bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_ID,
							identifiant);
					mainIntent.putExtras(bundle);
					getActivity().startActivityForResult(mainIntent, 101);
					
					
				}
			});
			
           
            return this.myContentsView;
        }
    }
    
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 101 && resultCode == -1) {
			String result = data.getStringExtra(Constant.RESULT_ACTION_CODE);
			if (result.equals(Constant.RESULT_ACTION_CODE_DELETE_MISSION)
					|| result
							.equals(Constant.RESULT_ACTION_CODE_CREATE_MESSAGE)
					|| result.equals(Constant.RESULT_ACTION_CODE_CREATE_OFFRE)
					|| result.equals(Constant.RESULT_ACTION_CODE_DELETE_OFFRE)) {
				
				ServicesFragment s = new ServicesFragment();
				Bundle bndl = new Bundle();
				bndl.putString(Constant.TYPE_TRANSACTION,
						String.valueOf(typeTransaction));
					
				s.setArguments(bndl);
				replaceFragment(s);
			}
		}
	}


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub		
		View rootView = inflater.inflate(R.layout.services_map_activity, container, false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT ));		
		return rootView;		
	}

    @Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		setMenuVisibility(true);
		imageLoader = ImageLoader.getInstance();
        idSrvcMap_buttonSearchAroundMe = (Button) getView().findViewById(R.id.idSrvcMap_buttonSearchAroundMe);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        connectedUser = prefs.getString("identifiant", "");
        password = prefs.getString("password", "");
        Bundle b = getArguments();
        typeTransaction = b.getString(Constant.TYPE_TRANSACTION);
        lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
        gps = new GPSTracker(ServicesMapFragment.this.getActivity());
       
        ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.idSrvcMap_map)).getMapAsync(this);

//        LocationManager locationManager = (LocationManager) getActivity().getSystemService("location");
//        Criteria myCriteria = new Criteria();
//        myCriteria.setAccuracy(1);
//        locationManager.requestLocationUpdates(locationManager.getBestProvider(myCriteria, true), 0, 2000.0f, this.locationListener);
        idSrvcMap_buttonSearchAroundMe.setBackgroundColor(getResources().getColor(R.color.Gray));
        idSrvcMap_buttonSearchAroundMe.invalidate();
        idSrvcMap_buttonSearchAroundMe.bringToFront();
        idSrvcMap_buttonSearchAroundMe.requestLayout();
        idSrvcMap_buttonSearchAroundMe.setOnClickListener(this.searchAroundMeListener);
        showMissions(ServicesAdapter.mapMissions);
    }
    



	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.menu, menu);
		menu.findItem(R.id.menu_list).setVisible(true);
	
	
	}
	
	private void replaceFragment(Fragment f) {
        android.support.v4.app.FragmentTransaction transaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, f);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
      }
	
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_list:
			ServicesFragment s = new ServicesFragment();
			Bundle bndl = new Bundle();
			bndl.putString(Constant.TYPE_TRANSACTION,
					String.valueOf(typeTransaction));
				
			s.setArguments(bndl);
			replaceFragment(s);
			//startActivity(new Intent(getActivity(), ServicesMap.class));
		}
		return true;
	}
    private void showMissions(List<Mission> missions) {
        for (Mission mission : missions) {
            try {
                if (mission.getStatut().equals("0") || mission.getStatut().equals("1")) {
                    MarkerOptions marker = new MarkerOptions().position(new LatLng(Double.parseDouble(mission.getLatitude()), Double.parseDouble(mission.getLongitude())));
                    if (mission.getStatut() == null || mission.getStatut().equals("") || mission.getStatut().equals("0")) {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin));
                    } else {
                        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
                    }
                    String snipet = "";
                    String url = (mission.getUrl1()==null || mission.getUrl1().equals(""))? " " : mission.getUrl1();
                    String article = ((mission.getArticle()==null || mission.getArticle().equals(""))? mission.getTitre(): mission.getArticle());
        			
                   // String article = (mission.getArticle()==null || mission.getArticle().equals(""))? " " : mission.getArticle();
                    //String objet = (mission.getObjetrecherche()==null || mission.getObjetrecherche().equals(""))? " " : mission.getObjetrecherche();
                    String objet="";
        			if(mission.getTypeTransaction().equals("0")){
        				if(Integer.parseInt(mission.getContre())==Constant.CONTRE_JE_SAIS_PAS){
        					objet =getActivity().getString(R.string.txt_detailsrvc_msg_jattendvotreproposition);
        				}else{
        					objet = (mission.getObjetrecherche()==null || mission.getObjetrecherche().equals(""))? " ": mission.getObjetrecherche();
        				}
        			}else{
        				objet =getActivity().getString(R.string.txt_mission_row_khod_ajr);
        			}
                    
                    snipet = mission.getIdentifiant() + "||" + url + "||" + article + "||" + objet+"||"+mission.getTypeTransaction()+"||"+mission.getOriginator()+"||"+mission.getStatut();
                    marker.title("");
                    marker.snippet(snipet);
                    idSrvcMap_map.addMarker(marker);
                }
            } catch (Exception e) {
            }
        }
    }

    public void dataLoadingError(int errorCode) {
        Toast.makeText(getActivity().getApplicationContext(), R.string.txtSrvcMap_txtMessageResultAutourDeMoi, Toast.LENGTH_LONG).show();
        progressDialog.dismiss();
    }

    public void dataLoaded(Object data, int method, int typeOperation) {
        List<Mission> missions = (List<Mission>) data;
        idSrvcMap_map.clear();
        showMissions(missions);
        idSrvcMap_map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 10.0f));
        progressDialog.dismiss();
    }

    public void dataLoadingError(String error) {
    }
}
