package mobile.a3tech.com.a3tech.test;
/**
 * Created by Suleiman on 03/02/17.
 */

public class ObjectMenu {

    private Integer id;
    private Integer type;
    private String name;
    private String description;

    public ObjectMenu() {
    }

    public ObjectMenu(String name, String description, int type, int id) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.id = id;
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
}