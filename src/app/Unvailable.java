package app;

import java.time.LocalTime;

public class Unvailable {
    private int day;
    private int startHours;
    private int finishHours;
    private int groupID;

    public Unvailable(int day, int startHours, int finishHours, int groupID) {
        this.day = day;
        this.startHours = startHours;
        this.finishHours = finishHours;
        this.groupID = groupID;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getStartHours() {
        return this.startHours;
    }

    public void setStartHours(int startHours) {
        this.startHours = startHours;
    }

    public int getfinishHours() {
        return this.finishHours;
    }

    public void setfinishHours(int finishHours) {
        this.finishHours = startHours;
    }

    public int getgroupID() {
        return this.groupID;
    }

    public void setgroupID(int groupID) {
        this.groupID = groupID;
    }

    public LocalTime getStarthours() {
        LocalTime time = LocalTime.of(this.startHours, 0);
        return time;
    }

    public LocalTime getFinishhours() {
        LocalTime time = LocalTime.of(this.finishHours, 0);
        return time;
    }
}