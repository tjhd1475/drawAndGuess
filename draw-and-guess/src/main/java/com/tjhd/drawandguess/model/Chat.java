package com.tjhd.drawandguess.model;

import com.tjhd.drawandguess.model.enums.ChatType;

public class Chat {
    private String talker;
    private ChatType chatType;
    private String chatContent;
    public Chat(String talker, ChatType chatType, String chatContent) {
        this.talker = talker;
        this.chatType = chatType;
        this.chatContent = chatContent;
    }
    public Chat(String talker, String chatType, String chatContent) {
        this.talker = talker;
        this.chatType = ChatType.valueOf(chatType);
        this.chatContent = chatContent;
    }

    public Chat() {
    }

    public ChatType getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = ChatType.valueOf(chatType);
    }

    public String getChatContent() {
        return chatContent;
    }

    public void setChatContent(String chatContent) {
        this.chatContent = chatContent;
    }



    public String getTalker() {
        return talker;
    }

    public void setTalker(String talker) {
        this.talker = talker;
    }

    public void setChatType(ChatType chatType) {
        this.chatType = chatType;
    }

    public boolean isVoidChat(){
        if(chatContent==null||chatContent.equals("")){
            return true;
        }else
            return false;
    }
}
