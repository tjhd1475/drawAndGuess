package com.tjhd.drawandguess.service.impl;

import com.tjhd.drawandguess.mapper.QuestionObjectMapper;
import com.tjhd.drawandguess.model.QuestionObject;
import com.tjhd.drawandguess.service.QustionObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QustionObjectServiceImpl implements QustionObjectService {
    @Autowired
    private QuestionObjectMapper questionObjectMapper;
    @Override
    public List<QuestionObject> query3RandomByTheme(String theme) {
        List<QuestionObject> questionObjects=questionObjectMapper.select3RandomByTheme(theme);
        return questionObjects;
    }
    @Override
    public List<QuestionObject> queryN_RandomByTheme(String theme,Integer amount){
        List<QuestionObject> questionObjects=questionObjectMapper.selectN_RandomByTheme(theme,amount);
        return questionObjects;
    }
}
