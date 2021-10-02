package com.tjhd.drawandguess.service;

import com.tjhd.drawandguess.model.Theme;

import java.util.List;

public interface ThemeService {
    String queryAllAsJson();
    List<Theme> queryAll();
    List<String> queryAllName();
}
