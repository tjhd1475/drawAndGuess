package com.tjhd.drawandguess.controller;

import com.tjhd.drawandguess.bean.RoomContainor;
import com.tjhd.drawandguess.service.PageService;
import com.tjhd.drawandguess.service.QuestionObjectService;
import com.tjhd.drawandguess.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ObjectController {
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

    @RequestMapping(value = "/addObject")
    public @ResponseBody String addObject(String objName, String theme){
        boolean res=questionObjectService.addNew(objName,theme);
        return "{\"result\":"+res+"}";
    }

    @RequestMapping(value = "/delObject")
    public @ResponseBody String delObject(String objName, String theme){
        boolean res=questionObjectService.delete(objName,theme);
        return "{\"result\":"+res+"}";
    }

    @RequestMapping(value = "/object")
    public @ResponseBody String object(String theme,Integer start,Integer end){
        String json=questionObjectService.queryByTheme(theme,start,end);
        return json;
    }
}
