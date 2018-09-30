package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techProfileInformationAdapter;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.ObjectMenu;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.ExpandableTextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techProfilInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techProfilInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techProfilInformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recycleProfil;
    private OnFragmentInteractionListener mListener;

    public A3techProfilInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techProfilInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techProfilInformationFragment newInstance(String param1, String param2) {
        A3techProfilInformationFragment fragment = new A3techProfilInformationFragment();
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
       View viewFr = inflater.inflate(R.layout.fragment_a3tech_profil_information, container, false);
       recycleProfil = viewFr.findViewById(R.id.recyle_profile_informations);
      prepareListeCoordonne();
       return viewFr;
    }


    private List prepareListeCoordonne(){
        final List listeRetour = new ArrayList();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String connectedUser = prefs.getString("identifiant", "");
        String password = prefs.getString("password", "");
        UserManager.getInstance().getProfil(connectedUser, password, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                switch (method) {
                    case Constant.KEY_USER_MANAGER_GET_PROFIL:
                        User user = (User) data;
                        listeRetour.add(new ObjectMenu(user.getEmail(), "Email", 1, 0));
                        listeRetour.add(new ObjectMenu(user.getTelephone(), "Telephone", 2, 0));
                        listeRetour.add(new ObjectMenu(user.getAdresse(), "Adresse", 3, 0));
                        break;
                }

                A3techProfileInformationAdapter  mAdapter = new A3techProfileInformationAdapter(getActivity(), listeRetour);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recycleProfil.setLayoutManager(mLayoutManager);
                recycleProfil.setItemAnimator(new DefaultItemAnimator());
                recycleProfil.setAdapter(mAdapter);
            }

            @Override
            public void dataLoadingError(int errorCode) {

            }
        });
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
