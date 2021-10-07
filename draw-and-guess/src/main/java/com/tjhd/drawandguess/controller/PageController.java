package com.tjhd.drawandguess.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjhd.drawandguess.bean.RoomContainor;
import com.tjhd.drawandguess.model.Room;
import com.tjhd.drawandguess.service.PageService;
import com.tjhd.drawandguess.service.QuestionObjectService;
import com.tjhd.drawandguess.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class PageController {
    @Value("${play.playerAmount}")
    private int playerAmount;
    @Value("${play.optionAmount}")
    private int optionAmount;
    @Autowired
    RoomContainor containor;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private QuestionObjectService questionObjectService;
    @Autowired
    private PageService pageService;

    public int getPlayerAmount() {
        return playerAmount;
    }

    public void setPlayerAmount(int playerAmount) {
        this.playerAmount = playerAmount;
    }

    public int getOptionAmount() {
        return optionAmount;
    }

    public void setOptionAmount(int optionAmount) {
        this.optionAmount = optionAmount;
    }

    @RequestMapping("/start")
    public String start(String userName, HttpServletRequest request){
        request.getSession().setAttribute("userName",userName);
        return "roomList";
    }
    @RequestMapping(value = "/enterRoom")
    public String enterRoom(String roomId,HttpServletRequest request){
        request.getSession().setAttribute("roomId",roomId);
        return "playScene";
    }
//    @RequestMapping(value = "/imageUpload",method = RequestMethod.POST)
//    public @ResponseBody String imageUpload(String image,HttpServletRequest request){
//        System.out.println(image);
//        request.getSession().setAttribute("image",image);
//        return "{\"image\":\""+image+"\"}";
//    }
//    @RequestMapping(value = "/test")
//    public String test(){
//        return "test";
//    }
//    @RequestMapping(value = "/test2")
//    public String test2(){
//        return "test2";
//    }

    @RequestMapping(value = "/room")
    public @ResponseBody String room(){
        Map<Integer, Room> roomMap= containor.getRooms();
        ObjectMapper om =new ObjectMapper();
        String json=null;
        try {
            json=om.writeValueAsString(roomMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "/roomFromStoE")
    public @ResponseBody String room(Integer start,Integer end){
        if(end<start)
            return null;
        Map<Integer, Room> roomMap= containor.getRooms();
        Map<Integer,Room> roomMapFromStoE=new HashMap<>();
        Set<Integer> keys=roomMap.keySet();
        int count=0;
        for(Integer key:keys){
            if(count>=start){
                roomMapFromStoE.put(key,roomMap.get(key));
            }
            if(count>end){
                break;
            }
            count++;
        }
        ObjectMapper om =new ObjectMapper();
        String json=null;
        try {
            json=om.writeValueAsString(roomMapFromStoE);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "/create")
    public String create(String theme,String drawDuration,String guessDuration,HttpServletRequest request){
        boolean success=pageService.CreateRoom(theme,drawDuration,guessDuration,request);
        request.getSession().setAttribute("isOwner",true);
        if(success)
            return "playScene";
        else
            return "roomList";
    }

    @RequestMapping(value = "/setRoom")
    public @ResponseBody String setRoom(String theme,String drawDuration,String guessDuration,HttpServletRequest request){
        boolean success=pageService.setRoom(theme,drawDuration,guessDuration,request);
        if(success){
            System.out.println("set room success");
            return "success";
        }else {
            System.out.println("set room fail");
            return "fail";
        }
    }

    @RequestMapping(value = "/check")
    public String check(){
        return "checkList";
    }
    @RequestMapping(value = "/roomList")
    public String roomList(){
        return "roomList";
    }

}
