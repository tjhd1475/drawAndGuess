package com.tjhd.drawandguess.model;


import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;

public class Room {
    private static final int SIZE=10;
    final private int MIN_PLAYER_NUMB=2;
    final public int roomId;
    private static int roomCode;
    private boolean isStart=false;
    private String theme;
    public int playNum=0;
    private String roomOwner;
    private int drawDuration;
    private int guessDuration;
    private int round=0;
    private String [] playerList=new String[SIZE];
    private boolean [] prepareState =new boolean[SIZE];
    private boolean [] finishState=new boolean[SIZE];
    private Map<String,Image> images=new HashMap<>();

    public Room() {
        roomId=roomCode;
        roomCode+=1;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public void addRound(){
        round++;
    }

    public void resetRound(){
        round=0;
    }

    public String getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(String roomOwner) {
        this.roomOwner = roomOwner;
    }

    public int getDrawDuration() {
        return drawDuration;
    }

    public void setDrawDuration(int drawDuration) {
        this.drawDuration = drawDuration;
    }

    public int getGuessDuration() {
        return guessDuration;
    }

    public void setGuessDuration(int guessDuration) {
        this.guessDuration = guessDuration;
    }

    public void setRoom(RoomSettings roomSettings){
        this.theme=roomSettings.getTheme();
        this.drawDuration=roomSettings.getDrawDuration();
        this.guessDuration=roomSettings.getGuessDuration();
    }

    public Map<String, Image> getImages() {
        return images;
    }

    public void addPlayer(String name){
        if(playNum<SIZE){
            playerList[playNum]=name;
            playNum++;
        }

    }

    public void removePlayer(String name){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                for(;i<=playNum-1;i++){
                    playerList[i]=playerList[i+1];
                    prepareState[i]= prepareState[i+1];
                    finishState[i]=finishState[i+1];
                    images.remove("name");
                }
                playNum--;
                break;
            }
        }
    }

    public String findNextPlayer(String name){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                return playerList[(i+1)%playNum];

            }
        }
        return null;
    }

    public String findBeforePlayer(String name){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                return playerList[(i-1+playNum)%playNum];

            }
        }
        return null;
    }

    public void setPrepareState(String name, Boolean state){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                prepareState[i]=state;
                break;
            }
        }
    }

    public void setFinishState(String name,Boolean state){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                finishState[i]=state;
                break;
            }
        }
    }

    public boolean IsAllPrepared(){
        for(int i=0;i<playNum;i++){
            if(prepareState[i]==false||playNum<MIN_PLAYER_NUMB){
                return false;
            }
        }
        return true;
    }

    public boolean IsAllFinished(){
        for(int i=0;i<playNum;i++){
            if(finishState[i]==false){
                return false;
            }
        }
        return true;
    }

    public void CancelFinished() {
        for(int i=0;i<playNum;i++){
            finishState[i]=false;
        }
    }

    public void CancelPrepared(){
        for(int i=0;i<playNum;i++){
            prepareState[i]=false;
        }
    }

    public boolean[] getPrepareState() {
        return prepareState;
    }

    public boolean[] getFinishState() {
        return finishState;
    }

    public boolean IsAllRoundDone(){
        if(playNum/2*2==round){
            return true;
        }
        return false;
    }

    public int findPlayerIndex(String name){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                return i;
            }
        }
        return -1;
    }

    public String[] getPlayers(){
        return playerList;
    }

    public void addImage(String originPicker,String drawer,String image){
        Image imageForName=null;
        if(!images.containsKey(originPicker)){
            imageForName=new Image();
        }else {
            imageForName=images.get(originPicker);
        }
        imageForName.addImage(drawer,image);
        images.put(originPicker,imageForName);
    }

    public void addGuess(String originPicker,String guesser,String guess){
        Image imageForName=null;
        if(!images.containsKey(originPicker)){
            imageForName=new Image();
        }else {
            imageForName=images.get(originPicker);
        }
        imageForName.addGuess(guesser,guess);
        images.put(originPicker,imageForName);
    }

    public boolean isDuplicateName(String name){
        for(int i=0;i<playNum;i++){
            if(name!=null&&playerList[i].equals(name)){
                return true;
            }
        }
        return false;
    }

    public String getNewestImage(String name){
        if(name==null){
            return null;
        }
        String resultImage=null;
        for(String key: images.keySet()){
            Image imageForName=images.get(key);
            String ldog=imageForName.findLastDrawOrGuess();
            if(ldog!=null&&name.equals(ldog)){
                resultImage=imageForName.findNewestImage();
                break;
            }
        }
        return resultImage;
    }

    public String getNewestGuess(String name){
        if(name==null){
            return null;
        }
        String resultGuess=null;
        for(String key: images.keySet()){
            Image imageForName=images.get(key);
            String ldog=imageForName.findLastDrawOrGuess();
            if(ldog!=null&&name.equals(ldog)){
                resultGuess=imageForName.findNewestGuess();
                break;
            }
        }
        return resultGuess;
    }

    public void setOriginPicker(String originPicker,String ImgName){
        Image imageForName=null;
        if(!images.containsKey(originPicker)){
            imageForName=new Image();
        }else {
            imageForName=images.get(originPicker);
        }
        imageForName.setName(ImgName);
        images.put(originPicker,imageForName);
    }

    public String getImageName(String originPicker){
        Image imageForName=null;
        if(!images.containsKey(originPicker)){
            return null;
        }else {
            imageForName=images.get(originPicker);
            return imageForName.getName();
        }
    }

    public Image getImage(String originPicker){
        return images.get(originPicker);
    }

    public String findOriginPicker(String presentDrawer){
        String res=null;
        for(int i=0;i<playNum;i++){
            if(presentDrawer!=null&&playerList[i].equals(presentDrawer)){
                res=playerList[(i-round+playNum)%playNum];
            }
        }
        return res;
    }

    public void endGame(){
        if(isStart){
            CancelFinished();
            CancelPrepared();
            resetRound();
            images.clear();
            isStart=false;
        }
    }
}
