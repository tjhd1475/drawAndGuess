package com.tjhd.drawandguess.mapper;

import com.tjhd.drawandguess.model.Theme;

import java.util.List;

public interface ThemeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Theme record);

    int insertSelective(Theme record);

    Theme selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Theme record);

    int updateByPrimaryKey(Theme record);

    int countAll();

    List<Theme> selectAll();

    List<String> selectAllName();
}