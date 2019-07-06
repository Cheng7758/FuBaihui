package com.example.zhanghao.woaisiji.widget.videolist.model;

import android.media.MediaPlayer;

import com.example.zhanghao.woaisiji.widget.videolist.widget.TextureVideoView;

//import com.yiw.circledemo.widgets.videolist.widget.TextureVideoView;


/**
 * @author Wayne
 */
public interface VideoLoadMvpView {

    TextureVideoView getVideoView();

    void videoBeginning();

    void videoStopped();

    void videoPrepared(MediaPlayer player);

    void videoResourceReady(String videoPath);
}
