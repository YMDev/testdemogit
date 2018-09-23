package mobile.a3tech.com.a3tech.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Avis implements Parcelable {

	public String getAvantage() {
		return avantage;
	}
	public void setAvantage(String avantage) {
		this.avantage = avantage;
	}
	public String getDate_creation() {
		return date_creation;
	}
	public void setDate_creation(String date_creation) {
		this.date_creation = date_creation;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getInconvenient() {
		return inconvenient;
	}
	public void setInconvenient(String inconvenient) {
		this.inconvenient = inconvenient;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	private String avantage;
	private String date_creation;
	private String identifiant;
	private String inconvenient;
	private String note;
	private String statut;
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
