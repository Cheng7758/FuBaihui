package com.example.zhanghao.woaisiji.view;


import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.example.zhanghao.woaisiji.R;

/**
 * ten
 * 2019/5/14
 * 加载更多处理
 */
public class BaseQuickLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_base_quick_load_more;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.ll_base_quick_loading_root;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.ll_base_quick_load_fail_root;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.fl_base_quick_load_more_load_end_view;
    }

}
