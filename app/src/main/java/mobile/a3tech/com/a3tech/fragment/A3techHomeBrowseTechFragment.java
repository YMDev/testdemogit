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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.A3techSelectMissionCategoryAdapter;
import mobile.a3tech.com.a3tech.manager.CategorieManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recycleSelectCategory;
    private OnFragmentInteractionListener mListener;

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
        prepareListeCategories();
        viewFr.setFocusableInTouchMode(true);
        viewFr.requestFocus();
        viewFr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_UP && i == KeyEvent.KEYCODE_BACK){
                    mListener.backAction();
                    return true;
                }
                return false;
            }
        });

        return viewFr;
    }
    private List prepareListeCategories(){
        final List<Categorie> listeRetour = new ArrayList();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String connectedUser = prefs.getString("identifiant", "");
        String password = prefs.getString("password", "");
        String lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
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
        void backAction();
        void actionNext(Integer typeAction, Object data);
    }

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }
}
