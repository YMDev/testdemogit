package mobile.a3tech.com.a3tech.service;

import java.util.HashMap;
import java.util.Map;


import org.codehaus.jackson.type.TypeReference;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.User;
import mobile.a3tech.com.a3tech.utils.Constant;

public class UserService extends AbstractService implements Constant {

	

	public User loginfb(String nom, String prenom, String facebookId,
						String email) throws EducationException {
		Map<String, String> params = new HashMap();
		params.put("nom", nom);
		params.put("prenom", prenom);
		params.put("facebookId", facebookId);
		params.put("email", email);
		User result = getResult(CHECK_EDU_LOGIN_FB_URL, params,
				new TypeReference<User>() {
				});
		return result;
	}

	// envoyer id gcm to server
	public String contactSupport(String idUser, String message,String password)
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
		public String checkMobile(String tel,String idUser,String password)
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
		
		public String checkMail(String mail,String idUser,String password)
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
		
		
		public String validerMobile(String idUser,String password)
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
	public User getUser(String login, String password)
			throws EducationException {
/*		Map<String, String> params = new HashMap<String, String>();
		params.put("email", login);
		params.put("password", password);
		User result = getResult(CHECK_EDU_LOGIN_URL, params,
				new TypeReference<User>() {
				});*/
		User userMocked = new User();
		userMocked.setEmail("mouad.test@gmail.com");
		userMocked.setAdresse("adresse rabat hay riad");
		userMocked.setCheckphone("02922029292");
		userMocked.setDateCreation("01/01/2012");
		userMocked.setDateNaissance("01/03/1922");
		userMocked.setIdentifiant("kdaasa");
		userMocked.setIsNew("true");
		userMocked.setNbrServiceEmis("0");
		userMocked.setNom("miyad");
		userMocked.setPrenom("kdkdlll");

		return userMocked;

	}

	public User createAccount(String nom, String prenom, String email,
			String password, String image, String regId, String pseudo)
			throws EducationException {
/*		Map<String, String> params = new HashMap<String, String>();
		params.put("nom", nom);
		params.put("prenom", prenom);
		params.put("email", email);
		params.put("password", password);
		params.put("id_photo_profil", image);
		params.put("gcm_regid", regId);
		params.put("pseudo", pseudo);
		User result = getResult(
				CHECK_EDU_CREATE_ACCOUNT_URL, params,
				new TypeReference<User>() {
				});*/


		User userMocked = new User();
		userMocked.setEmail("mouad.test@gmail.com");
		userMocked.setAdresse("adresse rabat hay riad");
		userMocked.setCheckphone("02922029292");
		userMocked.setDateCreation("01/01/2012");
		userMocked.setDateNaissance("01/03/1922");
		userMocked.setIdentifiant("kdaasa");
		userMocked.setIsNew("true");
		userMocked.setNbrServiceEmis("0");
		userMocked.setNom("miyad");
		userMocked.setPrenom("kdkdlll");

		return userMocked;
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

	public String desabonnerUser(String idUser,String password) throws EducationException {
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

	public User getProfil(String idUser,String password) throws EducationException {
	/*	Map<String, String> params = new HashMap<String, String>();
		params.put("idUser", idUser);
		params.put("password", password);
		User result = getResult(CHECK_EDU_PROFIL_URL, params,
				new TypeReference<User>() {
				});*/

		User userMocked = new User();
		userMocked.setEmail("mouad.test@gmail.com");
		userMocked.setAdresse("adresse rabat hay riad");
		userMocked.setCheckphone("02922029292");
		userMocked.setDateCreation("01/01/2012");
		userMocked.setDateNaissance("01/03/1922");
		userMocked.setIdentifiant("kdaasa");
		userMocked.setIsNew("true");
		userMocked.setNbrServiceEmis("0");
		userMocked.setNom("miyad");
		userMocked.setPrenom("kdkdlll");

		return userMocked;
	}

	public String updateProfil(String identifiant, String nom, String prenom,
			String tel, String pseudo, String categorie, String latitude,
			String longitude, String regId, String image, String mode,
			String distance, String srvc, String dateNaissance, String ville,String adresse,String password)
			throws EducationException {
		/*Map<String, String> params = new HashMap<String, String>();
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
		String s = result.get("result");*/
		return "OK";

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
