package mobile.a3tech.com.a3tech.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.BottomBarAdapter;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.SimpleAdapterTechnicien;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.NoSwipePager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techDisplayTechniciensPArentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techDisplayTechniciensPArentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techDisplayTechniciensPArentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SELECTED_MISSION = "param1";
    private static final String ARG_SELECTED_CAT = "ARG_SELECTED_CAT";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Mission mission;
    private Categorie categorie;

    private NoSwipePager viewPager;
    private AHBottomNavigation bottomNavigation;
    private BottomBarAdapter pagerAdapter;

    private OnFragmentInteractionListener mListener;

    public A3techDisplayTechniciensPArentFragment() {
        // Required empty public constructor
    }


    List<User> listeOfTechToDisplay;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment A3techDisplayTechniciensPArentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techDisplayTechniciensPArentFragment newInstance(Mission missionSelcted) {
        A3techDisplayTechniciensPArentFragment fragment = new A3techDisplayTechniciensPArentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTED_MISSION, new Gson().toJson(missionSelcted));
        fragment.setArguments(args);
        return fragment;
    }


    public static A3techDisplayTechniciensPArentFragment newInstance(Categorie catSelected) {
        A3techDisplayTechniciensPArentFragment fragment = new A3techDisplayTechniciensPArentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SELECTED_CAT, new Gson().toJson(catSelected));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonMission = getArguments().getString(ARG_SELECTED_MISSION);
            if (jsonMission != null) mission = new Gson().fromJson(jsonMission, Mission.class);


            String jsonCat = getArguments().getString(ARG_SELECTED_CAT);
            if (jsonCat != null) categorie = new Gson().fromJson(jsonCat, Categorie.class);


            if (mission == null) {
                mission = new Mission();
            }

            if (categorie != null) {
                mission.setCategoryMission(categorie);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFr = inflater.inflate(R.layout.fragment_a3tech_display_techniciens_parent, container, false);
        setupViewPager(viewFr);
        bottomNavigation = (AHBottomNavigation) viewFr.findViewById(R.id.display_tech_bottom_navigation);
        bottomNavigation.setTranslucentNavigationEnabled(false);
        setupBottomNavStyle();
        //createFakeNotification();
        addBottomNavigationItems();
        bottomNavigation.setCurrentItem(0);
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (!wasSelected)
                    viewPager.setCurrentItem(position);

                if(position == 1){
                    if(mListener != null){
                        mListener.actionToolbar(true);
                    }
                }else{
                    if(mListener != null){
                        mListener.actionToolbar(false);
                    }
                }
                return true;
            }
        });
        return viewFr;
    }

    private void setupViewPager(View rootView) {
        viewPager = (NoSwipePager) rootView.findViewById(R.id.display_tech_view_pager_navigation);
        viewPager.setPagingEnabled(false);
        getListOFTechToDisplay();

    }

    public void deleteMapInFragment(){
        ((A3techDisplayTechInMapFragment) pagerAdapter.getItem(1)).destroyMap();
    }

    private void getListOFTechToDisplay() {
        final ProgressDialog dd = CustomProgressDialog.createProgressDialog(getActivity(), "");
        //TODO get location of connected user not mission
        UserManager.getInstance().getTechnicienNearLocation(mission.getLatitude(), mission.getLongitude(), mission.getAdresse(), new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                listeOfTechToDisplay = (List<User>) data;
                if (listeOfTechToDisplay == null) listeOfTechToDisplay = new ArrayList<>();
                pagerAdapter = new BottomBarAdapter(getFragmentManager());
                pagerAdapter.addFragments(A3techAffecterTechnicienFragment.newInstance(mission, listeOfTechToDisplay));
                pagerAdapter.addFragments(A3techDisplayTechInMapFragment.newInstance(mission, listeOfTechToDisplay));

                viewPager.setAdapter(pagerAdapter);
                dd.dismiss();
                return;
            }

            @Override
            public void dataLoadingError(int errorCode) {
                dd.dismiss();
            }
        });
    }

    /**
     * Adds styling properties to {@link AHBottomNavigation}
     */
    private void setupBottomNavStyle() {
        /*
        Set Bottom Navigation colors. Accent color for active item,
        Inactive color when its view is disabled.
        Will not be visible if setColored(true) and default current item is set.
         */
        bottomNavigation.setDefaultBackgroundColor(Color.WHITE);
        bottomNavigation.setAccentColor(fetchColor(R.color.accent_500));
        bottomNavigation.setInactiveColor(fetchColor(R.color.semi_transparent));

        // Colors for selected (active) and non-selected items.
        bottomNavigation.setColoredModeColors(getResources().getColor(R.color.white),
                fetchColor(R.color.grey));

        //  Enables Reveal effect
        bottomNavigation.setColored(true);

        //  Displays item Title always (for selected and non-selected items)
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }

    /**
     * Adds (items) {@link AHBottomNavigationItem} to {@link AHBottomNavigation}
     * Also assigns a distinct color to each Bottom Navigation item, used for the color ripple.
     */
    private void addBottomNavigationItems() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.display_tech_tab_2, R.drawable.a3tech_display_as_list, R.color.colorPrimary);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.display_tech_tab_1, R.drawable.a3tech_display_as_maps, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);

    }


    private void prepareElementToolbar() {

    }

    /**
     * Simple facade to fetch color resource, so I avoid writing a huge line every time.
     *
     * @param color to fetch
     * @return int color value.
     */
    private int fetchColor(@ColorRes int color) {
        return ContextCompat.getColor(getContext(), color);
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
        void actionToolbar(boolean hide);
    }
}
