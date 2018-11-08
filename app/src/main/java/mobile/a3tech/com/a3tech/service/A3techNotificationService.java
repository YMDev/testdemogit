package mobile.a3tech.com.a3tech.service;

import com.google.gson.Gson;

import org.codehaus.jackson.type.TypeReference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.A3techNotification;
import mobile.a3tech.com.a3tech.model.Categorie;
import mobile.a3tech.com.a3tech.utils.Constant;

public class A3techNotificationService extends AbstractService implements Constant {



	public A3techNotification createNotification(A3techNotification notification) throws EducationException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("notification", notification.to_Json());
		HashMap<String, String> result = getResult(A3TECH_CREATE_NOTIFICATION, params,
				new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
		return new Gson().fromJson(s, A3techNotification.class);
	}

	public List<A3techNotification> filtreNotification(String lang, Long connectedUser, String keyWord, String start, String limit, String key, final Long missionID,final Long userTechID, String password, int order, int type) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("lang", lang);
		params.put("connectedUser", connectedUser+"");
		params.put("keyWord", keyWord);
		params.put("start", start);
		params.put("limit", limit+"");
		params.put("key", key);

		params.put("missionID", missionID+"");
		params.put("password", password);
		params.put("order", order+"");
		params.put("userTechID", userTechID+"");
		HashMap<String,List<A3techNotification>> result = getResult(CHECK_EDU_LISTE_QUARTIER_URL,params,
				new TypeReference<HashMap<String,List<A3techNotification>>>() {
				});
		List<A3techNotification> categories = result.get("resultat");
		return categories;
	}

	public String deleteNotification(Long idNotification, Long idUser, String password, boolean isTech) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("notification", idNotification+"");
		params.put("idUser", idUser+"");
		params.put("isTech", isTech+"");
		HashMap<String, String> result = getResult(CHECK_EDU_DELETE_MISSION_URL, params,
				new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
		return s;
	}
}
