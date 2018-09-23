package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.Map;


import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.utils.Constant;

public class MissionSuiviService extends AbstractService implements Constant {

	//creer un mission suivi
		public String createMissionSuivi(String idMission,String idUser,String password) throws EducationException {
			Map<String, String> params = new HashMap<String, String>();
			params.put("idMission", idMission);
			params.put("idUser", idUser);
			 params.put("password", password);
			HashMap<String,String> result = getResult(CHECK_EDU_CREATE_MISSION_SUIVI_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
	
	
	
	
		
}
