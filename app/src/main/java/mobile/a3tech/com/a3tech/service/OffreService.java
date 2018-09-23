package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Offre;
import mobile.a3tech.com.a3tech.model.ReponseOffre;
import mobile.a3tech.com.a3tech.utils.Constant;

public class OffreService extends AbstractService implements Constant {

	
	//liste des offres d'une mission
	public ReponseOffre offreMission(String idMission, String idUser, String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idMission", idMission);
		params.put("idUser", idUser);
		 params.put("password", password);
		ReponseOffre result = getResult(CHECK_EDU_OFFRES_MISSION_URL, params,
					new TypeReference<ReponseOffre>() {
					});
			
			return result;
    }
	
	//creer une offre
		public String createOffre(String idMission,String idUser,String[] bitmaps,String commentaire,String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			params.put("idMission", idMission);
			params.put("idUser", idUser);
			if (!(bitmaps == null || bitmaps[0].equals("")))  params.put("bitmap0", bitmaps[0]);
		    if (!(bitmaps == null || bitmaps[1].equals("")))  params.put("bitmap1", bitmaps[1]);
		    if (!(bitmaps == null || bitmaps[2].equals("")))  params.put("bitmap2", bitmaps[2]);
			params.put("commentaire", commentaire);
			 params.put("password", password);
			HashMap<String,String> result = getResult(CHECK_EDU_CREATE_OFFRE_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
	
		//update Offre
		public String updateeOffre(String idOffre,String etat,String idUser,String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			params.put("idOffre", idOffre);
			params.put("etat", etat);
			params.put("idUser", idUser);
			 params.put("password", password);
			HashMap<String,String> result = getResult(CHECK_EDU_UPDATE_OFFRE_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
		
		
		public String majOffre(String idOffre,String idUser,String etat,String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			params.put("idOffre", idOffre);
			params.put("etat", etat);
			params.put("idUser", idUser);
			 params.put("password", password);
			HashMap<String,String> result = getResult(CHECK_EDU_MAJ_OFFRE_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
		//detail offre
		public Offre detailOffre(String idOffre, String idUser, String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			 params.put("idOffre", idOffre);
			 params.put("idUser", idUser);
			 params.put("password", password);
			 Offre result = getResult(CHECK_EDU_DETAIL_OFFRE_URL, params,
					new TypeReference<Offre>() {
					});
			
			return result;
		}

		//delete Offre
		public String deleteOffre(String idOffre,String idUser,String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			params.put("idOffre", idOffre);
			params.put("idUser", idUser);
			 params.put("password", password);
			
			HashMap<String,String> result = getResult(CHECK_EDU_DELETE_OFFRE_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
		
		//delete offre cote emetteur
		public String deleteOffreCoteEmetteur(String idOffre,String idUser, String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			params.put("idOffre", idOffre);
			params.put("idUser", idUser);
			 params.put("password", password);
			HashMap<String,String> result = getResult(CHECK_EDU_DELETE_OFFRE_COTE_EMETTEUR_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
		
		
			
	
	
	
		
}
