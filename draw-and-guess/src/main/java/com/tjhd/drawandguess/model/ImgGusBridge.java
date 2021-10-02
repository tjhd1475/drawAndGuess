package com.tjhd.drawandguess.model;

public class ImgGusBridge {
    private String playerName;
    private String ImgOrGus;
    private boolean isImg;

    public boolean isImg() {
        return isImg;
    }

    public ImgGusBridge(String playerName, String imgOrGus,boolean isImg) {
        this.playerName = playerName;
        ImgOrGus = imgOrGus;
        this.isImg=isImg;
    }

    public ImgGusBridge() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getImgOrGus() {
        return ImgOrGus;
    }

    public void setImgOrGus(String imgOrGus) {
        ImgOrGus = imgOrGus;
    }
}
