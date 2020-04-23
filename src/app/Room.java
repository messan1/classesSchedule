package app;

public class Room {
    private int roomId;
    private String roomName;
    private int capacity;

    Room(int roomId,String roomName,int capacity){
        this.roomId = roomId;
        this.roomName = roomName;
        this.capacity =capacity;
    }
    public int getroomId(){
        return this.roomId;
    }
    public int getcapacity(){
        return this.capacity;
    }
    public String getroomName(){
        return this.roomName;
    }
}