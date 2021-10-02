package com.tjhd.drawandguess.model;

public class RoomSettings {
    private String theme;
    private int drawDuration;
    private int guessDuration;

    public RoomSettings(String theme, int drawDuration, int guessDuration) {
        this.theme = theme;
        this.drawDuration = drawDuration;
        this.guessDuration = guessDuration;
    }

    public RoomSettings() {
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDrawDuration() {
        return drawDuration;
    }

    public void setDrawDuration(int drawDuration) {
        this.drawDuration = drawDuration;
    }

    public int getGuessDuration() {
        return guessDuration;
    }

    public void setGuessDuration(int guessDuration) {
        this.guessDuration = guessDuration;
    }

    @Override
    public String toString() {
        StringBuffer stringBuffer=new StringBuffer()
                .append("     主题:")
                .append(theme)
                .append("     ")
                .append("作画时间:")
                .append(drawDuration)
                .append("s      ")
                .append("猜画时间:")
                .append(guessDuration)
                .append("s");
        return stringBuffer.toString();
    }
}
