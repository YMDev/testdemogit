package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Association;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AssociationService extends AbstractService implements Constant {

	public AssociationService() {
	}

	public Association detailAssociation(String idAssociation, String idUser, String password)
			throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idAssociation", idAssociation);
		 params.put("idUser", idUser);
	     params.put("password", password);
		Association result = getResult(CHECK_EDU_DETAIL_ASSOCIATION_URL, params,
				new TypeReference<Association>() {
				});
		return result;
	}

	public List<Association> listeAssociations(String idUser, String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idUser", idUser);
	     params.put("password", password);
		HashMap<String, List<Association>> result = getResult(
				CHECK_EDU_LISTE_ASSOCIATION_URL,params,
				new TypeReference<HashMap<String, List<Association>>>() {
				});
		List<Association> associations = result.get("associations");
		return associations;

	}
}
