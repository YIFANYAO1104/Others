package com.google;

import java.util.*;

public class VideoPlayer {

  private final VideoLibrary videoLibrary;
  private Video isPlaying;
  private Video isPaused;
  private Video isContuning;
  private List<VideoPlaylist> videoPlaylists;


  public VideoPlayer() {
    this.videoLibrary = new VideoLibrary();
    videoPlaylists=new ArrayList<>();

  }

  public void numberOfVideos() {
    System.out.printf("%s videos in the library%n", videoLibrary.getVideos().size());
  }

  public void showAllVideos() {

    List<Video> videos=videoLibrary.getVideos();
    videos.sort(Comparator.comparing(Video::getTitle));
    System.out.println("Here's a list of all available videos:");

    for (Video v:videos){
      if (v.getFlag().equals("")) {
        System.out.println(v.getTitle() + " " + "(" + v.getVideoId() + ")" + " " + v.getTags().toString().replaceAll("\\,", ""));
      }else {
        System.out.println(v.getTitle() + " " + "(" + v.getVideoId() + ")" + " " + v.getTags().toString().replaceAll("\\,", "")+" - FLAGGED (reason: "+v.getFlag()+")");

      }
    }

  }

  public void playVideo(String videoId) {



    if (videoLibrary.getVideo(videoId)==null){
      System.out.println("Cannot play video: Video does not exist");
    }else if (isPlaying!=null){
      if (isPlaying.getFlag().equals("")==false){
        System.out.println("Cannot play video: Video is currently flagged (reason: "+isPlaying.getFlag()+")");
        return;
      }

      System.out.println("Stopping video: "+isPlaying.getTitle());
      isPlaying=videoLibrary.getVideo(videoId);
      System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
    }else if(isPaused!=null){
      stopVideo();
      playVideo(videoId);

    } else {

      isPlaying=videoLibrary.getVideo(videoId);
      if (isPlaying.getFlag().equals("")==false){
        System.out.println("Cannot play video: Video is currently flagged (reason: "+isPlaying.getFlag()+")");
        return;
      }
      System.out.println("Playing video: " + videoLibrary.getVideo(videoId).getTitle());
    }
  }

  public void stopVideo() {
    if (isPlaying!=null){
      System.out.println("Stopping video: "+isPlaying.getTitle());

      isPlaying=null;


    }else if (isPaused!=null){
      System.out.println("Stopping video: "+isPaused.getTitle());

      isPaused=null;


    }else if (isContuning!=null){
      System.out.println("Stopping video: "+isContuning.getTitle());
      isContuning=null;

    }else {
      System.out.println("Cannot stop video: No video is currently playing");
    }
  }

  public void playRandomVideo() {
    Random r=new Random();
    int i=r.nextInt(videoLibrary.getVideos().size());


    for (Video video:videoLibrary.getVideos()){
      if (video.getFlag().equals("")){
        playVideo(videoLibrary.getVideos().get(i).getVideoId());
        return;
      }
    }

    System.out.println("No videos available");

  }

  public void pauseVideo() {
    if (isPaused==null&&isPlaying!=null){
    isPaused=isPlaying;
    System.out.println("Pausing video: "+isPaused.getTitle());
    isPlaying=null;
    }else if (isPaused!=null){
      System.out.println("Video already paused: "+isPaused.getTitle());
    }else {
      System.out.println("Cannot pause video: No video is currently playing");
    }
  }

  public void continueVideo() {

    if (isPaused!=null){
      isContuning=isPaused;
      System.out.println("Continuing video: "+isContuning.getTitle());
      isPaused=null;
    }else if (isPlaying==null){
      System.out.println("Cannot continue video: No video is currently playing");
    } else {
      System.out.println("Cannot continue video: Video is not paused");
    }
  }

  public void showPlaying() {

    if (isPlaying!=null&&isPlaying.getFlag().equals("")){
      System.out.println("Currently playing: "+isPlaying.getTitle()+" "+"("+isPlaying.getVideoId()+")"+" "+isPlaying.getTags().toString().replaceAll("\\,",""));
    } else if (isPaused!=null&&isPaused.getFlag().equals("")){
      System.out.println("Currently playing: "+isPaused.getTitle()+" "+"("+isPaused.getVideoId()+")"+" "+isPaused.getTags().toString().replaceAll("\\,","")+" - PAUSED");

    }else {
      System.out.println("No video is currently playing");
    }
  }

  public void createPlaylist(String playlistName) {
    if (videoPlaylists.size()==0) {
      videoPlaylists.add(new VideoPlaylist(playlistName));
      System.out.println("Successfully created new playlist: "+playlistName);
    }else {
      for (VideoPlaylist v:videoPlaylists){
        if (v.getName().equalsIgnoreCase(playlistName)==true){
          System.out.println("Cannot create playlist: A playlist with the same name already exists");
        }else {
          videoPlaylists.add(new VideoPlaylist(playlistName));
          System.out.println("Successfully created new playlist: "+playlistName);
          return;
        }
      }
    }

  }

  public void addVideoToPlaylist(String playlistName, String videoId) {

    VideoPlaylist videoPlaylist = null;

    Boolean beExistPlay=false;
   for (VideoPlaylist v: videoPlaylists) {
     if (v.getName().equalsIgnoreCase(playlistName) == false) {

     } else {
       beExistPlay=true;
       videoPlaylist = v;

     }
   }

   if (beExistPlay==false){
     System.out.println("Cannot add video to " + playlistName + ": Playlist does not exist");
     return;
   }

    Boolean beExistVideo=false;
   for (Video video: videoLibrary.getVideos()){
     if (videoLibrary.getVideo(videoId)==null){

     }else {
       beExistVideo=true;
     }
   }
   if (beExistVideo==false){
     System.out.println("Cannot add video to "+playlistName+": Video does not exist");
     return;
   }



   if (videoPlaylist.getVideos().size()!=0){
        for (Video video:videoPlaylist.getVideos()){
          if (video.getTitle().equalsIgnoreCase(videoLibrary.getVideo(videoId).getTitle())==false){

            if (videoLibrary.getVideo(videoId).getFlag().equals("")==false){
              System.out.println("Cannot add video to my_playlist: Video is currently flagged (reason: "+videoLibrary.getVideo(videoId).getFlag()+")");
              return;
            }

            videoPlaylist.getVideos().add(new Video(videoLibrary.getVideo(videoId).getTitle(),videoLibrary.getVideo(videoId).getVideoId(),videoLibrary.getVideo(videoId).getTags()));
            System.out.println("Added video to "+playlistName+": "+videoLibrary.getVideo(videoId).getTitle());
            return;
          }else {
            System.out.println("Cannot add video to "+playlistName+": Video already added");
            return;
          }
        }
        }else {

         if (videoLibrary.getVideo(videoId).getFlag().equals("")==false){
           System.out.println("Cannot add video to my_playlist: Video is currently flagged (reason: "+videoLibrary.getVideo(videoId).getFlag()+")");
           return;
          }

          videoPlaylist.getVideos().add(new Video(videoLibrary.getVideo(videoId).getTitle(),videoLibrary.getVideo(videoId).getVideoId(),videoLibrary.getVideo(videoId).getTags()));
          System.out.println("Added video to "+playlistName+": "+videoLibrary.getVideo(videoId).getTitle());
          return;
        }
    }


  public void showAllPlaylists() {
    if (videoPlaylists.size()==0){
      System.out.println("No playlists exist yet");
    }else {
      videoPlaylists.sort(new Comparator<VideoPlaylist>() {
        @Override
        public int compare(VideoPlaylist o1, VideoPlaylist o2) {
          return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
        }
      });
      System.out.println("Showing all playlists:");
      for (VideoPlaylist videoPlaylist:videoPlaylists){
        System.out.println(videoPlaylist.getName());
      }

    }
  }

  public void showPlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = null;

    Boolean beExistPlay=false;
    for (VideoPlaylist v: videoPlaylists) {
      if (v.getName().equalsIgnoreCase(playlistName) == false) {

      } else {
        beExistPlay=true;
        videoPlaylist = v;

      }
    }

    if (beExistPlay==false){
      System.out.println("Cannot show playlist "+playlistName+": Playlist does not exist");
      return;
    }

    if (videoPlaylist.getVideos().size()==0){
      System.out.println("Showing playlist: "+playlistName);
      System.out.println("No videos here yet");
    }else {

      System.out.println("Showing playlist: "+playlistName);
      for (Video v: videoPlaylist.getVideos()){
        if (v.getFlag().equals("")) {
          System.out.println(v.getTitle() + " " + "(" + v.getVideoId() + ")" + " " + v.getTags().toString().replaceAll("\\,", ""));
        }else {
          System.out.println(v.getTitle() + " " + "(" + v.getVideoId() + ")" + " " + v.getTags().toString().replaceAll("\\,", "")+" - FLAGGED (reason: "+v.getFlag()+")");
        }
      }

    }



  }

  public void removeFromPlaylist(String playlistName, String videoId) {
    VideoPlaylist videoPlaylist = null;

    Boolean beExistPlay=false;
    for (VideoPlaylist v: videoPlaylists) {
      if (v.getName().equalsIgnoreCase(playlistName) == false) {

      } else {
        beExistPlay=true;
        videoPlaylist = v;

      }
    }
    if (beExistPlay==false){
      System.out.println("Cannot remove video from "+playlistName+": Playlist does not exist");
      return;
    }


    if (videoLibrary.getVideo(videoId)!=null) {
      for (Video v : videoPlaylist.getVideos()) {

        if (v.getTitle().equalsIgnoreCase(videoLibrary.getVideo(videoId).getTitle()) == true) {
          videoPlaylist.getVideos().remove(v);
          System.out.println("Removed video from " + playlistName + ": " + v.getTitle());
          return;
        }
      }
    }else {
      System.out.println("Cannot remove video from " + playlistName + ": Video does not exist");
      return;

    }

    System.out.println("Cannot remove video from " + playlistName + ": Video is not in playlist");

  }

  public void clearPlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = null;

    Boolean beExistPlay=false;
    for (VideoPlaylist v: videoPlaylists) {
      if (v.getName().equalsIgnoreCase(playlistName) == false) {

      } else {
        beExistPlay=true;
        videoPlaylist = v;

      }
    }
    if (beExistPlay==false){
      System.out.println("Cannot clear playlist "+playlistName+": Playlist does not exist");
      return;
    }

    videoPlaylist.getVideos().clear();

    System.out.println("Successfully removed all videos from "+playlistName);
  }

  public void deletePlaylist(String playlistName) {
    VideoPlaylist videoPlaylist = null;

    Boolean beExistPlay=false;
    for (VideoPlaylist v: videoPlaylists) {
      if (v.getName().equalsIgnoreCase(playlistName) == false) {

      } else {
        beExistPlay=true;
        videoPlaylist = v;

      }
    }
    if (beExistPlay==false){
      System.out.println("Cannot delete playlist "+playlistName+": Playlist does not exist");
      return;
    }

    for (VideoPlaylist v: videoPlaylists){
      if (v.getName().equalsIgnoreCase(playlistName)){
        videoPlaylists.remove(v);
        System.out.println("Deleted playlist: "+playlistName);
        return;
      }
    }

  }

  public void searchVideos(String searchTerm) {

    List<Video> videos=videoLibrary.getVideos();
    videos.sort(Comparator.comparing(Video::getTitle));
    List<Video> output=new ArrayList<>();

    Boolean isExist=false;
    for (int i=0;i<videos.size();i++){
        if (videos.get(i).getTitle().toLowerCase().matches("(.*)"+searchTerm.toLowerCase()+"(.*)")){
          isExist=true;
        }
    }
    if (isExist!=true){
      System.out.println("No search results for "+searchTerm);
      return;
    }

    System.out.println("Here are the results for "+searchTerm+":");
    for (int i=0;i<videos.size();i++){
      if (videos.get(i).getTitle().toLowerCase().matches("(.*)"+searchTerm.toLowerCase()+"(.*)")) {
        if (videos.get(i).getFlag().equals("")) {
          System.out.println((output.size() + 1) + ") " + videos.get(i).getTitle() + " " + "(" + videos.get(i).getVideoId() + ")" + " " + videos.get(i).getTags().toString().replaceAll("\\,", ""));
          output.add(videos.get(i));
        }else {

        }
      }

    }
    Scanner input=new Scanner(System.in);
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
            "If your answer is not a valid number, we will assume it's a no.");
    String num=input.nextLine();
    if (num.matches("[0-9]+")==true){
      if(Integer.valueOf(num)>output.size()) {

      }else {

        System.out.println("Playing video: " + output.get(Integer.valueOf(num) - 1).getTitle());
      }
    }

  }

  public void searchVideosWithTag(String videoTag) {




    List<Video> videos=videoLibrary.getVideos();
    videos.sort(Comparator.comparing(Video::getTitle));
    List<Video> output=new ArrayList<>();

    Boolean isExist=false;
    for (int i=0;i<videos.size();i++){
      for (String tag:videos.get(i).getTags()){
        if (tag.toLowerCase().matches(videoTag.toLowerCase())){
         isExist=true;
        }
      }
    }
    if (isExist!=true){
      System.out.println("No search results for "+videoTag);
      return;
    }
    System.out.println("Here are the results for "+videoTag+":");
    for (int i=0;i<videos.size();i++){
      for (String tag:videos.get(i).getTags()){
        if (tag.toLowerCase().matches(videoTag.toLowerCase())) {
          if (videos.get(i).getFlag().equals("")) {
            System.out.println((output.size() + 1) + ") " + videos.get(i).getTitle() + " " + "(" + videos.get(i).getVideoId() + ")" + " " + videos.get(i).getTags().toString().replaceAll("\\,", ""));
            output.add(videos.get(i));
          }
        }else {

        }
      }
    }



    Scanner input=new Scanner(System.in);
    System.out.println("Would you like to play any of the above? If yes, specify the number of the video.\n" +
            "If your answer is not a valid number, we will assume it's a no.");
    String num=input.nextLine();
    if (num.matches("[0-9]+")==true){
      if(Integer.valueOf(num)>output.size()) {

      }else {
        System.out.println("Playing video: " + output.get(Integer.valueOf(num) - 1).getTitle());
      }
    }
  }

  public void flagVideo(String videoId) {
    if (this.videoLibrary.getVideo(videoId)==null){
      System.out.println("Cannot flag video: Video does not exist");
    } else if (this.videoLibrary.getVideo(videoId).getFlag().equals("")){
      this.videoLibrary.getVideo(videoId).setFlag("Not supplied");
      System.out.println("Successfully flagged video: "+videoLibrary.getVideo(videoId).getTitle()+" (reason: "+this.videoLibrary.getVideo(videoId).getFlag()+")");
    }else {
      System.out.println("Cannot flag video: Video is already flagged");
    }
  }

  public void flagVideo(String videoId, String reason) {
    if (videoLibrary.getVideo(videoId)==null){
      System.out.println("Cannot flag video: Video does not exist");
    }else if (videoLibrary.getVideo(videoId).getFlag().equals("")) {
      if (isPlaying!=null&&videoLibrary.getVideo(videoId).getTitle().equals(isPlaying.getTitle())||isPaused!=null&&videoLibrary.getVideo(videoId).getTitle().equals(isPaused.getTitle())){
        stopVideo();
      }

      videoLibrary.getVideo(videoId).setFlag(reason);
      System.out.println("Successfully flagged video: " + videoLibrary.getVideo(videoId).getTitle() + " (reason: " + this.videoLibrary.getVideo(videoId).getFlag() + ")");
    }else {
      System.out.println("Cannot flag video: Video is already flagged");
    }

    for (VideoPlaylist videoPlaylist: videoPlaylists){
      for (Video video: videoPlaylist.getVideos()){
        if (video.getTitle().equals(videoLibrary.getVideo(videoId).getTitle())){
          video.setFlag(videoLibrary.getVideo(videoId).getFlag());
        }
      }
    }

  }

  public void allowVideo(String videoId) {
    if (videoLibrary.getVideo(videoId)==null){
      System.out.println("Cannot remove flag from video: Video does not exist");
    }else if (!videoLibrary.getVideo(videoId).getFlag().equals("")) {
      videoLibrary.getVideo(videoId).setFlag("");
      System.out.println("Successfully removed flag from video: "+videoLibrary.getVideo(videoId).getTitle());
    } else {
      System.out.println("Cannot remove flag from video: Video is not flagged");
    }
  }
}