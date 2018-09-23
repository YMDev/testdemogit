package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.PieceJointe;
import mobile.a3tech.com.a3tech.utils.Constant;

public class PieceJointeService extends AbstractService implements Constant {

   

    public List<PieceJointe> listePiecesJointes(String idMission, String idUser, String password) throws EducationException {
    	
    	Map<String, String> params = new HashMap<String, String>();
		params.put("idMission", idMission);
		params.put("idUser", idUser);
		 params.put("password", password);
		HashMap<String,List<PieceJointe>> result = getResult(CHECK_EDU_LISTE_PIECES_JOINTES_URL, params,
				new TypeReference<HashMap<String,List<PieceJointe>>>() {
				});
		List<PieceJointe> pieceJointes = result.get("piecesjointes");
		return pieceJointes;
		
		
    }
}
