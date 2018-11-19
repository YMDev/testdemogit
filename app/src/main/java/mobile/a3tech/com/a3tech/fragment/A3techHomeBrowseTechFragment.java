package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techSearchMissionsResultsActivity;
import mobile.a3tech.com.a3tech.adapter.A3techSelectMissionCategoryAdapter;
import mobile.a3tech.com.a3tech.manager.CategorieManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.NetworkUtils;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techHomeBrowseTechFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techHomeBrowseTechFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techHomeBrowseTechFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int ACTION_SELECT_CATEGORY_BROWSE_TECH = 2;
    public static final String THIS_FRAGMENT_2 = "A3techHomeBrowseTechFragment";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recycleSelectCategory;
    private OnFragmentInteractionListener mListener;
    private RelativeLayout networkOn, networkDown;
    private TextView tapToRetry;
    public A3techHomeBrowseTechFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techHomeBrowseTechFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techHomeBrowseTechFragment newInstance(String param1, String param2) {
        A3techHomeBrowseTechFragment fragment = new A3techHomeBrowseTechFragment();
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
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_home_browse_tech, container, false);
        recycleSelectCategory = viewFr.findViewById(R.id.recycle_select_category_browse_tech);

        networkDown = viewFr.findViewById(R.id.network_down);
        networkOn = viewFr.findViewById(R.id.network_on);
        networkOn.setVisibility(View.VISIBLE);
        networkDown.setVisibility(View.GONE);


      /*  tapToRetry = viewFr.findViewById(R.id.tap_to_refresh_label);

        tapToRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareListeCategories();
            }
        });*/


        prepareListeCategories();
        viewFr.setFocusableInTouchMode(true);
        viewFr.requestFocus();
        return viewFr;
    }
    private List prepareListeCategories(){
        final List<Categorie> listeRetour = new ArrayList();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String connectedUser = prefs.getString("identifiant", "");
        String password = prefs.getString("password", "");
        String lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
        final  ProgressDialog waitingDialogue =  CustomProgressDialog.createProgressDialog(getActivity(), "");
        CategorieManager.getInstance().listeCategories(null,null, null, null,lang,connectedUser,password, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                switch (method) {
                    case Constant.KEY_CATEGORIE_MANAGER_LISTE_CATEGORIE:
                        listeRetour.addAll((List<Categorie>) data);
                        break;
                }

                A3techSelectMissionCategoryAdapter mAdapter = new A3techSelectMissionCategoryAdapter(getActivity(), listeRetour, A3techHomeBrowseTechFragment.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                recycleSelectCategory.setLayoutManager(mLayoutManager);
                recycleSelectCategory.setItemAnimator(new DefaultItemAnimator());
                recycleSelectCategory.setAdapter(mAdapter);
                waitingDialogue.dismiss();
            }

            @Override
            public void dataLoadingError(int errorCode) {
                waitingDialogue.dismiss();
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
        void backAction();
        void actionNext(Integer typeAction, Object data);
    }


    @Override
    public void onResume() {
        super.onResume();
        if(NetworkUtils.isNetworkAvailable(getActivity())){
            onNetworkUp();
        }
    }

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }

    public void onNetworkDown() {
        networkOn.setVisibility(View.GONE);
        networkDown.setVisibility(View.VISIBLE);
    }

    public void onNetworkUp() {
        networkOn.setVisibility(View.VISIBLE);
        networkDown.setVisibility(View.GONE);
        prepareListeCategories();
    }
}
