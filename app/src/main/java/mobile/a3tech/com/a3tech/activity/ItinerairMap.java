package mobile.a3tech.com.a3tech.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.json.JSONObject;

import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.DirectionsJSONParser;

public class ItinerairMap extends FragmentActivity implements OnMapReadyCallback {
    final int MODE_BICYCLING = 1;
    final int MODE_DRIVING = 0;
    final int MODE_WALKING = 2;

    LatLng dest;
    ImageButton idItinerary_buttonsClose;
    GoogleMap idItinerary_map;
    RadioGroup idItinerary_rgModes;
    double latitude;
    double longitude;
    int mMode = 0;
    LocationManager myLocationManager = null;
    LatLng origin;
    RadioButton rbDriving;
    RadioButton rbWalking;
    String statut;


    private LocationListener locationListener = new LocationListener() {

        public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
        }

        public void onProviderEnabled(String arg0) {
        }

        public void onProviderDisabled(String arg0) {
        }

        public void onLocationChanged(Location location) {
            idItinerary_map.clear();
            origin = new LatLng(location.getLatitude(), location.getLongitude());
            drawStartStopMarkers();
            Location locationA = location;
            Location locationB = new Location("");
            locationB.setLatitude(latitude);
            locationB.setLongitude(longitude);
            double distance = (double) locationA.distanceTo(locationB);
            idItinerary_map.animateCamera(CameraUpdateFactory.newLatLngZoom(dest, 16.0f));
        }
    };


    private OnClickListener closeListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };


    private class DownloadTask extends AsyncTask<String, Void, String> {
        private DownloadTask() {
        }

        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
            }
            return data;
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(new String[]{result});
        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        private ParserTask() {
        }

        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
            List<List<HashMap<String, String>>> routes = null;
            try {
                routes = new DirectionsJSONParser().parse(new JSONObject(jsonData[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();
            for (int i = 0; i < result.size(); i++) {
                ArrayList<LatLng> points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = (List<HashMap<String, String>>) result.get(i);
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = (HashMap<String, String>) path.get(j);
                    try {
                        points.add(new LatLng(Double.parseDouble((String) point.get("lat")), Double.parseDouble((String) point.get("lng"))));
                    } catch (Exception e) {

                    }
                }
                lineOptions.addAll(points);
                lineOptions.width(4.0f);
                if (ItinerairMap.this.mMode == 0) {
                    lineOptions.color(Color.RED);
                } else if (ItinerairMap.this.mMode == 1) {
                    lineOptions.color(Color.BLUE);
                } else if (ItinerairMap.this.mMode == 2) {
                    lineOptions.color(Color.MAGENTA);
                }
            }
            if (result.size() < 1) {
                Toast.makeText(ItinerairMap.this.getBaseContext(), R.string.txtItitenary_textAucunItineraireTrouve, Toast.LENGTH_SHORT).show();
            } else {
                ItinerairMap.this.idItinerary_map.addPolyline(lineOptions);
            }
        }
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);
        idItinerary_buttonsClose = (ImageButton) findViewById(R.id.idItinerary_buttonsClose);
        rbDriving = (RadioButton) findViewById(R.id.rb_driving);
        rbWalking = (RadioButton) findViewById(R.id.rb_walking);
        idItinerary_rgModes = (RadioGroup) findViewById(R.id.idItinerary_rgModes);
        idItinerary_buttonsClose.setOnClickListener(closeListener);
        //idItinerary_map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idItinerary_map)).getMapAsync(this);
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.idItinerary_map)).getMapAsync(this);
        idItinerary_map.setMyLocationEnabled(true);
        LocationManager locationManager = (LocationManager) getSystemService("location");
        Criteria myCriteria = new Criteria();
        myCriteria.setAccuracy(1);
        locationManager.requestLocationUpdates(locationManager.getBestProvider(myCriteria, true), 0, 2000.0f, locationListener);

        this.idItinerary_rgModes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                idItinerary_map.clear();
                drawStartStopMarkers();
                String url = getDirectionsUrl(origin, dest);
                new DownloadTask().execute(new String[]{url});
            }
        });
        Bundle b = getIntent().getExtras();
        if (b != null) {
            latitude = Double.parseDouble(b.getString("latitude"));
            longitude = Double.parseDouble(b.getString("longitude"));
            statut = b.getString("statut");
        }
        dest = new LatLng(latitude, longitude);
        drawStartStopMarkers();
        idItinerary_map.animateCamera(CameraUpdateFactory.newLatLngZoom(dest, 19.0f));
    }

    @Override
    public void onMapReady(GoogleMap map) {

        map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        idItinerary_map = map;
    }

    private void drawStartStopMarkers() {
        MarkerOptions marker = new MarkerOptions();
        marker.position(this.dest);
        switch (Integer.parseInt(this.statut)) {
            case Constant.STATUT_MISSION_ACCEPTE /*1*/:
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.red_pin));
                break;
            case Constant.STATUT_MISSION_CLOTURE /*2*/:
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.violet_pin));
                break;
            case Constant.STATUT_MISSION_EXPIRE /*3*/:
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.gray_pin));
                break;
            default:
                marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.green_pin));
                break;
        }
        this.idItinerary_map.addMarker(marker);
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving";
        if (rbDriving.isChecked()) {
            mode = "mode=driving";
            mMode = 0;
        } else if (rbWalking.isChecked()) {
            mode = "mode=walking";
            mMode = 2;
        }
        return "https://maps.googleapis.com/maps/api/directions/" + "json" + "?" + new StringBuilder(String.valueOf(str_origin)).append("&").append(str_dest).append("&").append(sensor).append("&").append(mode).toString();
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(strUrl).openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String str = "";
            while (true) {
                str = br.readLine();
                if (str == null) {
                    break;
                }
                sb.append(str);
            }
            data = sb.toString();
            br.close();
        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
