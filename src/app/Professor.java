package app;

public class Professor{
    private int professorId;
    private String professorName;

    Professor(int professorId,String professorName){
        this.professorId = professorId;
        this.professorName = professorName;
    }
    public int getProfessorId(){
        return this.professorId;
    }
    public String getprofessorName(){
        return this.professorName;
    }
}