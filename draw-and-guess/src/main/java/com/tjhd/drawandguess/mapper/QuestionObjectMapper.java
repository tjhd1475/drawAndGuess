package com.tjhd.drawandguess.mapper;

import com.tjhd.drawandguess.model.QuestionObject;

import java.util.List;

public interface QuestionObjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(QuestionObject record);

    int insertSelective(QuestionObject record);

    QuestionObject selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(QuestionObject record);

    int updateByPrimaryKey(QuestionObject record);

    List<QuestionObject> select3RandomByTheme(String theme);

    List<QuestionObject> selectN_RandomByTheme(String theme,Integer amount_N);
}