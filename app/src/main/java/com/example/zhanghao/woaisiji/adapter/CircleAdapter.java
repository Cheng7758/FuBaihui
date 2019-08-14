package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.ImagePagerActivity;
import com.example.zhanghao.woaisiji.activity.PersonalInfoDetailActivity;
import com.example.zhanghao.woaisiji.adapter.viewholder.CircleViewHolder;
import com.example.zhanghao.woaisiji.adapter.viewholder.HeaderViewHolder;
import com.example.zhanghao.woaisiji.adapter.viewholder.ImageViewHolder;
import com.example.zhanghao.woaisiji.adapter.viewholder.URLViewHolder;
import com.example.zhanghao.woaisiji.adapter.viewholder.VideoViewHolder;
import com.example.zhanghao.woaisiji.bean.ActionItem;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.CircleItem;
import com.example.zhanghao.woaisiji.bean.CommentConfig;
import com.example.zhanghao.woaisiji.bean.CommentItem;
import com.example.zhanghao.woaisiji.bean.FavortItem;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.mvp.presenter.CirclePresenter;
import com.example.zhanghao.woaisiji.serverdata.FriendsListData;
import com.example.zhanghao.woaisiji.utils.GlideCircleTransform;
import com.example.zhanghao.woaisiji.utils.UrlUtils;
import com.example.zhanghao.woaisiji.widget.CircleVideoView;
import com.example.zhanghao.woaisiji.widget.CommentListView;
import com.example.zhanghao.woaisiji.widget.MultiImageView;
import com.example.zhanghao.woaisiji.widget.PraiseListView;
import com.example.zhanghao.woaisiji.widget.SnsPopupWindow;
import com.example.zhanghao.woaisiji.widget.dialog.CommentDialog;
import com.google.gson.Gson;
import com.hyphenate.easeui.domain.EaseUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CircleAdapter extends BaseRecycleViewAdapter {

    public final static int TYPE_HEAD = 8;

    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;
    private static final int TYPE_IMAGE = 0;
    private static final int TYPE_FAVOR = 1;
    private static final int TYPE_COLLECTION = 2;
    private int videoState = STATE_IDLE;
    public static final int HEADVIEW_SIZE = 1;

    int curPlayIndex = -1;

    private CirclePresenter presenter;
    private Context context;
    private String favortNum;
    //    private String favortId;
    private AlterResultBean resultBean;
    private String circleId;
//    private ImageUrlBean imageUrl;

    public void setCirclePresenter(CirclePresenter presenter) {
        this.presenter = presenter;
    }

    public CircleAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }

        int itemType = 0;
        CircleItem item = (CircleItem) datas.get(position - 1);
        if (CircleItem.TYPE_URL.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_URL;
        } else if (CircleItem.TYPE_IMG.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_IMAGE;
        } else if (CircleItem.TYPE_VIDEO.equals(item.getType())) {
            itemType = CircleViewHolder.TYPE_VIDEO;
        }
        return itemType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == TYPE_HEAD) {//头布局
            String str = "" + context;
            View headView = LayoutInflater.from(parent.getContext())//加载布局
                    .inflate(R.layout.love_circle_head_circle, parent, false);
            viewHolder = new HeaderViewHolder(headView);//初始化控件
        } else {//列表布局
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.love_circle_adapter_circle_item, parent, false);

            if (viewType == CircleViewHolder.TYPE_URL) {
                viewHolder = new URLViewHolder(view);
            } else if (viewType == CircleViewHolder.TYPE_IMAGE) {
                viewHolder = new ImageViewHolder(view);
            } else if (viewType == CircleViewHolder.TYPE_VIDEO) {
                viewHolder = new VideoViewHolder(view);
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            String str = "" + context;
            final HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            FriendsListData friendsListData = new FriendsListData();
            friendsListData.getHeadPictureUrl();//加载头像
            friendsListData.setSendDataListener(new FriendsListData.SendDataListener() {
                @Override
                public void sendData(Map<String, EaseUser> userList) {

                }

                @Override
                public void sendStringData(String data) {
//                    ImageLoader.getInstance().displayImage(data,holder.rivDynamicHeadPic);
                }
            });
            holder.rivDynamicHeadPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PersonalInfoDetailActivity.class);
                    context.startActivity(intent);
                }
            });
        } else {
            final int circlePosition = position - HEADVIEW_SIZE;
            final CircleViewHolder holder = (CircleViewHolder) viewHolder;
            CircleItem circleItem = (CircleItem) datas.get(circlePosition);
            circleId = circleItem.getId();
            String name = circleItem.getUser().getName();
            String headImg = circleItem.getUser().getHeadUrl();
            final String content = circleItem.getContent();
            String createTime = circleItem.getCreateTime();
            final List<FavortItem> favortDatas = circleItem.getFavorters();
            final List<CommentItem> commentsDatas = circleItem.getComments();
            boolean hasFavort = circleItem.hasFavort();
            boolean hasComment = circleItem.hasComment();

            favortNum = circleItem.getFavorNum();

            Glide.with(context).load(headImg).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder
                    (R.color.bg_no_photo).transform(new GlideCircleTransform(context)).into(holder.headIv);

            holder.nameTv.setText(name);
            holder.timeTv.setText(createTime);

            if (!TextUtils.isEmpty(content)) {
                holder.contentTv.setText(UrlUtils.formatUrlString(content));
            }
            holder.contentTv.setVisibility(TextUtils.isEmpty(content) ? View.GONE : View.VISIBLE);

            if ((WoAiSiJiApp.getUid()).equals(circleItem.getUser().getId())) {
                holder.deleteBtn.setVisibility(View.GONE);
            } else {
                holder.deleteBtn.setVisibility(View.GONE);
            }

            if (hasFavort || hasComment) {
                if (hasFavort) {//处理点赞列表
                    holder.praiseListView.setOnItemClickListener(new PraiseListView.OnItemClickListener() {
                        @Override
                        public void onClick(int position) {
                            favortNum = String.valueOf(Integer.parseInt(favortNum) + 1);
                        }
                    });
                    holder.praiseListView.setDatas(favortNum);
                    holder.praiseListView.setVisibility(View.VISIBLE);
                } else {
                    holder.praiseListView.setVisibility(View.GONE);
                }

                if (hasComment) {//处理评论列表
                    holder.commentList.setOnItemClickListener(new CommentListView.OnItemClickListener() {
                        @Override
                        public void onItemClick(int commentPosition) {
                            CommentItem commentItem = commentsDatas.get(commentPosition);
                            if ((WoAiSiJiApp.getCurrentUserInfo() != null) && (WoAiSiJiApp
                                    .getCurrentUserInfo().getPic().equals(commentItem.getUser().getId()))) {//复制或者删除自己的评论
                                CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
                                dialog.show();
                            } else {//回复别人的评论
                                if (presenter != null) {
                                    CommentConfig config = new CommentConfig();
                                    config.circlePosition = circlePosition;
                                    config.commentPosition = commentPosition;
                                    config.commentType = CommentConfig.Type.REPLY;
                                    config.replyUser = commentItem.getUser();
                                    presenter.showEditTextBody(config);
                                }
                            }
                        }
                    });
                    holder.commentList.setOnItemLongClickListener(new CommentListView.OnItemLongClickListener() {
                        @Override
                        public void onItemLongClick(int commentPosition) {
                            //长按进行复制或者删除
                            CommentItem commentItem = commentsDatas.get(commentPosition);
                            CommentDialog dialog = new CommentDialog(context, presenter, commentItem, circlePosition);
                            dialog.show();
                        }
                    });
                    holder.commentList.setDatas(commentsDatas);
                    holder.commentList.setVisibility(View.VISIBLE);

                } else {
                    holder.commentList.setVisibility(View.GONE);
                }
                holder.digCommentBody.setVisibility(View.VISIBLE);
            } else {
                holder.digCommentBody.setVisibility(View.GONE);
            }

            holder.digLine.setVisibility(hasFavort && hasComment ? View.VISIBLE : View.GONE);

            final SnsPopupWindow snsPopupWindow = holder.snsPopupWindow;
            //判断是否已点赞
            snsPopupWindow.getmActionItems().get(0).mTitle = "赞";
            snsPopupWindow.update();
            snsPopupWindow.setmItemClickListener(new PopupItemClickListener(circlePosition, circleItem));
//            snsPopupWindow.setmItemClickListener(new PopupItemClickListener(circlePosition, circleItem, curUserFavortId));
            holder.snsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snsPopupWindow.showPopupWindow(view);
                }
            });

            switch (holder.viewType) {
                case CircleViewHolder.TYPE_URL:// 处理链接动态的链接内容和和图片
                    if (holder instanceof URLViewHolder) {
                        String linkImg = circleItem.getLinkImg();
                        String linkTitle = circleItem.getLinkTitle();
                        Glide.with(context).load(linkImg).into(((URLViewHolder) holder).urlImageIv);
                    }

                    break;
                case CircleViewHolder.TYPE_IMAGE:// 处理图片
                    if (holder instanceof ImageViewHolder) {
                        final List<String> photos = circleItem.getPhotos();
                        if (photos != null && photos.size() > 0) {
                            ((ImageViewHolder) holder).multiImageView.setVisibility(View.VISIBLE);
                            ((ImageViewHolder) holder).multiImageView.setList(photos);
                            ((ImageViewHolder) holder).multiImageView.setOnItemClickListener(new MultiImageView.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    //imagesize是作为loading时的图片size
                                    ImagePagerActivity.ImageSize imageSize = new ImagePagerActivity.ImageSize(view.getMeasuredWidth(), view.getMeasuredHeight());
                                    ImagePagerActivity.startImagePagerActivity(context, photos, position, imageSize);
                                }
                            });
                        } else {
                            ((ImageViewHolder) holder).multiImageView.setVisibility(View.GONE);
                        }
                    }
                    break;
                case CircleViewHolder.TYPE_VIDEO:
                    if (holder instanceof VideoViewHolder) {
                        ((VideoViewHolder) holder).videoView.setVideoUrl(circleItem.getVideoUrl());
                        ((VideoViewHolder) holder).videoView.setVideoImgUrl(circleItem.getVideoImgUrl());//视频封面图片
                        ((VideoViewHolder) holder).videoView.setPostion(position);
                        ((VideoViewHolder) holder).videoView.setOnPlayClickListener(new CircleVideoView.OnPlayClickListener() {
                            @Override
                            public void onPlayClick(int pos) {
                                curPlayIndex = pos;
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size() + 1;//有head需要加1
    }

    private class PopupItemClickListener implements SnsPopupWindow.OnItemClickListener {
        private String mFavorId;
        //动态在列表中的位置
        private int mCirclePosition;
        private long mLasttime = 0;
        private CircleItem mCircleItem;

        public PopupItemClickListener(int circlePosition, CircleItem circleItem) {
//            this.mFavorId = favorId;
            this.mCirclePosition = circlePosition;
            this.mCircleItem = circleItem;
        }

        @Override
        public void onItemClick(ActionItem actionitem, int position) {
            switch (position) {
                case 0://点赞、取消点赞
                    if (System.currentTimeMillis() - mLasttime < 700)//防止快速点击操作
                        return;
                    mLasttime = System.currentTimeMillis();
                    if (presenter != null) {
                        if ("赞".equals(actionitem.mTitle.toString())) {
                            CircleItem circleItem = (CircleItem) datas.get(mCirclePosition);
//                            Log.d("CircleAdapter", circleItem.getId());
                            // 点赞功能
                            addFavortToServer(mCirclePosition, circleItem.getId());
                        }
                    }
                    break;
                case 1://发布评论
                    if (presenter != null) {
                        CommentConfig config = new CommentConfig();
                        config.circlePosition = mCirclePosition;
                        config.commentType = CommentConfig.Type.PUBLIC;

                        presenter.showEditTextBody(config);
                    }
                    break;
                case 2:// 收藏
                    if (presenter != null) {
//                        Log.d("aaaaa","转发功能");
                        addCollectionToServer();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void addCollectionToServer() {
        StringRequest addCollectionRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_CIRCLE_ADD_COLLECTION, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response, TYPE_COLLECTION);
                if (resultBean.code == 200) {
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("cid", circleId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addCollectionRequest);
    }

    private void addFavortToServer(final int circlePosition, final String favortId) {
        StringRequest addFavortRequest = new StringRequest(Request.Method.POST, ServerAddress.
                URL_CIRCLE_FAVORT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response, TYPE_FAVOR);
                Toast.makeText(context, resultBean.msg, Toast.LENGTH_SHORT).show();
                if (resultBean.code == 200) {
                    Toast.makeText(context, resultBean.msg, Toast.LENGTH_SHORT).show();
                    presenter.addFavort(circlePosition);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("id", favortId);
//                params.put("id", );
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addFavortRequest);
    }

    private void transServerData(String response, int type) {
        Gson gson = new Gson();
        switch (type) {
            case TYPE_FAVOR:
            case TYPE_COLLECTION:
                resultBean = gson.fromJson(response, AlterResultBean.class);
                break;
            case TYPE_IMAGE:
//                imageUrl = gson.fromJson(response,ImageUrlBean.class);
                break;
        }
    }
}
