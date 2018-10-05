package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techDisplayTechniciensFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techDisplayTechniciensFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techDisplayTechniciensFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_SELECTED_CATEGORY = "ARG_SELECTED_CATEGORY";
    GPSTracker gps;
    Double userLatitude;
    Double userLongetude;
    Categorie categorieSelected;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerViewTechnicien;

    private OnFragmentInteractionListener mListener;

    public A3techDisplayTechniciensFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment A3techDisplayTechniciensFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techDisplayTechniciensFragment newInstance(Categorie categorie) {
        A3techDisplayTechniciensFragment fragment = new A3techDisplayTechniciensFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTED_CATEGORY, new Gson().toJson(categorie));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonMyObject = getArguments().getString(ARG_SELECTED_CATEGORY);
            if(jsonMyObject != null){
                categorieSelected = new Gson().fromJson(jsonMyObject, Categorie.class);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_display_techniciens, container, false);
        recyclerViewTechnicien = viewFr.findViewById(R.id.recycle_techniciens);
        displayTechniciens();
        return viewFr;
    }
    private void displayTechniciens(){
        if(categorieSelected != null){
            gps = new GPSTracker(getActivity());
            if (!PermissionsStuffs.canAccessLocation(getActivity())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PermissionsStuffs.INITIAL_PERMS, PermissionsStuffs.INITIAL_REQUEST);
                }
            }
            gpsGetLocation();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            String connectedUser = prefs.getString("identifiant", "");
            String password = prefs.getString("password", "");
            final ProgressDialog dd = CustomProgressDialog.createProgressDialog(getActivity(), "");
            UserManager.getInstance().getTechnicienNearLocation(userLatitude+"", userLongetude+"", "", new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    List<User> listeRetour = (List<User>) data;
                    Mission mission = new Mission();
                    mission.setCategoryMission(categorieSelected);
                    SimpleAdapterTechnicien adapter = new SimpleAdapterTechnicien(getActivity(),listeRetour, getActivity(), mission);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                    recyclerViewTechnicien.setLayoutManager(mLayoutManager);
                    recyclerViewTechnicien.setItemAnimator(new DefaultItemAnimator());
                    recyclerViewTechnicien.setAdapter(adapter);
                    dd.dismiss();
                }

                @Override
                public void dataLoadingError(int errorCode) {
                    dd.dismiss();
                }
            });
        }
    }
    private void gpsGetLocation() {
        if (gps.canGetLocation()) {
            userLatitude = gps.getLatitude();
            userLongetude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
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
        void backAction();
        void actionNext(Integer typeAction, Object data);
    }
}
