package mobile.a3tech.com.a3tech.model;

public class A3techReviewMission {

    private Long id;
    private String commentaire;
    private Float rating;
    private A3techUser userTechnicien;
    private A3techUser userClient;
    private A3techMission mission;
    private Long dateEvaluation;
    private Long dateEdition;

    public A3techReviewMission(Long id, String commentaire, Float rating, A3techUser userTechnicien, A3techUser userClient, A3techMission mission, Long dateEvaluation, Long dateEdition) {
        this.id = id;
        this.commentaire = commentaire;
        this.rating = rating;
        this.userTechnicien = userTechnicien;
        this.userClient = userClient;
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

    public A3techUser getUserTechnicien() {
        return userTechnicien;
    }

    public void setUserTechnicien(A3techUser userTechnicien) {
        this.userTechnicien = userTechnicien;
    }

    public A3techUser getUserClient() {
        return userClient;
    }

    public void setUserClient(A3techUser userClient) {
        this.userClient = userClient;
    }

    public A3techMission getMission() {
        return mission;
    }

    public void setMission(A3techMission mission) {
        this.mission = mission;
    }

    public Long getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(Long dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public Long getDateEdition() {
        return dateEdition;
    }

    public void setDateEdition(Long dateEdition) {
        this.dateEdition = dateEdition;
    }
}
