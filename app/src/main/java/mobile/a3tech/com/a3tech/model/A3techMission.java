package mobile.a3tech.com.a3tech.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class A3techMission {

    private String id;
    private A3techUser client;
    private String adresse;
    private String latitude;
    private String longitude;
    private Date dateCreation;
    private Date dateIntervention;
    private A3techMissionStatut statut;
    private A3techReviewMission reviewMission;
    private String titre;
    private Categorie categorie;
    private A3techUser technicien;
    private String description;
    private Date dateCloture;
    private Double montantFacture;
    private List<A3techEvenementiMission> evenements;
    private List<A3techMissionDuree> missionDurees;
    private String motifRejet;
    private String motifReport;


    public A3techMission() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateIntervention() {
        return dateIntervention;
    }

    public void setDateIntervention(Date dateIntervention) {
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



    public A3techUser getTechnicien() {
        return technicien;
    }

    public void setTechnicien(A3techUser technicien) {
        this.technicien = technicien;
    }

    public String getDescriptionMission() {
        return description;
    }



    public void setDescriptionMission(String descriptionMission) {
        this.description = descriptionMission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    public Double getMontantFacture() {
        return montantFacture;
    }

    public void setMontantFacture(Double montantFacture) {
        this.montantFacture = montantFacture;
    }

    public List<A3techEvenementiMission> getEvenement() {
        return evenements;
    }

    public void setEvenement(List<A3techEvenementiMission> evenement) {
        this.evenements = evenement;
    }

    public List<A3techEvenementiMission> getEvenements() {
        return evenements;
    }

    public void setEvenements(List<A3techEvenementiMission> evenements) {
        this.evenements = evenements;
    }

    public String getMotifRejet() {
        return motifRejet;
    }

    public void setMotifRejet(String motifRejet) {
        this.motifRejet = motifRejet;
    }

    public String getMotifReport() {
        return motifReport;
    }

    public void setMotifReport(String motifReport) {
        this.motifReport = motifReport;
    }

    public A3techUser getClient() {
        return client;
    }

    public void setClient(A3techUser client) {
        this.client = client;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<A3techMissionDuree> getMissionDurees() {
        return missionDurees;
    }

    public void setMissionDurees(List<A3techMissionDuree> missionDurees) {
        this.missionDurees = missionDurees;
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
