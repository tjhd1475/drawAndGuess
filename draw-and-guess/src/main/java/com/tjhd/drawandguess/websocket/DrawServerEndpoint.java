package com.tjhd.drawandguess.websocket;

import com.tjhd.drawandguess.bean.RoomContainor;
import com.tjhd.drawandguess.configurator.GetHttpSessionConfigurator;
import com.tjhd.drawandguess.model.*;
import com.tjhd.drawandguess.model.enums.ChatType;
import com.tjhd.drawandguess.service.QustionObjectService;
import com.tjhd.drawandguess.util.MessageUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket/draw/{name}/{roomId}" ,configurator = GetHttpSessionConfigurator.class)
public class DrawServerEndpoint {
    private final static int choiceNum=3;
    private static QustionObjectService qustionObjectService;
    @Autowired
    public void setQustionObjectService(QustionObjectService qustionObjectService){
        DrawServerEndpoint.qustionObjectService=qustionObjectService;
    }
    private static RoomContainor roomContainor;
    @Autowired
    public void setRoomContainor(RoomContainor roomContainor){
        DrawServerEndpoint.roomContainor=roomContainor;
    }
    private static Map<String,DrawServerEndpoint> onlinePlayers=new ConcurrentHashMap<>();
    private Session session;
    private HttpSession httpSession;
    private boolean trueIn=false;
    @OnOpen
    public void onOpen(@PathParam("name")String name,@PathParam("roomId")String roomId,Session session,EndpointConfig config){
        if(roomId!=null){
            Room room= roomContainor.getRoom(Integer.valueOf(roomId));
            if(room.isDuplicateName(name)&&!room.isStart()){
                try {
                    session.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                trueIn=true;
                room.addPlayer(name);
                roomContainor.updateRoom(room);
                System.out.println(name+" connected");
                this.session=session;
                HttpSession httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
                this.httpSession=httpSession;
                httpSession.setAttribute("roomId",roomId);
                onlinePlayers.put(name,this);
                Chat chat=new Chat("系统消息", ChatType.ENTER,name+"加入了游戏");
                MessageUtil.SendChat(onlinePlayers,room,chat);
                MessageUtil.SendInitialMessage(onlinePlayers,room,name);
            }

        }
    }

    @OnMessage
    public void onMessage(@PathParam("name")String name,@PathParam("roomId")String roomId,String message,Session session){
        System.out.println(message);
        Message<String> res= MessageUtil.getContent(message);
        Room room=roomContainor.getRoom(Integer.valueOf(roomId));
        String sendMessage=null;
        //得到准备情况结果
        if(res.getType().equals("1")){
            if(res.getContent().equals("0")){
                room.setPrepareState(name,false);
            }else {
                room.setPrepareState(name,true);
            }
            if(room.IsAllPrepared()){
                room.setStart(true);
                Map<String,Object> infoToInclude =new HashMap<>();
                infoToInclude.put("state","1");
                String []playerList=room.getPlayers();
                List<QuestionObject> questionObjects=qustionObjectService.queryN_RandomByTheme(room.getTheme(),room.playNum*choiceNum);
                int index=0;
                for(String player:playerList){
                    if(player!=null){
                        String beforePlayer=room.findBeforePlayer(player);
                        String nextPlayer=room.findNextPlayer(player);
                        infoToInclude.put("before",beforePlayer);
                        infoToInclude.put("next",nextPlayer);
                        List<String> objNames= new ArrayList<>();
                        for(int i=0;i<choiceNum;i++){
                            objNames.add(questionObjects.get(index).getName());
                            index++;
                        }
                        infoToInclude.put("objNames",objNames);
                        sendMessage =MessageUtil.createMessage("1",infoToInclude);
                        Session playerSession=onlinePlayers.get(player).session;
                        try {
                            playerSession.getBasicRemote().sendText(sendMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        //得到作画结果
        }else if(res.getType().equals("2")){
            room.addImage(room.findOriginPicker(name), name,res.getContent());
            room.setFinishState(name,true);
            if(room.IsAllFinished()){
                room.CancelFinished();
                room.addRound();
                if(room.IsAllRoundDone()){
                    finish(room);
                }else {
                    String []playerList=room.getPlayers();
                    for(String player:playerList){
                        if(player!=null){
                            String nextPlayer=room.findNextPlayer(player);
                            String newestImage=room.getNewestImage(player);
                            sendMessage=MessageUtil.createMessage("2",newestImage);
                            try {
                                Session nextPlayerSession=onlinePlayers.get(nextPlayer).session;
                                nextPlayerSession.getBasicRemote().sendText(sendMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        //得到猜测结果
        }else if(res.getType().equals("3")){
            room.addGuess(room.findOriginPicker(name),name,res.getContent());
            room.setFinishState(name,true);
            if(room.IsAllFinished()){
                room.CancelFinished();
                room.addRound();
                if(room.IsAllRoundDone()){
                    finish(room);
                }else {
                    String []playerList=room.getPlayers();
                    for(String player:playerList){
                        if(player!=null){
                            String nextPlayer=room.findNextPlayer(player);
                            String newestGuess=room.getNewestGuess(player);
                            sendMessage=MessageUtil.createMessage("3",newestGuess);
                            try {
                                Session nextPlayerSession=onlinePlayers.get(nextPlayer).session;
                                nextPlayerSession.getBasicRemote().sendText(sendMessage);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        //得到选择物品结果
        }else if(res.getType().equals("4")){
            room.setOriginPicker(name, res.getContent());
        //得到选择对错结果
        }else if(res.getType().equals("5")){
            sendMessage=MessageUtil.createMessage("6",res.getContent());
            System.out.println("转发对错判断:"+sendMessage);
            MessageUtil.SendToAllExceptSelf(onlinePlayers,room,sendMessage,name);
        //得到游戏结束结果
        }else if(res.getType().equals("6")){
            if(room.isStart()){
                room.endGame();
                roomContainor.updateRoom(room);
                Map<String,Object> infoToSend=new HashMap<>();
                infoToSend.put("state","0");
                sendMessage=MessageUtil.createMessage("1",infoToSend);
                MessageUtil.SendToAll(onlinePlayers,room,sendMessage);
            }
        }else if(res.getType().equals("7")){
            Message<Chat> chat=MessageUtil.getChat(message);
            MessageUtil.SendChat(onlinePlayers,room,chat.getContent());
        }else if(res.getType().equals("8")){
            RoomSettings roomSettings=MessageUtil.getRoomSettings(res.getContent());
            room.setRoom(roomSettings);
            message=roomSettings.toString();
            Chat chat=new Chat(name,ChatType.SETTING,message);
            MessageUtil.SendChat(onlinePlayers,room,chat);
        }
        roomContainor.updateRoom(room);
    }

    @OnClose
    public void onClose(@PathParam("name")String name,@PathParam("roomId")String roomId,Session session){
        if (trueIn) {
            Room room=roomContainor.getRoom(Integer.valueOf(roomId));
            room.removePlayer(name);
            if(room.playNum<=0){
                roomContainor.removeRoom(room);
            }else {
                roomContainor.updateRoom(room);
                Chat chat=new Chat("系统消息", ChatType.LEAVE,name+"离开了");
                MessageUtil.SendChat(onlinePlayers,room,chat);
            }
            onlinePlayers.remove(name);
            httpSession.setAttribute("isOwner",false);
        }
    }

    private void finish(@NotNull Room room){
        Map<String, Image> imageMap=room.getImages();
        String message=MessageUtil.createMessage("5",imageMap);
        String []playerList= room.getPlayers();
        for(String player:playerList){
            if(player!=null){
                Session playerSession=onlinePlayers.get(player).session;
                try {
                    playerSession.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Session getSession() {
        return session;
    }
}
