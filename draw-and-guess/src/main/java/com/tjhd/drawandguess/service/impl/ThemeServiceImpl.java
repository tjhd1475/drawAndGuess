package com.tjhd.drawandguess.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjhd.drawandguess.mapper.ThemeMapper;
import com.tjhd.drawandguess.model.Theme;
import com.tjhd.drawandguess.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeServiceImpl implements ThemeService {
    @Autowired
    private ThemeMapper themeMapper;
    private  ObjectMapper objectMapper=new ObjectMapper();
    @Override
    public String queryAllAsJson() {
        List<Theme> themes=themeMapper.selectAll();
        String json=null;
        try {
            json=objectMapper.writeValueAsString(themes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public String queryOverNAsJson(int amount) {
        //SELECT id,title from t_theme t1 JOIN (SELECT t.c,theme from (SELECT COUNT(*) c,theme from t_object GROUP BY theme) t where t.c>3;) t2 on t1.id=t2.theme;
        List<Theme> themes=themeMapper.selectHasNObj(amount);
        String json=null;
        try {
            json=objectMapper.writeValueAsString(themes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public List<Theme> queryAll(){
        List<Theme> themes=themeMapper.selectAll();
        return themes;
    }
    @Override
    public List<String> queryAllName(){
        List<String> names=themeMapper.selectAllName();
        return names;
    }

    @Override
    public boolean addNew(String title) {
        Theme oldExist=themeMapper.selectByTitle(title);
        if(oldExist==null){
            int res=themeMapper.insertByTitle(title);
            return res>0;
        }
        return true;
    }
}
