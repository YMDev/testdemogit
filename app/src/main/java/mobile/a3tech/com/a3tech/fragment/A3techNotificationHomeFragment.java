package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.adapter.A3techMissionsListeAdapter;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.NotificationsManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techMissionStatut;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.SimpleAdapterNotifications;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTimeLine;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techNotificationHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techNotificationHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techNotificationHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String THIS_FRAGMENT = "A3techNotificationHomeFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String keyWord = "";
    String premium = "";
    String lang = "";
    String connectedUser = "";
    String password = "";
    int distance = -1;
    int limit = 1000;

    private RecyclerView recycleNotifications;
    private OnFragmentInteractionListener mListener;

    public A3techNotificationHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techNotificationHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techNotificationHomeFragment newInstance(String param1, String param2) {
        A3techNotificationHomeFragment fragment = new A3techNotificationHomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        this.connectedUser = prefs.getString("identifiant", "");
        this.password = prefs.getString("password", "");
        this.lang = prefs.getString("ApplicationLanguage",
                Constant.LANGUAGE_FRENSH);
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_notification_home, container, false);
        recycleNotifications = viewFr.findViewById(R.id.recycle_notifications);

        final ProgressDialog dd = CustomProgressDialog.createProgressDialog(getActivityContext(), "");
        A3techMission criteriaM = new A3techMission();
        MissionManager.getInstance().filtreMission(lang, connectedUser,
                new Gson().toJson(criteriaM),
                String.valueOf("0"), String.valueOf(limit), 0, 0,
                new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        List<A3techMission> listeRetour = (List<A3techMission>) data;
                        SimpleAdapterNotifications adapter = new SimpleAdapterNotifications(getActivityContext(), listeRetour,  getActivity());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivityContext());
                        recycleNotifications.setLayoutManager(mLayoutManager);
                        recycleNotifications.setItemAnimator(new DefaultItemAnimator());
                        recycleNotifications.setAdapter(adapter);
                        dd.dismiss();
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {
                        dd.dismiss();
                    }
                });


        return viewFr;
    }


    private HashMap<String, List<A3techNotification>> getallNorification(List<A3techMission> missions){
        final HashMap<String, List<A3techNotification>> missionsHash = new HashMap<>();
        for (final A3techMission mission: missions
             ) {
            NotificationsManager.getInstance().filtreNotificationByMission(mission, "0", "0", new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    missionsHash.put(mission.getId(),(List<A3techNotification>) data);
                }
                @Override
                public void dataLoadingError(int errorCode) {
                }
            });
        }
        return missionsHash;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    Context activityContext;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityContext = context;
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
        activityContext = null;
    }


    public Context getActivityContext() {
        return activityContext;
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
