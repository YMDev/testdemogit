package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Evaluation;
import mobile.a3tech.com.a3tech.utils.Constant;

public class EvaluationService extends AbstractService implements Constant {

	    //liste evaluations
	 
    public List<Evaluation> listeEvaluation(String lang, String idUser, String type, String start, String limit, String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idUser", idUser);
		params.put("start", start);
		params.put("limit", limit);
		params.put("type", type);
		params.put("lang", lang);
		params.put("password", password);
		HashMap<String,List<Evaluation>> result = getResult(CHECK_EDU_LISTE_EVALUATION_URL, params,
				new TypeReference<HashMap<String,List<Evaluation>>>() {
				});
		
		List<Evaluation> evaluations = result.get("evaluations");
		return evaluations;
	}
    
    
    //creer evaluation
    public String createEvaluation(String idEvalue,String idEvaluateur,String rating,String idMission,String password) throws EducationException{
		Map<String, String> params = new HashMap<String, String>();
		params.put("idEvalue", idEvalue);
		params.put("idUser", idEvaluateur);
		params.put("rating", rating);
		params.put("idMission", idMission);
		params.put("password", password);
		HashMap<String,String> result = getResult(CHECK_EDU_CREATE_EVALUATION_URL, params,
				new TypeReference<HashMap<String,String>>() {
				});
		String s = result.get("result");
		return s;
	}
    
		
}
