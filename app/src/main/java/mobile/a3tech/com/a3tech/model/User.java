package mobile.a3tech.com.a3tech.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class User implements  Parcelable {
	
	
	private String identifiant ;
	private String nom;
	private String prenom;
	private String password;
	private String email;
	
	private String facebookId;
	private String id_photo_profil;
	private String    notation;
	private String dateCreation ;
	private String adresse;
	private String longitude;
	private String latitude;
	private String categorie;
	private String pseudo ;
	private String telephone;
	private String gcm_regid ;
	private String statut;
	private String mobil_check ;
	private String checkmail ;
	private String checkphone ;
	private String rating;
	private String nbrReviews;
	private List<Avis> listAvis;


	public String getRating() {
		if(StringUtils.isBlank(rating)) rating = "0";
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getNbrReviews() {
		return nbrReviews;
	}

	public void setNbrReviews(String nbrReviews) {
		this.nbrReviews = nbrReviews;
	}

	public List<Avis> getListAvis() {
		return listAvis;
	}

	public void setListAvis(List<Avis> listAvis) {
		this.listAvis = listAvis;
	}

	public String getCheckmail() {
		return checkmail;
	}

	public void setCheckmail(String checkmail) {
		this.checkmail = checkmail;
	}

	public String getCheckphone() {
		return checkphone;
	}

	public void setCheckphone(String checkphone) {
		this.checkphone = checkphone;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	private String nbr;
	private String dateNaissance;
	private String idVille ;
	private String ville;
	
	public String getIdVille() {
		return idVille;
	}

	public void setIdVille(String idVille) {
		this.idVille = idVille;
	}

	public String getDateNaissance() {
		return dateNaissance;
	}

	public void setDateNaissance(String dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	private String distance ;
	private String srvc ;
	
	public String getNbr() {
		return nbr;
	}

	public String getSrvc() {
		return srvc;
	}

	public void setSrvc(String srvc) {
		this.srvc = srvc;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setNbr(String nbr) {
		this.nbr = nbr;
	}

	public String getMobil_check() {
		return mobil_check;
	}

	public void setMobil_check(String mobil_check) {
		this.mobil_check = mobil_check;
	}

	public String getEmail_check() {
		return email_check;
	}

	public void setEmail_check(String email_check) {
		this.email_check = email_check;
	}

	private String email_check ;
	
	
	
	
	private String mode ;
	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
	}

	private String nbrServiceEmis ;
	private String nbrServiceSuivis;
	private String isNew;
	
	public String getGcm_regid() {
		return gcm_regid;
	}

	public void setGcm_regid(String gcm_regid) {
		this.gcm_regid = gcm_regid;
	}

	


	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	

	public String getId_photo_profil() {
		return id_photo_profil;
	}

	public void setId_photo_profil(String id_photo_profil) {
		this.id_photo_profil = id_photo_profil;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getNbrServiceEmis() {
		return nbrServiceEmis;
	}

	public void setNbrServiceEmis(String nbrServiceEmis) {
		this.nbrServiceEmis = nbrServiceEmis;
	}

	public String getNbrServiceSuivis() {
		return nbrServiceSuivis;
	}

	public void setNbrServiceSuivis(String nbrServiceSuivis) {
		this.nbrServiceSuivis = nbrServiceSuivis;
	}

	public String getIsNew() {
		return isNew;
	}
	public String isNew() {
		return isNew;
	}

	public void setNew(String isNew) {
		this.isNew = isNew;
	}

	
	
	

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}

}
