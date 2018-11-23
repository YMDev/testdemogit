
package mobile.a3tech.com.a3tech.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TextInputLayout;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import org.apache.commons.lang3.StringUtils;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.activity.A3techCreateAccountActivity;
import mobile.a3tech.com.a3tech.model.A3techUser;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link A3techAddUserNameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link A3techAddUserNameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A3techAddUserNameFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int ACTION_TYPE_USERNAME = 4;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextInputLayout username, userPname;
    private Button next;
    private OnFragmentInteractionListener mListener;

    public A3techAddUserNameFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A3techAddUserNameFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static A3techAddUserNameFragment newInstance(String param1, String param2) {
        A3techAddUserNameFragment fragment = new A3techAddUserNameFragment();
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
        View viewFr = inflater.inflate(R.layout.fragment_a3tech_add_user_name, container, false);
        username = viewFr.findViewById(R.id.input_layout_username);
        userPname = viewFr.findViewById(R.id.input_layout_userpname);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                username.getEditText().requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        next = viewFr.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailSaisi = username.getEditText().getText() != null ? username.getEditText().getText().toString() :"";
                if(StringUtils.isBlank(emailSaisi)){
                    username.getEditText().setError(getString(R.string.error_username_empty));
                    return;
                }

                String pnameSaisi = userPname.getEditText().getText() != null ? userPname.getEditText().getText().toString() :"";
                if(StringUtils.isBlank(pnameSaisi)){
                    userPname.getEditText().setError(getString(R.string.error_userpname_empty));
                    return;
                }
                A3techUser userTmp = new A3techUser();
                userTmp.setNom(username.getEditText().getText().toString());
                userTmp.setPrenom(userPname.getEditText().getText().toString());
                mListener.actionNext(ACTION_TYPE_USERNAME, userTmp);
            }
        });
        /*viewFr.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    mListener.backAction(A3techCreateAccountActivity.DEST_SELECT_ACCOUNT);
                    return true;
                }
                return false;
            }
        });*/

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

        void backAction(int destination);

        void actionNext(Integer typeAction, Object data);
    }
}
