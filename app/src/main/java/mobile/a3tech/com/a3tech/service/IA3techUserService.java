package mobile.a3tech.com.a3tech.service;

import java.util.List;

import mobile.a3tech.com.a3tech.exception.EducationException;
import mobile.a3tech.com.a3tech.model.A3techUser;

public interface IA3techUserService {

    public A3techUser loginfb(String nom, String prenom, String facebookId,
                              String email) throws EducationException;

    String contactSupport(String idUser, String message, String password)
            throws EducationException;

    public String checkMobile(String tel, String idUser, String password)
            throws EducationException;

    public String checkMail(String mail, String idUser, String password)
            throws EducationException;

    String validerMobile(String idUser, String password)
            throws EducationException;

    A3techUser getUser(String login, String password)
            throws EducationException;

    A3techUser createAccount(String nom, String prenom, String email,
                             String password, String image, String regId, String pseudo)
            throws EducationException;
    String initPassword(String email, String newPassword)
            throws EducationException;

    String desabonnerUser(String idUser, String password) throws EducationException ;


    A3techUser getProfil(String idUser, String password) throws EducationException;

    String updateProfil(String identifiant, String nom, String prenom,
                        String tel, String pseudo, String categorie, String latitude,
                        String longitude, String regId, String image, String mode,
                        String distance, String srvc, String dateNaissance, String ville, String adresse, String password)
            throws EducationException;

    Integer changePassword(String oldPassword, String newPassword,
                           String idUser) throws EducationException;

    List<A3techUser> getTechnicienNearLocation(String latitude, String longitude, String ville, int st, int en) throws EducationException;


    List<Long> getListeUserToEnabledForClient(Long clientId) throws EducationException;

    Boolean isTechnicienEnabledForClient(Long clientId, Long TechId) throws EducationException;

        Double fetchSoldeDisponible(Long userID) throws EducationException;
}
