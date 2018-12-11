package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import java.util.HashMap;
import java.util.regex.Pattern;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.ValidationPatternUtils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techSignInEmailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techSignInEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techSignInEmailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String ARG_PARAM1 = "param1";
    public static final String ARG_PARAM2 = "param2";
    public static final int ACTION_CONNEXION = 933;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputLayout email;
    private TextInputLayout pass;
    private Button btnConnexion;

    private OnFragmentInteractionListener mListener;

    public A3techSignInEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techSignInEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techSignInEmailFragment newInstance(String param1, String param2) {
        A3techSignInEmailFragment fragment = new A3techSignInEmailFragment();
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

        View viewFr = inflater.inflate(R.layout.fragment_a3tech_sign_in_email, container, false);
        email = viewFr.findViewById(R.id.input_layout_username);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                email.getEditText().requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });
        pass = viewFr.findViewById(R.id.input_layout_password);
        btnConnexion = viewFr.findViewById(R.id.btn_start_connexion);

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //TODO VERIFICATION
                if(!ValidationPatternUtils.isValideEmail(email.getEditText().getText().toString())){
                    email.getEditText().setError(getString(R.string.error_email_not_valide));
                    email.requestFocus();
                    return;
                }
               /* if(!ValidationPatternUtils.isValidPassword(pass.getEditText().getText().toString())){
                    pass.getEditText().setError(getString(R.string.error_password_empty));
                    return;
                }*/
                HashMap hashData = new HashMap();
                hashData.put("EMAIL", email.getEditText().getText().toString());
                hashData.put("PASS", pass.getEditText().getText().toString());
                mListener.actionNext(ACTION_CONNEXION,hashData);
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
        void actionNext(Integer typeAction, Object data);
    }
}
