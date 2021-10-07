package com.tjhd.drawandguess.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tjhd.drawandguess.mapper.QuestionObjectMapper;
import com.tjhd.drawandguess.mapper.ThemeMapper;
import com.tjhd.drawandguess.model.QuestionObject;
import com.tjhd.drawandguess.model.Theme;
import com.tjhd.drawandguess.service.QuestionObjectService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionObjectServiceImpl implements QuestionObjectService {
    @Autowired
    private QuestionObjectMapper questionObjectMapper;
    @Autowired
    private ThemeMapper themeMapper;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<QuestionObject> query3RandomByTheme(String theme) {
        return questionObjectMapper.select3RandomByTheme(theme);
    }

    @Override
    public List<QuestionObject> queryN_RandomByTheme(String theme, Integer amount) {
        return questionObjectMapper.selectN_RandomByTheme(theme, amount);
    }

    @Override
    public boolean addNew(String objName, String theme) {
        Theme target = themeMapper.selectByTitle(theme);
        if (target == null) {
            return false;
        } else {
            QuestionObject questionObject = new QuestionObject(objName, target.getId());
            int res = questionObjectMapper.insert(questionObject);
            return res > 0;
        }
    }

    @Override
    public String queryByTheme(String theme, Integer start,Integer end) {
        if(theme==null||end<start)
            return null;
        List<QuestionObject> questionObjects = questionObjectMapper.selectByTheme(theme,start,end-start);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(questionObjects);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    @Override
    public boolean delete(String objName, String theme) {
        if(objName==null||theme==null){
            return false;
        }
        return questionObjectMapper.deleteByNameOnTheme(objName,theme)>0;
    }
}
