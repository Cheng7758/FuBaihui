package com.example.zhanghao.woaisiji.base.social;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.base.BasePagerDetail;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.PersonalInfoDetailActivity;
import com.example.zhanghao.woaisiji.bean.SocialPagerStickyBean;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/26.
 */
public class FateDetailPager extends BasePagerDetail implements View.OnClickListener{


    private ImageView ivSocialDetailImage;
    private Button btnSocialFate;
    private Button btnSocialNextMember;
    private TextView tvSocialInfo01;
    private TextView tvSocialInfo02;
    private DisplayImageOptions options;

    private int position = 0;
    private int pager = 1;

    private SocialPagerStickyBean socialPagerSticky;
    private List<SocialPagerStickyBean.SocialList> socialLists;
    private RelativeLayout rlFateDetail;

    public FateDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.social_pager_fate_detail,null);

        rlFateDetail = (RelativeLayout) view.findViewById(R.id.rl_fate_detail);

        ivSocialDetailImage = (ImageView) view.findViewById(R.id.iv_social_detail_image);
        btnSocialFate = (Button) view.findViewById(R.id.btn_social_fate);
        btnSocialNextMember = (Button) view.findViewById(R.id.btn_social_next_member);
        tvSocialInfo01 = (TextView) view.findViewById(R.id.tv_social_info01);
        tvSocialInfo02 = (TextView) view.findViewById(R.id.tv_social_info02);
        return view;
    }

    @Override
    public void initData() {
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.loading)
                .showImageForEmptyUri(R.drawable.ic_empty)
                .showImageOnFail(R.drawable.load_failed)
                .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        responseClickListener();
        // 获取服务器会员信息
        socialLists = new ArrayList<>();
        getDataFromServer();
//        setData();
    }

    private void getDataFromServer() {
        // 获取服务器数据
        StringRequest stickyRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DRIVER_SOCIAL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                if (socialPagerSticky.code == 200) {
                    if (socialPagerSticky.list!= null){
                        for (SocialPagerStickyBean.SocialList bean : socialPagerSticky.list){
                            socialLists.add(bean);
                        }
                        rlFateDetail.setVisibility(View.VISIBLE);
                        setData();
                    }else {
                        if (pager == 1){
                            rlFateDetail.setVisibility(View.GONE);
                            Toast.makeText(mActivity,"暂无缘分推荐",Toast.LENGTH_SHORT).show();
                        }

                    }

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
                params.put("is_sticky", "" + 1);
                params.put("p", String.valueOf(pager));
                /*switch (type){
                    case TYPE_FATE:
                        params.put("is_sticky", "" + 1);
                        break;
                    case TYPE_FEMALE_MEM:
                        params.put("sex","2");
                        break;
                    case TYPE_MALE_MEM:
                        params.put("sex","1");
                        break;
                }*/
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(stickyRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        socialPagerSticky = gson.fromJson(response, SocialPagerStickyBean.class);
    }

    private void setData() {
        SocialPagerStickyBean.SocialList socialItem = socialLists.get(position);
        String info01 = socialItem.nickname + "   "+socialItem.age;
        String info02 = "身高:"+socialItem.height ;
        if (socialItem.detail != null){
            info02 = info02 + socialItem.detail.degree;
        }
        tvSocialInfo01.setText(info01);
        tvSocialInfo02.setText(info02);
        if (socialItem.headpic != null){
            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + socialItem.headpic, ivSocialDetailImage, options, null);
        }
    }

    private void responseClickListener() {
        btnSocialNextMember.setOnClickListener(this);
        btnSocialFate.setOnClickListener(this);
        ivSocialDetailImage.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_social_next_member:
                position++;
                if (position>=socialLists.size()){
                    Toast.makeText(mActivity,"这已经是最后一位了",Toast.LENGTH_SHORT).show();
                    position--;
                }else{
                    if (position == socialLists.size()-2){
                        pager ++ ;
//                        Toast.makeText(mActivity,""+socialLists.size(),Toast.LENGTH_SHORT).show();
                        // 获取服务器会员信息
                        getDataFromServer();
                    }
                    setData();
                }

                break;
            case R.id.btn_social_fate:
                SocialPagerStickyBean.SocialList socialList = socialLists.get(position);
                String username = socialList.name;
                // demo中直接进入聊天页面，实际一般是进入用户详情页
                Intent userId = new Intent(mActivity, ChatActivity.class)
                        .putExtra("userId", socialList.uid)
                        .putExtra("username",username)
                        .putExtra("pic",socialList.headpic)
                        ;
                mActivity.startActivity(userId);
                break;
            case R.id.iv_social_detail_image:
                Intent intent = new Intent(mActivity, PersonalInfoDetailActivity.class);
                intent.putExtra("uid", socialLists.get(position).uid);
                mActivity.startActivity(intent);
                break;
        }
    }
}
