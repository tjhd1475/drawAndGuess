package com.tjhd.drawandguess.service.impl;


import com.tjhd.drawandguess.bean.RoomContainor;
import com.tjhd.drawandguess.model.Room;
import com.tjhd.drawandguess.service.PageService;
import com.tjhd.drawandguess.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class PageServiceImpl implements PageService {
    @Autowired
    RoomContainor containor;
    @Autowired
    private ThemeService themeService;
    @Override
    public boolean CreateRoom(String theme, String drawDuration, String guessDuration, HttpServletRequest request) {
        List<String> themeNames= themeService.queryAllName();
        if (illegal(theme)||illegalNumber(drawDuration)||illegalNumber(guessDuration)||!themeNames.contains(theme)) {
            return false;
        }
        String name=(String) request.getSession().getAttribute("userName");
        Room room=new Room();
        room.setRoomOwner(name);
        room.setTheme(theme);
        room.setDrawDuration(Integer.valueOf(drawDuration));
        room.setGuessDuration(Integer.valueOf(guessDuration));
        containor.addRoom(room);
        request.getSession().setAttribute("roomId",room.roomId);
        return true;
    }

    @Override
    public boolean setRoom(String theme, String drawDuration, String guessDuration, HttpServletRequest request) {
        List<String> themeNames= themeService.queryAllName();
        if (illegal(theme)||illegalNumber(drawDuration)||illegalNumber(guessDuration)||!themeNames.contains(theme)) {
            return false;
        }
        Integer roomId=Integer.valueOf((String) request.getSession().getAttribute("roomId")) ;
        if(roomId!=null){
            Room room= containor.getRoom(roomId);
            room.setTheme(theme);
            room.setDrawDuration(Integer.valueOf(drawDuration));
            room.setGuessDuration(Integer.valueOf(guessDuration));
            containor.updateRoom(room);
            return true;
        }
        return false;
    }

    private boolean illegal(String text){
        if(text==null||text.equals("")){
            return true;
        }
        return false;
    }
    public boolean illegalNumber(String number){
        if(illegal(number))
            return true;
        Pattern pattern = Pattern.compile("^(\\-|\\+)?\\d+(\\.\\d+)?$");
        if(!pattern.matcher(number).matches()){
            return true;
        }
        int parseInt=Integer.parseInt(number);
        if(parseInt<=0||parseInt>=2147483647){
            return true;
        }
        return false;
    }
}
