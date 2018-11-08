package mobile.a3tech.com.a3tech.model;

public enum A3techNotificationType {

    CREATION_MISSION("Mission créée", 1, "USER"),
    VALIDATION_MISSION("Mission validée", 2, "USER"),
    ACCEPTATION_MISSION("Mission Acceptée", 3, "USER"),
    ANNULATION_MISSION("Mission Annulée", 4, "USER"),
    REPORTATION_MISSION("Mission Reportée", 5, "USER"),
    CLOTURE_MISSION("Mission Cloturée", 5, "USER"),
    REJET_MISSION("Mission Rjetée", 6, "SYSTEM");


    private String discreptionEnum;
    private String typeEnum;
    private int id;
    A3techNotificationType(String discreption, int identifiant, String typeStatut) {
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
