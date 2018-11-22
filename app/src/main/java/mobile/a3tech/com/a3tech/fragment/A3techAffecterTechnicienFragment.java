package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techDistanceBarView;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techAffecterTechnicienFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techAffecterTechnicienFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techAffecterTechnicienFragment extends Fragment {
    private static final String ARG_MISSION_OBJECT = "ARG_MISSION_OBJECT";
    private static final String ARG_LIST_TECH = "ARG_LIST_TECH";
    private static final int PAGE_SIZE = 20;
    int start = 0;
    int end = PAGE_SIZE - 1;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    private A3techMission mission;
    private List<A3techUser> listeOfTechToDisplay;
    private RecyclerView recyclerViewTechnicien;
    private OnFragmentInteractionListener mListener;
    private SimpleAdapterTechnicien adapter;
    private LinearLayoutManager mLayoutManager;
    protected Handler handler;

    public A3techAffecterTechnicienFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mission Mission.
     * @return A new instance of fragment A3techAffecterTechnicienFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techAffecterTechnicienFragment newInstance(A3techMission mission, List techniciens) {
        A3techAffecterTechnicienFragment fragment = new A3techAffecterTechnicienFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MISSION_OBJECT, new Gson().toJson(mission));
        args.putString(ARG_LIST_TECH, new Gson().toJson(techniciens));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String jsonMyObject = null;
        String jsonListTech = null;
        Bundle extras = getArguments();
        if (extras != null) {
            jsonMyObject = extras.getString(ARG_MISSION_OBJECT);
            jsonListTech = extras.getString(ARG_LIST_TECH);
        }
        if (jsonMyObject != null) {
            mission = new Gson().fromJson(jsonMyObject, A3techMission.class);
        }

        if (jsonListTech != null) {
            listeOfTechToDisplay = new Gson().fromJson(jsonListTech, new TypeToken<List<A3techUser>>() {
            }.getType());
        }

    }

    GPSTracker gps;
    Double userLatitude;
    Double userLongetude;

    private void gpsGetLocation() {
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()) {
            userLatitude = gps.getLatitude();
            userLongetude = gps.getLongitude();
            /*float zoomLevel = 12.0f; //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(userLatitude, userLongetude), zoomLevel));*/
        } else {
            gps.showSettingsAlert();
            userLatitude = 0d;
            userLongetude = 0d;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        handler = new Handler();
        gpsGetLocation();
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_affecter_technicien, container, false);
        recyclerViewTechnicien = viewFr.findViewById(R.id.recycle_techniciens);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTechnicien.setLayoutManager(mLayoutManager);
        adapter = new SimpleAdapterTechnicien(getActivity(), listeOfTechToDisplay, getActivity(), mission, recyclerViewTechnicien);
        adapter.setLatitude(userLatitude);
        adapter.setLongitude(userLongetude);
        recyclerViewTechnicien.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTechnicien.setAdapter(adapter);

        // load the first page of users (PAGE_SIZE to controle number of users to load)
        ProgressDialog dd = CustomProgressDialog.createProgressDialog(getActivity(),"");
        getListOFTechToDisplayFirstInit(start,end,PreferencesValuesUtils.getPermietreRechercheTechniciens(getActivity())+"",dd);
        start = start + PAGE_SIZE;
        end = start + PAGE_SIZE - 1;
        adapter.setOnLoadMoreListener(new SimpleAdapterTechnicien.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                  /*      adapter.getListObject().add(null);
                        adapter.notifyItemInserted(adapter.getListObject().size() - 1);*/
                      ProgressDialog dd = CustomProgressDialog.createProgressDialog(getActivity(),"");
                        // load the next page
                        getListOFTechToDisplay(start, end, PreferencesValuesUtils.getPermietreRechercheTechniciens(getActivity()) + "", dd);
                        start = start + PAGE_SIZE;
                        end = start + PAGE_SIZE - 1;
                    }
                },200);

            }
        });
        return viewFr;
    }

    private void getListOFTechToDisplay(final int start, final int end, String perim, final ProgressDialog dd) {


        UserManager.getInstance().getTechnicienNearLocation(userLatitude != null ? userLatitude + "" : "", userLongetude != null ? userLongetude + "" : "", perim, start, end, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                List<A3techUser> reslisteOfTechToDisplay = (List<A3techUser>) data;
                //whene loading next page, delete progress bar
              /*  if (dd == null) {
                    adapter.getListObject().remove(adapter.getListObject().size() - 1);
                    adapter.notifyItemRemoved(adapter.getListObject().size());
                }*/

                // adding each element to recycle
                if (reslisteOfTechToDisplay != null && reslisteOfTechToDisplay.size() != 0) {
                    for (A3techUser userTmp : reslisteOfTechToDisplay
                            ) {
                        adapter.getListObject().add(userTmp);
                        adapter.notifyItemInserted(adapter.getListObject().size());
                    }
                }
                adapter.setLoaded();
                if (dd != null) dd.dismiss();
                return;
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if (dd != null) dd.dismiss();
                /*if (dd == null) {
                    adapter.getListObject().remove(adapter.getListObject().size() - 1);
                    adapter.notifyItemRemoved(adapter.getListObject().size());
                }*/
            }
        });
    }

    private void getListOFTechToDisplayFirstInit(final int start, final int end, String perim, final ProgressDialog dd) {


        UserManager.getInstance().getTechnicienNearLocation(userLatitude != null ? userLatitude + "" : "", userLongetude != null ? userLongetude + "" : "", perim, start, end, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                List<A3techUser> reslisteOfTechToDisplay = (List<A3techUser>) data;
                // adding each element to recycle
                if (reslisteOfTechToDisplay != null && reslisteOfTechToDisplay.size() != 0) {
                    adapter.getListObject().addAll(reslisteOfTechToDisplay);
                    adapter.notifyDataSetChanged();
                }
                adapter.setLoaded();
                if (dd != null) dd.dismiss();
                return;
            }

            @Override
            public void dataLoadingError(int errorCode) {
                if (dd != null) dd.dismiss();
                /*if (dd == null) {
                    adapter.getListObject().remove(adapter.getListObject().size() - 1);
                    adapter.notifyItemRemoved(adapter.getListObject().size());
               *//*     recyclerViewTechnicien.post(new Runnable() {
                        public void run() {
                            adapter.notifyItemRemoved(adapter.getListObject().size());
                        }
                    });*//*
                }*/
            }
        });
    }


    public void notifyPerimetreChanged(Integer permi) {
        start = 0;
        end = PAGE_SIZE - 1;
        adapter.getListObject().clear();
        adapter.notifyDataSetChanged();
        getListOFTechToDisplayFirstInit(start, end, PreferencesValuesUtils.getPermietreRechercheTechniciens(getActivity()) + "", CustomProgressDialog.createProgressDialog(getActivity(), ""));
        start = start + PAGE_SIZE;
        end = start + PAGE_SIZE - 1;
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
    }


    public SimpleAdapterTechnicien getAdapter() {
        return adapter;
    }

    public void setAdapter(SimpleAdapterTechnicien adapter) {
        this.adapter = adapter;
    }
}
