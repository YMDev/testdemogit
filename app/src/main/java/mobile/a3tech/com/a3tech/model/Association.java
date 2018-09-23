package mobile.a3tech.com.a3tech.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Association implements Parcelable {

	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getDate_debut() {
		return date_debut;
	}
	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}
	public String getDate_fin() {
		return date_fin;
	}
	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIdentifiant() {
		return identifiant;
	}
	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSociete() {
		return societe;
	}
	public void setSociete(String societe) {
		this.societe = societe;
	}
	public String getCa() {
		return ca;
	}
	public void setCa(String ca) {
		this.ca = ca;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getSecteur() {
		return secteur;
	}
	public void setSecteur(String secteur) {
		this.secteur = secteur;
	}
	public String getEffectif() {
		return effectif;
	}
	public void setEffectif(String effectif) {
		this.effectif = effectif;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getSite() {
		return site;
	}
	public void setSite(String site) {
		this.site = site;
	}
	public String getType_abonement() {
		return type_abonement;
	}
	public void setType_abonement(String type_abonement) {
		this.type_abonement = type_abonement;
	}
	private String active;
	private String date_debut;
	private String date_fin;
	private String description;
	private String identifiant;
	private String latitude;
	private String libelle;
	private String logo;
	private String longitude;
	private String nombre;
	private String societe;
	private String type_abonement;
	
	private String ca ;
	private String tel ;
	private String secteur ;
	private String effectif ;
	private String adresse ;
	private String site; 
	
	
	
	
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
