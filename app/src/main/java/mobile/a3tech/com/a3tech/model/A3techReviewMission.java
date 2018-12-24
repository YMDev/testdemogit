package mobile.a3tech.com.a3tech.model;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.Date;

public class A3techReviewMission {

    private Long id;
    private String commentaire;
    private Float rating;
    private A3techMission mission;
    private Date dateEvaluation;
    private Date dateEdition;

    public A3techReviewMission(Long id, String commentaire, Float rating, A3techMission mission, Date dateEvaluation, Date dateEdition) {
        this.id = id;
        this.commentaire = commentaire;
        this.rating = rating;
        this.mission = mission;
        this.dateEvaluation = dateEvaluation;
        this.dateEdition = dateEdition;
    }

    public A3techReviewMission() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }


    public A3techMission getMission() {
        return mission;
    }

    public void setMission(A3techMission mission) {
        this.mission = mission;
    }

    public Date getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Date dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public Date getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(Date dateEdition) {
        this.dateEdition = dateEdition;
    }
}
