package mobile.a3tech.com.a3tech.model;

public enum A3techMissionStatut {

    CREE("Crée", 1, "USER"),
    VALIDEE("Validée", 2, "USER"),
    CLOTUREE("Clôturée", 3, "USER"),
    ANNULEE("Annulée", 4, "USER"),
    REJETEE("Rejetée", 5, "SYSTEM");


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
