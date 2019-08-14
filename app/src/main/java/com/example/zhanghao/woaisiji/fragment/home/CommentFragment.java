package com.example.zhanghao.woaisiji.fragment.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.comment.ReleaseDynamicActivity2;
import com.example.zhanghao.woaisiji.adapter.CircleAdapter;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.CircleItem;
import com.example.zhanghao.woaisiji.bean.CommentConfig;
import com.example.zhanghao.woaisiji.bean.CommentItem;
import com.example.zhanghao.woaisiji.bean.FavortItem;
import com.example.zhanghao.woaisiji.dynamic.ReleaseDynamicActivity;
import com.example.zhanghao.woaisiji.fragment.BaseFragment;
import com.example.zhanghao.woaisiji.friends.util.Utils;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.mvp.contract.CircleContract;
import com.example.zhanghao.woaisiji.mvp.presenter.CirclePresenter;
import com.example.zhanghao.woaisiji.utils.CommonUtils;
import com.example.zhanghao.woaisiji.widget.CommentListView;
import com.example.zhanghao.woaisiji.widget.DivItemDecoration;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommentFragment extends BaseFragment implements CircleContract.View,
        XRecyclerView.LoadingListener {

    private CirclePresenter presenter;
    private final static int TYPE_PULLREFRESH = 1;
    private final static int TYPE_UPLOADREFRESH = 2;
    private TextView tv_title_bar_view_centre_title, tv_title_bar_view_right_right_introduction;
    private XRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CircleAdapter mAdapter;
    private LinearLayout edittextbody;
    private EditText editText;
    private ImageView sendIv;
    private RelativeLayout bodyLayout;

    private CommentConfig commentConfig;
    private int selectCircleItemH;
    private int selectCommentItemOffset;
    private int currentKeyboardH;
    private int screenHeight;
    private int editTextBodyHeight;
    private static AlterResultBean resultBean;

    @Override
    public View initBaseFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        if (!TextUtils.isEmpty(WoAiSiJiApp.getUid())) {
            presenter = new CirclePresenter(this);
            initView(view);
            loadData(TYPE_PULLREFRESH);
        }
        return view;
    }

    public void loadData(int type) {
        try {
            presenter.loadData(type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView(View rootView) {
        tv_title_bar_view_centre_title = (TextView) rootView.findViewById(R.id.tv_title_bar_view_centre_title);
        tv_title_bar_view_right_right_introduction = (TextView) rootView.findViewById(R.id.tv_title_bar_view_right_right_introduction);
        tv_title_bar_view_centre_title.setText("分享");
        tv_title_bar_view_right_right_introduction.setVisibility(View.VISIBLE);
        tv_title_bar_view_right_right_introduction.setText("发布");

        recyclerView = (XRecyclerView) rootView.findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, true));

        mAdapter = new CircleAdapter(getContext());
        mAdapter.setCirclePresenter(presenter);
        recyclerView.setAdapter(mAdapter);

        edittextbody = (LinearLayout) rootView.findViewById(R.id.editTextBodyLl);
        editText = (EditText) rootView.findViewById(R.id.circleEt);
        sendIv = (ImageView) rootView.findViewById(R.id.sendIv);
        bodyLayout = (RelativeLayout) rootView.findViewById(R.id.bodyLayout);

        initListener();
    }

    /**
     * 初始化监听
     */
    @SuppressLint({"ClickableViewAccessibility", "InlinedApi"})
    private void initListener() {
        //发布
        tv_title_bar_view_right_right_introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ReleaseDynamicActivity.class));//跳转到写动态界面
//                startActivity(new Intent(getActivity(), ReleaseDynamicActivity2.class));//跳转到写动态界面
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (edittextbody.getVisibility() == View.VISIBLE) {
                    updateEditTextBodyVisible(View.GONE, null);
                    return true;
                }
                return false;
            }
        });
        recyclerView.setLoadingListener(this);

        //设置滚动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Glide.with(getActivity()).resumeRequests();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    Glide.with(getActivity()).pauseRequests();
                }
            }
        });

        sendIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (presenter != null) {
                    //发布评论
                    String content = editText.getText().toString().trim();
                    if (TextUtils.isEmpty(content)) {
                        Toast.makeText(getActivity(), "评论内容不能为空...", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    presenter.addComment(content, commentConfig);
                }
                updateEditTextBodyVisible(View.GONE, null);
            }
        });

        ViewTreeObserver viewTreeObserver = bodyLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                bodyLayout.getWindowVisibleDisplayFrame(r);
                int statusBarH = Utils.getStatusBarHeight(getContext());//状态栏高度
                int screenH = bodyLayout.getRootView().getHeight();
                if (r.top != statusBarH) {
                    //在这个demo中r.top代表的是状态栏高度，在沉浸式状态栏时r.top＝0，通过getStatusBarHeight获取状态栏高度
                    r.top = statusBarH;
                }
                int keyboardH = screenH - (r.bottom - r.top);
                if (keyboardH == currentKeyboardH) {//有变化时才处理，否则会陷入死循环
                    return;
                }
                currentKeyboardH = keyboardH;
                screenHeight = screenH;//应用屏幕的高度
                editTextBodyHeight = edittextbody.getHeight();

                if (keyboardH < 150) {//说明是隐藏键盘的情况
                    updateEditTextBodyVisible(View.GONE, null);
                    return;
                }
                //偏移listview
                if (layoutManager != null && commentConfig != null) {
                    layoutManager.scrollToPositionWithOffset(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE,
                            getListviewOffset(commentConfig));
                }
            }
        });
    }

    /**
     * 测量偏移量
     */
    private int getListviewOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return 0;
        int listviewOffset = screenHeight - selectCircleItemH - currentKeyboardH - editTextBodyHeight;
        if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            listviewOffset = listviewOffset + selectCommentItemOffset;
        }
        return listviewOffset;
    }

    private void measureCircleItemHighAndCommentItemOffset(CommentConfig commentConfig) {
        if (commentConfig == null)
            return;
        int firstPosition = layoutManager.findFirstVisibleItemPosition();
        //只能返回当前可见区域（列表可滚动）的子项
        View selectCircleItem = layoutManager.getChildAt(commentConfig.circlePosition + CircleAdapter.HEADVIEW_SIZE - firstPosition);
        if (selectCircleItem != null) {
            selectCircleItemH = selectCircleItem.getHeight();
        }

        /*if (commentConfig.commentType == CommentConfig.Type.REPLY) {
            //回复评论的情况
            CommentListView commentLv = (CommentListView) selectCircleItem.findViewById(R.id.commentList);
            if (commentLv != null) {
                //找到要回复的评论view,计算出该view距离所属动态底部的距离
                View selectCommentItem = commentLv.getChildAt(commentConfig.commentPosition);
                if (selectCommentItem != null) {
                    //选择的commentItem距选择的CircleItem底部的距离
                    selectCommentItemOffset = 0;
                    View parentView = selectCommentItem;
                    do {
                        int subItemBottom = parentView.getBottom();
                        parentView = (View) parentView.getParent();
                        if (parentView != null) {
                            selectCommentItemOffset += (parentView.getHeight() - subItemBottom);
                        }
                    } while (parentView != null && parentView != selectCircleItem);
                }
            }
        }*/
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadData(TYPE_PULLREFRESH);
                recyclerView.refreshComplete();
//                mAdapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 加载数据
                presenter.pager++;
                presenter.getDataFromServer(TYPE_UPLOADREFRESH);
                recyclerView.loadMoreComplete();
            }
        }, 2000);
    }

    // 将评论提交到服务器
    private void addCommentToServer(final String id, final CommentItem addItem) {
        StringRequest addCommentRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_CIRCLE_ADD_COMMENT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                resultBean = gson.fromJson(response, AlterResultBean.class);
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
                params.put("cid", id);
                params.put("content", addItem.getContent());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addCommentRequest);
    }

    @Override
    public void update2DeleteCircle(String circleId) {
        List<CircleItem> circleItems = mAdapter.getDatas();
        for (int i = 0; i < circleItems.size(); i++) {
            if (circleId.equals(circleItems.get(i).getId())) {
                circleItems.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void update2AddFavorite(int circlePosition, FavortItem addItem) {
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        item.setFavorNum(String.valueOf(Integer.parseInt(item.getFavorNum()) + 1));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void update2DeleteFavort(int circlePosition, String favortId) {
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        List<FavortItem> items = item.getFavorters();
        for (int i = 0; i < items.size(); i++) {
            if (favortId.equals(items.get(i).getId())) {
                items.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void update2AddComment(int circlePosition, CommentItem addItem) {
        if (addItem != null) {
            mAdapter.notifyDataSetChanged();
            CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
            addCommentToServer(item.getId(), addItem);
            item.getComments().add(addItem);
            mAdapter.notifyDataSetChanged();
        }
        //清空评论文本
        editText.setText("");
    }

    @Override
    public void update2DeleteComment(int circlePosition, String commentId) {
        CircleItem item = (CircleItem) mAdapter.getDatas().get(circlePosition);
        List<CommentItem> items = item.getComments();
        for (int i = 0; i < items.size(); i++) {
            if (commentId.equals(items.get(i).getCid())) {
                items.remove(i);
                mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public void updateEditTextBodyVisible(int visibility, CommentConfig commentConfig) {
        this.commentConfig = commentConfig;
        edittextbody.setVisibility(visibility);
        measureCircleItemHighAndCommentItemOffset(commentConfig);
        if (View.VISIBLE == visibility) {
            editText.requestFocus();
            //弹出键盘
            CommonUtils.showSoftInput(editText.getContext(), editText);
        } else if (View.GONE == visibility) {
            //隐藏键盘
            CommonUtils.hideSoftInput(editText.getContext(), editText);
        }
    }

    @Override
    public void update2loadData(int loadType, List<CircleItem> datas) {
        if (loadType == TYPE_PULLREFRESH) {
            mAdapter.setDatas(datas);
        } else if (loadType == TYPE_UPLOADREFRESH) {
            mAdapter.getDatas().addAll(datas);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }
}
