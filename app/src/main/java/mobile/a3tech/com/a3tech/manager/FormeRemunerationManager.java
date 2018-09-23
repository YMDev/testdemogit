package mobile.a3tech.com.a3tech.manager;

import java.util.List;


import android.content.Context;

import mobile.a3tech.com.a3tech.R;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.model.FormeRemuneration;
import mobile.a3tech.com.a3tech.utils.Constant;

public class FormeRemunerationManager {
	
	private static FormeRemunerationManager uniqueInstance = null;

	
	public static FormeRemunerationManager getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new FormeRemunerationManager();
		}
		return uniqueInstance;
	}

	public FormeRemuneration getFormeRemunerationSrvc(int serviceId){
		FormeRemuneration[] resources = FormeRemuneration.values();
		for (FormeRemuneration type : resources ) {
		  if(type.getId()== serviceId)
			  return type;
		}
		return null;
	}
	public  List<Categorie> getFormeRemuneration(
			FormeRemuneration formeRemuneration, Context context) {
//		int[] pos = formeRemuneration.getPositions();
//		List<Categorie> categories = new ArrayList<Categorie>();
//		String[] items = context.getResources().getStringArray(R.array.rem);
//		for (int i = 0; i < pos.length; i++) {
//			String item = items[pos[i]];
//			Categorie categorie = new Categorie();
//			categorie.setLibelle(item);
//			categorie.setIdentifiant(String.valueOf(pos[i]));
//			categories.add(categorie);
//
//		}
//		return categories;
		return null;

	}
	
	
	

	
	public int getImageService(int service) {
		switch (service) {
			case Constant.SERVICE_ELECTRONIQUE:
				return R.drawable.ic_cat_electronique;
			case Constant.SERVICE_INFORMATIQUE:
				return R.drawable.ic_cat_informatique;
			case Constant.SERVICE_VETEMENTS_HOMME:
				return R.drawable.ic_cat_vetementshomme;
			case Constant.SERVICE_VETEMENTS_FEMME:
				return R.drawable.ic_cat_vetementsfemme;
			case Constant.SERVICE_ENFANT:
				return R.drawable.ic_cat_enfant;
			case Constant.SERVICE_BEBE:
				return R.drawable.ic_cat_bebe;
				
			case Constant.SERVICE_BIEN_ETRE:
				return R.drawable.ic_cat_bienetre;
			case Constant.SERVICE_MAISON:
				return R.drawable.ic_cat_maison;
			case Constant.SERVICE_TRANSPORT:
				return R.drawable.ic_cat_transport;
				
			case Constant.SERVICE_BRICOLAGE:
				return R.drawable.ic_cat_bricolage;
			case Constant.SERVICE_LIVRE:
				return R.drawable.ic_cat_livre;
			case Constant.SERVICE_SPORT:
				return R.drawable.ic_cat_sport;
			case Constant.SERVICE_SORTIE:
				return R.drawable.ic_cat_auto_sortie;
			case Constant.SERVICE_ART:
				return R.drawable.ic_cat_arts;
			case Constant.SERVICE_ALIMENTATION:
				return R.drawable.ic_cat_alimentation;
			case Constant.SERVICE_ANIMAUX:
				return R.drawable.ic_cat_animaux;
			case Constant.SERVICE_COUP_MAIN:
				return R.drawable.ic_cat_coupmain;
			case Constant.SERVICE_AUTRE:
				return R.drawable.ic_cat_autre;
		}
		return 0;
	}
	
	public String getCatScat(int service, String cat, String scat){
		String result = "";
		if(cat!=null && !cat.equals(""))
			result = result+cat;
		if(scat!=null && !scat.equals(""))
			result= result+": "+scat ;
		return result;
	}
	
	public String getForme(int service, int contre,Context context){
		String[] items = context.getResources().getStringArray(R.array.service_name);
		String forme = "";
		switch (contre) {
		case 5:
		case 1:
			forme = context.getString(R.string.txt_msg_mission_row_je_suis_pret_a_payer)+" "+items[service];
			break;
		case 2:
			forme = context.getString(R.string.txt_msg_mission_row_je_cherche_a_me_faire_preter)+" "+items[service];
			break;
		case 3:
			forme = context.getString(R.string.txt_msg_mission_row_jechange)+" "+items[service]+" "+ context.getString(R.string.txt_msg_mission_row_contre_autre_chose);
			break;
		case 7:
		case 6:
		case 4:
			forme = context.getString(R.string.txt_msg_mission_row_je_cherche)+" "+items[service]+ " "+context.getString(R.string.txt_msg_mission_row_gratuitement);
			break;
		case 8 :
			forme = context.getString(R.string.txt_msg_mission_row_je_cherche)+" "+items[service]+ " "+context.getString(R.string.txt_msg_mission_row_remunere);
			break;
		case 9:
			forme = context.getString(R.string.txt_msg_mission_row_je_accepte)+" "+items[service]+ " "+context.getString(R.string.txt_msg_mission_row_non_remunere);
			break;
		}
		return forme;
	}
	
	
	public String getFormeDetail(int contre,Context context){
		String forme = "";
		switch (contre) {
		case 5:
		case 1:
			forme = context.getString(R.string.txtDetailSrvc_textViewEchangePrix);
			break;
		case 2:
			forme = context.getString(R.string.txtDetailSrvc_textViewEchangePret);
			break;
		case 3:
			forme = context.getString(R.string.txtDetailSrvc_textViewEchangeContre);
			break;
		case 7:
		case 6:
		case 4:
			forme = context.getString(R.string.txtDetailSrvc_textViewEchangeDon);
			break;
		case 8 :
		case 9:
			forme = context.getString(R.string.txtDetailSrvc_textViewRemunerationObligatoire);
			break;
		}
		return forme;
	}
}
