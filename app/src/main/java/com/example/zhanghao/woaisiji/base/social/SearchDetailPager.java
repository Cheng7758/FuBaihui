package com.example.zhanghao.woaisiji.base.social;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhanghao.woaisiji.activity.search.SearchResultsActivity;
import com.example.zhanghao.woaisiji.base.BasePagerDetail;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.view.DialogWheelView;

/**
 * Created by admin on 2016/10/24.
 */
public class SearchDetailPager extends BasePagerDetail implements View.OnClickListener {
    private static final int INFO_SEX = 6;
    private static final int INFO_AGE = 7;
    private static final int INFO_MARRY = 3;
    private static final int INFO_INCOME = 4;
    private static final int INFO_EDU = 5;
    private static final int TYPE_01 = 1;
    private static final int TYPE_02 = 2;
    private LinearLayout llSocialSearchSex;
    private LinearLayout llSocialSearchAge;
    private TextView tvSocialSearchSex;
    private String[] sex = new String[]{"不限", "男", "女"};
    private String[] age = {"18岁以下", "18-22岁", "23-25岁", "26-28岁", "29-35岁", "35岁以上"};
    private TextView tvSocialSearchAge;
    private Button btnSocialSearch;

    private String searchSex = "";
    private String searchAge = "";
    private String searchNickName = "";
    private EditText etSocialSearchQuery;

    public SearchDetailPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.social_pager_search_detail, null);
        llSocialSearchSex = (LinearLayout) view.findViewById(R.id.ll_social_search_sex);//性别栏
        llSocialSearchAge = (LinearLayout) view.findViewById(R.id.ll_social_search_age);//年龄栏
        tvSocialSearchSex = (TextView) view.findViewById(R.id.tv_social_search_sex);//女按钮
        tvSocialSearchAge = (TextView) view.findViewById(R.id.tv_social_search_age);//18岁按钮
        btnSocialSearch = (Button) view.findViewById(R.id.btn_social_search);//查找按钮
        etSocialSearchQuery = (EditText) view.findViewById(R.id.et_social_search_query);//搜索框
        responseClickListener();//listener
        return view;
    }

    private void responseClickListener() {
        llSocialSearchSex.setOnClickListener(this);
        llSocialSearchAge.setOnClickListener(this);
        btnSocialSearch.setOnClickListener(this);
    }

    private void showWheelViewDialog(final int infos) {//WheelView： 竖直滚动选择器
        DialogWheelView dialogWheelView = new DialogWheelView(mActivity, infos);
        dialogWheelView.show();
        dialogWheelView.setSendDataToActivityListener(new DialogWheelView.SendDataToActivityListener() {
            @Override
            public void sendData(String data) {
                switch (infos) {
                    case INFO_SEX:
                        searchSex = data;
                        tvSocialSearchSex.setText(sex[Integer.parseInt(data)]);//女按钮
                        break;
                    case INFO_AGE:
                        searchAge = data;
                        tvSocialSearchAge.setText(age[Integer.parseInt(data)]);//18岁按钮
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_social_search_sex://性别栏
                showWheelViewDialog(INFO_SEX);
                break;
            case R.id.ll_social_search_age://年龄栏
                showWheelViewDialog(INFO_AGE);
                break;
            case R.id.btn_social_search://查找按钮
                searchNickName = etSocialSearchQuery.getText().toString().trim();
                /*Intent intent = new Intent(mActivity, SearchResultActivity.class);
                intent.putExtra("sex", searchSex);
                intent.putExtra("age", searchAge);
                intent.putExtra("nickname", searchNickName);*/
                Intent intent = new Intent(mActivity, SearchResultsActivity.class);
                intent.putExtra("keyword", searchNickName);
                Log.e("----查找会员", searchNickName);
                mActivity.startActivity(intent);
                break;
        }
    }
}
