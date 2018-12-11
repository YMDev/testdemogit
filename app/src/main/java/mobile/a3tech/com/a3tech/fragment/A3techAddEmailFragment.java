package mobile.a3tech.com.a3tech.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.regex.Pattern;

import mobile.a3tech.com.a3tech.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techAddEmailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techAddEmailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techAddEmailFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final int ACTION_TYPE_EMAIL = 5;
    private String mParam1;
    private String mParam2;

    private EditText email;
    private EditText phone;
    private Button next;
    private OnFragmentInteractionListener mListener;

    public A3techAddEmailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techAddEmailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techAddEmailFragment newInstance(String param1, String param2) {
        A3techAddEmailFragment fragment = new A3techAddEmailFragment();
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
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_add_email_v2, container, false);
        email = viewFr.findViewById(R.id.input_email);
        phone = viewFr.findViewById(R.id.input_phone);
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(email, InputMethodManager.SHOW_FORCED);
        next = viewFr.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailSaisi = email.getText() != null ? email.getText().toString() :"";
                String phoneSaisi = phone.getText() != null ? phone.getText().toString() :"";
                if(StringUtils.isBlank(emailSaisi)){
                    email.setError(getString(R.string.error_email_empty));
                    email.requestFocus();
                    return;
                }
                if(StringUtils.isBlank(phoneSaisi)){
                    phone.setError(getString(R.string.error_phone_empty));
                    phone.requestFocus();
                    return;
                }


                if(!isValidEmailAddress(emailSaisi)){
                    email.setError(getString(R.string.error_email_not_valide));
                    email.requestFocus();
                    return;
                }
                HashMap mapData  = new HashMap();
                mapData.put("EMAIL", emailSaisi);
                mapData.put("PHONE", phoneSaisi);
                mListener.actionNext(ACTION_TYPE_EMAIL, mapData);
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
    private boolean isValidEmailAddress(String emailAddress) {
        return Pattern
                .compile(
                        "^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+.([a-zA-Z])+([a-zA-Z])+",
                        Pattern.CASE_INSENSITIVE).matcher(emailAddress).matches();
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
