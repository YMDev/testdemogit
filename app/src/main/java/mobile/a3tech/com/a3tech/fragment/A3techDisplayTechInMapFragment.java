package mobile.a3tech.com.a3tech.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.LetterTileProvider;
import mobile.a3tech.com.a3tech.utils.MapUtilities;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.DialogDisplayTechProfile;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techDisplayTechInMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techDisplayTechInMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techDisplayTechInMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MISSION_SELECTED = "param1";
    private static final String ARG_LIST_TECH = "ARG_LIST_TECH";
    private List<User> listeOfTechToDisplay;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Mission mission;
    SupportMapFragment mMapFragment;
    GoogleMap map;
    GPSTracker gps;
    Double userLatitude;
    Double userLongetude;
    MarkerOptions currentPositionMarker;
    Marker currentLocationMarker;
    private OnFragmentInteractionListener mListener;


    public A3techDisplayTechInMapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment A3techDisplayTechInMapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techDisplayTechInMapFragment newInstance(Mission mission, List technicien) {
        A3techDisplayTechInMapFragment fragment = new A3techDisplayTechInMapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MISSION_SELECTED, new Gson().toJson(mission));
        args.putString(ARG_LIST_TECH, new Gson().toJson(technicien));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonMission = getArguments().getString(ARG_MISSION_SELECTED);
            if (StringUtils.isNoneBlank(jsonMission)) {
                mission = new Gson().fromJson(jsonMission, Mission.class);
            }
            String jsonListTech = getArguments().getString(ARG_LIST_TECH);
            if (StringUtils.isNoneBlank(jsonListTech)) {
                listeOfTechToDisplay = new Gson().fromJson(jsonListTech, new TypeToken<List<User>>() {
                }.getType());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View viewFr = inflater.inflate(R.layout.fragment_a3tech_display_tech_in_map, container, false);
        final RelativeLayout mMapLayout = viewFr.findViewById(R.id.layout_container_map);
        gps = new GPSTracker(getActivity());
        if (!PermissionsStuffs.canAccessLocation(getActivity())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PermissionsStuffs.INITIAL_PERMS, PermissionsStuffs.INITIAL_REQUEST);
            }
        }
        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        mMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map_tech);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map_tech, mMapFragment).commit();
        }
        refreshMap();


        return viewFr;
    }


    public void destroyMap() {
        if (mMapFragment != null)
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(mMapFragment).commit();
    }

    public void refreshMap() {
        mMapFragment.getMapAsync(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
        gpsGetLocation();
        addMArker();
        addActionToMarkers();
        doZoomItiniraire(positions);
    }


    private void addActionToMarkers() {
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                User userClicked = (User) marker.getTag();

                // Check if a click count was set, then display the click count.
                if (userClicked != null) {

                    if (getActivity() instanceof A3techAddMissionActivity) {
                        DialogDisplayTechProfile.createProfileDialog(getActivity(), userClicked, mission, true);
                    } else if (getActivity() instanceof A3techDisplayTechniciensListeActivity) {
                        DialogDisplayTechProfile.createProfileDialog(getActivity(), userClicked, mission, false);
                    }

                }
            }
        });
    }

    private void gpsGetLocation() {
        if (gps.canGetLocation()) {
            userLatitude = gps.getLatitude();
            userLongetude = gps.getLongitude();
            /*float zoomLevel = 12.0f; //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLatitude, userLongetude), zoomLevel));*/
        } else {
            gps.showSettingsAlert();
        }
    }

    private List<Marker> markers = new ArrayList<>();
    private List<LatLng> positions = new ArrayList<>();

    private void addMArker() {
        for (Marker markerToRemove : markers
                ) {
            if (markerToRemove != null) markerToRemove.remove();
        }
        markers.clear();
        positions.clear();
        if (listeOfTechToDisplay != null) {
            for (User userTmp : listeOfTechToDisplay
                    ) {
                if (userTmp != null) {
                    addMarkerFotUSer(userTmp);
                }
            }
        }


        //user marker:
        LatLng currentUserPosition = new LatLng(userLatitude, userLongetude);
        Marker markerUserTmp = map.addMarker(new MarkerOptions()
                .position(currentUserPosition)
                .title("Your position")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.a3tech_home_mission)));
        markerUserTmp.setTag(null);
        // markerUserTmp.setSnippet(user.getNom());
        markers.add(markerUserTmp);
        positions.add(currentUserPosition);
        // Set a listener for marker click.
        //map.setOnMarkerClickListener(this);

    }

    private void doZoomItiniraire(List<LatLng> latLngList) {
        int padding = 30; // offset from edges of the map in pixels
        List<Marker> markers = MapUtilities.getMarkersFromListLatLng(map, latLngList);
        LatLngBounds bounds = MapUtilities.getBoundsFromMarkers(markers);
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.animateCamera(mCameraUpdate);
    }

    private void addMarkerFotUSer(User user) {

        final LetterTileProvider tileProvider = new LetterTileProvider(getActivity());
        final Bitmap letterTile = tileProvider.getLetterTile(user.getNom(), user.getNom(), 88, 88);

        LatLng currentPosition = new LatLng(Double.valueOf(user.getLatitude()), Double.valueOf(user.getLongitude()));
        Marker markerUserTmp = map.addMarker(new MarkerOptions()
                .position(currentPosition)
                .title(user.getNom() + " " + user.getPrenom().substring(0, 1) + ".").
                        icon(BitmapDescriptorFactory.fromBitmap(letterTile)));
        markerUserTmp.setTag(user);
        markerUserTmp.setSnippet(user.getNom());
        markers.add(markerUserTmp);
        positions.add(currentPosition);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
       /* User userClicked = (User) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (userClicked != null) {

            if (getActivity() instanceof A3techAddMissionActivity) {
                DialogDisplayTechProfile.createProfileDialog(getActivity(), userClicked, mission, true);
            } else if (getActivity() instanceof A3techDisplayTechniciensListeActivity) {
                DialogDisplayTechProfile.createProfileDialog(getActivity(), userClicked, mission, false);
            }

        }*/

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
