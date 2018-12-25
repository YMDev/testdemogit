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

import com.google.gson.Gson;

import java.util.List;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.test.SimpleAdapterReviews;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.PreferencesValuesUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techReviewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techReviewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techReviewsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_OBJECT_USER = "ARG_OBJECT_USER";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private A3techUser userReviews;

    private RecyclerView recyclerViewReviews;
    private OnFragmentInteractionListener mListener;

    public A3techReviewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment A3techReviewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techReviewsFragment newInstance(A3techUser user) {
        A3techReviewsFragment fragment = new A3techReviewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_OBJECT_USER, new Gson().toJson(user));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String jsonUser = getArguments().getString(ARG_OBJECT_USER);
            if(jsonUser != null){
                userReviews = new Gson().fromJson(jsonUser, A3techUser.class);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_reviews, container, false);
        recyclerViewReviews = viewFr.findViewById(R.id.recycle_reviews);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String connectedUser = prefs.getString("identifiant", "");
        String password = prefs.getString("password", "");
        UserManager.getInstance().getUserReviews(userReviews.getId()+"", password, new DataLoadCallback() {
            @Override
            public void dataLoaded(Object data, int method, int typeOperation) {
                switch (method) {
                    case Constant.KEY_USER_MANAGER_GET_PROFIL:
                        List<Avis> userV = (List) data;
                        SimpleAdapterReviews mAdapter = new SimpleAdapterReviews(getActivity(), userV, getActivity());
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
                        recyclerViewReviews.setLayoutManager(mLayoutManager);
                        recyclerViewReviews.setItemAnimator(new DefaultItemAnimator());
                        recyclerViewReviews.setAdapter(mAdapter);
                        break;
                }


            }

            @Override
            public void dataLoadingError(int errorCode) {

            }
        });
        return viewFr;
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
