package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.view.View;
import android.view.ViewStub;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.widget.CircleVideoView;

/*import com.yiw.circledemo.R;
import com.yiw.circledemo.widgets.CircleVideoView;*/

/**
 * Created by suneee on 2016/8/16.
 */
public class NewVideoViewHolder extends DriverReviewHolder {
    public CircleVideoView videoView;

    public NewVideoViewHolder(View itemView){
        super(itemView, TYPE_VIDEO);
    }

    @Override
    public void initSubView(int viewType, ViewStub viewStub) {
        if(viewStub == null){
            throw new IllegalArgumentException("viewStub is null...");
        }
        
        viewStub.setLayoutResource(R.layout.viewstub_videobody);
        View subView = viewStub.inflate();

        CircleVideoView videoBody = (CircleVideoView) subView.findViewById(R.id.videoView);
        if(videoBody!=null){
            this.videoView = videoBody;
        }
    }
}
