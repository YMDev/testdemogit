package mobile.a3tech.com.a3tech.model;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

public class Message implements Parcelable{
	
	private int identifiant;

	private String missionId;
	private String type;
	private String texte;
	private Date created_at;
	private String dateCreation ;
	private String originator;
	private String heure ;
	
	private String pseudo ;
	private String userId ;
	private String pseudoOriginator ;
	private String userIdOriginator;
	private int abus = 0 ;
	public String getUserIdOriginator() {
		return userIdOriginator;
	}

	public void setUserIdOriginator(String userIdOriginator) {
		this.userIdOriginator = userIdOriginator;
	}

	public String getPseudoOriginator() {
		return pseudoOriginator;
	}

	public int getAbus() {
		return abus;
	}

	public void setAbus(int abus) {
		this.abus = abus;
	}

	public void setPseudoOriginator(String pseudoOriginator) {
		this.pseudoOriginator = pseudoOriginator;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	

	public String getHeure() {
		return heure;
	}

	public void setHeure(String heure) {
		this.heure = heure;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getMissionId() {
		return missionId;
	}

	public void setMissionId(String missionId) {
		this.missionId = missionId;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	
	public int getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(int identifiant) {
		this.identifiant = identifiant;
	}

	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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
