package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.widget.CommentListView;
import com.example.zhanghao.woaisiji.widget.ExpandTextView;
import com.example.zhanghao.woaisiji.widget.PraiseListView;
import com.example.zhanghao.woaisiji.widget.SnsPopupWindow;
import com.example.zhanghao.woaisiji.widget.videolist.model.VideoLoadMvpView;
import com.example.zhanghao.woaisiji.widget.videolist.widget.TextureVideoView;


public abstract class CircleViewHolder extends RecyclerView.ViewHolder implements VideoLoadMvpView {

    public final static int TYPE_URL = 1;
    public final static int TYPE_IMAGE = 2;
    public final static int TYPE_VIDEO = 3;
    public final static int TYPE_NONE= 4;

    public int viewType;

    public ImageView headIv;
    public TextView nameTv;
//    public TextView urlTipTv;
    /** 动态的内容 */
    public ExpandTextView contentTv;
    public TextView timeTv;
    public TextView deleteBtn;
    public ImageView snsBtn;
    /** 点赞列表*/
    public PraiseListView praiseListView;

    public LinearLayout digCommentBody;
    public View digLine;

    /** 评论列表 */
    public CommentListView commentList;
    // ===========================
    public SnsPopupWindow snsPopupWindow;

    /**
     * 发布评论
     * @param itemView
     * @param viewType
     */
    public LinearLayout editTextBodyL1;
    public EditText circleEt;
    public ImageView sendIv;

    public CircleViewHolder(View itemView, int viewType) {
        super(itemView);
        this.viewType = viewType;

        ViewStub viewStub = (ViewStub) itemView.findViewById(R.id.viewStub);

        initSubView(viewType, viewStub);

        headIv = (ImageView) itemView.findViewById(R.id.headIv);
        nameTv = (TextView) itemView.findViewById(R.id.nameTv);
        digLine = itemView.findViewById(R.id.lin_dig);
        digLine.setVisibility(View.GONE);

        contentTv = (ExpandTextView) itemView.findViewById(R.id.contentTv);
//        urlTipTv = (TextView) itemView.findViewById(R.id.urlTipTv);

        timeTv = (TextView) itemView.findViewById(R.id.timeTv);
        deleteBtn = (TextView) itemView.findViewById(R.id.deleteBtn);
        snsBtn = (ImageView) itemView.findViewById(R.id.snsBtn);
        praiseListView = (PraiseListView) itemView.findViewById(R.id.praiseListView);

        digCommentBody = (LinearLayout) itemView.findViewById(R.id.digCommentBody);
        commentList = (CommentListView)itemView.findViewById(R.id.commentList);

        editTextBodyL1 = (LinearLayout) itemView.findViewById(R.id.editTextBodyLl);
        circleEt = (EditText) itemView.findViewById(R.id.circleEt);
        sendIv = (ImageView) itemView.findViewById(R.id.sendIv);

        snsPopupWindow = new SnsPopupWindow(itemView.getContext());

    }

    public abstract void initSubView(int viewType, ViewStub viewStub);

    @Override
    public TextureVideoView getVideoView() {
        return null;
    }

    @Override
    public void videoBeginning() {

    }

    @Override
    public void videoStopped() {

    }

    @Override
    public void videoPrepared(MediaPlayer player) {

    }

    @Override
    public void videoResourceReady(String videoPath) {

    }
}
