package com.example.zhanghao.woaisiji.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.view.RoundImageView;

//import com.yiw.circledemo.R;

/**
 * Created by admin on 2016/8/25.
 */
public class HeaderViewHolder extends RecyclerView.ViewHolder {
    public RadioGroup rgDynamicList;
    public RadioButton rbShuoShuo;
    public RadioButton rbZhuanFa;
    public RadioButton rbPingLun;
    public RadioButton rbShouCang;
    public RadioButton rbZan;
    public RelativeLayout rlHeadLayout;
    //    public Button btnReleaseDynamic;
    //    public Button btnBack;
    public RoundImageView rivDynamicHeadPic;
    public Button btnDynamicRelease;
    public  Button btnDynamicBack;


    public HeaderViewHolder(View itemView) {
        super(itemView);

//        btnBack = (Button) itemView.findViewById(R.id.btn_back);
//        btnReleaseDynamic = (Button) itemView.findViewById(R.id.btn_release_dynamic);

        rlHeadLayout = (RelativeLayout) itemView.findViewById(R.id.rl_head_layout);
        rgDynamicList = (RadioGroup) itemView.findViewById(R.id.rg_dynamic_list);
        rbShuoShuo = (RadioButton) itemView.findViewById(R.id.rb_shuoshuo);
        rbZhuanFa= (RadioButton) itemView.findViewById(R.id.rb_zhuanfa);
        rbPingLun= (RadioButton) itemView.findViewById(R.id.rb_pinglun);
        rbShouCang= (RadioButton) itemView.findViewById(R.id.rb_shoucang);
        rbZan= (RadioButton) itemView.findViewById(R.id.rb_zan);

        rivDynamicHeadPic = (RoundImageView) itemView.findViewById(R.id.riv_dynamic_head_pic);
        btnDynamicRelease = (Button) itemView.findViewById(R.id.btn_dynamic_release);
        btnDynamicBack = (Button) itemView.findViewById(R.id.btn_dynamic_back);
    }

}
