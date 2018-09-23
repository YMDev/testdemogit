package mobile.a3tech.com.a3tech.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Offre implements Parcelable{
	
	private String identifiant;
	private String pseudo;
	private String telephone ;
	private String telephoneEmetteur ;
	private String userId ;
	private String missionId;
	private String etat ;
	private String dateCreation ;
	
	private String notation;
	private String commentaire ;
	private String url1;
	private String url2;
	private String url3;
	private String note ;
	private boolean isOK ;
	private String connectedUser;
	private String missionUser ;
	private String statut ;
	private String pseudoMission ;
	private String noteMission ;
	private String emailEmetteur ;
	private String email ;
	private List<Message> messages = new ArrayList<Message>();
	private boolean selected ;
	
           
           
           
            
         
            
	
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setOK(boolean isOK) {
		this.isOK = isOK;
	}

	public String getPseudoMission() {
		return pseudoMission;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void setPseudoMission(String pseudoMission) {
		this.pseudoMission = pseudoMission;
	}

	public String getNoteMission() {
		return noteMission;
	}

	public void setNoteMission(String noteMission) {
		this.noteMission = noteMission;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getMissionUser() {
		return missionUser;
	}

	public void setMissionUser(String missionUser) {
		this.missionUser = missionUser;
	}

	public String getNote() {
		return note;
	}

	public String getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(String connectedUser) {
		this.connectedUser = connectedUser;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean getIsOK() {
		return isOK;
	}

	public void setIsOK(boolean isOK) {
		this.isOK = isOK;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getTelephoneEmetteur() {
		return telephoneEmetteur;
	}

	public String getUrl1() {
		return url1;
	}

	public void setUrl1(String url1) {
		this.url1 = url1;
	}

	public String getUrl2() {
		return url2;
	}

	public void setUrl2(String url2) {
		this.url2 = url2;
	}

	public String getUrl3() {
		return url3;
	}

	public void setUrl3(String url3) {
		this.url3 = url3;
	}

	public void setTelephoneEmetteur(String telephoneEmetteur) {
		this.telephoneEmetteur = telephoneEmetteur;
	}

	public String getEmailEmetteur() {
		return emailEmetteur;
	}

	public void setEmailEmetteur(String emailEmetteur) {
		this.emailEmetteur = emailEmetteur;
	}

	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	

	
	public String getNotation() {
		return notation;
	}

	public void setNotation(String notation) {
		this.notation = notation;
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
