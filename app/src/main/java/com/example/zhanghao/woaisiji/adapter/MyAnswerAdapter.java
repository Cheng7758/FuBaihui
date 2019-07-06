package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.QuestionDetailActivity;
import com.example.zhanghao.woaisiji.activity.QuestionsMyQuestionActivity;
import com.example.zhanghao.woaisiji.bean.MemberShipInfosBean;
import com.example.zhanghao.woaisiji.bean.forum.MyAnswerBean;
import com.example.zhanghao.woaisiji.bean.forum.QuestionBean;
import com.example.zhanghao.woaisiji.utils.TimeFormatUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2016/8/10.
 */
public class MyAnswerAdapter extends BaseAdapter {
    private MemberShipInfosBean memberShipInfo;
    public List<MyAnswerBean.ListBean> myAnswerLists;
    private Context context;
    public MyAnswerAdapter(Context context) {
        this.context=context;
        myAnswerLists = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return myAnswerLists.size();
    }

    @Override
    public MyAnswerBean.ListBean getItem(int position) {
        return myAnswerLists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.question_my_answer_item,null);
            viewHolder.tvMyAnswerTitle= (TextView) convertView.findViewById(R.id.tv_my_answer_title);
            viewHolder.tvMyAnswerAdoptStatus= (TextView) convertView.findViewById(R.id.tv_my_answer_adopt_status);
            viewHolder.tvMyAnswerNum= (TextView) convertView.findViewById(R.id.tv_my_answer_num);
            viewHolder.tvMyAnswerTime= (TextView) convertView.findViewById(R.id.tv_my_answer_time);
            viewHolder.ll_my_answer_adopt_status = (LinearLayout) convertView.findViewById(R.id.ll_my_answer_adopt_status);
            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        final MyAnswerBean.ListBean item = getItem(position);
        viewHolder.tvMyAnswerTitle.setText(item.title);
//        viewHolder.tvMyAnswerAdoptStatus.setText("");
        viewHolder.tvMyAnswerNum.setText("回答:"+item.num);
        viewHolder.tvMyAnswerTime.setText(TimeFormatUtil.format(item.create_time));

        memberShipInfo = new MemberShipInfosBean();
        memberShipInfo = WoAiSiJiApp.memberShipInfos;
//        Toast.makeText(context, memberShipInfo.info.getUid() , Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, memberShipInfo.info.uid , Toast.LENGTH_SHORT).show();
//        Toast.makeText(context, memberShipInfo.info.getNickname() , Toast.LENGTH_SHORT).show();

        if (item.is_cnyj.equals("0")){
            viewHolder.tvMyAnswerAdoptStatus.setText("待采纳");
        }else {
            if (item.uid.equals(memberShipInfo.info.uid)){
                viewHolder.tvMyAnswerAdoptStatus.setText("已解决");
                viewHolder.tvMyAnswerAdoptStatus.setTextColor(Color.GREEN);
            }else {
                viewHolder.tvMyAnswerAdoptStatus.setText("已解决");
            }
        }


        viewHolder.ll_my_answer_adopt_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getType_id(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getUid(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.id, Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getIs_cnyj(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getIs_sticky(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getNum(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getPrice(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, item.getStatus(), Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context, QuestionDetailActivity.class);
                intent.putExtra("nickname",memberShipInfo.info.getNickname());
                intent.putExtra("pid",item.id);

//                // 添加标记:去采纳
//                intent.addFlags(1);
                context.startActivity(intent);
            }
        });




//        viewHolder. wenti.setText(items.get(position));
        return convertView;
    }
    static class ViewHolder{
        public TextView tvMyAnswerAdoptStatus;
        public TextView tvMyAnswerNum;
        public TextView tvMyAnswerTime;
        public TextView tvMyAnswerTitle;
        public LinearLayout ll_my_answer_adopt_status;
    }
}
