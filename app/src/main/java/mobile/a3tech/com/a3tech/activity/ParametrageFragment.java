package mobile.a3tech.com.a3tech.activity;

import com.google.android.gcm.GCMRegistrar;


import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.utils.Constant;

public class ParametrageFragment extends Fragment  {

	RelativeLayout idParametrage_rl_general ;
	RelativeLayout idParametrage_rl_langue ;
	RelativeLayout idParametrage_rl_notification ;
	RelativeLayout idParametrage_rl_securite ;
	RelativeLayout idParametrage_rl_deconnexion ;
	RelativeLayout idParametrage_rl_apropos ;
	RelativeLayout idParametrage_rl_reseauxSociaux ;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.parametrage_fragment,
				container, false);
		rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		return rootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		 idParametrage_rl_general  = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_general);
		 idParametrage_rl_langue  = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_langue);
		 idParametrage_rl_notification  = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_notification);
		 idParametrage_rl_securite = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_securite);
		 idParametrage_rl_deconnexion  = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_deconnexion);
		 idParametrage_rl_apropos = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_apropos);
		 idParametrage_rl_reseauxSociaux = (RelativeLayout) getView().findViewById(R.id.idParametrage_rl_reseauxSociaux);
		 idParametrage_rl_general.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), ProfilFragment.class));
			}
		});
		 idParametrage_rl_notification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), NotificationsFragment.class));
			}
		});
		 idParametrage_rl_apropos.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getActivity(), AproposFragment.class));
			}
		});
		 idParametrage_rl_reseauxSociaux.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), ReseauxFragment.class));
				}
			});
		 idParametrage_rl_deconnexion.setOnClickListener(deconnectionListener);
		 idParametrage_rl_langue.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), LangueFragment.class));
				}
			});
		 
		 idParametrage_rl_securite.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getActivity(), SecuriteFragment.class));
				}
			});
	}
	
	 private OnClickListener deconnectionListener =new OnClickListener() {
	        public void onClick(View v) {
	            String conMode = PreferenceManager.getDefaultSharedPreferences(ParametrageFragment.this.getActivity()).getString("conMode", "");
	           deconnexion();
	            if (conMode.equals("facebook")) {
	                Intent mainIntent = new Intent(ParametrageFragment.this.getActivity(), FacebookActivity.class);
	                Bundle bundle = new Bundle();
	                bundle.putString(Constant.RESULT_ACTION_CODE_FACEBOOK_SRC, Constant.RESULT_ACTION_CODE_FACEBOOK_SRC_LOGOUT);
	                mainIntent.putExtras(bundle);
	                getActivity().startActivityForResult(mainIntent,  Constant.SPLASH_FACEBOOK_REQUEST_CODE_LOGOUT);
	               
	            }else{
	            	startActivity(new Intent(ParametrageFragment.this.getActivity(), LoginActivity.class));
	            	getActivity().finish();
	            }
	        }
	    };
	
	 public void deconnexion() {
	        Editor editor = PreferenceManager.getDefaultSharedPreferences(this.getActivity()).edit();
	        editor.putString("MyCredentials", "");
	        editor.putString("facebookId", "");
	        editor.putString("conMode", "");
	        editor.putString("ApplicationLanguage", "");
	        editor.putString("identifiant", "");
	        
	        GCMRegistrar.checkDevice(this.getActivity());
	        GCMRegistrar.checkManifest(this.getActivity());
	        GCMRegistrar.unregister(this.getActivity());
	        editor.commit();
	    }

}
