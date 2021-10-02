package com.tjhd.drawandguess.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjhd.drawandguess.bean.RoomContainor;
import com.tjhd.drawandguess.model.Room;
import com.tjhd.drawandguess.service.PageService;
import com.tjhd.drawandguess.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class PageController {
    @Autowired
    RoomContainor containor;
    @Autowired
    private ThemeService themeService;
    @Autowired
    private PageService pageService;
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
    @RequestMapping(value = "/imageUpload",method = RequestMethod.POST)
    public @ResponseBody String imageUpload(String image,HttpServletRequest request){
        System.out.println(image);
        request.getSession().setAttribute("image",image);
        return "{\"image\":\""+image+"\"}";
    }
    @RequestMapping(value = "/test")
    public String test(){
        return "test";
    }
    @RequestMapping(value = "/test2")
    public String test2(){
        return "test2";
    }

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
    @RequestMapping(value = "/create")
    public String create(String theme,String drawDuration,String guessDuration,HttpServletRequest request){
//        String name=(String) request.getSession().getAttribute("userName");
//        Room room=new Room();
//        room.setRoomOwner(name);
//        room.setTheme(theme);
//        room.setDrawDuration(Integer.valueOf(drawDuration));
//        room.setGuessDuration(Integer.valueOf(guessDuration));
//        containor.addRoom(room);
//        request.getSession().setAttribute("roomId",room.roomId);
        boolean success=pageService.CreateRoom(theme,drawDuration,guessDuration,request);
        request.getSession().setAttribute("isOwner",true);
        if(success)
            return "playScene";
        else
            return "roomList";
    }
    @RequestMapping(value = "/theme")
    public @ResponseBody String theme(){
        String res=themeService.queryAllAsJson();
        return res;
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
}
