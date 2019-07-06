package com.example.zhanghao.woaisiji.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.DetailsBean;
import com.example.zhanghao.woaisiji.fragment.WenZiDetails;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ExchangeCommentActivity extends AppCompatActivity {

    private Button back;
    private ListView listView;
    private List< DetailsBean.PllistBean> CommentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        initView();
        initData();
    }


    private void initView() {
        //后退
        back = (Button) findViewById(R.id.btn_back);
        //评论
        listView = (ListView) findViewById(R.id.lv_whole_comment);
    }
    private void initData() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //获取请求id
        Intent intent=getIntent();
        final String id=intent.getStringExtra(WenZiDetails.ID);
        //网络请求
        StringRequest stringRequest=new StringRequest(Request.Method.POST, ServerAddress.URL_EXCHANGE_DETAILS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                CommentList=new ArrayList<>();
                DetailsBean bean=gson.fromJson(response,DetailsBean.class);
                if(bean.getCode()==200) {
                    for (int i = 0; i < bean.getPllist().size();i++) {
                        DetailsBean.PllistBean pllistBean = bean.getPllist().get(i);
                        CommentList.add(pllistBean);
                    }
                    listView.setAdapter(new CommentAdapter(CommentList));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(),"服务器获取评论失败",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String ,String > map=new ArrayMap<>();
                map.put("id",id);
                return map;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }


    class CommentAdapter extends BaseAdapter{
        private List<DetailsBean.PllistBean> commentList;
        public CommentAdapter(List<DetailsBean.PllistBean> commentList) {
            this.commentList=commentList;
        }

        @Override
        public int getCount() {
            return commentList.size();
        }

        @Override
        public Object getItem(int position) {
            return commentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView==null){
                viewHolder=new ViewHolder();
                convertView= LayoutInflater.from(ExchangeCommentActivity.this).inflate(R.layout.productdetail_itme_comment,null);
                viewHolder.comment= (TextView) convertView.findViewById(R.id.tv_pinglun);
                viewHolder.name= (TextView) convertView.findViewById(R.id.te_nickname);
                viewHolder.portrait= (RoundImageView) convertView.findViewById(R.id.iv_pingjiaportrait);
                viewHolder.riqi= (TextView) convertView.findViewById(R.id.tv_riqi);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            viewHolder.name.setText(commentList.get(position).getUser_name());
            viewHolder.comment.setText(commentList.get(position).getContent());
            viewHolder.riqi.setText(times(commentList.get(position).getCtime()));
            String url= ServerAddress.SERVER_ROOT+commentList.get(position).getStarts();
            ImageLoader.getInstance().displayImage(url,viewHolder.portrait);
            return convertView;
        }
        public String times(String time) {
            SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日");
            @SuppressWarnings("unused")
            long lcc = Long.valueOf(time);
            int i = Integer.parseInt(time);
            String times = sdr.format(new Date(i * 1000L));
            return times;

        }
    }
    static class ViewHolder{
        //头像
        public RoundImageView portrait;
        //昵称
        public TextView name;
        //评论 comment
        public TextView comment;
        //时间
        public TextView riqi;

    }
}
