package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techChooseSignInOption.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techChooseSignInOption#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techChooseSignInOption extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int ACTION_SIGN_IN_PAR_EMAIL = 1;
    public static final int ACTION_SIGN_IN_PAR_FB = 2;
    public static final int ACTION_SIGN_UP = 3;
    public static final int ACTION_RESET_PASS = 4;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button resetPassword;
    Button facebookLogIn;
    Button emailLogIn;
    TextView signin;

    private OnFragmentInteractionListener mListener;

    public A3techChooseSignInOption() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techChooseSignInOption.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techChooseSignInOption newInstance(String param1, String param2) {
        A3techChooseSignInOption fragment = new A3techChooseSignInOption();
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
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_choose_sign_in_option, container, false);
        resetPassword = viewFr.findViewById(R.id.reset_password);
        facebookLogIn = viewFr.findViewById(R.id.btnfacebookConnection);
        emailLogIn = viewFr.findViewById(R.id.btn_connect_email);
        signin = viewFr.findViewById(R.id.signin_action);
        viewFr.setFocusableInTouchMode(true);
        viewFr.requestFocus();

        emailLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.actionNext(ACTION_SIGN_IN_PAR_EMAIL, true);
            }
        });

        facebookLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.actionNext(ACTION_SIGN_IN_PAR_FB, true);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.actionNext(ACTION_SIGN_UP, true);
            }
        });


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.actionNext(ACTION_RESET_PASS, true);
            }
        });

        return  viewFr;
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
}
