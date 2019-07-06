package com.example.zhanghao.woaisiji.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhanghao.woaisiji.R;

/**
 * Created by admin on 2016/8/18.
 */
public class PersonalSelectRegionActivity extends Activity {
    private ListView lvRegionList;
    private String[] address = {"北京","安徽","福建","甘肃","北京","安徽","福建","甘肃","北京","安徽","福建","甘肃","北京","安徽","福建","甘肃","北京","安徽","福建","甘肃"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_select_region);
        lvRegionList = (ListView) findViewById(R.id.lv_region_list);
        lvRegionList.setAdapter(new RegionAdapter());
    }

    class RegionAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return address.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            if(view == null){
                view = View.inflate(PersonalSelectRegionActivity.this,R.layout.personal_region_list_item,null);
                viewHolder = new ViewHolder();
                viewHolder.tvRegionName = (TextView) view.findViewById(R.id.tv_region_name);
                viewHolder.ibSelector = (ImageButton) view.findViewById(R.id.ib_selector);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) view.getTag();
            }
            final String name = address[i];
            viewHolder.tvRegionName.setText(address[i]);
            viewHolder.ibSelector.setImageResource(R.drawable.iv_enter);
            viewHolder.ibSelector.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(PersonalSelectRegionActivity.this,"点击了"+name+"选项",Toast.LENGTH_SHORT).show();
                }
            });
            return view;
        }
    }

    static class ViewHolder{
        public TextView tvRegionName;
        public ImageButton ibSelector;
    }
}
