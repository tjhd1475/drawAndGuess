package com.tjhd.drawandguess.model;

public class Message<E> {
    private String type;
    private E content;

    public Message(String type, E content) {
        this.type = type;
        this.content = content;
    }

    public Message() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public E getContent() {
        return content;
    }

    public void setContent(E content) {
        this.content = content;
    }
}
