package com.example.zhanghao.woaisiji.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.activity.QuestionDetailActivity;
import com.example.zhanghao.woaisiji.activity.QuestionsMyQuestionActivity;
import com.example.zhanghao.woaisiji.bean.forum.QuestionBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.utils.TimeFormatUtil;
import com.example.zhanghao.woaisiji.view.RoundImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghao on 2016/8/5.
 */
public class WenDaAdapter extends BaseAdapter {
    public List<QuestionBean.ListBean> questionLists;
    private Context context;

    public WenDaAdapter(Context context) {
        this.context = context;
        questionLists = new ArrayList<>();
//        this.items = items;
    }

    @Override
    public int getCount() {
        return questionLists.size();
    }

    @Override
    public QuestionBean.ListBean getItem(int position) {
        return questionLists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.driver_question_answer_item, null);
            holder.tvQuestionAnswerTitle = (TextView) convertView.findViewById(R.id.tv_question_answer_title);
            holder.tvQuestionAnswerBoins = (TextView) convertView.findViewById(R.id.tv_question_answer_boins);
            holder.tvQuestionAnswerTimes = (TextView) convertView.findViewById(R.id.tv_question_answer_times);
            holder.rivQuestionAnswerHeadpic = (RoundImageView) convertView.findViewById(R.id.riv_question_answer_headpic);
            holder.tvQuestionAnswerNickName = (TextView) convertView.findViewById(R.id.tv_question_answer_nickname);
            holder.tvQuestionAnswerNum = (TextView) convertView.findViewById(R.id.tv_question_answer_num);
            holder.llReplyQuestion = (LinearLayout) convertView.findViewById(R.id.ll_reply_question);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
         final QuestionBean.ListBean item = getItem(position);


//
//        Toast.makeText(context ,item.id,Toast.LENGTH_SHORT).show();
//        Toast.makeText(context ,item.uid,Toast.LENGTH_SHORT).show();
//        Toast.makeText(context ,item.price,Toast.LENGTH_SHORT).show();




        holder.tvQuestionAnswerTitle.setText(item.title);
        holder.tvQuestionAnswerBoins.setText(item.price +"/银A币");
        holder.tvQuestionAnswerNickName.setText(item.nickname);
        holder.tvQuestionAnswerNum.setText(item.num+"人回答");
        holder.tvQuestionAnswerTimes.setText(TimeFormatUtil.format(item.create_time));
        ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT+item.headpic,holder.rivQuestionAnswerHeadpic);
        holder.llReplyQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, QuestionDetailActivity.class);
                intent.putExtra("nickname",item.nickname);
                intent.putExtra("pid",item.id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    static class ViewHolder {
        public TextView tvQuestionAnswerTitle;
        public TextView tvQuestionAnswerBoins;
        public TextView tvQuestionAnswerTimes;
        public RoundImageView rivQuestionAnswerHeadpic;
        public TextView tvQuestionAnswerNickName;
        public TextView tvQuestionAnswerNum;
        public LinearLayout llReplyQuestion;
    }
}
