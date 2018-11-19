package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import eltos.simpledialogfragment.SimpleDateDialog;
import eltos.simpledialogfragment.SimpleDialog;
import eltos.simpledialogfragment.SimpleTimeDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.A3techMission;
import mobile.a3tech.com.a3tech.model.A3techMissionStatut;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.GPSTracker;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.utils.PermissionsStuffs;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techPostMissionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techPostMissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techPostMissionFragment extends A3techBaseFragment implements SimpleDialog.OnDialogResultListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_CAT_ID = "ID_CAT";
    private static final String ARG_CAT_OBJECT = "ID_CAT";
    private static final String ARG_MISS_OBJECT = "ARG_MISS_OBJECT";
    private static final String TAG_CALENDAR_MISSION = "MISSION_DATE";
    private static final String TAG_TIME_MISSION = "MISSION_TIME";
    public static final int ACTION_SAVE_MISSION_INFO = 2;
    public static final int ACTION_SAVE_MISSION_INFO_FROM_TECH = 3;
    private Date dateAlarmSelected;
    // TODO: Rename and change types of parameters
    private String categoryLibelle;
    private String categoryDescription;
    private String categoryIdentifiant;
    private A3techMission missionObject;
    private Categorie categoryObject;
    private TextView categorySelcted;
    private TextView dateIntervension;
    private EditText titleMission;
    private EditText descriptionMission;
    private EditText locationMission;
    private Double latitude;
    private Double longitude;
    private String cityName, stateName, countryName;
    private ImageView btnGetLocation;
    private ImageView btnGetDate;
    private Button btnValidation;
    private RelativeLayout containerUserSelected;
    private ImageView avatareTechnicien;
    private TextView nameTechnicien;
    private TextView adresseTechnicien;
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
    public static A3techPostMissionFragment newInstance(Categorie categorieSelected) {
        A3techPostMissionFragment fragment = new A3techPostMissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, categorieSelected.getLibelle());
        args.putString(ARG_PARAM2, categorieSelected.getDescription());
        args.putString(ARG_CAT_ID, categorieSelected.getIdentifiant());
        args.putString(ARG_CAT_OBJECT, new Gson().toJson(categorieSelected));
        fragment.setArguments(args);
        return fragment;
    }

    public static A3techPostMissionFragment newInstance(A3techMission missionSelected) {
        A3techPostMissionFragment fragment = new A3techPostMissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MISS_OBJECT, new Gson().toJson(missionSelected));
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
            String jsonCategory = getArguments().getString(ARG_CAT_OBJECT);
            String jsonMission = getArguments().getString(ARG_MISS_OBJECT);
            if (jsonCategory != null) {
                categoryObject = new Gson().fromJson(jsonCategory, Categorie.class);
            }
            if (jsonMission != null) {
                missionObject = new Gson().fromJson(jsonMission, A3techMission.class);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_post_mission, container, false);
        titleMission = viewFr.findViewById(R.id.title_mission);
        titleMission.setOnFocusChangeListener(this);

        descriptionMission = viewFr.findViewById(R.id.description_mission);
        descriptionMission.setOnFocusChangeListener(this);

        locationMission = viewFr.findViewById(R.id.location_mission);
        locationMission.setOnFocusChangeListener(this);


        categorySelcted = viewFr.findViewById(R.id.category_mission_selected);
        containerUserSelected = viewFr.findViewById(R.id.user_selected);
        avatareTechnicien = viewFr.findViewById(R.id.avatare_technicien);
        nameTechnicien = viewFr.findViewById(R.id.name_tech);
        adresseTechnicien = viewFr.findViewById(R.id.adresse_alpha);
        btnValidation = viewFr.findViewById(R.id.validate_mission);
        btnGetDate = viewFr.findViewById(R.id.add_date_act);
        String categorie = "";
        if (categoryObject != null) {
            categorie = categoryObject.getLibelle();
            categorySelcted.setText(categorie);
            categorySelcted.setVisibility(View.VISIBLE);
            containerUserSelected.setVisibility(View.GONE);
            btnValidation.setText(getText(R.string.affecter_un_technicien));
        } else if (missionObject != null) {
            categorySelcted.setVisibility(View.GONE);
            containerUserSelected.setVisibility(View.VISIBLE);
            nameTechnicien.setText(missionObject.getTechnicien().getNom() + " " + missionObject.getTechnicien().getPrenom());
            adresseTechnicien.setText(missionObject.getTechnicien().getAdresse());
            avatareTechnicien.setImageDrawable(getResources().getDrawable(R.drawable.photo_login_1));
            btnValidation.setText(getText(R.string.valider_votre_demande));
        }

        dateIntervension = viewFr.findViewById(R.id.date_mission);
        dateIntervension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                SimpleDateDialog cal = SimpleDateDialog.build().title(R.string.hint_date_mission).theme(R.style.SimpleDialogThemeProfile);
                cal.setCancelable(true);
                cal.show(A3techPostMissionFragment.this, TAG_CALENDAR_MISSION);
            }
        });
        btnGetLocation = viewFr.findViewById(R.id.getMyLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // create class object
                hideKeyboard();
                gps = new GPSTracker(getActivity());
                if (!PermissionsStuffs.canAccessLocation(getActivity())) {
                    requestPermissions(PermissionsStuffs.INITIAL_PERMS, PermissionsStuffs.INITIAL_REQUEST);
                }
                gpsGetLocation();

            }
        });

        btnGetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                SimpleDateDialog cal = SimpleDateDialog.build().title(R.string.hint_date_mission).theme(R.style.SimpleDialogThemeProfile);
                cal.setCancelable(true);
                cal.show(A3techPostMissionFragment.this, TAG_CALENDAR_MISSION);
            }
        });
        btnValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!verifyDataInserted()){
                    A3techCustomToastDialog.createToastDialog(getActivity(),getString(R.string.donnee_obligatoires), A3techCustomToastDialog.TOAST_ERROR, Toast.LENGTH_SHORT);
                    return;
                }
                if (mListener != null) {
                    hideKeyboard();
                    if (missionObject != null) {
                        mListener.actionNext(ACTION_SAVE_MISSION_INFO_FROM_TECH, populateMission());
                    } else if (categoryObject != null) {
                        mListener.actionNext(ACTION_SAVE_MISSION_INFO, populateMission());
                    }

                }
            }
        });
        return viewFr;
    }


    public void hideKeyboard() {
        if (titleMission != null) {
            hideKeyboard(titleMission);
        } else if (descriptionMission != null) {
            hideKeyboard(descriptionMission);
        } else if (locationMission != null) {
            hideKeyboard(locationMission);
        }
    }


    private void gpsGetLocation() {
        // check if GPS enabled
        final ProgressDialog waitingDialog = CustomProgressDialog.createProgressDialog(getActivity(), "");
        latitude = null;
        longitude = null;

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        cityName = addresses.get(0).getAddressLine(0); // adresse
                        stateName = addresses.get(0).getFeatureName(); // city
                        countryName = addresses.get(0).getCountryName(); // country

                        if (cityName != null) {
                            try {
                                getActivity().runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        locationMission.setText(cityName);
                                        waitingDialog.dismiss();
                                    }
                                });
                                Thread.sleep(300);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        waitingDialog.dismiss();
                    }

                    // \n is for new line
                } else {
                    waitingDialog.dismiss();
                    gps.showSettingsAlert();
                }
            }
        }).start();

    }


    private Boolean verifyDataInserted(){
        if (StringUtils.isBlank(dateIntervension.getText().toString())) {
            return Boolean.FALSE;
        }
        if (StringUtils.isBlank(locationMission.getText().toString())) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    private A3techMission populateMission() {
        A3techMission mission = null;
        if (missionObject != null) {
            mission = missionObject;
        } else {
            mission = new A3techMission();
            mission.setCategoryMission(categoryObject);
        }
        if (StringUtils.isBlank(titleMission.getText().toString())) {
            if (StringUtils.isNoneBlank(categoryLibelle)) {
                mission.setTitre(getString(R.string.mission_default_title) + " " + categoryLibelle);
            } else {
                mission.setTitre(getString(R.string.mission_default_title_without_cat));
            }
        } else {
            mission.setTitre(titleMission.getText().toString());
        }
        mission.setDateCreation(new Date().getTime());
        mission.setDateIntervention(((Calendar)dateIntervension.getTag()).getTime().getTime());
        mission.setClientMission(PreferencesValuesUtils.getConnectedUser(getActivity()));
        mission.setLatitude(String.valueOf(latitude));
        mission.setLongitude(String.valueOf(longitude));
        mission.setAdresse(locationMission.getText().toString());
        mission.setDescriptionMission(descriptionMission.getText().toString());
        mission.setStatut(A3techMissionStatut.CREE);
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
        SimpleDateDialog cal = SimpleDateDialog.build().title(R.string.hint_date_mission).minDate(new Date().getTime()).cancelable(true).neg(R.string.cancel).pos(R.string.ok_label).theme(R.style.SimpleDialogThemeProfile);
        cal.setCancelable(true);
        cal.show(this, TAG_CALENDAR_MISSION);
    }

    private void showAlarmDateTimePickup() {
        SimpleTimeDialog cal = SimpleTimeDialog.build().title(R.string.choose_time_mission).cancelable(true).neg(R.string.cancel).pos(R.string.ok_label).theme(R.style.SimpleDialogThemeProfile);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case PermissionsStuffs.CAMERA_REQUEST:
                if (PermissionsStuffs.canAccessCamera(getActivity())) {
                    ///doCameraThing();
                } else {
                    // bzzzt();
                }
                break;

            case PermissionsStuffs.CONTACTS_REQUEST:
                if (PermissionsStuffs.canAccessContacts(getActivity())) {
                    // doContactsThing();
                } else {
                    //  bzzzt();
                }
                break;

            case PermissionsStuffs.LOCATION_REQUEST:
                if (PermissionsStuffs.canAccessLocation(getActivity())) {
                    //gpsGetLocation();
                } else {
                    // bzzzt();
                }
                break;
        }
    }

}
