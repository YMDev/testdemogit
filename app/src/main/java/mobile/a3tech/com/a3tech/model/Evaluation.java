package mobile.a3tech.com.a3tech.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Evaluation implements Parcelable{
	

	public Evaluation() {
		// TODO Auto-generated constructor stub
	}
	public Evaluation(String identifiant, String evaluateurId, String nom,
			String prenom, String pseudo, String note, String dateCreation,
			String missionId, String missionDescription) {
		super();
		this.identifiant = identifiant;
		this.evaluateurId = evaluateurId;
		this.nom = nom;
		this.prenom = prenom;
		this.pseudo = pseudo;
		this.note = note;
		this.dateCreation = dateCreation;
		this.missionId = missionId;
		this.missionDescription = missionDescription;
	}

	private String identifiant;
	private String evaluateurId;
	private String nom;
	private String prenom;
	private String pseudo;
	private String note;
	private String dateCreation;
	private String missionId;
	private String missionDescription;
	private String categorie ;
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public String getScategorie() {
		return scategorie;
	}
	public void setScategorie(String scategorie) {
		this.scategorie = scategorie;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}

	private String scategorie ;
	private String service ;
	
	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getEvaluateurId() {
		return evaluateurId;
	}

	public void setEvaluateurId(String evaluateurId) {
		this.evaluateurId = evaluateurId;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getMissionDescription() {
		return missionDescription;
	}

	public void setMissionDescription(String missionDescription) {
		this.missionDescription = missionDescription;
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
