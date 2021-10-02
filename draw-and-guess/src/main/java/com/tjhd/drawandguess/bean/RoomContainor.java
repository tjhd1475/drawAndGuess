package com.tjhd.drawandguess.bean;

import com.tjhd.drawandguess.model.Room;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomContainor {
    private Map<Integer, Room> Rooms=null;
    public int roomNum=0;
    public RoomContainor() {
        Rooms=new ConcurrentHashMap<>();
    }

    public void addRoom(Room room){
        roomNum++;
        Rooms.put(room.roomId,room);
    }

    public Room getRoom(Integer roomId){
        return  Rooms.get(roomId);
    }

    public void updateRoom(Room room){
        Rooms.put(room.roomId,room);
    }

    public void removeRoom(Room room){
        roomNum--;
        Rooms.remove(room.roomId);
    }

    public int getRoomNum(){
        return Rooms.size();
    }

    public Map<Integer, Room> getRooms() {
        return Rooms;
    }

    public void setRooms(Map<Integer, Room> rooms) {
        Rooms = rooms;
    }
}
