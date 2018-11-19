package mobile.a3tech.com.a3tech.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAddMissionActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.NotificationsManager;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.model.A3techNotificationType;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.SimpleAdapterMission;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.NetworkUtils;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techMissionsHomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techMissionsHomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techMissionsHomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQ_ADD_MISSION = 4953;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String keyWord = "";
    String premium = "";
    String lang = "";
    String connectedUser = "";
    String password = "";
    int distance = -1;
    int limit = 10;

    private RecyclerView recycleMission;
    private FloatingActionButton addMission;
    private RelativeLayout networkOn, networkDown;
    private TextView tapToRetry;

    private OnFragmentInteractionListener mListener;

    public A3techMissionsHomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techMissionsHomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techMissionsHomeFragment newInstance(String param1, String param2) {
        A3techMissionsHomeFragment fragment = new A3techMissionsHomeFragment();
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
        // Inflate the layout for this fragment
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(getActivity());
        this.connectedUser = prefs.getString("identifiant", "");
        this.password = prefs.getString("password", "");
        this.lang = prefs.getString("ApplicationLanguage",
                Constant.LANGUAGE_FRENSH);
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_missions_home, container, false);
        recycleMission = viewFr.findViewById(R.id.recycle_missions);
        networkDown = viewFr.findViewById(R.id.network_down);
        networkOn = viewFr.findViewById(R.id.network_on);
        /*tapToRetry = viewFr.findViewById(R.id.tap_to_refresh_label);

        tapToRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshMissionsList();
            }
        });*/
        networkOn.setVisibility(View.VISIBLE);
        networkDown.setVisibility(View.GONE);
        refreshMissionsList();

        addMission = viewFr.findViewById(R.id.add_mission);
        addMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start activity add mission
                startActivityForResult(new Intent(getActivity(), A3techAddMissionActivity.class), REQ_ADD_MISSION);
            }
        });
        return viewFr;
    }


    private void refreshMissionsList() {
        final ProgressDialog dd = CustomProgressDialog.createProgressDialog(getActivity(), "");
        MissionManager.getInstance().filtreMission(lang, connectedUser,
                keyWord, String.valueOf(distance), "",
                Constant.LOAD_DATA_FINISH, String.valueOf(0),
                String.valueOf(limit), connectedUser, null, premium, password, 0, 0,
                new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        List<A3techMission> listeRetour = (List<A3techMission>) data;
                        SimpleAdapterMission adapter = new SimpleAdapterMission(getActivityContext(), listeRetour, (A3techHomeActivity) getActivityContext());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivityContext());
                        recycleMission.setLayoutManager(mLayoutManager);
                        recycleMission.setItemAnimator(new DefaultItemAnimator());
                        recycleMission.setAdapter(adapter);
                        dd.dismiss();
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {
                        dd.dismiss();
                    }
                });
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (REQ_ADD_MISSION): {
                if (resultCode == Activity.RESULT_OK) {
                    String jsonMission = data.getStringExtra(A3techAddMissionActivity.TAG_RESULT_FROM_SELECT_TECH);
                    final A3techMission mission = new Gson().fromJson(jsonMission, A3techMission.class);
                    ((SimpleAdapterMission) recycleMission.getAdapter()).addMissionb(mission);

                    //TODO create mission
                    final ProgressDialog dialogWaiting = CustomProgressDialog.createProgressDialog(getActivity(), "");
                    MissionManager.getInstance().createMission(mission, new DataLoadCallback() {
                        @Override
                        public void dataLoaded(Object data, int method, int typeOperation) {
                            //TODO commentaire a reconstituer.
                            String commentaire = "Demande créée pour une Mission en  " + mission.getCategoryMission().getLibelle() + "";
                            A3techNotification notification = NotificationsManager.getNotificationInstance(PreferencesValuesUtils.getConnectedUser(getActivity()),
                                    null, mission, A3techNotificationType.CREATION_MISSION, commentaire, getString(R.string.libelle_creatio_mission));
                            NotificationsManager.getInstance().createNotification(notification, new DataLoadCallback() {
                                @Override
                                public void dataLoaded(Object data, int method, int typeOperation) {
                                    A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.mission_cree), Toast.LENGTH_LONG, A3techCustomToastDialog.TOAST_SUCESS);
                                    dialogWaiting.dismiss();
                                }

                                @Override
                                public void dataLoadingError(int errorCode) {
                                    dialogWaiting.dismiss();
                                }
                            });
                        }

                        @Override
                        public void dataLoadingError(int errorCode) {
                            dialogWaiting.dismiss();
                            A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.error_create_mission), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                        }
                    });


                }
                break;
            }
        }
    }


    public void addMissionToLise(A3techMission mission) {
        if (recycleMission != null) {
            ((SimpleAdapterMission) recycleMission.getAdapter()).addMissionb(mission);
        } else {

        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if(NetworkUtils.isNetworkAvailable(getActivity())){
            onNetworkUp();
        }
    }
    public void onNetworkDown() {
        networkOn.setVisibility(View.GONE);
        networkDown.setVisibility(View.VISIBLE);
    }

    public void onNetworkUp() {
        networkOn.setVisibility(View.VISIBLE);
        networkDown.setVisibility(View.GONE);
        refreshMissionsList();
    }
}
