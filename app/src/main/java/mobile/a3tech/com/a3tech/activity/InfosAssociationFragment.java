package mobile.a3tech.com.a3tech.activity;

import java.util.regex.Pattern;


import org.apache.commons.lang3.StringEscapeUtils;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.manager.AssociationManager;
import mobile.a3tech.com.a3tech.model.Association;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;

public class InfosAssociationFragment extends Fragment implements
		DataLoadCallback {

	
	String idAssociation;
	View rootView;
	TextView idInfoAssociation_textViewtDescription ;
	TextView idInfoAssociation_textViewtCaValue ;
	TextView idInfoAssociation_textViewtTelValue ;
	TextView idInfoAssociation_textViewtSecteurValue ;
	TextView idInfoAssociation_textViewtEffectifValue ;
	TextView idInfoAssociation_textViewtAdresseValue ;
	TextView idInfoAssociation_textViewtSiteValue ;
	TextView idInfoAssociation_textViewSocieteValue ;
	String connectedUser = "";
	String password = "" ;

	public static InfosAssociationFragment newInstance() {
		return new InfosAssociationFragment();
	}

	@Override
	public void dataLoaded(Object data, int method, int j) {
		Association association = (Association) data;
		if(association.getDescription()!=null && !association.getDescription().equals(""))
		
		 idInfoAssociation_textViewtDescription.setText(Html.fromHtml(StringEscapeUtils.unescapeHtml3(association.getDescription())));
		 idInfoAssociation_textViewtCaValue.setText(association.getCa());
		 idInfoAssociation_textViewtTelValue.setText(association.getTel());
		 idInfoAssociation_textViewtSecteurValue.setText(association.getSecteur());
		 idInfoAssociation_textViewtEffectifValue.setText(association.getEffectif());
		 idInfoAssociation_textViewtAdresseValue.setText(association.getAdresse());
		 idInfoAssociation_textViewtSiteValue.setText(association.getSite());
		 idInfoAssociation_textViewSocieteValue.setText(association.getSociete());
		
		 idInfoAssociation_textViewtSiteValue.setMovementMethod(LinkMovementMethod.getInstance());
//		this.IdDetailAssociation_tv_info.getSettings()
//				.setDefaultTextEncodingName("utf-8");
//		this.IdDetailAssociation_tv_info.loadUrl(Constant.NOOVOO_ENTREPRISE_URL
//				+ association.getDescription());

	}

	@Override
	public void dataLoadingError(int errorCode) {

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getActivity().getIntent().getExtras();
//		IdDetailAssociation_tv_info = ((WebView) getView().findViewById(
//				R.id.IdDetailAssociation_tv_info));
		 SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
		  connectedUser = prefs.getString("identifiant", "");
		  password = prefs.getString("password", "");
		 idInfoAssociation_textViewtDescription = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtDescription));
		 idInfoAssociation_textViewtCaValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtCaValue));
		 idInfoAssociation_textViewtTelValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtTelValue));
		 idInfoAssociation_textViewtSecteurValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtSecteurValue));
		 idInfoAssociation_textViewtEffectifValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtEffectifValue));
		 idInfoAssociation_textViewtAdresseValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtAdresseValue));
		 idInfoAssociation_textViewtSiteValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewtSiteValue));
		 idInfoAssociation_textViewSocieteValue = ((TextView) getView().findViewById(R.id.idInfoAssociation_textViewSocieteValue));
		 idInfoAssociation_textViewtSiteValue.setMovementMethod(LinkMovementMethod.getInstance());
		 idInfoAssociation_textViewtEffectifValue.setMovementMethod(LinkMovementMethod.getInstance());
		
		if (bundle != null) {
			idAssociation = bundle.getString("KEY_EXTRA_DETAIL_ASSOCIATION_ID");
		}
		AssociationManager.getInstance().detailAssociation(idAssociation,connectedUser,password, this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (rootView == null) {
			rootView = inflater.inflate(R.layout.infos_association_fragment,
					container, false);
			rootView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			if(rootView.getParent()!=null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
		}

		return rootView;

	}

}
