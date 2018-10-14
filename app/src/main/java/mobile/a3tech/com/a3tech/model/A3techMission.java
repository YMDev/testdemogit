package mobile.a3tech.com.a3tech.model;

import java.util.ArrayList;
import java.util.List;


public class A3techMission {

    private String id;
    private A3techUser clientMission;
    private String adresse;
    private String latitude;
    private String longitude;
    private Long dateCreation;
    private Long dateIntervention;
    private A3techMissionStatut statut;
    private A3techReviewMission reviewMission;
    private String titre;
    private Categorie categoryMission;
    private A3techUser technicien;
    private String descriptionMission;

    public A3techMission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public A3techUser getClientMission() {
        return clientMission;
    }

    public void setClientMission(A3techUser clientMission) {
        this.clientMission = clientMission;
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

    public Long getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Long dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(Long dateIntervention) {
        this.dateIntervention = dateIntervention;
    }

    public A3techMissionStatut getStatut() {
        return statut;
    }

    public void setStatut(A3techMissionStatut statut) {
        this.statut = statut;
    }

    public A3techReviewMission getReviewMission() {
        return reviewMission;
    }

    public void setReviewMission(A3techReviewMission reviewMission) {
        this.reviewMission = reviewMission;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Categorie getCategoryMission() {
        return categoryMission;
    }

    public void setCategoryMission(Categorie categoryMission) {
        this.categoryMission = categoryMission;
    }

    public A3techUser getTechnicien() {
        return technicien;
    }

    public void setTechnicien(A3techUser technicien) {
        this.technicien = technicien;
    }

    public String getDescriptionMission() {
        return descriptionMission;
    }



    public void setDescriptionMission(String descriptionMission) {
        this.descriptionMission = descriptionMission;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((id == null) ? 0 : id.hashCode());
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
        A3techMission other = (A3techMission) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


}
