package mobile.a3tech.com.a3tech.activity;

import java.util.List;


import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.FormeRemuneration;
import mobile.a3tech.com.a3tech.model.SortCategorie;
import mobile.a3tech.com.a3tech.utils.Constant;

public class PostageActivityStep1 extends Activity{
	LinearLayout idPostage_linearLayoutElectronique;
	LinearLayout idPostage_linearLayoutInformatique;
	LinearLayout idPostage_linearLayoutVetementsHomme;
	LinearLayout idPostage_linearLayoutVetementsFemme;
	LinearLayout idPostage_linearLayoutEnfant;
	LinearLayout idPostage_linearLayoutbebe;
	LinearLayout idPostage_linearLayoutBienEtre;
	LinearLayout idPostage_linearLayoutMaison;
	LinearLayout idPostage_linearLayoutTransport;
	LinearLayout idPostage_linearLayoutBricolage;
	LinearLayout idPostage_linearLayoutLivre;
	LinearLayout idPostage_linearLayoutSport;
	LinearLayout idPostage_linearSortie;
	LinearLayout idPostage_linearLayoutArt;
	LinearLayout idPostage_linearLayoutAlimentation;
	LinearLayout idPostage_linearLayoutRetour;
	LinearLayout idPostage_linearLayoutAnimaux;
	LinearLayout idPostage_linearLayoutCoupMain;
	LinearLayout idPostage_linearLayoutAutre;
	TextView idPostage_Service_textViewTitle ;

	protected Dialog countDialog;
	FormeRemuneration formeRemuneration;
	ImageView id_circle_cat;
	ProgressDialog progressDialog = null;
	SortCategorie sortCategorie;
	private String[] mItemTexts;
	private int[] mItemImgs;
	List<Categorie> categories;
	int typeTransaction ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.postage_activity_step1);
		idPostage_linearLayoutElectronique = (LinearLayout) findViewById(R.id.idPostage_linearLayoutElectronique);
		idPostage_linearLayoutInformatique = (LinearLayout) findViewById(R.id.idPostage_linearLayoutInformatique);
		idPostage_linearLayoutVetementsHomme = (LinearLayout) findViewById(R.id.idPostage_linearLayoutVetementsHomme);
		idPostage_linearLayoutVetementsFemme = (LinearLayout) findViewById(R.id.idPostage_linearLayoutVetementsFemme);
		idPostage_linearLayoutEnfant = (LinearLayout) findViewById(R.id.idPostage_linearLayoutEnfant);
		idPostage_linearLayoutbebe = (LinearLayout) findViewById(R.id.idPostage_linearLayoutbebe);
		idPostage_linearLayoutBienEtre = (LinearLayout) findViewById(R.id.idPostage_linearLayoutBienEtre);

		idPostage_linearLayoutMaison = (LinearLayout) findViewById(R.id.idPostage_linearLayoutMaison);
		idPostage_linearLayoutTransport = (LinearLayout) findViewById(R.id.idPostage_linearLayoutTransport);
		idPostage_linearLayoutBricolage = (LinearLayout) findViewById(R.id.idPostage_linearLayoutBricolage);
		idPostage_linearLayoutLivre = (LinearLayout) findViewById(R.id.idPostage_linearLayoutLivre);

		idPostage_linearLayoutSport = (LinearLayout) findViewById(R.id.idPostage_linearLayoutSport);
		idPostage_linearSortie = (LinearLayout) findViewById(R.id.idPostage_linearSortie);
		idPostage_linearLayoutArt = (LinearLayout) findViewById(R.id.idPostage_linearLayoutArt);
		idPostage_linearLayoutAlimentation = (LinearLayout) findViewById(R.id.idPostage_linearLayoutAlimentation);
		idPostage_linearLayoutRetour = (LinearLayout) findViewById(R.id.idPostage_linearLayoutRetour);
		idPostage_linearLayoutAnimaux = (LinearLayout) findViewById(R.id.idPostage_linearLayoutAnimaux);
		idPostage_linearLayoutCoupMain = (LinearLayout) findViewById(R.id.idPostage_linearLayoutCoupMain);
		idPostage_linearLayoutAutre = (LinearLayout) findViewById(R.id.idPostage_linearLayoutAutre);
		idPostage_Service_textViewTitle = (TextView) findViewById(R.id.idPostage_Service_textViewTitle);

		idPostage_linearLayoutElectronique.setOnClickListener(serviceListener);
		idPostage_linearLayoutInformatique.setOnClickListener(serviceListener);
		idPostage_linearLayoutVetementsHomme
				.setOnClickListener(serviceListener);
		idPostage_linearLayoutVetementsFemme
				.setOnClickListener(serviceListener);
		idPostage_linearLayoutEnfant.setOnClickListener(serviceListener);
		idPostage_linearLayoutbebe.setOnClickListener(serviceListener);
		idPostage_linearLayoutBienEtre.setOnClickListener(serviceListener);
		idPostage_linearLayoutMaison.setOnClickListener(serviceListener);
		idPostage_linearLayoutTransport.setOnClickListener(serviceListener);
		idPostage_linearLayoutBricolage.setOnClickListener(serviceListener);
		idPostage_linearLayoutLivre.setOnClickListener(serviceListener);
		idPostage_linearLayoutSport.setOnClickListener(serviceListener);
		idPostage_linearSortie.setOnClickListener(serviceListener);
		idPostage_linearLayoutArt.setOnClickListener(serviceListener);
		idPostage_linearLayoutAlimentation.setOnClickListener(serviceListener);
		idPostage_linearLayoutRetour.setOnClickListener(serviceListener);
		idPostage_linearLayoutAnimaux.setOnClickListener(serviceListener);
		idPostage_linearLayoutCoupMain.setOnClickListener(serviceListener);
		idPostage_linearLayoutAutre.setOnClickListener(serviceListener);

		idPostage_linearLayoutRetour.setOnClickListener(retourListener);
		
		Bundle b = getIntent().getExtras();
        if (b != null) {
        	typeTransaction = Integer.parseInt(b.getString(Constant.TYPE_TRANSACTION));
        }
    	idPostage_Service_textViewTitle.setText(typeTransaction==Constant.TRANSACTION_DON ?getString(R.string.txtPostage_Service_textViewTitleDon):getString(R.string.txtPostage_Service_textViewTitle));
 

	}

	private OnClickListener retourListener = new OnClickListener() {
		public void onClick(View v) {
			PostageActivityStep1.this.finish();

		}
	};

	private OnClickListener serviceListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.idPostage_linearLayoutElectronique:
				formeRemuneration = FormeRemuneration.ELECTRONIQUE;
				break;
			case R.id.idPostage_linearLayoutInformatique:
				formeRemuneration = FormeRemuneration.INFORMATIQUE;
				break;
			case R.id.idPostage_linearLayoutVetementsHomme:
				formeRemuneration = FormeRemuneration.VETEMENTS_HOMME;
				break;
			case R.id.idPostage_linearLayoutVetementsFemme:
				formeRemuneration = FormeRemuneration.VETEMENTS_FEMME;
				break;
			case R.id.idPostage_linearLayoutEnfant:
				formeRemuneration = FormeRemuneration.ENFANT;
				break;
			case R.id.idPostage_linearLayoutbebe:
				formeRemuneration = FormeRemuneration.BEBE;
				break;
			case R.id.idPostage_linearLayoutBienEtre:
				formeRemuneration = FormeRemuneration.BIEN_ETRE;
				break;
			case R.id.idPostage_linearLayoutMaison:
				formeRemuneration = FormeRemuneration.MAISON;
				break;
			case R.id.idPostage_linearLayoutTransport:
				formeRemuneration = FormeRemuneration.TRANSPORT;
				break;
			case R.id.idPostage_linearLayoutBricolage:
				formeRemuneration = FormeRemuneration.BRICOLAGE;
				break;
			case R.id.idPostage_linearLayoutLivre:
				formeRemuneration = FormeRemuneration.LIVRE;
				break;
			case R.id.idPostage_linearLayoutSport:
				formeRemuneration = FormeRemuneration.SPORT;
				break;
			case R.id.idPostage_linearSortie:
				formeRemuneration = FormeRemuneration.SORTIE;
				break;
			case R.id.idPostage_linearLayoutArt:
				formeRemuneration = FormeRemuneration.ART;
				break;
			case R.id.idPostage_linearLayoutAlimentation:
				formeRemuneration = FormeRemuneration.ALIMENTATION;
				break;
			case R.id.idPostage_linearLayoutAnimaux:
				formeRemuneration = FormeRemuneration.ANIMAUX;
				break;
			case R.id.idPostage_linearLayoutCoupMain:
				formeRemuneration = FormeRemuneration.COUP_MAIN;
				break;
			case R.id.idPostage_linearLayoutAutre:
				formeRemuneration = FormeRemuneration.AUTRE;
				break;

			}
			
			Intent mainIntent = new Intent(
					PostageActivityStep1.this,
					PostageActivityStep2.class);
			mainIntent.putExtra(Constant.CATEGORIE,String.valueOf(formeRemuneration.getId()));
			mainIntent.putExtra(Constant.TYPE_TRANSACTION,String.valueOf(typeTransaction));
			PostageActivityStep1.this.startActivity(mainIntent);
			PostageActivityStep1.this.finish();
			
		}
	};

	


}
