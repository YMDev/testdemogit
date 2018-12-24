package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techAccountActivity;
import mobile.a3tech.com.a3tech.activity.A3techHomeActivity;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.ObjectMenu;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTest;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;
import mobile.a3tech.com.a3tech.view.A3techCustomToastDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techHomeAccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techHomeAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

/**
 * Fragment pour afficher le compte de l'utilisateur connecté
 */
public class A3techHomeAccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean isAccountDisplayed = false;

    private RecyclerView recycyleInformation;
    private RecyclerView recycyleAccountSetting;

    private TextView soldeDisponibleForConnectedUser;
    private ProgressBar progresseSoldeDispo;
    private OnFragmentInteractionListener mListener;
    View viewFr;

    public A3techHomeAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techHomeAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techHomeAccountFragment newInstance(String param1, String param2) {
        A3techHomeAccountFragment fragment = new A3techHomeAccountFragment();
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
         viewFr = inflater.inflate(R.layout.fragment_a3tech_home_account, container, false);
        initAccountInfoFragment();
         return viewFr;
    }


    private void initAccountInfoFragment(){
        recycyleInformation = viewFr.findViewById(R.id.recycle_informtaions);
        recycyleInformation.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SimpleAdapterTest adapter = new SimpleAdapterTest(getActivity(), prepareListeObjectToshow(1));
        adapter.setmDecListener((A3techAccountActivity)getActivity());
        recycyleInformation.setAdapter(adapter);

        recycyleAccountSetting = viewFr.findViewById(R.id.recycle_account_setting);
        recycyleAccountSetting.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        SimpleAdapterTest adapterrecycyleAccountSetting = new SimpleAdapterTest(getActivity(), prepareListeObjectToshow(2));
        adapterrecycyleAccountSetting.setmDecListener((A3techAccountActivity)getActivity());
        recycyleAccountSetting.setAdapter(adapterrecycyleAccountSetting);


        soldeDisponibleForConnectedUser = viewFr.findViewById(R.id.solde_valable);
        soldeDisponibleForConnectedUser.setVisibility(View.GONE);
        progresseSoldeDispo = viewFr.findViewById(R.id.progress_solde_valable);
        progresseSoldeDispo.setVisibility(View.VISIBLE);

        fetchSoldeTech();
    }


    private void fetchSoldeTech(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // get solde disponible
                UserManager.getInstance().fetchSoldeDisponible(PreferencesValuesUtils.getConnectedUser(getActivity()).getId(), new DataLoadCallback() {
                    @Override
                    public void dataLoaded(Object data, int method, int typeOperation) {
                        Double solde = (Double) data;
                        soldeDisponibleForConnectedUser.setText(solde + " DH");
                        progresseSoldeDispo.setVisibility(View.GONE);
                        soldeDisponibleForConnectedUser.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void dataLoadingError(int errorCode) {
                        soldeDisponibleForConnectedUser.setText( "N/A"+ " DH");
                        progresseSoldeDispo.setVisibility(View.GONE);
                        soldeDisponibleForConnectedUser.setVisibility(View.VISIBLE);
                        A3techCustomToastDialog.createToastDialog(getActivity(), getString(R.string.txtInscription_msg_verification_connexion_internet), Toast.LENGTH_SHORT, A3techCustomToastDialog.TOAST_ERROR);
                    }
                });
            }
        });
    }
    private List<ObjectMenu> prepareListeObjectToshow(int typeRecycle) {
        List<ObjectMenu> listeRetour = new ArrayList<>();
        switch (typeRecycle) {
            case 1:
                //Information
                listeRetour.add(new ObjectMenu("Notification", "Les notifications du jour", 1, 1));
                //listeRetour.add(new ObjectMenu("Demander une mission", "chercher parmi nos meilleurs techniciens", 1, 2, ObjectMenu.CODE_DEMANDER_MISSION));
                listeRetour.add(new ObjectMenu("Recever un crédit gratuit", "Inviter vos amis est gagner 200 MAD", 1, 3));
                break;
            case 2:
                //Information
                listeRetour.add(new ObjectMenu("Devenez un Technicien 3TEC", "Inscrivez-vous et bénificier de nos services", 2, 1, ObjectMenu.CODE_DEVENIR_TECH));
                listeRetour.add(new ObjectMenu("Recommander un Technicien", "Recommander un Technicien à 3TEC et gangner un solde de 100 dh", 2, 2, ObjectMenu.CODE_RECOMMANDER_TECH));
                listeRetour.add(new ObjectMenu("Disponibilité", "", 2, 5,ObjectMenu.CODE_DISPO));
                listeRetour.add(new ObjectMenu("Deconnexion", "", 2, 4,ObjectMenu.CODE_DECONNEXION));
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

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isAccountDisplayed ) {
            if(getActivity() != null){
                initAccountInfoFragment();
                isAccountDisplayed = true;
            }

        }
    }

    public void onNetworkDown() {
        soldeDisponibleForConnectedUser.setText( "N/A"+ " DH");
        progresseSoldeDispo.setVisibility(View.GONE);
        soldeDisponibleForConnectedUser.setVisibility(View.VISIBLE);
    }

    public void onNetworkUp() {
       fetchSoldeTech();
    }
}
