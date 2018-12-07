package mobile.a3tech.com.a3tech.test;
/**
 * Created by Suleiman on 03/02/17.
 */

public class ObjectMenu {

    public static String CODE_DISPO = "DISPO";
    public static String CODE_DECONNEXION = "DECON";
    public static String CODE_DEVENIR_TECH = "DTECH";
    public static String CODE_RECOMMANDER_TECH = "RTECH";
    public static String CODE_DEMANDER_MISSION = "DMISSION";
    private Integer id;
    private Integer type;
    private String name;
    private String description;
    private String code;

    public ObjectMenu() {
    }

    public ObjectMenu(String name, String description, int type, int id) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.id = id;
    }
    public ObjectMenu(String name, String description, int type, int id, String code) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.id = id;
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}