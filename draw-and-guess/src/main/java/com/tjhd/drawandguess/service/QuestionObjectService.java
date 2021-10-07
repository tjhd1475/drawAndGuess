package com.tjhd.drawandguess.service;

import com.tjhd.drawandguess.model.QuestionObject;

import java.util.List;

public interface QuestionObjectService {
    List<QuestionObject> query3RandomByTheme(String theme);
    List<QuestionObject> queryN_RandomByTheme(String theme,Integer amount);
    boolean addNew(String objName,String theme);
    String queryByTheme(String theme,Integer start,Integer end);
    boolean delete(String objName, String theme);
}

