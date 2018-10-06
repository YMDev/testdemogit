package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
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
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.adapter.A3techSelectMissionCategoryAdapter;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
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
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Mission mission;
    private  List<User> listeOfTechToDisplay;

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
            if(jsonMyObject != null){
                  mission = new Gson().fromJson(jsonMyObject, Mission.class);
            }

            if(jsonListTech != null){
                listeOfTechToDisplay = new Gson().fromJson(jsonListTech, new TypeToken<List<User>>(){}.getType());
            }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_affecter_technicien, container, false);
        recyclerViewTechnicien = viewFr.findViewById(R.id.recycle_techniciens);
        if(listeOfTechToDisplay == null) listeOfTechToDisplay = new ArrayList<>();
        SimpleAdapterTechnicien adapter = new SimpleAdapterTechnicien(getActivity(),listeOfTechToDisplay, getActivity(), mission);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerViewTechnicien.setLayoutManager(mLayoutManager);
        recyclerViewTechnicien.setItemAnimator(new DefaultItemAnimator());
        recyclerViewTechnicien.setAdapter(adapter);
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
