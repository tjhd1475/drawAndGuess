package com.tjhd.drawandguess.service;


import javax.servlet.http.HttpServletRequest;

public interface PageService {
    boolean CreateRoom(String theme, String drawDuration, String guessDuration, HttpServletRequest request);

    boolean setRoom(String theme, String drawDuration, String guessDuration, HttpServletRequest request);
}
