package mobile.a3tech.com.a3tech.model;

public enum A3techMissionStatut {

    CREE("Crée", 1, "USER"),
    VALIDEE("Validée", 2, "USER"),
    CLOTUREE("Clôturée", 3, "USER"),
    ANNULEE("Annulée", 4, "USER"),
    REPORTEE("Reportée", 5, "USER"),
    REJETEE("Rejetée", 6, "SYSTEM"),
    DEMARREE("Démarrée", 7, "USER"),
    EN_PAUSE("En Pause", 8, "SYSTEM");


    private String discreptionEnum;
    private String typeEnum;
    private int id;
    A3techMissionStatut(String discreption, int identifiant, String typeStatut) {
        discreptionEnum = discreption;
        typeEnum = typeStatut;
        id = identifiant;

    }

    public String getDiscreptionEnum() {
        return discreptionEnum;
    }

    public void setDiscreptionEnum(String discreptionEnum) {
        this.discreptionEnum = discreptionEnum;
    }

    public String getTypeEnum() {
        return typeEnum;
    }

    public void setTypeEnum(String typeEnum) {
        this.typeEnum = typeEnum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
