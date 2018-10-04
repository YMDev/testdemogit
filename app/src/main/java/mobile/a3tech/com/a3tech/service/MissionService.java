package mobile.a3tech.com.a3tech.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.Mission;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.utils.Constant;

public class MissionService extends AbstractService implements Constant {

	// creer une nouvelle mission
	public String createMission(String connectedUser,String typeTransaction,String categorie,String sousCategorie,String idTypeTroc ,String titre,
			String article,String idEtatArticle,String  contre,String objetRechercheTitre,String objetRecherche,String lieu,String latitude,String longitude,String idVille,
			String[] bitmaps,String echeance,
			String typeSponsoring,String typePaiement,String dateDebut,String dateFin,String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		if(connectedUser!=null) params.put("idUser", connectedUser);
		if(categorie!=null) params.put("typeTransaction", typeTransaction);
		if(categorie!=null) params.put("service", categorie);
		if(sousCategorie!=null) params.put("type_mission_id", sousCategorie);
		if(idTypeTroc!=null) params.put("typetroc", idTypeTroc);
		if(article!=null) params.put("article", article);
		if(idEtatArticle!=null) params.put("etatarticle", idEtatArticle);
		if(objetRecherche!=null) params.put("objetrecherche", objetRecherche);
		if(contre!=null) params.put("contre", contre);
		if(lieu!=null) params.put("adresse", lieu);
		if(idVille!=null && !idVille.equals("")) params.put("idVille", idVille);
		if(latitude!=null) params.put("latitude", latitude);
		if(longitude!=null) params.put("longitude", longitude);
		if(objetRechercheTitre!=null) params.put("titreobjetrecherche", objetRechercheTitre);
		if(titre!=null) params.put("titre", titre);
		if(echeance!=null) params.put("echeance", echeance);
		if (!(bitmaps == null || bitmaps[0].equals("")))  params.put("bitmap0", bitmaps[0]);
	    if (!(bitmaps == null || bitmaps[1].equals("")))  params.put("bitmap1", bitmaps[1]);
	    if (!(bitmaps == null || bitmaps[2].equals("")))  params.put("bitmap2", bitmaps[2]);
	    if(typeSponsoring!=null) params.put("typeSponsoring", typeSponsoring);
	    if(typePaiement!=null) params.put("typePaiement", typePaiement);
	    if(dateDebut!=null) params.put("dateDebut", dateDebut);
	    if(dateFin!=null) params.put("dateFin", dateFin);
	    params.put("password", password);
		HashMap<String, String> result = getResult(
				CHECK_EDU_CREATE_MISSION_URL, params,
				new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
		return s;
	}
	
    //filtrer missions
    public List<Mission> filtreMission(String lang, String connectedUser, String keyWord, String distance, String services, String start, String limit, String key, String typeTransaction, String premium, String password,
									   int order, int type) throws EducationException{
/*		Map<String, String> params = new HashMap<String, String>();
		params.put("start", start);
        params.put("limit", limit);
        params.put("idUser", connectedUser);
        params.put("keyword", keyWord);
        params.put("distance", distance);
        params.put("services", services);
        params.put("premium", premium);
        params.put("lang", lang);
        params.put("password", password);
        params.put("order", String.valueOf(order));
        params.put("type", String.valueOf(type));
        if (key != null) {
            params.put("key", key);
        }
        if (typeTransaction != null) {
            params.put("typeTransaction", typeTransaction);
        }
        HashMap<String,List<Mission>> result = getResult(CHECK_EDU_ALGO_MISSION_URL, params,
		//HashMap<String,List<Mission>> result = getResult(CHECK_EDU_FILTER_MISSION_URL, params,
				new TypeReference<HashMap<String,List<Mission>>>() {
				});
		List<Mission> missions = result.get("missions");*/
		List<Mission> missions = new ArrayList<>();
		Mission mm1 = new Mission();
		mm1.setTechnicien(new User());
		mm1.getTechnicien().setNom("mouad");
		mm1.getTechnicien().setPrenom("bouhjra");
		mm1.setAdresse("adresse mission 1");
		mm1.setArticle("article mission");
		mm1.setCatDescription("cat description");
		mm1.setCategorieId("cat1");
		mm1.setCheckmail("mail.mail.cheked@gmail.com");
		mm1.setCheckphone("02020202020");
		mm1.setContre("contre");
		mm1.setDateCreation("20/09/2018");
		mm1.setDateFin("23/09/2018");
		mm1.setNote("2");
		mm1.setSponsorise(0);
		mm1.setService("1");
		mm1.setTypeTransaction("1");
		mm1.setPseudo("mission pseudo");
		mm1.setDistance("-1.0d");
		mm1.setNbrVues("2");
		mm1.setStatut("3");
		mm1.setIdentifiant("928292");
		mm1.setOriginator("kdaasa");
		missions.add(mm1);
		return missions;
    }
    
    //detail missions
    public Mission detailMission(String idMission, String userId,String password) throws EducationException{
	/*	Map<String, String> params = new HashMap<String, String>();
		 params.put("idMission", idMission);
	     params.put("idUser", userId);
	     params.put("password", password);
		Mission result = getResult(CHECK_EDU_DETAIL_MISSION_URL, params,
				new TypeReference<Mission>() {
				});
		*/

		Mission mm1 = new Mission();
		mm1.setAdresse("adresse mission 1");
		mm1.setArticle("article mission");
		mm1.setCatDescription("cat description");
		mm1.setCategorieId("cat1");
		mm1.setCheckmail("mail.mail.cheked@gmail.com");
		mm1.setCheckphone("02020202020");
		mm1.setContre("contre");
		mm1.setDateCreation("2018-09-20 00:00:00");
		mm1.setDateFin("2018-09-22 00:00:00");
		mm1.setNote("2");
		mm1.setSponsorise(0);
		mm1.setService("1");
		mm1.setTypeTransaction("1");
		mm1.setPseudo("mission pseudo");
		mm1.setDistance("-1.0d");
		mm1.setNbrVues("2");
		mm1.setStatut("3");
		mm1.setIdentifiant("928292");
		mm1.setOriginator("kdaasa");
		return mm1;
	}
    
    //missions emises
      public List<Mission> missionsEmises(String lang, String idUser,String start,String limit,String password) throws EducationException{
  		Map<String, String> params = new HashMap<String, String>();
  		params.put("idUser", idUser);
  		params.put("start", start);
  		params.put("limit", limit);
  		params.put("lang", lang);
  		params.put("password", password);
  		HashMap<String,List<Mission>> result = getResult(CHECK_EDU_MISSIONS_EMISES_URL, params,
  				new TypeReference<HashMap<String,List<Mission>>>() {
  				});
  		
  		List<Mission> missions = result.get("missions");
  		return missions;
  		
  		
  	}
      
      //missions suivies
      public List<Mission> missionsSuivies(String idUser,String start,String limit,String password) throws EducationException{
  		Map<String, String> params = new HashMap<String, String>();
  		params.put("idUser", idUser);
  		params.put("start", start);
  		params.put("limit", limit);
  		params.put("password", password);
  		HashMap<String,List<Mission>> result = getResult(CHECK_EDU_MISSIONS_SUIVIES_URL, params,
  				new TypeReference<HashMap<String,List<Mission>>>() {
  				});
  		
  		List<Mission> missions = result.get("missions");
  		return missions;
  	}
      
      
    //missions suivies chat
      public List<Mission> missionsSuiviesChat(String lang, String idUser,String start,String limit,String password) throws EducationException{
  		Map<String, String> params = new HashMap<String, String>();
  		params.put("idUser", idUser);
  		params.put("start", start);
  		params.put("limit", limit);
  		params.put("lang", lang);
  		params.put("password", password);
  		HashMap<String,List<Mission>> result = getResult(CHECK_EDU_MISSIONS_SUIVIES_CHAT_URL, params,
  				new TypeReference<HashMap<String,List<Mission>>>() {
  				});
  		
  		List<Mission> missions = result.get("missions");
  		return missions;
  	}
      
    //delete mission
  	public String deleteMission(String identifiant,  String idUser,String password) throws EducationException{
  				Map<String, String> params = new HashMap<String, String>();
  				params.put("idMission", identifiant);
  				
  				params.put("idUser", idUser);
  				params.put("password", password);
  				HashMap<String,String> result = getResult(CHECK_EDU_DELETE_MISSION_URL, params,
  						new TypeReference<HashMap<String,String>>() {
  						});
  				String s = result.get("result");
  				return s;
  		    }
      
  	
    

}
