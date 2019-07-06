package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.UploadCommentBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.tools.TimeUtils;
import com.example.zhanghao.woaisiji.utils.DpTransPx;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.util.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/8/19.
 */
public class WebViewActivity extends Activity  {
    private static final int TYPE_UPLOAD = 1;
    private static final int TYPE_ADD = 2;
    private WebView wvDetailContent;
    private UploadCommentBean uploadComment;
    private List<UploadCommentBean.CommentBean> commentLists;
    private String commentId;
    private String commentType;
    private String webUrl;
    private View webView;
    private String urlComment;
    private String urlAddComment;
    private EditText etCircle;
    private ImageView ivSend;
    private AlterResultBean resultBean;
    private LinearLayout editTextBodyL1;
    private ExpandableListView expandLvWebView;
    private ImageView ivPraise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_comment);
//        Toast.makeText(WebViewActivity.this, "加载数据aaaa", Toast.LENGTH_SHORT).show();
        commentId = getIntent().getStringExtra("id");
        commentType = getIntent().getStringExtra("type");

        initView();


        // 加载评论
        urlComment = null;
        urlAddComment = null;
        commentLists = new ArrayList<>();
        switch (Integer.parseInt(commentType)) {
            case 3:
                editTextBodyL1.setVisibility(View.VISIBLE);
                urlComment = ServerAddress.URL_HEALTH_COMMENT;
                urlAddComment = ServerAddress.URL_HEALTH_ADD_COMMENT;
                uploadComment();
                break;
            case 4:
                editTextBodyL1.setVisibility(View.VISIBLE);
                urlComment = ServerAddress.URL_TRAVEL_COMMENT;
                urlAddComment = ServerAddress.URL_TRAVEL_ADD_COMMENT;
                ivPraise.setVisibility(View.VISIBLE);
                uploadComment();
                break;
//            case 5:
//                // 如果是司机资讯，则没有评论功能
//                editTextBodyL1.setVisibility(View.GONE);
//
////                lvComment.setAdapter(new CommentAdapter());
////                lvComment.addHeaderView(webView);
//
//                break;
        }



    }

    private void releaseComment(final String comment) {
        // 把评论添加到服务器
        StringRequest addCommentRequest = new StringRequest(Request.Method.POST, urlAddComment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Log.d("WebViewActivity",response);
                transServerData(response, TYPE_ADD);
//                Toast.makeText(WebViewActivity.this, "code:" + resultBean.code, Toast.LENGTH_SHORT).show();
                if (resultBean.code == 200) {
//                    commentLists.clear();
                    uploadComment();
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
                params.put("uid", (WoAiSiJiApp.getUid()));
                params.put("pid", commentId);
                params.put("content", comment);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(addCommentRequest);
    }


    private void initView() {
//        lvLoveDriverConsultList = (BaseListView) findViewById(R.id.refresh_view);
//        wvDetailContent = (WebView) findViewById(R.id.wv_detail_content);
        expandLvWebView = (ExpandableListView) findViewById(R.id.list_view_comment);
//        lvComment = (ListView) findViewById(R.id.list_view_comment);
        etCircle = (EditText) findViewById(R.id.circleEt);
        ivSend = (ImageView) findViewById(R.id.sendIv);
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment = etCircle.getText().toString().trim();
                if (!TextUtils.isEmpty(comment)) {
                    releaseComment(comment);
                    etCircle.setText("");
                }

            }
        });
        editTextBodyL1 = (LinearLayout) findViewById(R.id.editTextBodyLl);
        ivPraise = (ImageView) findViewById(R.id.iv_praise);
        ivPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 点赞功能
                praiseToServer();
            }
        });

    }

    // 点赞功能
    private void praiseToServer(){
        StringRequest praiseRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_TRAVEL_ADD_PRAISE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response,TYPE_ADD);
                Toast.makeText(WebViewActivity.this,resultBean.msg,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(WebViewActivity.this,"服务器忙，请重试!",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",(WoAiSiJiApp.getUid()));
                params.put("id",commentId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(praiseRequest);
    }



    private void uploadComment() {

        commentLists.clear();
        final StringRequest uploadCommentRequest = new StringRequest(Request.Method.POST, urlComment, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                transServerData(response, TYPE_UPLOAD);
                if (uploadComment.code == 200) {
                    if (uploadComment.list != null) {
                        for (int i = 0; i < uploadComment.list.size(); i++) {
                            commentLists.add(uploadComment.list.get(i));
                        }
                    }
                    expandLvWebView.setAdapter(new OrderFormExpandListView());
                    expandLvWebView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                            return true;
                        }
                    });
                    expandLvWebView.expandGroup(0);
                    expandLvWebView.setGroupIndicator(null);
//                    lvComment.setAdapter(new CommentAdapter());
////                    webView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    if (lvComment.getHeaderViewsCount() > 0) {
//                        lvComment.removeHeaderView(webView);
//                    }
//                    lvComment.addHeaderView(webView);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(WebViewActivity.this, "加载数据失败", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", commentId);
//                Log.d("activity  id:", commentId);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(uploadCommentRequest);
    }

    private void transServerData(String response, int type) {
        Gson gson = new Gson();
        switch (type) {
            case TYPE_UPLOAD:
                uploadComment = gson.fromJson(response, UploadCommentBean.class);
                break;
            case TYPE_ADD:
                resultBean = gson.fromJson(response, AlterResultBean.class);
                break;
        }

    }

    class OrderFormExpandListView extends BaseExpandableListAdapter {


        @Override
        public int getGroupCount() {
            return 1;
        }

        @Override
        public int getChildrenCount(int i) {
            return commentLists.size();
        }

        @Override
        public Object getGroup(int i) {
            return null;
        }

        @Override
        public UploadCommentBean.CommentBean getChild(int i, int i1) {
            return null;
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            ViewHolderTitle viewHolderTitle;
            if (view == null) {
                view = View.inflate(WebViewActivity.this, R.layout.activity_web_view_header, null);
                viewHolderTitle = new ViewHolderTitle();
                viewHolderTitle.wvDetailContent = (WebView) view.findViewById(R.id.wv_detail_content);
                view.setTag(viewHolderTitle);
            } else {
                viewHolderTitle = (ViewHolderTitle) view.getTag();
            }
//            webUrl = "http://www.woaisiji.net/APP/Public/get_goods_detail" + "/id/" + 3 + "/type/" + 4;
            webUrl = ServerAddress.URL_HEALTH_DETAIL + "/id/" + commentId + "/type/" + commentType;

            viewHolderTitle.wvDetailContent.loadUrl(webUrl);
            viewHolderTitle.wvDetailContent.getSettings().setJavaScriptEnabled(true);
            viewHolderTitle.wvDetailContent.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            WindowManager wm = (WindowManager) WebViewActivity.this.getSystemService(Context.WINDOW_SERVICE);
//        int width = wm.getDefaultDisplay().getWidth();
            int llHeight = wm.getDefaultDisplay().getHeight();
            int webHeight = viewHolderTitle.wvDetailContent.getMeasuredHeightAndState();
//            Toast.makeText(WebViewActivity.this, "llHeight的内容的高度" + webHeight, Toast.LENGTH_SHORT).show();
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, DpTransPx.Dp2Px(WebViewActivity.this, llHeight));
            view.setLayoutParams(lp);
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(WebViewActivity.this, R.layout.web_view_comment_list, null);
                viewHolder = new ViewHolder();
                viewHolder.rivCommentHead = (RoundImageView) view.findViewById(R.id.riv_comment_head);
                viewHolder.tvCommentNick = (TextView) view.findViewById(R.id.tv_comment_nick);
                viewHolder.tvCommentDate = (TextView) view.findViewById(R.id.tv_comment_date);
                viewHolder.tvCommentContent = (TextView) view.findViewById(R.id.tv_comment_content);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            UploadCommentBean.CommentBean item = commentLists.get(i1);
            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+ item.headpic,viewHolder.rivCommentHead);
//            Log.d("activity-nick", item.nickname);
            viewHolder.tvCommentNick.setText(item.nickname);
            viewHolder.tvCommentDate.setText(TimeUtils.times(item.create_time));
            viewHolder.tvCommentContent.setText(item.content);
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }
    }


    static class ViewHolderTitle {
        public WebView wvDetailContent;

    }

    static class ViewHolder {
        public RoundImageView rivCommentHead;
        public TextView tvCommentNick;
        public TextView tvCommentDate;
        public TextView tvCommentContent;
    }


}
