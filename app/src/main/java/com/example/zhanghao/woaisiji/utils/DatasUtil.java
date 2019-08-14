package com.example.zhanghao.woaisiji.utils;

import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.CircleItem;
import com.example.zhanghao.woaisiji.bean.CommentItem;
import com.example.zhanghao.woaisiji.bean.FavortItem;
import com.example.zhanghao.woaisiji.bean.User;
import com.example.zhanghao.woaisiji.bean.circle.CircleIndexBean;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.resp.RespDriveReviewList;
import com.example.zhanghao.woaisiji.tools.TimeUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatasUtil {

//    public static List<User> users = new ArrayList<User>();
    /**
     * 动态id自增长
     */
    private static int circleId = 0;
    /**
     * 点赞id自增长
     */
    private static int favortId = 0;
    /**
     * 评论id自增长
     */
    public static User curUser = null;

    public static List<CircleItem> createCircleDatas(List<CircleIndexBean.ListBean> list) {
        List<CircleItem>circleDatas = new ArrayList<>();
        if (list!=null && list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                CircleIndexBean.ListBean bean = list.get(i);
                CircleItem item = new CircleItem();
                User user = new User(bean.getUid(), bean.getNickname(), ServerAddress.SERVER_ROOT + bean.getHeadpic());
                item.setId(bean.getId());
                item.setUser(user);
                item.setContent(bean.getContent());
                item.setCreateTime(TimeUtils.timesTwo(list.get(i).getCreate_time()));
                item.setFavorNum(bean.getLike_num());
                item.setFavortId(bean.getId());
                if (bean.commentList != null) {
                    item.setComments(createCommentItemList(bean.commentList));
                }else {
                    List<CommentItem> items = new ArrayList<CommentItem>();
                    item.setComments(items);
                }
                int type = 0;
                if (bean.getPicture() != null) {
                    type = 1;
                }

                if (type == 0) {
                    item.setType("1");// 链接
                } else if (type == 1) {
                    item.setType("2");// 图片
                    item.setPhotos(createPhotos(bean.getPicture()));
                }
                circleDatas.add(item);
            }
        }
        return circleDatas;
    }

    public static int getRandomNum(int max) {
        Random random = new Random();
        int result = random.nextInt(max);
        return result;
    }

    public static List<String> createPhotos(List<String> picture) {
        List<String> photos = new ArrayList<String>();
        int size = picture.size();
        if (size > 0) {
            if (size > 9) {
                size = 9;
            }
            for (int i = 0; i < size; i++) {
                String photo = ServerAddress.SERVER_ROOT + picture.get(i);
                if (!photos.contains(photo)) {
                    photos.add(photo);
                } else {
                    i--;
                }
            }
        }
        return photos;
    }

    public static List<FavortItem> createFavortItemList() {
        int size =2;
        List<FavortItem> items = new ArrayList<FavortItem>();
        List<String> history = new ArrayList<String>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                FavortItem newItem = createFavortItem();
                String userid = newItem.getUser().getId();
                if (!history.contains(userid)) {
                    items.add(newItem);
                    history.add(userid);
                } else {
                    i--;
                }
            }
        }
        return items;
    }

    public static FavortItem createFavortItem() {
        FavortItem item = new FavortItem();
        item.setId(String.valueOf(favortId++));
//        item.setUser(getUser());
        return item;
    }

    public static FavortItem createCurUserFavortItem() {
        FavortItem item = new FavortItem();
        item.setId(String.valueOf(favortId++));
//		item.setUser(curUser);
        return item;
    }

    public static List<CommentItem> createCommentItemList(List<CircleIndexBean.ListBean.CommentListBean> commentList) {
        List<CommentItem> items = new ArrayList<CommentItem>();
        int size = commentList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items.add(createComment(commentList.get(i)));
            }
        }
        return items;
    }

    public static CommentItem createComment(CircleIndexBean.ListBean.CommentListBean commentListBean) {
        CommentItem item = new CommentItem();
//		item.setId(String.valueOf(commentId++));
        item.setCid(commentListBean.cid);
        item.setHeadpic(commentListBean.headpic);
        item.setNickname(commentListBean.nickname);
        item.setUid(commentListBean.uid);
        item.setContent(commentListBean.content);
        User user = new User(commentListBean.cid, commentListBean.nickname, ServerAddress.SERVER_ROOT + commentListBean.headpic);
        item.setUser(user);

        // 评论回复功能，暂时不管
		/*if (getRandomNum(10) % 2 == 0) {
			while (true) {
				User replyUser = getUser();
				if (!user.getId().equals(replyUser.getId())) {
					item.setToReplyUser(replyUser);
					break;
				}
			}
		}*/
        return item;
    }

    /**
     * 创建发布评论
     *
     * @return
     */
    public static CommentItem createPublicComment(final String content) {
        CommentItem item = new CommentItem();
//		item.setCid(String.valueOf(commentId++));
//		item.setCid();
        item.setContent(content);
        item.setUser(curUser());
        return item;
    }

    private static User curUser() {
        if (curUser == null)
            curUser = new User(WoAiSiJiApp.getUid(), WoAiSiJiApp.getCurrentUserInfo().getNickname(), WoAiSiJiApp.getCurrentUserInfo().getPic());
        return curUser;
    }

    /**
     * 创建回复评论
     *
     * @return
     */
    public static CommentItem createReplyComment(User replyUser, String content) {
        CommentItem item = new CommentItem();
//		item.setId(String.valueOf(commentId++));
        item.setContent(content);
        item.setUser(curUser());
        item.setToReplyUser(replyUser);
        return item;
    }


    public static CircleItem createVideoItem(String videoUrl, String imgUrl) {
        CircleItem item = new CircleItem();
        item.setId(String.valueOf(circleId++));
        item.setUser(curUser());
        //item.setContent(getContent());
        item.setCreateTime("12月24日");

        //item.setFavorters(createFavortItemList());
        //item.setComments(createCommentItemList());
        item.setType("3");// 图片
        item.setVideoUrl(videoUrl);
        item.setVideoImgUrl(imgUrl);
        return item;
    }
















    public static List<CircleItem> createDriverReviewItemListData(List<RespDriveReviewList.DriveReviewListBean> list) {
        List<CircleItem> datas = new ArrayList<>();
        if (list!=null && list.size() > 0){
            for (int i = 0; i < list.size(); i++) {
                RespDriveReviewList.DriveReviewListBean bean = list.get(i);
                CircleItem item = new CircleItem();
                User user = new User(bean.getUid(), bean.getNickname(), ServerAddress.SERVER_ROOT + bean.getHeadpic());
                item.setId(bean.getId());
                item.setUser(user);
                item.setContent(bean.getContent());
                item.setCreateTime(TimeUtils.timesTwo(list.get(i).getCreate_time()));
                item.setFavorNum(bean.getLike_num());
                item.setFavortId(bean.getId());
                if (bean.commentList != null) {
                    item.setComments(createNewCommentItemList(bean.commentList));
                }else {
                    List<CommentItem> items = new ArrayList<CommentItem>();
                    item.setComments(items);
                }
                int type = 0;
                if (bean.getPicture() != null) {
                    type = 1;
                }

                if (type == 0) {
                    item.setType("1");// 链接
                } else if (type == 1) {
                    item.setType("2");// 图片
                    item.setPhotos(createPhotos(bean.getPicture()));
                }
                datas.add(item);
            }
        }
        return datas;
    }
    public static List<CommentItem> createNewCommentItemList(List<RespDriveReviewList.DriveReviewCommentListBean> commentList) {
        List<CommentItem> items = new ArrayList<CommentItem>();
        int size = commentList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items.add(createNewComment(commentList.get(i)));
            }
        }
        return items;
    }
    public static CommentItem createNewComment(RespDriveReviewList.DriveReviewCommentListBean commentListBean) {
        CommentItem item = new CommentItem();
//		item.setId(String.valueOf(commentId++));
        item.setCid(commentListBean.cid);
        item.setHeadpic(commentListBean.headpic);
        item.setNickname(commentListBean.nickname);
        item.setUid(commentListBean.uid);
        item.setContent(commentListBean.content);
        User user = new User(commentListBean.cid, commentListBean.nickname, ServerAddress.SERVER_ROOT + commentListBean.headpic);
        item.setUser(user);
        return item;
    }
}
