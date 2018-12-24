package mobile.a3tech.com.a3tech.model;

public class A3techMissionDuree {
    private Integer id;

    private Mission mission;

    private Long timeDebut;

    private Long timeFin;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public Long getTimeDebut() {
        return timeDebut;
    }

    public void setTimeDebut(Long timeDebut) {
        this.timeDebut = timeDebut;
    }

    public Long getTimeFin() {
        return timeFin;
    }

    public void setTimeFin(Long timeFin) {
        this.timeFin = timeFin;
    }

    public A3techMissionDuree() {
    }

    public A3techMissionDuree(Integer id, Mission mission, Long timeDebut, Long timeFin) {
        this.id = id;
        this.mission = mission;
        this.timeDebut = timeDebut;
        this.timeFin = timeFin;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
