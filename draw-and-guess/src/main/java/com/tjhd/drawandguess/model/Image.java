package com.tjhd.drawandguess.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Image {
    private String name;
    private String lastDrawOrGuess=null;
//    private Map<String,String> images;
//    private Map<String,String> guesses;
    private List<ImgGusBridge> imgGusBridges;
    private boolean lastIsImg;


    public Image(String name) {
        this.name = name;
    }

    public Image() {
        imgGusBridges=new ArrayList<>();
    }

    public String findLastDrawOrGuess() {
        return lastDrawOrGuess;
    }

    public void setLastDrawOrGuess(String lastDrawOrGuess) {
        this.lastDrawOrGuess = lastDrawOrGuess;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ImgGusBridge> getImgGusBridges() {
        return imgGusBridges;
    }

    public void setImgGusBridges(List<ImgGusBridge> imgGusBridges) {
        this.imgGusBridges = imgGusBridges;
    }

    public String findNewestImage(){
        ImgGusBridge newest=null;
        if(imgGusBridges.isEmpty()){
            return null;
        }else {
            newest=imgGusBridges.get(imgGusBridges.size()-1);
            if(!lastIsImg){
                return null;
            }
        }
        return newest.getImgOrGus();
    }

    public String findNewestGuess(){
        ImgGusBridge newest=null;
        if(imgGusBridges.isEmpty()){
            return null;
        }else {
            newest=imgGusBridges.get(imgGusBridges.size()-1);
            if(lastIsImg){
                return null;
            }
        }
        return newest.getImgOrGus();
    }

    public void addImage(String name,String image){
        ImgGusBridge imgGusBridge=new ImgGusBridge(name,image,true);
        if(imgGusBridges==null){
            imgGusBridges=new ArrayList<>();
        }
        imgGusBridges.add(imgGusBridge);
        lastIsImg=true;
        lastDrawOrGuess=name;
    }
    public void addGuess(String name,String guess){
        ImgGusBridge imgGusBridge=new ImgGusBridge(name,guess,false);
        if(imgGusBridges==null){
            imgGusBridges=new ArrayList<>();
        }
        imgGusBridges.add(imgGusBridge);
        lastIsImg=false;
        lastDrawOrGuess=name;
    }

}
