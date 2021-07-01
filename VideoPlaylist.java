package com.google;

import java.util.ArrayList;
import java.util.List;

/** A class used to represent a Playlist */
class VideoPlaylist {
    private String name;
    private List<Video> videos;

    public VideoPlaylist(String string){
        name=string;
        videos=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Video> getVideos() {
        return videos;
    }




}
