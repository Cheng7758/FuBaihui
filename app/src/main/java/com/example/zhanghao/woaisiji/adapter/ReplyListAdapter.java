package com.example.zhanghao.woaisiji.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.QuestionDetailActivity;
import com.example.zhanghao.woaisiji.bean.AlterResultBean;
import com.example.zhanghao.woaisiji.bean.MemberShipInfosBean;
import com.example.zhanghao.woaisiji.bean.forum.QuestionDetailBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.SpUtils;
import com.example.zhanghao.woaisiji.utils.TimeFormatUtil;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/10/30.
 */
public class ReplyListAdapter extends BaseAdapter {

    private Activity mActivity;
    private int flag;
    public String is_cnyj = null;
    public List<QuestionDetailBean.InfoBean.ReplyBean> replyBeanList;
    public ReplyListAdapter(Activity activity, int flag){
        this.mActivity = activity;
        this.flag = flag;
        replyBeanList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return replyBeanList.size();
    }

    @Override
    public QuestionDetailBean.InfoBean.ReplyBean getItem(int i) {
        return replyBeanList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null){
            view = View.inflate(mActivity, R.layout.adapter_reply_item,null);
            viewHolder = new ViewHolder();
            viewHolder.rivCommentHead  = (RoundImageView) view.findViewById(R.id.riv_comment_head);
            viewHolder.tvCommentNick    = (TextView) view.findViewById(R.id.tv_comment_nick);
            viewHolder.tvCommentContent = (TextView) view.findViewById(R.id.tv_comment_content);
            viewHolder.tvCommentDate    = (TextView) view.findViewById(R.id.tv_comment_date);
            viewHolder.tvCommentCount   = (TextView) view.findViewById(R.id.tv_comment_count);

            viewHolder.btnMyAdoption = (Button) view.findViewById(R.id.btn_my_adoption);
            viewHolder.ll_around = (LinearLayout) view.findViewById(R.id.ll_around);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final QuestionDetailBean.InfoBean.ReplyBean item = getItem(i);

        viewHolder.tvCommentNick   .setText(item.nickname);
        viewHolder.tvCommentContent.setText(item.content);
        viewHolder.tvCommentDate   .setText(TimeFormatUtil.format(item.create_time));
        viewHolder.tvCommentCount  .setText(item.top+"条问答补充");


        viewHolder.ll_around.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(mActivity,is_cnyj,Toast.LENGTH_SHORT).show();
//                Toast.makeText(mActivity,item.status,Toast.LENGTH_SHORT).show();
            }
        });


        if (is_cnyj.equals("0")){//未解决
            viewHolder.btnMyAdoption.setText("采纳");

            if (flag == 1){
                viewHolder.btnMyAdoption.setVisibility(View.VISIBLE);



                viewHolder.btnMyAdoption.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 问题采纳
                        int i = Integer.parseInt(is_cnyj);
                        i++;
                        is_cnyj = String.valueOf(i);

                        adoptionQuestion(item.pid,item.id,item.uid);

                        notifyDataSetChanged();
                        Toast.makeText(mActivity,is_cnyj,Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                viewHolder.btnMyAdoption.setVisibility(View.GONE);
            }
        }else {//已解决
                viewHolder.btnMyAdoption.setText("已解决");
                viewHolder.btnMyAdoption.setEnabled(false);
                viewHolder.btnMyAdoption.setClickable(false);
        }

        ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+item.headpic,viewHolder.rivCommentHead);
        return view;
    }

    private void adoptionQuestion(final String pid, final String hdid, final String hhuid) {
        StringRequest adoptionRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_PROBLEM_ADOPTION_ANSWER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                AlterResultBean alterResultBean = gson.fromJson(response,AlterResultBean.class);

                Log.d("ReplyList",response);
                Toast.makeText(mActivity,alterResultBean.msg,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("uid",hhuid);
                params.put("wtid",pid);
                params.put("hdid",hdid);
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(adoptionRequest);
    }

    static class ViewHolder{
        public RoundImageView rivCommentHead;
        public TextView tvCommentNick;
        public TextView tvCommentContent;
        public TextView tvCommentDate;
        public TextView tvCommentCount;
        public Button btnMyAdoption;
        public LinearLayout ll_around;
    }
}
