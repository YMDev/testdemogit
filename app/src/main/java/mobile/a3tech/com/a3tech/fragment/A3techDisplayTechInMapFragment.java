package mobile.a3tech.com.a3tech.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techDisplayTechInMapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techDisplayTechInMapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techDisplayTechInMapFragment extends Fragment implements OnMapReadyCallback ,GoogleMap.OnMarkerClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_MISSION_SELECTED = "param1";
    private static final String ARG_LIST_TECH = "ARG_LIST_TECH";
    private  List<User> listeOfTechToDisplay;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Mission mission;
    SupportMapFragment mMapFragment;
    GoogleMap map;
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
            if(StringUtils.isNoneBlank(jsonMission)){
                mission = new Gson().fromJson(jsonMission, Mission.class);
            }
            String jsonListTech = getArguments().getString(ARG_LIST_TECH);
            if(StringUtils.isNoneBlank(jsonListTech)){
                listeOfTechToDisplay = new Gson().fromJson(jsonListTech, new TypeToken<List<User>>(){}.getType());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFr =  inflater.inflate(R.layout.fragment_a3tech_display_tech_in_map, container, false);
        FragmentManager fm = getActivity().getSupportFragmentManager();/// getChildFragmentManager();
        mMapFragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
        if (mMapFragment == null) {
            mMapFragment = SupportMapFragment.newInstance();
            fm.beginTransaction().replace(R.id.map, mMapFragment).commit();
        }
        mMapFragment.getMapAsync(this);
        return viewFr;
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
        map.animateCamera(CameraUpdateFactory.zoomTo(15));
        addMArker();
    }

    private static final LatLng PERTH = new LatLng(-31.952854, 115.857342);
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
    private static final LatLng BRISBANE = new LatLng(-27.47093, 153.0235);
    private Marker mPerth;
    private Marker mSydney;
    private Marker mBrisbane;

 private void addMArker(){
     mPerth = map.addMarker(new MarkerOptions()
             .position(PERTH)
             .title("Perth"));
     mPerth.setTag(0);

     mSydney = map.addMarker(new MarkerOptions()
             .position(SYDNEY)
             .title("Sydney"));
     mSydney.setTag(0);

     mBrisbane = map.addMarker(new MarkerOptions()
             .position(BRISBANE)
             .title("Brisbane"));
     mBrisbane.setTag(0);

     // Set a listener for marker click.
     map.setOnMarkerClickListener(this);

 }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();

        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            A3techCustomToastDialog.createToastDialog(getActivity(), marker.getTitle() +
                    " has been clicked " + clickCount + " times.",Toast.LENGTH_SHORT,A3techCustomToastDialog.TOAST_INFO);
        }

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
