package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.utils.Constant;

public class VilleService extends AbstractService implements Constant {

	
	//liste des villes
	public List<Categorie> listeVilles(String pays, String lang, String idUser, String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		 params.put("pays", pays);
	     params.put("lang", lang);
	     params.put("idUser", idUser);
	     params.put("password", password);
		HashMap<String,List<Categorie>> result = getResult(CHECK_EDU_LISTE_VILLE_URL,params,
				new TypeReference<HashMap<String,List<Categorie>>>() {
				});
		List<Categorie> categories = result.get("villes");
		return categories;
    }
	
	
	//liste des quartiers
		public List<Categorie> listeQuartiers(String idVille,String idUser, String password) throws EducationException{
			Map<String, String> params = new HashMap<String, String>();
			params.put("idVille", idVille);
			params.put("idUser", idUser);
		     params.put("password", password);
			HashMap<String,List<Categorie>> result = getResult(CHECK_EDU_LISTE_QUARTIER_URL,params,
					new TypeReference<HashMap<String,List<Categorie>>>() {
					});
			List<Categorie> categories = result.get("quartiers");
			return categories;
	    }
	
		
}
