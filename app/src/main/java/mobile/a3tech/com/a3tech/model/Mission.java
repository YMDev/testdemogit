package mobile.a3tech.com.a3tech.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Mission implements Parcelable {



	public static final String STATUT_CREEE = "CREEE";
	public static final String STATUT_VALIDEE = "VALIDEE";
	public static final String STATUT_ANNULEE = "ANNULEE";
	public static final String STATUT_CLOTUREE = "CLOTUREE";
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((identifiant == null) ? 0 : identifiant.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Mission other = (Mission) obj;
		if (identifiant == null) {
			if (other.identifiant != null)
				return false;
		} else if (!identifiant.equals(other.identifiant))
			return false;
		return true;
	}

	private String nbrOffres;
	private String nbrVues;
	private String nbrMessages;
	private String identifiant;
	private String originator;
	private String adresse;
	private String latitude;
	private String longitude;
	private String dateCreation;
	private String dateFin;
	private String pseudo;
	private String statut;
	private String distance;
	private String service;
	private String categorieId;
	private String catDescription;
	private String note;
	private String typetroc;
	private String article;
	private String contre ;
	private String objetrecherche ;
	private String etatarticle ;
	private String url1 ; 
	private String url2 ;
	private String url3 ;
	private String membre ;
	private List<Message> messages = new ArrayList<Message>();
	private List<Offre> offres = new ArrayList<Offre>();
	private String typeTransaction ;
	private String titre ;
	private String dateIntervention;
	private Avis reviewMission;

	public User getTechnicien() {
		return technicien;
	}

	public void setTechnicien(User technicien) {
		this.technicien = technicien;
	}

	private Integer sponsorise ;
	private Integer evalmission ;
	private String facebookid ;
	private String checkmail ;
	private String checkphone ;
	private Categorie categoryMission;
	private User technicien;
	private String discreptionMission;

	public Categorie getCategoryMission() {
		return categoryMission;
	}

	public void setCategoryMission(Categorie categoryMission) {
		this.categoryMission = categoryMission;
	}

	public String getFacebookid() {
		return facebookid;
	}

	public void setFacebookid(String facebookid) {
		this.facebookid = facebookid;
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

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Integer getEvalmission() {
		return evalmission;
	}

	public void setEvalmission(Integer evalmission) {
		this.evalmission = evalmission;
	}

	public Integer getSponsorise() {
		return sponsorise;
	}

	public void setSponsorise(Integer sponsorise) {
		this.sponsorise = sponsorise;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getTypeTransaction() {
		return typeTransaction;
	}

	public void setTypeTransaction(String typeTransaction) {
		this.typeTransaction = typeTransaction;
	}

	public List<Offre> getOffres() {
		return offres;
	}

	public void setOffres(List<Offre> offres) {
		this.offres = offres;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getMembre() {
		return membre;
	}

	public void setMembre(String membre) {
		this.membre = membre;
	}

	public String getUrl1() {
		return url1;
	}

	public String getNbrVues() {
		return nbrVues;
	}

	public void setNbrVues(String nbrVues) {
		this.nbrVues = nbrVues;
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

	public String getNbrOffres() {
		return nbrOffres;
	}

	public void setNbrOffres(String nbrOffres) {
		this.nbrOffres = nbrOffres;
	}

	public String getNbrMessages() {
		return nbrMessages;
	}

	public void setNbrMessages(String nbrMessages) {
		this.nbrMessages = nbrMessages;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getOriginator() {
		return originator;
	}

	public void setOriginator(String originator) {
		this.originator = originator;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(String dateCreation) {
		this.dateCreation = dateCreation;
	}

	public String getDateFin() {
		return dateFin;
	}

	public void setDateFin(String dateFin) {
		this.dateFin = dateFin;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getCategorieId() {
		return categorieId;
	}

	public void setCategorieId(String categorieId) {
		this.categorieId = categorieId;
	}

	public String getCatDescription() {
		return catDescription;
	}

	public void setCatDescription(String catDescription) {
		this.catDescription = catDescription;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTypetroc() {
		return typetroc;
	}

	public void setTypetroc(String typetroc) {
		this.typetroc = typetroc;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public String getContre() {
		return contre;
	}

	public void setContre(String contre) {
		this.contre = contre;
	}

	public String getObjetrecherche() {
		return objetrecherche;
	}

	public void setObjetrecherche(String objetrecherche) {
		this.objetrecherche = objetrecherche;
	}

	public String getEtatarticle() {
		return etatarticle;
	}

	public void setEtatarticle(String etatarticle) {
		this.etatarticle = etatarticle;
	}

	public String getDiscreptionMission() {
		return discreptionMission;
	}

	public void setDiscreptionMission(String discreptionMission) {
		this.discreptionMission = discreptionMission;
	}

	public String getDateIntervention() {
		return dateIntervention;
	}

	public void setDateIntervention(String dateIntervention) {
		this.dateIntervention = dateIntervention;
	}

	public Avis getReviewMission() {
		return reviewMission;
	}

	public void setReviewMission(Avis reviewMission) {
		this.reviewMission = reviewMission;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}}
