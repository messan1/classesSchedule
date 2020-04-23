package app;

public class Module {
    private int moduleId;
    private String moduleCode;
    private int[] professorIds;
    private int moduleHours;

    public Module(int moduleId, String moduleCode,int[] professorIds,int moduleHours){
        this.professorIds = professorIds;
        this.moduleCode = moduleCode;
        this.moduleId = moduleId;
        this.moduleHours = moduleHours;
    }

    public int getModuleId(){
        return this.moduleId;
    }
    public int[] getProfessorIds(){
        return this.professorIds;
    }
    public String getModuleCode(){
        return this.moduleCode;
    }
    public int getRandomProfessor(){
        return (int) this.professorIds[(int) Math.random() * this.professorIds.length];
    }
    public int getModuleHours(){
        return this.moduleHours;
    }
    public void setModuleHours(int hours){
        this.moduleHours = hours;
    }
}