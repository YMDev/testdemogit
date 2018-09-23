package mobile.a3tech.com.a3tech.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import org.codehaus.jackson.util.MinimalPrettyPrinter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.adapter.CategorieAdapter;
import mobile.a3tech.com.a3tech.capturephoto.util.CapturePhoto;
import mobile.a3tech.com.a3tech.images.Util;
import mobile.a3tech.com.a3tech.manager.CategorieManager;
import mobile.a3tech.com.a3tech.manager.MissionManager;
import mobile.a3tech.com.a3tech.manager.UserManager;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.service.DataLoadCallback;
import mobile.a3tech.com.a3tech.utils.Constant;
import mobile.a3tech.com.a3tech.utils.ImageManager;
import mobile.a3tech.com.a3tech.view.CustomProgressDialog;
import mobile.a3tech.com.a3tech.view.Toaster;
import mobile.a3tech.com.a3tech.widgets.DatePicker;

public class PostageActivityStep2 extends Activity implements DataLoadCallback {

	Spinner idPostage2_spinnerSousCategorie ;
	LinearLayout txtPostage2_linearLayoutSousCategorie;
	Categorie hint;
	List<Categorie> sCategories;
	CategorieAdapter scategorieAdapter;
	
	Spinner idPostage2_spinnerTypeTroc;
	EditText idPostage2_editTextTitre ;
	EditText idPostage2_editTextArticle;
	Spinner idPostage2_spinnerEtatArticle;
	TextView idPostage2_textViewTypeTroc ;

	Spinner idPostage2_spinnerContre;
	EditText idPostage2_editTextObjetRecherche;
	EditText idPostage2_editTextObjetRechercheTitre ;
	LinearLayout txtPostage2_linearLayoutContre ;
	
	public static EditText idPostage2_editTextLieu;
	ImageButton Postage2_ImageButtonAttachementOne;
	ImageButton Postage2_ImageButtonAttachementTwo;
	ImageButton Postage2_ImageButtonAttachementTree;

	ImageView idPostage2_ImageviewAttachementOne;
	ImageView idPostage2_ImageviewAttachementTwo;
	ImageView idPostage2_ImageviewAttachementTree;
	LinearLayout idPostage2_linearLayoutValider ;
	
	CategorieAdapter typePaiementAdapter;
	List<Categorie> typePaiement;
	CategorieAdapter typeSponsoringAdapter;
	List<Categorie> typeSponsoring;

	CategorieAdapter typeTrocAdapter;
	List<Categorie> typeTrocs;
	CategorieAdapter etatArticleAdapter;
	List<Categorie> etatArticle;

	CategorieAdapter contreAdapter;
	List<Categorie> contres;

	LinearLayout idPostage2_linearLayoutRetour;
	TextView idPostage_textviewTitleKhod;
	TextView idPostage_textviewTitleAra;
	

	String[] bitmaps = new String[] { "", "", "" };
	private CapturePhoto capture;
	
	RadioButton idPostage2_radio_nonSponsorise ;
	RadioButton idPostage2_radio_sponsorise ;
	Spinner idPostage2_spinnerTypeSponsor ;
	Spinner idPostage2_spinnerTypePaiement ;
	DatePicker idPostage2_editTextDateDebut ;
	SimpleDateFormat sdfHMS = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);

	int selectedView;
	private String mAgentPictureName;
	private String mAgentPicturePath;
	private static final int PICK_FROM_CAMERA = 1;
	private static final int PICK_FROM_GALLERY = 2;
	public static final int REQUEST_CAMERA = 1000;
	public static final int SELECT_FILE = 1001;
	ProgressDialog progressDialog = null;
	String sousCategorie ;
	String connectedUser = "";
	String password ="" ;
	String lang = "" ;

	
	String idTypeTroc;
	String article;
	String titre ;
	String idEtatArticle;
	String contre;
	String objetRecherche;
	String objetRechercheTitre ;
	String lieu;
	String echeance ;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
			Locale.FRANCE);
	int typeTransaction ;
	int categorie ;
	static String adresse ;
	public static String latitude ;
	public static String longitude ;
	static String idVille ;
	 AlertDialog alertDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.postage_activity_step2);
		Bundle b = getIntent().getExtras();
        if (b != null) {
        	typeTransaction = Integer.parseInt(b.getString(Constant.TYPE_TRANSACTION));
        	categorie = Integer.parseInt(b.getString(Constant.CATEGORIE));
        }
        SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getApplicationContext());
		connectedUser = prefs.getString("identifiant", "");
		password = prefs.getString("password", "");
		
        lang = prefs.getString("ApplicationLanguage", Constant.LANGUAGE_FRENSH);
        
		//sous categorie
		 idPostage2_spinnerSousCategorie = (Spinner) findViewById(R.id.idPostage2_spinnerSousCategorie);
		 txtPostage2_linearLayoutSousCategorie = (LinearLayout) findViewById(R.id.txtPostage2_linearLayoutSousCategorie);
		 hint = new Categorie();
	     hint.setLibelle(getString(R.string.txtPostage2_spinnerDefaultCategorie));
	     sCategories = new ArrayList<Categorie>();
	     sCategories.add(hint);
	     scategorieAdapter = new CategorieAdapter(getApplicationContext(), sCategories);
	     idPostage2_spinnerSousCategorie.setAdapter(scategorieAdapter);
	     progressDialog = CustomProgressDialog.createProgressDialog(PostageActivityStep2.this, getString(R.string.txtMenu_dialogChargement));
	     CategorieManager.getInstance().listeCategories(null,String.valueOf(categorie), null, null,lang,connectedUser,password, PostageActivityStep2.this);

		//type
		idPostage2_textViewTypeTroc = (TextView) findViewById(R.id.idPostage2_textViewTypeTroc);
		idPostage2_spinnerTypeTroc = (Spinner) findViewById(R.id.idPostage2_spinnerTypeTroc);
		idPostage2_editTextArticle = (EditText) findViewById(R.id.idPostage2_editTextArticle);
		idPostage2_editTextTitre = (EditText) findViewById(R.id.idPostage2_editTextTitre);
		
		idPostage_textviewTitleKhod = (TextView) findViewById(R.id.idPostage_textviewTitleKhod);
		idPostage_textviewTitleAra = (TextView) findViewById(R.id.idPostage_textviewTitleAra);
		
		
		idPostage2_spinnerEtatArticle = (Spinner) findViewById(R.id.idPostage2_spinnerEtatArticle);
		idPostage2_textViewTypeTroc.setText(typeTransaction==Constant.TRANSACTION_DON?getString(R.string.txtPostage2_textViewTypeDonTitle):getString(R.string.txtPostage2_textViewTypeTrocTitle));
		typeTrocs = getTypeTrocs();
		typeTrocAdapter = new CategorieAdapter(getApplicationContext(),typeTrocs);
		idPostage2_spinnerTypeTroc.setAdapter(typeTrocAdapter);
		idPostage2_spinnerTypeTroc.setOnItemSelectedListener(typeTrocListener);
		idPostage2_spinnerTypeTroc.setSelection(0);
		
		//contre
		idPostage2_spinnerContre = (Spinner) findViewById(R.id.idPostage2_spinnerContre);
		idPostage2_editTextObjetRechercheTitre = (EditText) findViewById(R.id.idPostage2_editTextObjetRechercheTitre);
		idPostage2_editTextObjetRecherche = (EditText) findViewById(R.id.idPostage2_editTextObjetRecherche);
		txtPostage2_linearLayoutContre = (LinearLayout) findViewById(R.id.txtPostage2_linearLayoutContre);
		txtPostage2_linearLayoutContre.setVisibility(typeTransaction==Constant.TRANSACTION_DON?View.GONE:View.VISIBLE);
		contres = getContres();
		contreAdapter = new CategorieAdapter(getApplicationContext(), contres);
		idPostage2_spinnerContre.setAdapter(contreAdapter);
		idPostage2_spinnerContre.setSelection(0);
		idPostage2_spinnerContre.setOnItemSelectedListener(contreListener);
		
		
		idPostage2_editTextLieu = (EditText) findViewById(R.id.idPostage2_editTextLieu);
		Postage2_ImageButtonAttachementOne = (ImageButton) findViewById(R.id.Postage2_ImageButtonAttachementOne);
		Postage2_ImageButtonAttachementTwo = (ImageButton) findViewById(R.id.Postage2_ImageButtonAttachementTwo);
		Postage2_ImageButtonAttachementTree = (ImageButton) findViewById(R.id.Postage2_ImageButtonAttachementTree);
		
		idPostage2_linearLayoutValider =(LinearLayout)findViewById(R.id.idPostage2_linearLayoutValider);
		
		idPostage2_linearLayoutRetour = (LinearLayout) findViewById(R.id.idPostage2_linearLayoutRetour);
		idPostage2_linearLayoutRetour.setOnClickListener(retourListener);

		idPostage2_ImageviewAttachementOne = (ImageView) findViewById(R.id.idPostage2_ImageviewAttachementOne);
		idPostage2_ImageviewAttachementTwo = (ImageView) findViewById(R.id.idPostage2_ImageviewAttachementTwo);
		idPostage2_ImageviewAttachementTree = (ImageView) findViewById(R.id.idPostage2_ImageviewAttachementTree);

		idPostage2_ImageviewAttachementOne.setOnClickListener(captureListener);
		idPostage2_ImageviewAttachementTwo.setOnClickListener(captureListener);
		idPostage2_ImageviewAttachementTree.setOnClickListener(captureListener);
		Postage2_ImageButtonAttachementOne
				.setOnClickListener(deleteCaptureListener);
		Postage2_ImageButtonAttachementTwo
				.setOnClickListener(deleteCaptureListener);
		Postage2_ImageButtonAttachementTree
				.setOnClickListener(deleteCaptureListener);
		idPostage2_linearLayoutValider.setOnClickListener(validerListener);


		etatArticle = getEtatArticle();
		etatArticleAdapter = new CategorieAdapter(getApplicationContext(),
				etatArticle);
		idPostage2_spinnerEtatArticle.setAdapter(etatArticleAdapter);
		idPostage2_spinnerEtatArticle.setSelection(0);

		
		idPostage2_editTextLieu.setOnTouchListener(lieuListener);
		//sponsoring
		
		 idPostage2_radio_nonSponsorise  = (RadioButton) findViewById(R.id.idPostage2_radio_nonSponsorise);
		 idPostage2_radio_sponsorise = (RadioButton) findViewById(R.id.idPostage2_radio_sponsorise);
		 idPostage2_spinnerTypeSponsor = (Spinner) findViewById(R.id.idPostage2_spinnerTypeSponsor);
		 idPostage2_spinnerTypePaiement  = (Spinner) findViewById(R.id.idPostage2_spinnerTypePaiement);
		 idPostage2_editTextDateDebut = (DatePicker) findViewById(R.id.idPostage2_editTextDateDebut);
//		 Calendar c = Calendar.getInstance();
//		 c.setTime(new Date());
//		 c.add(Calendar.DATE, 2);
//		 idPostage2_editTextDateDebut.setMinDate(c.get(1), c.get(2), c.get(5));
		 idPostage2_radio_nonSponsorise.setOnClickListener(radioButtonListener);
		 idPostage2_radio_sponsorise.setOnClickListener(radioButtonListener);
		 idPostage2_radio_nonSponsorise.setChecked(true);
		 idPostage2_radio_sponsorise.setChecked(false);
		 
		 typePaiement = getTypePaiement();
		 typePaiementAdapter = new CategorieAdapter(getApplicationContext(), typePaiement);
		 idPostage2_spinnerTypePaiement.setAdapter(typePaiementAdapter);
		 idPostage2_spinnerTypePaiement.setSelection(0);
		 
		 typeSponsoring= getTypeSponsor();
		 typeSponsoringAdapter = new CategorieAdapter(getApplicationContext(), typeSponsoring);
		 idPostage2_spinnerTypeSponsor.setAdapter(typeSponsoringAdapter);
		 idPostage2_spinnerTypeSponsor.setSelection(0);
		 idPostage2_spinnerEtatArticle.setVisibility(View.GONE);
		
//		
//		idPostage_textviewTitleKhod.setText(Html.fromHtml("<FONT COLOR=\"red\" >Khod</FONT>hod"));
//		idPostage_textviewTitleAra.setText(Html.fromHtml("<FONT COLOR=\"black\" >Ara</FONT>ra"));
	

	}
	
	private OnClickListener radioButtonListener = new OnClickListener() {
		public void onClick(View v) {
			
			 boolean checked = ((RadioButton) v).isChecked();
			    switch(v.getId()) {
			        case R.id.idPostage2_radio_nonSponsorise:
			          //  if (checked)
			            	idPostage2_spinnerTypeSponsor.setVisibility(View.GONE);
			            	idPostage2_spinnerTypePaiement.setVisibility(View.GONE);
			            	idPostage2_editTextDateDebut.setVisibility(View.GONE);
			            break;
			        case R.id.idPostage2_radio_sponsorise:
			        	 progressDialog = CustomProgressDialog.createProgressDialog(PostageActivityStep2.this, getString(R.string.txtMenu_dialogChargement));
			        	 UserManager.getInstance().getProfil(connectedUser,password, PostageActivityStep2.this);
			           // if (checked)
			            	idPostage2_spinnerTypeSponsor.setVisibility(View.VISIBLE);
		            		idPostage2_spinnerTypePaiement.setVisibility(View.VISIBLE);
		            		idPostage2_editTextDateDebut.setVisibility(View.VISIBLE);
			              
			            break;
			    }

			    
			
		}
	};

	private OnClickListener captureListener = new OnClickListener() {
		public void onClick(View v) {
			int targetID = v.getId();
			selectedView = targetID;
			selectImage(targetID);
		}
	};

	private OnClickListener validerListener = new OnClickListener() {
		public void onClick(View v) {
			if (controlerDemande()) {
				if(idPostage2_radio_sponsorise.isChecked()){
					afficherConditionsPaiement();
				}else{
					envoyerDemande();
				}
				
			}
		}
	};
	
	   private void afficherConditionsPaiement() {
		  	
		  		 Handler handler = new Handler();
						handler.post(new Runnable() {
							@Override
							public void run() {
								Builder builder = new Builder(PostageActivityStep2.this);
								LayoutInflater inflater = PostageActivityStep2.this.getLayoutInflater();
								View content = inflater.inflate(R.layout.conditions_dialog_box, null);
								WebView idConditions_webviewContent = (WebView) content.findViewById(R.id.idConditions_webviewContent);
								LinearLayout idCondition_relativeLayoutValider = (LinearLayout) content.findViewById(R.id.idCondition_relativeLayoutValider);
								((TextView) content.findViewById(R.id.idCondition_textviewValider)).setText(getString(R.string.txtCondition_textviewFermer));
								idCondition_relativeLayoutValider.setOnClickListener(alertValiderListener);
								builder.setView(content);
								idConditions_webviewContent.loadUrl(Constant.PAIEMENT_URL_FR);
								alertDialog = builder.create();
								alertDialog.show();
							}
						});
		  }
	   
	   private OnClickListener alertValiderListener = new OnClickListener() {
			public void onClick(View v) {
				alertDialog.dismiss();
				envoyerDemande();
			}
		};

	private void envoyerDemande() {

		progressDialog = CustomProgressDialog.createProgressDialog(this,
				getString(R.string.txtMenu_dialogChargement));
		String typeSp= null;
		String typePa = null; 
		String dateDebut = null;
		String dateFin = null;
		if(idPostage2_radio_sponsorise.isChecked()){
			Categorie sp = (Categorie) typeSponsoring.get(idPostage2_spinnerTypeSponsor.getSelectedItemPosition());
			typeSp = sp.getIdentifiant();
			Categorie pa = (Categorie) typeSponsoring.get(idPostage2_spinnerTypePaiement.getSelectedItemPosition());
			typePa = pa.getLibelle();
			
			dateDebut = sdfHMS.format(idPostage2_editTextDateDebut.getDateSelected());
			dateDebut += MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + "00:00:00";
			switch (Integer.parseInt(typeSp)) {
			case 1:
				dateFin = sdfHMS.format(addDays(idPostage2_editTextDateDebut.getDateSelected(),1))+ " 00:00:00";
				break;
			case 2:
				dateFin = sdfHMS.format(addDays(idPostage2_editTextDateDebut.getDateSelected(),7))+ " 00:00:00";
				break;
			case 3:
				dateFin = sdfHMS.format(addDays(idPostage2_editTextDateDebut.getDateSelected(),30))+ " 00:00:00";
				break;
			}
		}
		
		MissionManager.getInstance().createMission(connectedUser,String.valueOf(typeTransaction), String.valueOf(categorie),
				sousCategorie, idTypeTroc,titre, article, idEtatArticle, contre,objetRechercheTitre,
				objetRecherche, lieu, latitude, longitude,idVille, bitmaps,echeance,typeSp,typePa,dateDebut,dateFin,password, this);

	}
	
	public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

	private boolean controlerEchange(){
		
		
		if (idPostage2_spinnerSousCategorie.getSelectedItemPosition() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixCategorieTroc, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerSousCategorie.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixCategorieTroc));
			return false;
		}
		Categorie categorieTroc = (Categorie) sCategories
				.get(idPostage2_spinnerSousCategorie.getSelectedItemPosition());
		sousCategorie = categorieTroc.getIdentifiant();
		
		if (idPostage2_spinnerTypeTroc.getSelectedItemPosition() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixTypeTroc, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerTypeTroc.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixTypeTroc));
			return false;
		}
		Categorie typeTroc = (Categorie) typeTrocs
				.get(idPostage2_spinnerTypeTroc.getSelectedItemPosition());
		idTypeTroc = typeTroc.getIdentifiant();
		
		
		
		if (idPostage2_editTextTitre.getText().toString().equals("")) {
			idPostage2_editTextTitre
					.setError(getString(R.string.txtPostage2_msgChoixTitre));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixTitre, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if (idTypeTroc.equals(Constant.TYPE_TROC_SERVICE)
				&& idPostage2_editTextArticle.getText().toString().equals("")) {
			idPostage2_editTextArticle
					.setError(getString(R.string.txtPostage2_msgChoixService));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixService, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (idTypeTroc.equals(Constant.TYPE_TROC_OBJET)
				&& idPostage2_editTextArticle.getText().toString().equals("")) {
			idPostage2_editTextArticle
					.setError(getString(R.string.txtPostage2_msgChoixArticle));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixArticle, Toast.LENGTH_SHORT).show();
			return false;
		}
		article = idPostage2_editTextArticle.getText().toString();
		titre = idPostage2_editTextTitre.getText().toString();
		

//		if (idTypeTroc.equals(Constant.TYPE_TROC_OBJET)
//				&& idPostage2_spinnerEtatArticle.getSelectedItemPosition() == 0) {
//
//			Toast.makeText(getApplicationContext(),
//					R.string.txtPostage2_msgChoixTypeTroc, 0).show();
//			((TextView) idPostage2_spinnerEtatArticle.getSelectedView())
//					.setError(getString(R.string.txtPostage2_msgChoixTypeTroc));
//			return false;
//		}
//		Categorie etatArt = (Categorie) typeTrocs
//				.get(idPostage2_spinnerEtatArticle.getSelectedItemPosition());
//		idEtatArticle = etatArt.getIdentifiant();
		idEtatArticle = "1";
		
		if (idPostage2_spinnerContre.getSelectedItemPosition() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixContre, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerContre.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixContre));
			return false;
		}
		
		Categorie contreCat = (Categorie) contres
				.get(idPostage2_spinnerContre.getSelectedItemPosition());
		contre = contreCat.getIdentifiant();
		if (!contre.equals(String.valueOf(Constant.CONTRE_JE_SAIS_PAS)) && idPostage2_editTextObjetRechercheTitre.getText().toString().equals("")) {
			idPostage2_editTextObjetRechercheTitre
					.setError(getString(R.string.txtPostage2_msgChoixTitre));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixTitre, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		objetRecherche = idPostage2_editTextObjetRecherche.getText().toString();
		objetRechercheTitre = idPostage2_editTextObjetRechercheTitre.getText().toString();
		if (idPostage2_editTextLieu.getText().toString().equals("")) {
			idPostage2_editTextLieu
					.setError(getString(R.string.txtPostage2_msgChoixLieu));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixLieu, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		if(idPostage2_radio_sponsorise.isChecked() && idPostage2_spinnerTypeSponsor.getSelectedItemPosition() == 0){
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixTypeSponsoring, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerTypeSponsor.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixTypeSponsoring));
			return false;
		}
		
		if(idPostage2_radio_sponsorise.isChecked() && idPostage2_spinnerTypePaiement.getSelectedItemPosition() == 0){
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixTypePaiement, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerTypePaiement.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixTypePaiement));
			return false;
		}
		
		  if (idPostage2_radio_sponsorise.isChecked() && idPostage2_editTextDateDebut.getText().toString().equals("")) {
			  idPostage2_editTextDateDebut.setError(getString(R.string.txtPostage2_msgChoixDateDebut));
	            Toast.makeText(getApplicationContext(), R.string.txtPostage2_msgChoixDateDebut, Toast.LENGTH_SHORT).show();
	            return false;
	      }
		
		
		
		lieu =idPostage2_editTextLieu.getText().toString();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 60);
		calendar.set(Calendar.HOUR, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.MINUTE, 59);
		echeance = dateFormat.format(calendar.getTime());

		return true;

	}
	
	private boolean controlerDon(){
		if (idPostage2_spinnerSousCategorie.getSelectedItemPosition() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixCategorieDon, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerSousCategorie.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixCategorieDon));
			return false;
		}
		Categorie categorieTroc = (Categorie) sCategories
				.get(idPostage2_spinnerSousCategorie.getSelectedItemPosition());
		sousCategorie = categorieTroc.getIdentifiant();
		
		if (idPostage2_spinnerTypeTroc.getSelectedItemPosition() == 0) {
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixTypeDon, Toast.LENGTH_SHORT).show();
			((TextView) idPostage2_spinnerTypeTroc.getSelectedView())
					.setError(getString(R.string.txtPostage2_msgChoixTypeDon));
			return false;
		}
		Categorie typeTroc = (Categorie) typeTrocs
				.get(idPostage2_spinnerTypeTroc.getSelectedItemPosition());
		idTypeTroc = typeTroc.getIdentifiant();
		
		if (idTypeTroc.equals(Constant.TYPE_TROC_SERVICE)
				&& idPostage2_editTextArticle.getText().toString().equals("")) {
			idPostage2_editTextArticle
					.setError(getString(R.string.txtPostage2_msgChoixService));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixService, Toast.LENGTH_SHORT).show();
			return false;
		}
		if (idTypeTroc.equals(Constant.TYPE_TROC_OBJET)
				&& idPostage2_editTextArticle.getText().toString().equals("")) {
			idPostage2_editTextArticle
					.setError(getString(R.string.txtPostage2_msgChoixArticle));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixArticle, Toast.LENGTH_SHORT).show();
			return false;
		}
		article = idPostage2_editTextArticle.getText().toString();
		

//		if (idTypeTroc.equals(Constant.TYPE_TROC_OBJET)
//				&& idPostage2_spinnerEtatArticle.getSelectedItemPosition() == 0) {
//
//			Toast.makeText(getApplicationContext(),
//					R.string.txtPostage2_msgChoixTypeTroc, 0).show();
//			((TextView) idPostage2_spinnerEtatArticle.getSelectedView())
//					.setError(getString(R.string.txtPostage2_msgChoixTypeTroc));
//			return false;
//		}
//		Categorie etatArt = (Categorie) typeTrocs
//				.get(idPostage2_spinnerEtatArticle.getSelectedItemPosition());
//		idEtatArticle = etatArt.getIdentifiant();
		idEtatArticle = "1";
		
		if (idPostage2_editTextLieu.getText().toString().equals("")) {
			idPostage2_editTextLieu
					.setError(getString(R.string.txtPostage2_msgChoixLieu));
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgChoixLieu, Toast.LENGTH_SHORT).show();
			return false;
		}
		
		
		lieu =idPostage2_editTextLieu.getText().toString();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 2);
		echeance = dateFormat.format(calendar.getTime());

		return true;


	}
	
	

	private boolean controlerDemande() {
		switch (typeTransaction) {
		case Constant.TRANSACTION_DON:
			return controlerDon();
		default:
			return controlerEchange();
		}
	}

	private OnClickListener retourListener = new OnClickListener() {
		public void onClick(View v) {
			retour();
		}
	};

	private void selectImage(int id) {
		CharSequence[] items = new CharSequence[] {
				getString(R.string.txtPostage2_attachementItemPrendreImage),
				getString(R.string.txtPostage2_attachementItemChoisirImage),
				getString(R.string.txtPostage2_attachementItemAnnuler) };
		Builder builder = new Builder(this);
		builder.setTitle(getString(R.string.txtPostage2_textViewAttachmentTitleDialog));
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				File folder = new File(new StringBuilder(String
						.valueOf(Environment.getExternalStorageDirectory()
								.toString())).append("/.KhodaraTempImages")
						.toString());
				if (!folder.exists()) {
					folder.mkdirs();
				}
				mAgentPictureName = System.currentTimeMillis() + ".jpg";
				mAgentPicturePath = folder.getAbsolutePath() + "/"
						+ mAgentPictureName;
				switch (item) {
				case 0 /* 0 */:
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					intent.putExtra("output",
							Uri.fromFile(new File(folder, mAgentPictureName)));
					intent.putExtra("return-data", true);
					startActivityForResult(intent, PICK_FROM_CAMERA);
					break;
				case 1 /* 1 */:
					Toast.makeText(PostageActivityStep2.this, getString(R.string.txtProfil_choisirdelagalerie), Toast.LENGTH_LONG).show();
					Intent intent2 = new Intent();
					intent2.setType("image/*");
					intent2.setAction("android.intent.action.GET_CONTENT");
					intent2.putExtra("return-data", true);
					startActivityForResult(intent2, PICK_FROM_GALLERY);
					break;
				default:
					dialog.dismiss();
				}
			}
		});
		builder.show();
	}

	

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != -1) {
			return;
		}
		if (requestCode == PICK_FROM_CAMERA || requestCode == PICK_FROM_GALLERY) {
			if (requestCode == PICK_FROM_GALLERY) {
				Util.saveImage(Util.getBitmap(
						Util.getRealPathFromURI(this, data.getData()),
						Util.MAX_IMAGE_SIZE), mAgentPictureName.replace(".jpg",
						""));
			}
			try {
				Bitmap picture = Util.getBitmap(mAgentPicturePath,
						Util.MAX_IMAGE_SIZE);
				if (picture != null) {
					picture = Util.adjustImageOrientation(mAgentPicturePath,
							picture);
				} else {
					new File(mAgentPicturePath).exists();
				}
				if (picture != null) {
					ImageView imageView = (ImageView) findViewById(selectedView);
					imageView.setImageBitmap(picture);
					switch (selectedView) {
					case R.id.idPostage2_ImageviewAttachementOne:
						Postage2_ImageButtonAttachementOne.setVisibility(View.VISIBLE);
						bitmaps[0] = ImageManager.getInstance().getString(picture);
						break;
					case R.id.idPostage2_ImageviewAttachementTwo:
						Postage2_ImageButtonAttachementTwo.setVisibility(View.VISIBLE);
						bitmaps[1] = ImageManager.getInstance().getString(picture);
						break;
					default:
						Postage2_ImageButtonAttachementTree.setVisibility(View.VISIBLE);
						bitmaps[2] = ImageManager.getInstance().getString(picture);
						break;
					}
				}
			} catch (Exception e) {
			}
			Util.deleteDirectory(new File(new StringBuilder(String
					.valueOf(Environment.getExternalStorageDirectory()
							.getAbsolutePath())).append("/.KhodaraTempImages")
					.toString()));
		}
	}

	private OnClickListener deleteCaptureListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Postage2_ImageButtonAttachementOne:
				bitmaps[0] = "";
				idPostage2_ImageviewAttachementOne
						.setImageResource(R.drawable.creercompte_btnaddphoto);
				Postage2_ImageButtonAttachementOne.setVisibility(View.GONE);
				break;
			case R.id.Postage2_ImageButtonAttachementTwo:
				bitmaps[1] = "";
				idPostage2_ImageviewAttachementTwo
						.setImageResource(R.drawable.creercompte_btnaddphoto);
				Postage2_ImageButtonAttachementTwo.setVisibility(View.GONE);
				break;
			case R.id.Postage2_ImageButtonAttachementTree:
				bitmaps[2] = "";
				idPostage2_ImageviewAttachementTree
						.setImageResource(R.drawable.creercompte_btnaddphoto);
				Postage2_ImageButtonAttachementTree.setVisibility(View.GONE);
			default:
			}
		}
	};

	private OnTouchListener lieuListener = new OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			idPostage2_editTextLieu.clearFocus();
			if (event.getAction() == 0) {
				Intent mainIntent = new Intent(PostageActivityStep2.this,
						MapActivity.class);

				startActivity(mainIntent);
			}
			return false;
		}
	};

	public void onBackPressed() {
		retour();
	}

	private void retour() {
		startActivity(new Intent(this, PostageActivityStep1.class));
		finish();
	}

	private OnItemSelectedListener typeTrocListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			if (position == 1) {
				idPostage2_spinnerEtatArticle.setVisibility(View.GONE);
				idPostage2_editTextArticle
						.setHint(getString(R.string.txtPostage2_editTextService));
				idPostage2_editTextArticle.setVisibility(View.VISIBLE);
			}
			if (position == 2) {
				idPostage2_spinnerEtatArticle.setVisibility(View.VISIBLE);
				idPostage2_editTextArticle.setVisibility(View.VISIBLE);
				idPostage2_editTextArticle
						.setHint(getString(R.string.txtPostage2_editTextArticle));
			}
			if (position == 0) {
				idPostage2_spinnerEtatArticle.setVisibility(View.GONE);
				idPostage2_editTextArticle.setVisibility(View.GONE);

			}
			idPostage2_spinnerEtatArticle.setVisibility(View.GONE);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};
	
	private OnItemSelectedListener contreListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int position, long arg3) {
			Categorie contreCat = contres.get(position);
			int id = -1;
			try{
				id= Integer.parseInt(contreCat.getIdentifiant()) ;
			}catch(Exception e){
				
			}
			switch (id) {
			case Constant.CONTRE_SERVICE:
				idPostage2_editTextObjetRechercheTitre.setHint(getString(R.string.txtPostage2_edittextChoixContreServiceTitreService));
				idPostage2_editTextObjetRecherche.setHint(getString(R.string.txtPostage2_edittextChoixContreServiceDescService));
				idPostage2_editTextObjetRecherche.setVisibility(View.VISIBLE);
				idPostage2_editTextObjetRechercheTitre.setVisibility(View.VISIBLE);
				break;
			case Constant.CONTRE_OBJET:
				idPostage2_editTextObjetRechercheTitre.setHint(getString(R.string.txtPostage2_edittextChoixContreServiceTitreObjet));
				idPostage2_editTextObjetRecherche.setHint(getString(R.string.txtPostage2_edittextChoixContreServiceDescObjet));
				idPostage2_editTextObjetRecherche.setVisibility(View.VISIBLE);
				idPostage2_editTextObjetRechercheTitre.setVisibility(View.VISIBLE);
				break;
			case Constant.CONTRE_ESPECE:
				idPostage2_editTextObjetRechercheTitre
				.setHint(getString(R.string.txtPostage2_edittextChoixContreEspce));
				idPostage2_editTextObjetRecherche.setVisibility(View.GONE);
				idPostage2_editTextObjetRechercheTitre.setVisibility(View.VISIBLE);
				break;	
			case Constant.CONTRE_JE_SAIS_PAS:
				idPostage2_editTextObjetRecherche.setVisibility(View.GONE);	
				idPostage2_editTextObjetRechercheTitre.setVisibility(View.GONE);
				
			default:
				break;
			}
			
			
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};
	

	public List<Categorie> getTypeTrocs() {
		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(getString(R.string.txtPostage2_choisirTypeTroc));
		Categorie service = new Categorie();
		service.setIdentifiant(String.valueOf(Constant.TYPE_TROC_SERVICE));
		service.setLibelle(getString(R.string.txtPostage2_typeTrocService));
		Categorie objet = new Categorie();
		objet.setIdentifiant(String.valueOf(Constant.TYPE_TROC_OBJET));
		objet.setLibelle(getString(R.string.txtPostage2_typeTrocObjet));
		categories.add(hint);
		categories.add(service);
		categories.add(objet);
		return categories;

	}

	public List<Categorie> getEtatArticle() {

		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(getString(R.string.txtPostage2_choisirEtatArticle));
		Categorie service = new Categorie();
		service.setIdentifiant(String.valueOf(Constant.ETAT_ARTICLE_NOUVEAU));
		service.setLibelle(getString(R.string.txtPostage2_etatArticleNouveau));
		Categorie objet = new Categorie();
		objet.setIdentifiant(String.valueOf(Constant.ETAT_ARTICLE_ANCIEN));
		objet.setLibelle(getString(R.string.txtPostage2_etatArticleAncien));
		categories.add(hint);
		categories.add(service);
		categories.add(objet);
		return categories;

	}
	
	public List<Categorie> getTypePaiement() {

		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(getString(R.string.txtPostage2_choisirTypePaiement));
		Categorie virement = new Categorie();
		virement.setIdentifiant(String.valueOf(Constant.TYPE_PAIEMENT_VIREMENT));
		virement.setLibelle(getString(R.string.txtPostage2_choixTypePaiementVirement));
		Categorie espece = new Categorie();
		espece.setIdentifiant(String.valueOf(Constant.TYPE_PAIEMENT_ESPECE));
		espece.setLibelle(getString(R.string.txtPostage2_choixTypePaiementEspece));
		Categorie cheque = new Categorie();
		cheque.setIdentifiant(String.valueOf(Constant.TYPE_PAIEMENT_CHEQUE));
		cheque.setLibelle(getString(R.string.txtPostage2_choixTypePaiementCheque));

	
		categories.add(hint);
		categories.add(virement);
		categories.add(espece);
		//categories.add(cheque);
	
		return categories;

	}
	
	public List<Categorie> getTypeSponsor() {

		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(getString(R.string.txtPostage2_choisirTypeSponsoring));

		Categorie unjour = new Categorie();
		unjour.setIdentifiant(String.valueOf(Constant.UN_JOURS));
		unjour.setLibelle(getString(R.string.txtPostage2_choixUnJour));
		
		Categorie septjours = new Categorie();
		septjours.setIdentifiant(String.valueOf(Constant.SEPTE_JOURS));
		septjours.setLibelle(getString(R.string.txtPostage2_choixSeptJours));
		
		Categorie trentejours = new Categorie();
		trentejours.setIdentifiant(String.valueOf(Constant.TRENTE_JOURS));
		trentejours.setLibelle(getString(R.string.txtPostage2_choixTrenteJours));

		categories.add(hint);
		categories.add(unjour);
		categories.add(septjours);
		categories.add(trentejours);
		
		return categories;

	}

	
	public List<Categorie> getContres() {

		List<Categorie> categories = new ArrayList<Categorie>();
		Categorie hint = new Categorie();
		hint.setLibelle(getString(R.string.txtPostage2_choisirContre));

		Categorie service = new Categorie();
		service.setIdentifiant(String.valueOf(Constant.CONTRE_SERVICE));
		service.setLibelle(getString(R.string.txtPostage2_choixContreService));
		
		Categorie objet = new Categorie();
		objet.setIdentifiant(String.valueOf(Constant.CONTRE_OBJET));
		objet.setLibelle(getString(R.string.txtPostage2_choixContreObjet));
		
		Categorie espece = new Categorie();
		espece.setIdentifiant(String.valueOf(Constant.CONTRE_ESPECE));
		espece.setLibelle(getString(R.string.txtPostage2_choixContreEspece));

		Categorie jeSaisPas = new Categorie();
		jeSaisPas.setIdentifiant(String.valueOf(Constant.CONTRE_JE_SAIS_PAS));
		jeSaisPas.setLibelle(getString(R.string.txtPostage2_choixContreJesaispas));

		categories.add(hint);
		categories.add(service);
		categories.add(objet);
		categories.add(espece);
		categories.add(jeSaisPas);
		return categories;

	}

	@Override
	public void dataLoaded(Object data, int method, int typeOperation) {
		switch (method) {
		case Constant.KEY_CATEGORIE_MANAGER_LISTE_CATEGORIE:
			List<Categorie> categories = (List<Categorie>) data;
			if(categories!=null && categories.size()>0){
				sCategories.addAll(categories);
				scategorieAdapter.notifyDataSetChanged();
				idPostage2_spinnerSousCategorie.setSelection(0);
				txtPostage2_linearLayoutSousCategorie.setVisibility(View.VISIBLE);
				
			}
			progressDialog.dismiss();
			break;
		case Constant.KEY_USER_MANAGER_GET_PROFIL :
            User user = (User) data;
            String  nom = user.getNom();
            String prenom = user.getPrenom();
            String email = user.getEmail();
            String adresse = user.getAdresse();
            if(nom==null || nom.equals("") ||prenom==null ||prenom.equals("") ||
            		email==null || email.equals("") || adresse==null ||adresse.equals("")){
            	Toaster.makeLongToast(getApplicationContext(),
						getString(R.string.txtLogin_messageConfirmationInfosFacturation),
						8000);

				startActivity(new Intent(PostageActivityStep2.this, ProfilFragment.class));
            }
           
            progressDialog.dismiss();
            
            break;
		default:
			String missionId  = (String) data;
			Toast.makeText(getApplicationContext(),
					R.string.txtPostage2_msgsuccessCreation,
					Toast.LENGTH_LONG).show();
			progressDialog.dismiss();
			Intent mainIntent  = new Intent(PostageActivityStep2.this,DetailServiceViewPagerFragment.class);
			Bundle bundle = new Bundle();
			bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_USER_ID,
					connectedUser);
			bundle.putString(Constant.KEY_EXTRA_DETAIL_MISSION_ID,
					missionId);
			mainIntent.putExtras(bundle);
			startActivity(mainIntent);
			finish();
			break;
		}
	

	}

	@Override
	public void dataLoadingError(int errorCode) {
		Toast.makeText(getApplicationContext(),R.string.txtSplash_messageCheckConnexion,Toast.LENGTH_SHORT).show();
		try {
			progressDialog.dismiss();
		} catch (Exception e) {
			
		}

	}

}
