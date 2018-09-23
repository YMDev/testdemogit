package mobile.a3tech.com.a3tech.activity;

import java.util.ArrayList;
import java.util.List;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.AlertBuilderAdresseAdapter;
import mobile.a3tech.com.a3tech.adapter.CategorieAdapter;
import mobile.a3tech.com.a3tech.manager.VilleManager;
import mobile.a3tech.com.a3tech.map.getReverseGeoCoding;
import mobile.a3tech.com.a3tech.model.Adresse;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, DataLoadCallback,  LocationSource, LocationListener {
	
	ImageButton idAdresseMap_buttonEnregistrerVille ;
	EditText idAdresseMap_TextAdresse;
	ImageButton idAdresseMap_buttonEnregistrer;
	Spinner idAdresseMap_spinnerVille ;
	ImageButton location ;
	View idDetailSrvc_ViewSepCar1 ;
	GoogleMap myMap;
	
	LinearLayout idMap_linearLayoutRetour;

	String lang;
	List<Address> result;
	AlertDialog alertDialog;
	AlertBuilderAdresseAdapter listAdapter;
	String latitude = "";

	String longitude = "";
	String adresse ;

	OnLocationChangedListener myLocationListener = null;
	LocationManager myLocationManager = null;
	int f126i = -1;
	int f127i = -1;
	GPSTracker gps;
	int pays;
	ProgressDialog progressDialog = null;
	
	List<Categorie> villes;
    CategorieAdapter villesAdapter;
    Categorie hintVille;
    String connectedUser = "";
	String password = "" ;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map);
		 idAdresseMap_spinnerVille  = (Spinner) findViewById(R.id.idAdresseMap_spinnerVille);
		 idAdresseMap_buttonEnregistrerVille = (ImageButton) findViewById(R.id.idAdresseMap_buttonEnregistrerVille);
		 idAdresseMap_TextAdresse = (EditText) findViewById(R.id.idAdresseMap_TextAdresse);
		 idAdresseMap_buttonEnregistrer = (ImageButton) findViewById(R.id.idAdresseMap_buttonEnregistrer);
		 location  = (ImageButton) findViewById(R.id.location);
		 idMap_linearLayoutRetour = (LinearLayout)findViewById(R.id.idMap_linearLayoutRetour);
		 idMap_linearLayoutRetour.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		 idDetailSrvc_ViewSepCar1 = (View) findViewById(R.id.idDetailSrvc_ViewSepCar1);
		 ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);
		location.invalidate();
	    location.bringToFront();
	    location.requestLayout();
	    idDetailSrvc_ViewSepCar1.invalidate();
	    idDetailSrvc_ViewSepCar1.bringToFront();
	    idDetailSrvc_ViewSepCar1.requestLayout();
	    
		 
		  
	    gps = new GPSTracker(MapActivity.this);
        if(gps.canGetLocation()){             
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            try {
                new ReverseGeocodingTask().execute(new Double[]{Double.valueOf(latitude), Double.valueOf(longitude),Double.valueOf(1.0)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            gps.showSettingsAlert();
        }
           
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
		 connectedUser = prefs.getString("identifiant", "");
		  password = prefs.getString("password", "");
		
		idAdresseMap_buttonEnregistrer.setOnClickListener(enregistrerListener);
		idAdresseMap_buttonEnregistrerVille.setOnClickListener(enregistrerVilleListener);
		
		
		myMap.setMyLocationEnabled(true);
		myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		myMap.getUiSettings().setZoomGesturesEnabled(true);
		Criteria myCriteria = new Criteria();
		myCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		myLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = myLocationManager.getBestProvider(myCriteria, true);
//		Location location = myLocationManager.getLastKnownLocation(provider);
//        if (location != null) {
//            onLocationChanged(location);
//        }
        myLocationManager.requestLocationUpdates(provider, 200, 0.0f, this);
        this.myMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
			@Override
			public void onMyLocationChange(Location location) {
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                try {
	                new ReverseGeocodingTask().execute(new Double[]{Double.valueOf(latitude), Double.valueOf(longitude),Double.valueOf(0.0)});
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
				
			}
		});
        this.myMap.setOnCameraChangeListener(new OnCameraChangeListener() {
			
			@Override
			public void onCameraChange(CameraPosition position) {
				  try {
		                new ReverseGeocodingTask().execute(new Double[]{Double.valueOf(position.target.latitude), Double.valueOf(position.target.longitude),Double.valueOf(0.0)});
		            } catch (Exception e) {
		                e.printStackTrace();
		            }
				
			}
		});
        hintVille = new Categorie();
        hintVille.setLibelle(getString(R.string.txtProfil_spinnerVille));
        villes = new ArrayList<Categorie>();
        villes.add(hintVille);
        villesAdapter = new CategorieAdapter(this.getApplicationContext(), villes);
        idAdresseMap_spinnerVille.setAdapter(villesAdapter);
        idAdresseMap_spinnerVille.setSelection(0);
        progressDialog = CustomProgressDialog.createProgressDialog(this, getString(R.string.txtMenu_dialogChargement));
        pays = prefs.getInt("city", 3);
        VilleManager.getInstance().listeVilles(String.valueOf(pays), lang,connectedUser,password, this);
	}

    @Override
    public void onMapReady(GoogleMap googleMap) {
        myMap = googleMap;
    }


    private class ReverseGeocodingTask extends AsyncTask<Double, Void, Adresse> {
	        protected Adresse doInBackground(Double... params) {
	            Adresse adresse = new Adresse();
	            getReverseGeoCoding coding = new getReverseGeoCoding();
	            double latitude = params[0].doubleValue();
	            double longitude = params[1].doubleValue();
	            double update = params[2].doubleValue();
	            adresse.setLat(latitude);
	            adresse.setLon(longitude);
	            adresse.setUpdate(update==0.0 ? false:true);
	            adresse.setAdresse(coding.getAddress(latitude, longitude));
	            return adresse;
	        }

	        protected void onPostExecute(Adresse result) {
	            putLocation(result.getAdresse(), String.valueOf(result.getLat()), String.valueOf(result.getLon()), result.isUpdate());
	            super.onPostExecute(result);
	        }
	    }

	
	   
	   public void putLocation(String adr, String lat, String ln, boolean updateCamera) {
	        LatLng position = new LatLng(Double.parseDouble(lat), Double.parseDouble(ln));
	        idAdresseMap_TextAdresse.setText(adr);
	         latitude = lat ;
	         longitude = ln ;
	    	 adresse = adr ;
	        if (updateCamera) {
	            myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15.0f));
	        }
	    }

	protected void onDestroy() {
		super.onDestroy();
		// this.myLocationManager.removeUpdates(this);
	}

	protected void onResume() {
		super.onResume();
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());
		if (resultCode == 0) {
			this.myMap.setLocationSource(this);
		} else {
			GooglePlayServicesUtil.getErrorDialog(resultCode, this, 1);
		}
	}

	protected void onPause() {
		this.myMap.setLocationSource(null);
		super.onPause();
	}

	public void activate(OnLocationChangedListener listener) {
		this.myLocationListener = listener;
	}

	public void deactivate() {
		this.myLocationListener = null;
	}

	@Override
	public void onLocationChanged(Location location) {
        if (this.myLocationListener != null && this.f127i == -1) {
            this.myLocationListener.onLocationChanged(location);
            this.myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
            this.f127i = 0;
        }
    }

	public void onProviderDisabled(String arg0) {
	}

	public void onProviderEnabled(String arg0) {
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
	}

	

	

	private OnClickListener enregistrerListener = new OnClickListener() {
		public void onClick(View v) {
			if (idAdresseMap_TextAdresse.getText().toString().equals("") ) {
                Toast.makeText(getApplicationContext(), R.string.txtProfil_msgChoixVille, Toast.LENGTH_SHORT).show();
                idAdresseMap_TextAdresse.setError(getString(R.string.txtProfil_msgChoixVille));
                return;
            }
		 PostageActivityStep2.idVille= "";
		 PostageActivityStep2.idPostage2_editTextLieu.setText(adresse);
		 PostageActivityStep2.latitude = latitude;
		 PostageActivityStep2.longitude = longitude ;
		 PostageActivityStep2.adresse = adresse ;
		 finish();
		}
	};

	private OnClickListener enregistrerVilleListener = new OnClickListener() {
		public void onClick(View v) {
			 if (idAdresseMap_spinnerVille.getSelectedItemPosition() == 0) {
	                Toast.makeText(getApplicationContext(), R.string.txtProfil_msgChoixVille, Toast.LENGTH_SHORT).show();
	                ((TextView) idAdresseMap_spinnerVille.getSelectedView()).setError(getString(R.string.txtProfil_msgChoixVille));
	                return;
	            }
			 PostageActivityStep2.idVille= ((Categorie) villes.get(idAdresseMap_spinnerVille.getSelectedItemPosition())).getIdentifiant();
			 PostageActivityStep2.idPostage2_editTextLieu.setText(((Categorie) villes.get(idAdresseMap_spinnerVille.getSelectedItemPosition())).getLibelle());
			 PostageActivityStep2.latitude = "";
			 PostageActivityStep2.longitude = "" ;
			 PostageActivityStep2.adresse = "" ;
			 finish();
		}
	};

	

	


	

	@Override
	public void dataLoaded(Object data, int method, int typeOperation) {
		switch (method) {
		case Constant.KEY_VILLE_MANAGER_LISTE_VILLE :
            villes.addAll((List<Categorie>) data);
            villesAdapter.notifyDataSetChanged();
            idAdresseMap_spinnerVille.setSelection(0);
            villesAdapter.notifyDataSetChanged();
                
            progressDialog.dismiss();
           
            break;

		default:
			break;
		}
		
	}

	@Override
	public void dataLoadingError(int errorCode) {
		Toast.makeText(getApplicationContext(),R.string.txtSplash_messageCheckConnexion,Toast.LENGTH_SHORT).show();
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			
		}
		
	}

}