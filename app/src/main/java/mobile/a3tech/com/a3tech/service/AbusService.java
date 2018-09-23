package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.Map;


import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.utils.Constant;

public class AbusService extends AbstractService implements Constant {

	
	
	
	//creer un abus
		public String createAbus(String idMission,int idMessage,int typeAbus,String idUser,String password) throws EducationException {
			Map<String, String> params = new HashMap<String, String>();
			params.put("idMission", idMission);
			params.put("idUser", idUser);
			params.put("typeAbus", String.valueOf(typeAbus));
			params.put("password", password);
			if(idMessage!=0) params.put("idMessage", String.valueOf(idMessage));
			HashMap<String,String> result = getResult(CHECK_EDU_CREATE_ABUS_URL, params,
					new TypeReference<HashMap<String,String>>() {
					});
			String s = result.get("result");
			return s;
	    }
		

	
	
		
}
