package mobile.a3tech.com.a3tech.model;

public class A3techDisponibility {
    private String id;
    private Long timeFrom;
    private Long timeTo;
    private Boolean valideForMonday;
    private Boolean valideForTuesday;
    private Boolean valideForWednesday;
    private Boolean valideForThursday;
    private Boolean valideForFriday;
    private Boolean valideForSaturday;
    private Boolean valideForSunday;

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

    public Boolean getValideForMonday() {
        if(valideForMonday == null) valideForMonday = Boolean.FALSE;
        return valideForMonday;
    }

    public void setValideForMonday(Boolean valideForMonday) {
        this.valideForMonday = valideForMonday;
    }

    public Boolean getValideForTuesday() {
        if(valideForTuesday == null) valideForTuesday = Boolean.FALSE;
        return valideForTuesday;
    }

    public void setValideForTuesday(Boolean valideForTuesday) {
        this.valideForTuesday = valideForTuesday;
    }

    public Boolean getValideForWednesday() {
        if(valideForWednesday == null) valideForWednesday = Boolean.FALSE;
        return valideForWednesday;
    }

    public void setValideForWednesday(Boolean valideForWednesday) {
        this.valideForWednesday = valideForWednesday;
    }

    public Boolean getValideForThursday() {
        if(valideForThursday == null) valideForThursday = Boolean.FALSE;
        return valideForThursday;
    }

    public void setValideForThursday(Boolean valideForThursday) {
        this.valideForThursday = valideForThursday;
    }

    public Boolean getValideForFriday() {
        if(valideForFriday == null) valideForFriday = Boolean.FALSE;
        return valideForFriday;
    }

    public void setValideForFriday(Boolean valideForFriday) {
        this.valideForFriday = valideForFriday;
    }

    public Boolean getValideForSaturday() {
        if(valideForSaturday == null) valideForSaturday = Boolean.FALSE;
        return valideForSaturday;
    }

    public void setValideForSaturday(Boolean valideForSaturday) {
        this.valideForSaturday = valideForSaturday;
    }

    public Boolean getValideForSunday() {
        if(valideForSunday == null) valideForSunday = Boolean.FALSE;
        return valideForSunday;
    }

    public void setValideForSunday(Boolean valideForSunday) {
        this.valideForSunday = valideForSunday;
    }
}
