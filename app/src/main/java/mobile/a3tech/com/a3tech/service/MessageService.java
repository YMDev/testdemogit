package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Message;
import mobile.a3tech.com.a3tech.utils.Constant;

public class MessageService extends AbstractService implements Constant {

	// liste des messages d'une mission
	public List<Message> messageMission(String idMission, String idUser,
										String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idMission", idMission);
		params.put("idUser", idUser);
		params.put("password", password);
		HashMap<String, List<Message>> result = getResult(
				CHECK_EDU_MESSAGES_MISSION_URL, params,
				new TypeReference<HashMap<String, List<Message>>>() {
				});

		List<Message> messages = result.get("messages");
		return messages;

	}

	// creer un message
	public String createMessage(String idMission, String idUser, String texte,
			String type, String idOffre, String password)
			throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idMission", idMission);
		params.put("idUser", idUser);
		params.put("texte", texte);
		params.put("type", type);
		params.put("password", password);
		if (idOffre != null)
			params.put("idOffre", idOffre);
		HashMap<String, String> result = getResult(
				CHECK_EDU_CREATE_MESSAGE_URL, params,
				new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
		return s;
	}

}
