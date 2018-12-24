package mobile.a3tech.com.a3tech.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.utils.Constant;

public class CategorieService extends AbstractService implements Constant {

	// liste des categories
	public List<Categorie> listeCategories(String identifiant, String service,
										   String type, String parentId, String lang, String idUser,
										   String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("identifiant", "");
		 List<Categorie> categories = getResult(
				 A3TECH_LISTE_CATEGOIES, params,
				new TypeReference<List<Categorie>>() {
				});

	/*	List<Categorie> categories = new ArrayList<>();
		categories.add(new Categorie("1","Electricité","installation, réparation, fabrication, autres ...", "1", "1"));
		categories.add(new Categorie("2","Plomberie","installation, réparation, fabrication, autres ...", "1", "2"));
		categories.add(new Categorie("3","Climatisation", "installation, réparation, fabrication, autres ...","1", "3"));
		categories.add(new Categorie("4","Serrurerie", "installation, réparation, fabrication, autres ...","1", "4"));
		categories.add(new Categorie("5","Divers","installation, réparation, fabrication, autres ...", "1", "5"));
		*/
		return categories;
	}

}
