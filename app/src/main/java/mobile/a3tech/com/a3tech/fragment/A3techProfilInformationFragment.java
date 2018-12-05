package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import eltos.simpledialogfragment.form.Input;
import eltos.simpledialogfragment.form.SimpleFormDialog;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techDisplayTechniciensListeActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.activity.A3techTechnicienAvailabilityActivity;
import mobile.a3tech.com.a3tech.activity.A3techViewEditProfilActivity;
import mobile.a3tech.com.a3tech.adapter.A3techProfileInformationAdapter;
import mobile.a3tech.com.a3tech.animation.ProgresseAnimation;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.ObjectMenu;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.DateStuffs;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techProfilInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techProfilInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techProfilInformationFragment extends Fragment implements A3techViewEditProfilActivity.DataUpdateListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.detail_about_user)
    ExpandableTextView aboutUser;

    @BindView(R.id.rating_nbr)
    TextView ratingNumber;

    @BindView(R.id.rating_tech)
    RatingBar ratingUser;
    @BindView(R.id.rating_nbr_text)
    TextView numberOvReviews;

    @BindView(R.id.edit_action_about)
    ImageView editAboutAction;

    @BindView(R.id.edit_action_coordonnees)
    ImageView editCoordonneesAction;

    @BindView(R.id.email_value)
    TextView emailValue;

    @BindView(R.id.adresse_value)
    TextView adresseValue;


    @BindView(R.id.phone_value)
    TextView phoneValue;

    @BindView(R.id.call_user)
    ImageView callPhoneAction;

    @BindView(R.id.send_msg)
    ImageView sendMsgAction;


    @BindView(R.id.date_naissance_value)
    TextView dateNaissanceValue;


    @BindView(R.id.date_inscription_value)
    TextView dateInscriptionValue;

    @BindView(R.id.taux_completion_profil)
    TextView tauxCompletionProfil;

    @BindView(R.id.progress_complete_profil)
    ProgressBar progressCompletionProfil;

    @BindView(R.id.label_completion_profil)
    TextView labelCompletionProfil;

    private A3techUser user;
    private A3techViewEditProfilActivity activity;
    private OnFragmentInteractionListener mListener;

    public A3techProfilInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment A3techProfilInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techProfilInformationFragment newInstance(A3techUser user) {
        A3techProfilInformationFragment fragment = new A3techProfilInformationFragment();
        Bundle args = new Bundle();
        args.putString(A3techViewEditProfilActivity.ARG_USER_OBJECT, new Gson().toJson(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        String jsonMyObject = null;
        Bundle extras = getArguments();
        if (extras != null) {
            jsonMyObject = extras.getString(A3techViewEditProfilActivity.ARG_USER_OBJECT);
        }
        if (jsonMyObject != null) {
            user = new Gson().fromJson(jsonMyObject, A3techUser.class);
        }
    }

    public void startAlphaAnimation(View v, long duration, int visibility) {
        Animation alphaAnimation = (visibility == View.VISIBLE)
                ? AnimationUtils.loadAnimation(activity, R.anim.scale_up)
                : AnimationUtils.loadAnimation(activity, R.anim.scale_down);

        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        v.startAnimation(alphaAnimation);
        v.setVisibility(visibility);

    }

    public void enablEditMode(Boolean mode) {
        editAboutAction.setVisibility(mode ? View.VISIBLE : View.GONE);
        editCoordonneesAction.setVisibility(mode ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFr = inflater.inflate(R.layout.fragment_a3tech_profil_information, container, false);
        ButterKnife.bind(this, viewFr);
        prepareListeCoordonne();
        activity = (A3techViewEditProfilActivity) getActivity();  // <--- add this line here
        setRetainInstance(true);
        actionsEdition();
        initProgresseCompletionProfil();
        return viewFr;
    }

    private void actionsEdition() {
        editAboutAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAboutEditDialogue(aboutUser.getText().toString());
            }
        });

        editCoordonneesAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayCoordonneeEditDialogue(user);
            }
        });
    }


    private void initProgresseCompletionProfil(){
        //TODO get taux profile from server
        ProgresseAnimation anim = new ProgresseAnimation(progressCompletionProfil, tauxCompletionProfil, 0, 75);
        anim.setDuration(1000);
        progressCompletionProfil.startAnimation(anim);
        labelCompletionProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(getActivity(), A3techTechnicienAvailabilityActivity.class);
                startActivity(mainIntent);
            }
        });

    }

    private static final String TAG_EDIT_ABOUT_INPUT = "TAG_EDIT_ABOUT_INPUT";
    private static final String TAG_EDIT_EMAIL_INPUT = "TAG_EDIT_EMAIL_INPUT";
    private static final String TAG_EDIT_PHONE_INPUT = "TAG_EDIT_PHONE_INPUT";
    private static final String TAG_EDIT_ADRESSE_INPUT = "TAG_EDIT_ADRESSE_INPUT";
    private static final String TAG_EDIT_DATE_NAISSANCE_INPUT = "TAG_EDIT_DATE_NAISSANCE_INPUT";
    private static final String TAG_EDIT_ABOUT_DIALOG = "TAG_EDIT_ABOUT_DIALOG";

    private void displayAboutEditDialogue(String aboutOld) {
        SimpleFormDialog.build().title(getResources().getString(R.string.edit_about_profil)).cancelable(true).neg(R.string.cancel).pos(R.string.save)
                .fields(Input.plain(TAG_EDIT_ABOUT_INPUT).required(true).text(aboutOld)
                        .hint(R.string.hint_edit_add_about_profil)
                        .inputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE)).theme(R.style.SimpleDialogThemeProfile)
                .show(getActivity(), TAG_EDIT_ABOUT_DIALOG);
    }

    private void displayCoordonneeEditDialogue(A3techUser user) {
        SimpleFormDialog.build().title(getResources().getString(R.string.coordonnees)).cancelable(true).neg(R.string.cancel).pos(R.string.save)
                .fields(Input.phone(TAG_EDIT_PHONE_INPUT).required(true).text(user.getTelephone())
                                .hint(R.string.hint_edit_add_phone_profil)
                                .inputType(InputType.TYPE_CLASS_PHONE), Input.phone(TAG_EDIT_ADRESSE_INPUT).required(true).text(user.getAdresse())
                                .hint(R.string.hint_edit_add_adresse_profile)
                                .inputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE),
                        Input.phone(TAG_EDIT_DATE_NAISSANCE_INPUT).required(true).text(DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, new Date(user.getDateNaissance())))
                                .hint(R.string.hint_edit_add_date_naissance)
                                .inputType(InputType.TYPE_CLASS_DATETIME)).theme(R.style.SimpleDialogThemeProfile)
                .show(getActivity(), TAG_EDIT_ABOUT_DIALOG);
    }


    private List prepareListeCoordonne() {
        final List listeRetour = new ArrayList();

        if (user == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            String connectedUser = prefs.getString("identifiant", "");
            String password = prefs.getString("password", "");
            UserManager.getInstance().getProfil(connectedUser, password, new DataLoadCallback() {
                @Override
                public void dataLoaded(Object data, int method, int typeOperation) {
                    switch (method) {
                        case Constant.KEY_USER_MANAGER_GET_PROFIL:
                            A3techUser userV = (A3techUser) data;
                            aboutUser.setText(getString(R.string.lorem));
                            emailValue.setText(userV.getEmail());
                            phoneValue.setText(userV.getTelephone());
                            adresseValue.setText(userV.getAdresse());
                            dateNaissanceValue.setText(DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, new Date(userV.getDateNaissance())));
                            dateInscriptionValue.setText(DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, new Date(userV.getDateCreation())));
                            break;
                    }


                }

                @Override
                public void dataLoadingError(int errorCode) {

                }
            });
        } else {
            aboutUser.setText(getString(R.string.lorem));
            emailValue.setText(user.getEmail());
            phoneValue.setText(user.getTelephone());
            adresseValue.setText(user.getAdresse());
            if (user.getDateNaissance() != null)
                dateNaissanceValue.setText(DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, new Date(user.getDateNaissance())));
            if (user.getDateCreation() != null)
                dateInscriptionValue.setText(DateStuffs.dateToString(DateStuffs.SIMPLE_DATE_FORMAT, new Date(user.getDateCreation())));

        }

        return listeRetour;
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
        ((A3techViewEditProfilActivity) context).setmDataUpdateListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDataUpdate(Boolean modeEdit) {
        enablEditMode(modeEdit);
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
