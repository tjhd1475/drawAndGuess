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
    public List<Theme> queryAll(){
        List<Theme> themes=themeMapper.selectAll();
        return themes;
    }
    @Override
    public List<String> queryAllName(){
        List<String> names=themeMapper.selectAllName();
        return names;
    }
}
