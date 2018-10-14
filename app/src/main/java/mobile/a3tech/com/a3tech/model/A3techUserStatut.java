package mobile.a3tech.com.a3tech.model;

public enum A3techUserStatut {

    DISPONIBLE("Disponible", 1, "USER"),
    EN_MISSION("En Mission", 2, "USER"),
    EN_CONGE("En Congé", 3, "USER"),
    INACTIVE("Inactive", 4, "USER"),
    SUSPENDU("Suspendu", 5, "SYSTEM");


    private String discreptionEnum;
    private String typeEnum;
    private int id;
    A3techUserStatut(String discreption, int identifiant, String typeStatut) {
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
