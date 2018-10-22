package mobile.a3tech.com.a3tech.model;

import com.google.gson.Gson;

import java.util.Date;

public class A3techNotification {

    private Long id;
    private String titre;
    private String commentaire;
    private Date dateCreation;
    private A3techUser userClient;
    private A3techUser userTech;
    private A3techMission mission;
    private Date dateNotification;
    private A3techNotificationType typeNotification;


    public A3techNotification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public A3techUser getUserClient() {
        return userClient;
    }

    public void setUserClient(A3techUser userClient) {
        this.userClient = userClient;
    }

    public A3techUser getUserTech() {
        return userTech;
    }

    public void setUserTech(A3techUser userTech) {
        this.userTech = userTech;
    }

    public A3techMission getMission() {
        return mission;
    }

    public void setMission(A3techMission mission) {
        this.mission = mission;
    }

    public Date getDateNotification() {
        return dateNotification;
    }

    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }

    public A3techNotificationType getTypeNotification() {
        return typeNotification;
    }

    public void setTypeNotification(A3techNotificationType typeNotification) {
        this.typeNotification = typeNotification;
    }

    public A3techNotification(Long id, String titre, String commentaire, Date dateCreation, A3techUser userClient, A3techUser userTech, A3techMission mission, Date dateNotification, A3techNotificationType typeNotification) {
        this.id = id;
        this.titre = titre;
        this.commentaire = commentaire;
        this.dateCreation = dateCreation;
        this.userClient = userClient;
        this.userTech = userTech;
        this.mission = mission;
        this.dateNotification = dateNotification;
        this.typeNotification = typeNotification;
    }

    public String to_Json() {
        return new Gson().toJson(this);
    }

    public A3techNotification from_Json(String jsonValue) {
        return new Gson().fromJson(jsonValue, A3techNotification.class);
    }

}
