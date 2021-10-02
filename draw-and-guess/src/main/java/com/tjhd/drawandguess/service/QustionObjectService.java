package com.tjhd.drawandguess.service;

import com.tjhd.drawandguess.model.QuestionObject;

import java.util.List;

public interface QustionObjectService {
    List<QuestionObject> query3RandomByTheme(String theme);
    List<QuestionObject> queryN_RandomByTheme(String theme,Integer amount);
}

