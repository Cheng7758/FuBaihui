package com.example.zhanghao.woaisiji.activity.send;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.AutoServiceBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinAutoFragment extends Fragment {


    @InjectView(R.id.tv_01)
    TextView tv01;
    @InjectView(R.id.tv_02)
    TextView tv02;
    @InjectView(R.id.tv_03)
    TextView tv03;
    private int code;
    private AutoServiceBean autoServiceBean;
    MyItemClickListener listener;

    public JoinAutoFragment() {
        // Required empty public constructor
    }

    public void setListener(MyItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_join_auto, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void fenleiList() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_GPS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                transServerData(response);
                Gson gson = new Gson();
                autoServiceBean = gson.fromJson(response, AutoServiceBean.class);
                if (autoServiceBean == null) {
                    Log.d("商品详情获取服务器废了", "" + response);
                } else {
                    JSONArray array = (JSONArray) autoServiceBean.getCategory();


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
                params.put("uid", ((WoAiSiJiApp)(getActivity().getApplication())).getUid());

                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(stringRequest);
    }

    private void transServerData(String response) {
        Gson gson = new Gson();
        autoServiceBean = gson.fromJson(response, AutoServiceBean.class);
    }

    public void setDifferentUse(String tag) {
        switch (tag) {
            case "frist":
                tv01.setText("全部");
                tv02.setText("汽修厂");
                tv03.setText("加油站");
                code = 1;
                break;
            case "sec":
                tv01.setText("智能排序");
                tv02.setText("离我最近");
                tv03.setText("人气最高");
                code = 2;
                break;
            case "third":
                tv01.setText("全部");
                tv02.setText("旗舰店");
                tv03.setText("加盟店");
                code = 3;
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.tv_01, R.id.tv_02, R.id.tv_03})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_01:
                String tv1 = String.valueOf(tv01.getText());
                listener.onItemClick(view, 1, tv1, code);
                break;
            case R.id.tv_02:
                String tv2 = String.valueOf(tv02.getText());
                listener.onItemClick(view, 2, tv2, code);
                break;
            case R.id.tv_03:
                String tv3 = String.valueOf(tv03.getText());
                listener.onItemClick(view, 3, tv3, code);
                break;
        }
    }

}
