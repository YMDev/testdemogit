package mobile.a3tech.com.a3tech.model;

public class A3techEvenementiMission {

    private  Long id;
    private String titre;
    private Long dateDebut;
    private Long dateFin;
    private A3techEvenementMissionType type;

    public A3techEvenementiMission(Long id) {
        this.id = id;
    }

    public A3techEvenementiMission() {
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

    public Long getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Long dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Long getDateFin() {
        return dateFin;
    }

    public void setDateFin(Long dateFin) {
        this.dateFin = dateFin;
    }

    public A3techEvenementMissionType getType() {
        return type;
    }

    public void setType(A3techEvenementMissionType type) {
        this.type = type;
    }
}
