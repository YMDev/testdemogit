package mobile.a3tech.com.a3tech.service;

import com.google.gson.Gson;

import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.A3techUser;
import mobile.a3tech.com.a3tech.model.Avis;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.utils.Constant;

public class A3techUserService extends AbstractService implements Constant, IA3techUserService {


    public A3techUser loginfb(String nom, String prenom, String facebookId,
                              String email) throws EducationException {
        Map<String, String> params = new HashMap();
        params.put("nom", nom);
        params.put("prenom", prenom);
        params.put("facebookId", facebookId);
        params.put("email", email);
        A3techUser result = getResult(A3TECH_LOGIN_FB_URL, params,
                new TypeReference<A3techUser>() {
                });
        return result;
    }

    // envoyer id gcm to server
    public String contactSupport(String idUser, String message, String password)
            throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("idUser", idUser);
        params.put("message", message);
        params.put("password", password);
        HashMap<String, String> result = getResult(
                CHECK_EDU_CONTACT_SUPPORT_URL, params,
                new TypeReference<HashMap<String, String>>() {
                });
        String s = result.get("result");
        return s;

    }

    // envoyer id gcm to server
    public String checkMobile(String tel, String idUser, String password)
            throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("idUser", idUser);
        params.put("tel", tel);
        params.put("password", password);
        HashMap<String, String> result = getResult(
                CHECK_EDU_CHECK_MOBILE_URL, params,
                new TypeReference<HashMap<String, String>>() {
                });
        String s = result.get("result");
        return s;

    }

    public String checkMail(String mail, String idUser, String password)
            throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("idUser", idUser);
        params.put("mail", mail);
        params.put("password", password);
        HashMap<String, String> result = getResult(
                CHECK_EDU_CHECK_MAIL_URL, params,
                new TypeReference<HashMap<String, String>>() {
                });
        String s = result.get("result");
        return s;

    }


    public String validerMobile(String idUser, String password)
            throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("idUser", idUser);
        params.put("password", password);
        HashMap<String, String> result = getResult(
                CHECK_EDU_VALIDER_MOBILE_URL, params,
                new TypeReference<HashMap<String, String>>() {
                });
        String s = result.get("result");
        return s;

    }

    // verifier si l'utilisateur existe etant donn�e son login et son password
    public A3techUser getUser(String login, String password)
            throws EducationException {
	Map<String, String> params = new HashMap<String, String>();
		params.put("login", login);
		params.put("password", password);
        A3techUser result = getResult(CHECK_EDU_LOGIN_URL, params,
				new TypeReference<A3techUser>() {
				});
        return result;

    }

    public A3techUser createAccount(String nom, String prenom, String email,
                              String password, String image, String regId, String pseudo)
            throws EducationException {
	Map<String, String> params = new HashMap<String, String>();
		params.put("nom", nom);
		params.put("prenom", prenom);
		params.put("email", email);
		params.put("password", password);
		params.put("id_photo_profil", image);
		params.put("gcm_regid", regId);
		params.put("pseudo", pseudo);
        A3techUser result = getResult(
                A3TECH_CREATE_ACCOUNT, params,
				new TypeReference<A3techUser>() {
				});

        return result;
    }

    public String initPassword(String email, String newPassword)
            throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("newPassword", newPassword);
        HashMap<String, String> result = getResult(CHECK_EDU_NEW_PASSWORD_URL,
                params, new TypeReference<HashMap<String, String>>() {
                });
        String s = result.get("result");
        return s;

    }

    public String desabonnerUser(String idUser, String password) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("idUser", idUser);
        params.put("password", password);
        HashMap<String, String> result = getResult(
                CHECK_EDU_DESABONNER_USER_URL, params,
                new TypeReference<HashMap<String, String>>() {
                });
        String s = result.get("result");
        return s;
    }

    public A3techUser getProfil(String idUser, String password) throws EducationException {
		Map<String, String> params = new HashMap<String, String>();
		params.put("idUser", idUser);
		params.put("password", password);
        A3techUser result = getResult(CHECK_EDU_PROFIL_URL, params,
				new TypeReference<A3techUser>() {
				});
        return result;
    }

    public String updateProfil(String identifiant, String nom, String prenom,
                               String tel, String pseudo, String categorie, String latitude,
                               String longitude, String regId, String image, String mode,
                               String distance, String srvc, String dateNaissance, String ville, String adresse, String password)
            throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
		params.put("idUser", identifiant);
		params.put("nom", nom);
		params.put("prenom", prenom);
		params.put("tel", tel);
		params.put("pseudo", pseudo);
		params.put("categorie", categorie);
		params.put("latitude", latitude);
		params.put("longitude", longitude);
		params.put("regId", regId);
		params.put("id_photo_profil", image);
		params.put("mode", mode);
		params.put("distance", distance);
		params.put("srvc", srvc);
		params.put("dateNaissance", dateNaissance);
		params.put("ville", ville);
		params.put("adresse", adresse);
		params.put("password", password);
		HashMap<String, String> result = getResult(CHECK_EDU_UPDATE_PROFIL_URL,
				params, new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");
        return s;

    }

    public Integer changePassword(String oldPassword, String newPassword,
                                  String idUser) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("password", oldPassword);
        params.put("newPassword", newPassword);
        params.put("idUser", idUser);
        HashMap<String, Integer> result = getResult(
                CHECK_EDU_CHANGE_PASSWORD_URL, params,
                new TypeReference<HashMap<String, Integer>>() {
                });
        Integer s = result.get("result");
        return s;

    }

    public String getVersion()
            throws EducationException {
	/*	HashMap<String, String> result = getResult(
				CHECK_EDU_GET_VERSION_URL,
				new TypeReference<HashMap<String, String>>() {
				});
		String s = result.get("result");*/
        return "1.0";

    }

    public List<A3techUser> getTechnicienNearLocation(String latitude, String longitude, String ville, int st, int en) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("start", st+"");
        params.put("end", en+"");
		List<A3techUser> result = getResult(CHECK_local, params,
				new TypeReference<List<A3techUser>>() {
				});

        return result;
    }

    @Override
    public List<Long> getListeUserToEnabledForClient(Long clientId) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("clientId", clientId+"");
        List<Long> result = getResult(A3TECH_GET_TECH_ENABLED_FOR_CLIENT, params,
                new TypeReference<List<Long>>() {
                });

        return result;
    }

    @Override
    public Boolean isTechnicienEnabledForClient(Long clientId, Long TechId) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
		params.put("clientId", clientId+"");
		params.put("TechId", TechId+"");
        Boolean s = getResult(
                A3TECH_IS_TECH_ENABLED_FOR_CLIENT, params,
                new TypeReference<Boolean>() {
                });
        return s;
    }

    @Override
    public Double fetchSoldeDisponible(Long userID) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userID", userID+"");

        HashMap<String, Double> result = getResult(
                A3TECH_FETCH_USER_SOLDE_DISPO, params,
                new TypeReference<HashMap<String, Double>>() {
                });
        Double s = result.get("result");
        return s;
    }

    @Override
    public List<A3techUser> fetchTechnicien(String keyword, int start, int end) throws EducationException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("keyword", keyword+"");
        params.put("start", start+"");
        params.put("end", end+"");

        List<A3techUser> result = getResult(A3TECH_FETCH_TECHNICIEN, params,
                new TypeReference<List<A3techUser>>() {
                });
        return result;
    }



    private User getUserMocked(String name, String pname) {
        User userMocked = new User();
        userMocked.setEmail("hahhsas@mail.com");
        userMocked.setAdresse("adresse rabat hay riad");
        userMocked.setCheckphone("02922029292");
        userMocked.setTelephone("02922029292");
        userMocked.setDateCreation("01/01/2012");
        userMocked.setDateNaissance("01/03/1922");
        Random rand = new Random();
        int id = rand.nextInt(556550) + 1;
        userMocked.setIdentifiant(id + "");
        userMocked.setIsNew("true");
        int n = rand.nextInt(50) + 1;
        userMocked.setNbrServiceEmis(n + "");
        userMocked.setNbr(n + "");
        userMocked.setNom(name);
        userMocked.setPrenom(pname);
        userMocked.setPseudo(name);
        userMocked.setRating(rand.nextInt(5)+"");
        userMocked.setNbrReviews(rand.nextInt(15)+"");
        userMocked.setLatitude("34.267471");
        userMocked.setLongitude("-6.562630");
        return userMocked;
    }

    public List getUserReviews(String idUser, String password) throws EducationException {
	/*	Map<String, String> params = new HashMap<String, String>();
		params.put("idUser", idUser);
		params.put("password", password);
		User result = getResult(CHECK_EDU_PROFIL_URL, params,
				new TypeReference<User>() {
				});*/

	List<Avis> listeReview = new ArrayList<>();

        listeReview.add(new Avis("Goood job","02/02/2010", "4","", null));
        listeReview.add(new Avis("Cool Fancy Text Generator is a copy and paste font generator and font changer that creates Twitter, Facebook, Instagram fonts. It converts a normal text to different free cool fonts styles, such as tattoo fonts, calligraphy fonts","02/02/2010", "4","", null));
        listeReview.add(new Avis("\n" +
                "Cool Fancy Text Generator is a copy and paste font generator and font changer that creates Twitter, Facebook, Instagram fonts. It converts a normal text to different free cool fonts styles, such as tattoo fonts, calligraphy fonts, web script fonts, cursive fonts, handwriting fonts, old English fonts, word fonts, pretty fonts, font art... Facebook, Twitter, Instagram Fonts or Fonts for Instagram, Twitter, Facebook - If that is what you want then this tool is a perfect place to go because it provides more than that!\n" +
                "Basically, Cool Text Generator a cute copy and paste font generator online, font ma","02/02/2010", "4","", null));
        listeReview.add(new Avis("ext generator, weird text generator, word art generatb","02/02/2010", "4","", null));
        listeReview.add(new Avis("Goood job","02/02/2010", "4","", null));
        listeReview.add(new Avis("Goood job","02/02/2010", "4","", null));
        return listeReview;
    }


    //
    // public User getProfil(String idUser)throws TsamsiratException{
    // Map<String, String> params = new HashMap<String, String>();
    // params.put("idUser", idUser);
    //
    // User result = getResult(CHECK_TSAM_PROFIL_URL, params,
    // new TypeReference<User>() {
    // });
    // return result;
    // }
    //

}
