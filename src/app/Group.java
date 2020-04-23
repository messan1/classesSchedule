package app;

public class Group {
    private int groupId;
    private String groupName;
    private int groupSize;
    private int[] modulesId;

    public Group(int groupId, String groupName, int groupSize, int[] modulesId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupSize = groupSize;
        this.modulesId = modulesId;

    }

    public void setModuleIds(int[] modulesId) {
        this.modulesId = modulesId;
    }

    public int[] getModuleIds() {
        return this.modulesId;
    }

    public int getGroupId() {
        return this.groupId;
    }

    public int getGroupSize() {
        return this.groupSize;
    }

    public String getGroupName() {
        return this.groupName;
    }
    public int[] moduleIds(){
        return this.modulesId;
    }
}