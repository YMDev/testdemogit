package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AvisService extends AbstractService implements Constant {

	public AvisService() {
	}

	public List<Avis> avisAssociation(String idAssociation, String idUser,
									  String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idAssociation", idAssociation);
		params.put("idUser", idUser);
		params.put("password", password);
		HashMap<String, List<Avis>> result = getResult(
				CHECK_EDU_AVISS_ASSOCIATION_URL, params,
				new TypeReference<HashMap<String, List<Avis>>>() {
				});

		List<Avis> aviss = result.get("aviss");
		return aviss;
	}

	public String createAvis(String idAssociation, String avantage,
			String inconvenient, String note, String statut, String idUser,
			String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idAssociation", idAssociation);
		params.put("avantage", avantage);
		params.put("inconvenient", inconvenient);
		params.put("note", note);
		params.put("statut", statut);
		params.put("idUser", idUser);
		params.put("password", password);
		HashMap<String, String> result = getResult(CHECK_EDU_CREATE_AVIS_URL,
				params, new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
		return s;
	}

	public String deleteAvis(String identifiant, String idUser, String password)
			throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("identifiant", identifiant);
		params.put("idUser", idUser);
		params.put("password", password);
		HashMap<String, String> result = getResult(CHECK_EDU_DELETE_AVIS_URL,
				params, new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
		return s;
	}
}
