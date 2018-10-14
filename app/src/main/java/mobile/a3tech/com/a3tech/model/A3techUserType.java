package mobile.a3tech.com.a3tech.model;

public enum A3techUserType {

    TECHNICIEN("Technicien", 1, "USER"),
    CLIENT("Client", 2, "USER");


    private String discreptionEnum;
    private String typeEnum;
    private int id;
    A3techUserType(String discreption, int identifiant, String typeStatut) {
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
