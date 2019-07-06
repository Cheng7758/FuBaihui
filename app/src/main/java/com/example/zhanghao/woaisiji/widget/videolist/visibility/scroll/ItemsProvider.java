package com.example.zhanghao.woaisiji.widget.videolist.visibility.scroll;



import com.example.zhanghao.woaisiji.widget.videolist.visibility.items.ListItem;


public interface ItemsProvider {

    ListItem getListItem(int position);

    int listItemSize();

}
