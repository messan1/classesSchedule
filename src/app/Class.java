package app;

public class Class {
    private int professorID;
    private int groupID;
    private int roomID;
    private int moduleID;
    private int startHours;
    private int finishHours;
    private final int classId;
    private int day;

    public Class(int classId, int groupID, int moduleID) {
        this.groupID = groupID;
        this.moduleID = moduleID;
        this.classId = classId;
    }

    public int getDay(){
        return this.day;
    }
    public void addDay(int day){
        this.day = day;
    }
    public int getStartHours(){
        return this.startHours;
    }
    public void addStartHours(int hour){
        this.startHours = hour;
    }
    public int getfinishHours(){
        return this.finishHours;
    }
    public void addfinishHours(int hour){
        this.finishHours = hour;
    }
    public int getProfessorID() {
        return this.professorID;
    }

    public void addProfessorID(int id) {
        this.professorID = id;
    }


    public int getClassId() {
        return this.classId;
    }


    public int getGroupID() {
        return this.groupID;
    }

    public void addGroupID(int id) {
        this.groupID = id;
    }

    public int getRoomID() {
        return this.roomID;
    }

    public void addRoomID(int id) {
        this.roomID = id;
    }

    public int getModuleID() {
        return this.moduleID;
    }

    public void addModuleID(int id) {
        this.moduleID = id;
    }
}