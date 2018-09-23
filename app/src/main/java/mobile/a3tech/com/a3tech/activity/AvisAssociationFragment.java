package mobile.a3tech.com.a3tech.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.AvisAdapter;
import mobile.a3tech.com.a3tech.adapter.CategorieAdapter;
import mobile.a3tech.com.a3tech.manager.AvisManager;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;

public class AvisAssociationFragment extends Fragment implements
		DataLoadCallback {

	public static ProgressDialog progressDialog = null;

	String avantage = "";
	AvisAdapter avisAdapter;
	List<Avis> aviss = new ArrayList();
	String connectedUser;
	Dialog countDialog;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	EditText idCreateAvis_editTextAvantage;
	EditText idCreateAvis_editTextInconvenient;
	LinearLayout idCreateAvis_linearLayoutAnnuler;
	LinearLayout idCreateAvis_linearLayoutValider;
	RatingBar idCreateAvis_ratingBar;
	Spinner idCreateAvis_spinnerStatut;
	ListView idDetailAssociation_listeAvis;
	String idAssociation;
	ImageButton idProfilRow_imageButtonAddAv;
	String inconvenient = "";
	String rating;
	View rootView;
	SimpleDateFormat sdfHMS = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	String statut = "";
	List<Categorie> statuts;
	CategorieAdapter statutsAdapter;
	
	
	String password = "" ;

	private boolean controlerDemande() {
		avantage = idCreateAvis_editTextAvantage.getText().toString();
		inconvenient = idCreateAvis_editTextInconvenient.getText().toString();
		rating = String.valueOf(idCreateAvis_ratingBar.getRating());
		if (this.idCreateAvis_spinnerStatut.getSelectedItemPosition() == 0) {
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.txtCreateAvis_msgChoixStatut, Toast.LENGTH_LONG)
					.show();
			((TextView) idCreateAvis_spinnerStatut.getSelectedView())
					.setError(getString(R.string.txtCreateAvis_msgChoixStatut));
			return false;
		}
		statut = ((Categorie) this.statuts.get(idCreateAvis_spinnerStatut
				.getSelectedItemPosition())).getIdentifiant();
		return true;
	}

	private View.OnClickListener validerListener = new View.OnClickListener() {
		public void onClick(View paramAnonymousView) {
			if (controlerDemande()) {
				progressDialog = CustomProgressDialog.createProgressDialog(
						getActivity(),
						getString(R.string.txtMenu_dialogChargement));
				AvisManager.getInstance().createAvis(idAssociation, avantage,
						inconvenient, rating, statut,connectedUser,password,
						AvisAssociationFragment.this);
			}
		}
	};

	public List<Categorie> getListStatuts() {
		String types[] = getResources().getStringArray(R.array.type_employes);
		List<Categorie> categories = new ArrayList<Categorie>();
		for (int i = 0; i < types.length; i++) {
			Categorie categorie = new Categorie();
			categorie.setIdentifiant(String.valueOf(i));
			categorie.setLibelle(types[i]);
			categories.add(categorie);

		}
		return categories;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (rootView == null) {
			rootView = inflater.inflate(R.layout.avis_association_fragment,
					container, false);
			rootView.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		} else {
			if(rootView.getParent()!=null)
				((ViewGroup) rootView.getParent()).removeView(rootView);
		}

		return rootView;

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null) {
			Bundle b = getActivity().getIntent().getExtras();
			if (b != null) {
				idAssociation = b.getString("KEY_EXTRA_DETAIL_ENTREPRISE_ID");
			}
			idDetailAssociation_listeAvis = ((ListView) getView().findViewById(
					R.id.idDetailAssociation_listeAvis));
			idProfilRow_imageButtonAddAv = ((ImageButton) getView()
					.findViewById(R.id.idProfilRow_imageButtonAddAv));
			idProfilRow_imageButtonAddAv.bringToFront();
			idProfilRow_imageButtonAddAv
					.setOnClickListener(showDialogAddEducationListener);
			avisAdapter = new AvisAdapter(this, aviss,
					idDetailAssociation_listeAvis);
			idDetailAssociation_listeAvis.setAdapter(avisAdapter);
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(getActivity()
							.getApplicationContext());
			connectedUser = prefs.getString("identifiant", "");
			password = prefs.getString("password", "");
			AvisManager.getInstance().avisAssociation(this.idAssociation,connectedUser,password, this);
		}
	}

	private View.OnClickListener showDialogAddEducationListener = new View.OnClickListener() {
		public void onClick(View v) {
			View view = getActivity().getLayoutInflater().inflate(
					R.layout.create_avis_fragment, null);
			idCreateAvis_editTextAvantage = ((EditText) view
					.findViewById(R.id.idCreateAvis_editTextAvantage));
			idCreateAvis_editTextInconvenient = ((EditText) view
					.findViewById(R.id.idCreateAvis_editTextInconvenient));
			idCreateAvis_ratingBar = ((RatingBar) view
					.findViewById(R.id.idCreateAvis_ratingBar));
			idCreateAvis_spinnerStatut = ((Spinner) view
					.findViewById(R.id.idCreateAvis_spinnerStatut));
			statuts = getListStatuts();
			statutsAdapter = new CategorieAdapter(getActivity().getApplicationContext(), statuts);
			idCreateAvis_spinnerStatut.setAdapter(statutsAdapter);
			idCreateAvis_spinnerStatut.setSelection(0);
			idCreateAvis_linearLayoutAnnuler = ((LinearLayout) view
					.findViewById(R.id.idCreateAvis_linearLayoutAnnuler));
			idCreateAvis_linearLayoutValider = ((LinearLayout) view
					.findViewById(R.id.idCreateAvis_linearLayoutValider));
			idCreateAvis_linearLayoutAnnuler
					.setOnClickListener(annulerListener);
			idCreateAvis_linearLayoutValider
					.setOnClickListener(validerListener);
			countDialog = new Dialog(getActivity(), R.style.TransparentDialog2);
			countDialog.setContentView(view);
			countDialog.getWindow().getAttributes();
			countDialog.getWindow().setLayout(-1, -1);
			countDialog.show();
		}
	};

	private Avis createAvis(String identifiant) {
		Avis avis = new Avis();
		avis.setDate_creation(dateFormat.format(new Date()));
		avis.setIdentifiant(identifiant);
		avis.setAvantage(avantage);
		avis.setInconvenient(inconvenient);
		avis.setNote(rating);
		avis.setStatut(statut);
		return avis;
	}

	private View.OnClickListener annulerListener = new View.OnClickListener() {
		public void onClick(View paramAnonymousView) {
			countDialog.dismiss();
		}
	};

	public static AvisAssociationFragment newInstance() {
		return new AvisAssociationFragment();
	}

	@Override
	public void dataLoaded(Object data, int method, int j) {
		switch (method) {
		case Constant.KEY_AVISS_ASSOCIATION:
			List<Avis> aviss = (List<Avis>) data;
			this.avisAdapter.setAviss(aviss);
			break;
		case Constant.KEY_DELETE_AVIS:
			progressDialog.dismiss();
			break;
		case Constant.KEY_CREATE_AVIS:
			String identifiant = (String) data;
			Avis avis = createAvis(identifiant);
			List<Avis> aviss2 = avisAdapter.getAviss();
			aviss2.add(avis);
			this.avisAdapter.setAviss(aviss2);
			progressDialog.dismiss();
			countDialog.dismiss();
			Toast.makeText(getActivity().getApplicationContext(),
					R.string.txtCreateAvis_txtMessageResultSuccess,
					Toast.LENGTH_LONG).show();

		}

	}

	@Override
	public void dataLoadingError(int errorCode) {
		// TODO Auto-generated method stub

	}

}
