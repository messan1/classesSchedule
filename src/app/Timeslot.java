package app;

public class Timeslot {
    private int timeslotId;
    private String timeslotName;
    private int startHours;
    private int finishHours;

    Timeslot(int timeslotId, String timeslotName,int startHours) {
        this.timeslotId = timeslotId;
        this.timeslotName = timeslotName;
        this.startHours = startHours;
    }

    public int gettimeslotId() {
        return this.timeslotId;
    }

    public String gettimeslotName() {
        return this.timeslotName;
    }
    
    public int getStartHours(){
        return this.startHours;
    }
    public void setStartHours(int startHours){
        this.startHours = startHours;
    }
    public int getFinishHours(){
        return this.finishHours;
    }
    public void setFinishHours(int finishHours){
        this.finishHours = finishHours;
    }
}