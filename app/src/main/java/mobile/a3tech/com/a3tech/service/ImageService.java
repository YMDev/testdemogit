package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.images.Image;
import mobile.a3tech.com.a3tech.model.Indicateur;
import mobile.a3tech.com.a3tech.utils.Constant;

public class ImageService extends AbstractService implements Constant {

	public List<Image> listeImages(String idAssociation, String idUser,
								   String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idAssociation", idAssociation);
		params.put("idUser", idUser);
		params.put("password", password);
		HashMap<String, List<Image>> result = getResult(
				CHECK_EDU_LISTE_IMAGES_ASSOCIATION_URL, params,
				new TypeReference<HashMap<String, List<Image>>>() {
				});

		List<Image> images = result.get("images");
		return images;
	}

	public Indicateur nbrImage(String idAssociation, String idUser,
							   String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idAssociation", idAssociation);
		params.put("idUser", idUser);
		params.put("password", password);
		Indicateur result = getResult(CHECK_EDU_NOMBRE_IMAGES_ASSOCIATION_URL,
				params, new TypeReference<Indicateur>() {
				});

		return result;
	}
}
