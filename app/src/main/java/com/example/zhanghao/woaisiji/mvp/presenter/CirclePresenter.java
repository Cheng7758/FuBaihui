package com.example.zhanghao.woaisiji.mvp.presenter;

import android.text.TextUtils;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.CircleItem;
import com.example.zhanghao.woaisiji.bean.CommentConfig;
import com.example.zhanghao.woaisiji.bean.CommentItem;
import com.example.zhanghao.woaisiji.bean.circle.CircleIndexBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.listener.IDataRequestListener;
import com.example.zhanghao.woaisiji.mvp.contract.CircleContract;
import com.example.zhanghao.woaisiji.mvp.modle.CircleModel;
import com.example.zhanghao.woaisiji.resp.RespDriveReviewList;
import com.example.zhanghao.woaisiji.utils.DatasUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: CirclePresenter 
* @Description: 通知model请求服务器和通知view更新
* @author yiw
* @date 2015-12-28 下午4:06:03 
*
 */
public class CirclePresenter implements CircleContract.Presenter{
	private CircleModel circleModel;
	private CircleContract.View view;

	private static final int TYPE_CIRCLE_INDEX = 0;
	private static RespDriveReviewList respDriveReviewList;
	private List<CircleIndexBean.ListBean> circleLists;
	public int pager = 1;

	public CirclePresenter(CircleContract.View view){
		circleModel = new CircleModel();
		this.view = view;
	}

	public void loadData(int loadType){
		// 加载服务器数据
		circleLists = new ArrayList<>();
		pager = 1;
		getDataFromServer(loadType);
	}

	/**
	 * 获取服务器  商城点评数据
	 * @param loadType
	 */
	public void getDataFromServer(final int loadType){
		StringRequest uploadDataRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DRIVER_REVIEW_COMMENT_LIST, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (TextUtils.isEmpty(response))
					return;
				Gson gson = new Gson();
				respDriveReviewList = gson.fromJson(response,RespDriveReviewList.class);
				if (respDriveReviewList.getCode() == 200){
					if (respDriveReviewList.getList() != null){
						List<CircleItem> datas = DatasUtil.createDriverReviewItemListData(respDriveReviewList.getList());
						if(view!=null){
							view.update2loadData(loadType, datas);
						}
					}
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {

			}
		}){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String,String> params = new HashMap<>();
				params.put("uid", WoAiSiJiApp.getUid());
				params.put("p", String.valueOf(pager));
				return params;
			}
		};
		WoAiSiJiApp.mRequestQueue.add(uploadDataRequest);
	}

	/**
	 * 
	* @Title: deleteCircle 
	* @Description: 删除动态 
	* @param  circleId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteCircle(final String circleId){
		circleModel.deleteCircle(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteCircle(circleId);
                }
			}
		});
	}
	/**
	 * 
	* @Title: addFavort 
	* @Description: 点赞
	* @param  circlePosition     
	* @return void    返回类型 
	* @throws
	 */
	public void addFavort(final int circlePosition){
		circleModel.addFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
//				FavortItem item = DatasUtil.createCurUserFavortItem();
                if(view !=null ){
                    view.update2AddFavorite(circlePosition, null);
                }

			}
		});
	}
	/**
	 * 
	* @Title: deleteFavort 
	* @Description: 取消点赞 
	* @param @param circlePosition
	* @param @param favortId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteFavort(final int circlePosition, final String favortId){
		circleModel.deleteFavort(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
                if(view !=null ){
                    view.update2DeleteFavort(circlePosition, favortId);
                }
			}
		});
	}
	
	/**
	 * 
	* @Title: addComment 
	* @Description: 增加评论
	* @param  content
	* @param  config  CommentConfig
	* @return void    返回类型 
	* @throws
	 */
	public void addComment(final String content, final CommentConfig config){
		if(config == null){
			return;
		}
		circleModel.addComment(new IDataRequestListener() {

			@Override
			public void loadSuccess(Object object) {
				CommentItem newItem = null;
				if (config.commentType == CommentConfig.Type.PUBLIC) {
					newItem = DatasUtil.createPublicComment(content);
				} else if (config.commentType == CommentConfig.Type.REPLY) {
					newItem = DatasUtil.createReplyComment(config.replyUser, content);
				}
                if(view!=null){
                    view.update2AddComment(config.circlePosition, newItem);
                }
			}

		});
	}
	
	/**
	 * 
	* @Title: deleteComment 
	* @Description: 删除评论 
	* @param @param circlePosition
	* @param @param commentId     
	* @return void    返回类型 
	* @throws
	 */
	public void deleteComment(final int circlePosition, final String commentId){
		circleModel.deleteComment(new IDataRequestListener(){

			@Override
			public void loadSuccess(Object object) {
                if(view!=null){
                    view.update2DeleteComment(circlePosition, commentId);
                }
			}
			
		});
	}

	/**
	 *
	 * @param commentConfig
	 */
	public void showEditTextBody(CommentConfig commentConfig){
        if(view != null){
            view.updateEditTextBodyVisible(View.VISIBLE, commentConfig);
        }
	}


    /**
     * 清除对外部对象的引用，反正内存泄露。
     */
    public void recycle(){
        this.view = null;
    }
}
