package com.tjhd.drawandguess.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjhd.drawandguess.model.Chat;
import com.tjhd.drawandguess.model.Message;
import com.tjhd.drawandguess.model.Room;
import com.tjhd.drawandguess.model.RoomSettings;
import com.tjhd.drawandguess.model.enums.ChatType;
import com.tjhd.drawandguess.websocket.DrawServerEndpoint;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MessageUtil {
    private static final ObjectMapper objectMapper=new ObjectMapper();
    public static Message getContent(String message){
        Message res=null;
        try {
            res=objectMapper.readValue(message, Message.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static RoomSettings getRoomSettings(String content){
        RoomSettings res=null;
        try {
            res=objectMapper.readValue(content, RoomSettings.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static <T> T getMessage(String message, Class<T> valueType){
        T res=null;
        try {
            res=(T)objectMapper.readValue(message, valueType.getClass());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }
    public static Message<Chat> getChat(String message){
        Message<Chat> res=null;
        try {
            Map<String,Object> map=objectMapper.readValue(message,Map.class);
            res=new Message<>();
            res.setType((String)map.get("type"));
            Map<String,String> content=(Map<String, String>) map.get("content");
            Chat chat=new Chat(content.get("talker"), content.get("chatType"),content.get("chatContent"));
            res.setContent(chat);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static String createMessage(String type,Object content){
        Map<String,Object> map= new HashMap<>();
        map.put("type",type);
        map.put("content",content);
        String json=null;
        try {
            json= objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    public static String createChat(Chat chat){
        Map<String,Object> map= new HashMap<>();
        map.put("type","7");
        map.put("talker",chat.getTalker());
        map.put("chatType",chat.getChatType());
        map.put("chatContent",chat.getChatContent());
        String json=null;
        try {
            json= objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
    public static void SendToAll(Map<String, DrawServerEndpoint> onlinePlayers, Room room,String message){
        String []playerList=room.getPlayers();
        for(String player:playerList){
            if(player!=null){
                try {
                    Session nextPlayerSession=onlinePlayers.get(player).getSession();
                    nextPlayerSession.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void SendToAllExceptSelf(Map<String, DrawServerEndpoint> onlinePlayers, Room room,String message,String myself){
        String []playerList=room.getPlayers();
        for(String player:playerList){
            if(player!=null&&!player.equals(myself)){
                try {
                    Session nextPlayerSession=onlinePlayers.get(player).getSession();
                    nextPlayerSession.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void SendChat(Map<String, DrawServerEndpoint> onlinePlayers, Room room,Chat chat){
        if(onlinePlayers==null||room==null)
            return;
        if(chat.getChatType()==ChatType.SAYTOALL||chat.getChatType()==ChatType.ENTER||chat.getChatType()==ChatType.LEAVE||chat.getChatType()==ChatType.SETTING){
            if(!chat.isVoidChat()){
                String message=MessageUtil.createMessage("7",chat);
                String []playerList=room.getPlayers();
                for(String player:playerList){
                    if(player!=null){
                        try {
                            Session nextPlayerSession=onlinePlayers.get(player).getSession();
                            if(nextPlayerSession!=null)
                                nextPlayerSession.getBasicRemote().sendText(message);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public static void SendInitialMessage(Map<String, DrawServerEndpoint> onlinePlayers,Room room,String player){
        if(room==null&&player==null&&!room.isDuplicateName(player)){
            return;
        }
        Map<String,Object> map=new HashMap<>();
        map.put("players",room.getPlayers());
        map.put("readyState",room.getPrepareState());
        map.put("theme",room.getTheme());
        map.put("drawDuration",room.getDrawDuration());
        map.put("guessDuration",room.getGuessDuration());
        String message=MessageUtil.createMessage("0",map);
        SendMessage(onlinePlayers,player,message);

    }
    public static void SendMessage(Map<String, DrawServerEndpoint> onlinePlayers,String player,String message){
        if(player!=null){
            try {
                Session nextPlayerSession=onlinePlayers.get(player).getSession();
                if(nextPlayerSession!=null)
                    nextPlayerSession.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
