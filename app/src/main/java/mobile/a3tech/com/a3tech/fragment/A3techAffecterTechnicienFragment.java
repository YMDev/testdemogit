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

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.EndlessRecyclerViewScrollListener;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
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
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_MISSION_OBJECT = "ARG_MISSION_OBJECT";
    private static final String ARG_LIST_TECH = "ARG_LIST_TECH";
    private static final int PAGE_SIZE = 20;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    private Mission mission;
    private List<User> listeOfTechToDisplay;

    private RecyclerView recyclerViewTechnicien;

    private OnFragmentInteractionListener mListener;

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
    public static A3techAffecterTechnicienFragment newInstance(Mission mission, List techniciens) {
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
            mission = new Gson().fromJson(jsonMyObject, Mission.class);
        }

        if (jsonListTech != null) {
            listeOfTechToDisplay = new Gson().fromJson(jsonListTech, new TypeToken<List<User>>() {
            }.getType());
        }

    }

    int start = 0;
    int end = PAGE_SIZE - 1;
    SimpleAdapterTechnicien adapter;
    private LinearLayoutManager mLayoutManager;
    protected Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_affecter_technicien, container, false);
        recyclerViewTechnicien = viewFr.findViewById(R.id.recycle_techniciens);
        handler = new Handler();
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewTechnicien.setLayoutManager(mLayoutManager);
        adapter = new SimpleAdapterTechnicien(getActivity(), listeOfTechToDisplay, getActivity(), mission,recyclerViewTechnicien);
        recyclerViewTechnicien.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTechnicien.setAdapter(adapter);
        // Adds the scroll listener to RecyclerView
        getListOFTechToDisplay(start, end, CustomProgressDialog.createProgressDialog(getActivity(), ""));
        adapter.setOnLoadMoreListener(new SimpleAdapterTechnicien.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                //add null , so the adapter will check view_type and show progress bar at bottom
                adapter.getListObject().add(null);

                recyclerViewTechnicien.post(new Runnable() {
                    public void run() {
                        adapter.notifyItemInserted(adapter.getListObject().size() - 1);
                    }
                });
                start = start + PAGE_SIZE;
                end = start + PAGE_SIZE -1;
                getListOFTechToDisplay(start, end, null);


            }
        });
        return viewFr;
    }

    private void getListOFTechToDisplay(final int start, final int end,final ProgressDialog dd) {
        //TODO get location of connected user not mission

        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        String latitudeUSer = prefs.getString(A3techHomeActivity.TAG_CONNECTED_USER_LATITUDE, "");
        String longetudeUSer = prefs.getString(A3techHomeActivity.TAG_CONNECTED_USER_LONGETUDE, "");
        UserManager.getInstance().getTechnicienNearLocation(latitudeUSer, longetudeUSer, mission.getAdresse(), start, end, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                List<User> reslisteOfTechToDisplay = (List<User>) data;
                if (start > 0) {
                    adapter.getListObject().remove(adapter.getListObject().size() - 1);

                    recyclerViewTechnicien.post(new Runnable() {
                        public void run() {
                            adapter.notifyItemRemoved(adapter.getListObject().size());
                        }
                    });
                }

                if(reslisteOfTechToDisplay != null && reslisteOfTechToDisplay.size() != 0) {
                    for (User userTmp:reslisteOfTechToDisplay
                         ) {
                        adapter.getListObject().add(userTmp);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyItemInserted(adapter.getListObject().size());


                            }
                        });
                    }
                }
                adapter.setLoaded();

               /* getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                    }
                });*/


               if(dd != null) dd.dismiss();
                return;
            }

            @Override
            public void dataLoadingError(int errorCode) {

            }
        });
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
}
