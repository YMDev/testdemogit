package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import eltos.simpledialogfragment.SimpleDateDialog;
import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.SimpleTimeDialog;
import eltos.simpledialogfragment.list.SimpleListDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.DateStuffs;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techPostMissionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techPostMissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techPostMissionFragment extends Fragment implements SimpleDialog.OnDialogResultListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CAT_ID = "ID_CAT";
    private static final String TAG_CALENDAR_MISSION = "MISSION_DATE";
    private static final String TAG_TIME_MISSION = "MISSION_TIME";
    public static final int ACTION_SAVE_MISSION_INFO = 2;
    private Date dateAlarmSelected;
    // TODO: Rename and change types of parameters
    private String categoryLibelle;
    private String categoryDescription;
    private String categoryIdentifiant;
    private TextView categorySelcted;
    private TextView dateIntervension;
    private EditText titleMission;
    private EditText descriptionMission;
    private AutoCompleteTextView locationMission;
    private Double latitude;
    private Double longitude;
    private String cityName, stateName, countryName;
    private ImageView btnGetLocation;
    private Button btnValidation;
    GPSTracker gps;
    String CityName;
    private OnFragmentInteractionListener mListener;

    public A3techPostMissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment A3techPostMissionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techPostMissionFragment newInstance(Categorie categorieSelected) {
        A3techPostMissionFragment fragment = new A3techPostMissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, categorieSelected.getLibelle());
        args.putString(ARG_PARAM2, categorieSelected.getDescription());
        args.putString(ARG_CAT_ID, categorieSelected.getIdentifiant());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryLibelle = getArguments().getString(ARG_PARAM1);
            categoryDescription = getArguments().getString(ARG_PARAM2);
            categoryIdentifiant = getArguments().getString(ARG_CAT_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_post_mission, container, false);
        titleMission = viewFr.findViewById(R.id.title_mission);
        descriptionMission = viewFr.findViewById(R.id.description_mission);
        locationMission = viewFr.findViewById(R.id.location_mission);
        categorySelcted = viewFr.findViewById(R.id.category_mission_selected);
        categorySelcted.setText(categoryLibelle);
        dateIntervension = viewFr.findViewById(R.id.date_mission);
        dateIntervension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateDialog cal = SimpleDateDialog.build().title(R.string.hint_date_mission);
                cal.setCancelable(true);
                cal.show(A3techPostMissionFragment.this, TAG_CALENDAR_MISSION);
            }
        });
        btnGetLocation = viewFr.findViewById(R.id.getMyLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // create class object
                gps = new GPSTracker(getActivity());

                // check if GPS enabled
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        cityName = addresses.get(0).getAddressLine(0);
                        stateName = addresses.get(0).getAddressLine(1);
                        countryName = addresses.get(0).getAddressLine(2);
                        Toast.makeText(getActivity().getApplicationContext(), "Your Location is - \ncity: " + cityName + "\nstate: " + stateName + "\ncounrty: " + countryName, Toast.LENGTH_LONG).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // \n is for new line
                } else {

                    gps.showSettingsAlert();
                }

            }
        });

        btnValidation = viewFr.findViewById(R.id.validate_mission);
        btnValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.actionNext(ACTION_SAVE_MISSION_INFO, populateMission());
                }
            }
        });
        return viewFr;
    }

    private Mission populateMission() {
        Mission mission = new Mission();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String connectedUser = prefs.getString("identifiant", "");
        mission.setTitre(titleMission.getText().toString());
        mission.setDateCreation(DateStuffs.dateToString(DateStuffs.TIME_FORMAT, new Date()));
        mission.setOriginator(connectedUser);
        mission.setLatitude(String.valueOf(latitude));
        mission.setLongitude(String.valueOf(longitude));
        mission.setCatDescription(descriptionMission.getText().toString());
        mission.setCategorieId(categoryIdentifiant);
        return mission;
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

        void actionNext(Integer typeAction, Object data);
    }

    private void showAlarmDatePickup() {
        SimpleDateDialog cal = SimpleDateDialog.build().title(R.string.hint_date_mission).minDate(new Date().getTime()).cancelable(true).neg(R.string.cancel).pos(R.string.ok_label);
        cal.setCancelable(true);
        cal.show(this, TAG_CALENDAR_MISSION);
    }

    private void showAlarmDateTimePickup() {
        SimpleTimeDialog cal = SimpleTimeDialog.build().title(R.string.choose_time_mission).cancelable(true).neg(R.string.cancel).pos(R.string.ok_label);
        ;
        cal.setCancelable(true);
        cal.show(this, TAG_TIME_MISSION);
    }


    @Override
    public boolean onResult(@NonNull String dialogTag, int which, @NonNull Bundle extras) {
        if (which == BUTTON_POSITIVE && TAG_CALENDAR_MISSION.equals(dialogTag)) {
            dateAlarmSelected = new Date(extras.getLong(SimpleDateDialog.DATE));
            showAlarmDateTimePickup();
        } else if (which == BUTTON_POSITIVE && TAG_TIME_MISSION.equals(dialogTag)) {
            int hourSelected = new Integer(extras.getInt(SimpleTimeDialog.HOUR));
            int minutesSelcted = new Integer(extras.getInt(SimpleTimeDialog.MINUTE));
            if (dateAlarmSelected == null) return true;
            Calendar missionDate = Calendar.getInstance();
            missionDate.setTime(dateAlarmSelected);
            missionDate.set(Calendar.HOUR_OF_DAY, hourSelected);
            missionDate.set(Calendar.MINUTE, minutesSelcted);
            missionDate.set(Calendar.SECOND, 0);
            dateIntervension.setText(DateStuffs.dateToString(DateStuffs.TIME_FORMAT, missionDate.getTime()));
            dateIntervension.setTag(missionDate);
        }
        return false;
    }
}
