package mobile.a3tech.com.a3tech.model;

public class A3techDisponibility {
    private String id;
    private Long timeFrom;
    private Long timeTo;

    public A3techDisponibility(String id, Long timeFrom, Long timeTo) {
        this.id = id;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public A3techDisponibility() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Long timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Long getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Long timeTo) {
        this.timeTo = timeTo;
    }
}
