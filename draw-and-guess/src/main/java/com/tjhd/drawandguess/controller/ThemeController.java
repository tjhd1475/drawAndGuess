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
public class ThemeController {
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

    @RequestMapping(value = "/theme")
    public @ResponseBody
    String theme(){
        return themeService.queryOverNAsJson(playerAmount*optionAmount);
    }

    @RequestMapping(value = "/allTheme")
    public @ResponseBody String allTheme(){
        return themeService.queryAllAsJson();
    }

    @RequestMapping(value = "/addTheme")
    public @ResponseBody String addTheme(String theme){
        boolean res=themeService.addNew(theme);
        return "{\"result\":"+res+"}";
    }


}
