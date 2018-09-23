package mobile.a3tech.com.a3tech.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortCategorie {
	List<Categorie> categories;
	Map<String, List<Categorie>> ssCategories = new HashMap<String, List<Categorie>>();

	
	public static List<Categorie> convertArrayToCategories(String[] list) {
		List<Categorie> categories = new ArrayList<Categorie>();
		int i = 0;
		for (String string : list) {
			Categorie categorie = new Categorie();
			categorie.setIdentifiant(String.valueOf(i));
			categorie.setLibelle(string);
			categories.add(categorie);

		}
		return categories;

	}
	
	
	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}

	public Map<String, List<Categorie>> getSsCategories() {
		return ssCategories;
	}

	public void setSsCategories(Map<String, List<Categorie>> ssCategories) {
		this.ssCategories = ssCategories;
	}

	public static SortCategorie sortCat(List<Categorie> data) {

		List<Categorie> categories = new ArrayList<Categorie>();
		Map<String, List<Categorie>> ssCategories = new HashMap<String, List<Categorie>>();

		for (Categorie categorie : data) {
			if (categorie.getType().equals("c")) {
				categories.add(categorie);
			}
		}
		for (Categorie categorie : data) {
			if(categorie.getType().equals("sc")){
				String parentId = (categorie.getParentId() != null && !categorie.getParentId().equals("")) ? categorie.getParentId() :"xx" ;
			
					List<Categorie> categories2 = ssCategories.get(parentId);
					if (categories2 == null || categories2.size() == 0)
						categories2 = new ArrayList<Categorie>();
					categories2.add(categorie);
					ssCategories.put(parentId, categories2);
				
			}
		}

		SortCategorie sortCategorie = new SortCategorie();
		sortCategorie.setCategories(categories);
		sortCategorie.setSsCategories(ssCategories);
		return sortCategorie;

	}

}
