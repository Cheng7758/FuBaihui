package com.example.zhanghao.woaisiji.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.MyFriendsListBean;
import com.example.zhanghao.woaisiji.friends.Constant;
import com.example.zhanghao.woaisiji.friends.DemoHelper;
import com.example.zhanghao.woaisiji.friends.DemoModel;
import com.example.zhanghao.woaisiji.friends.db.InviteMessgeDao;
import com.example.zhanghao.woaisiji.friends.db.UserDao;
import com.example.zhanghao.woaisiji.friends.runtimepermissions.PermissionsManager;
import com.example.zhanghao.woaisiji.friends.runtimepermissions.PermissionsResultAction;
import com.example.zhanghao.woaisiji.friends.ui.AddContactActivity;
import com.example.zhanghao.woaisiji.friends.ui.BaseActivity;
import com.example.zhanghao.woaisiji.friends.ui.ChatActivity;
import com.example.zhanghao.woaisiji.friends.ui.ContactListFragment;
import com.example.zhanghao.woaisiji.friends.ui.EmLoginActivity;
import com.example.zhanghao.woaisiji.friends.ui.GroupsActivity;
import com.example.zhanghao.woaisiji.friends.ui.NewFriendsMsgActivity;
import com.example.zhanghao.woaisiji.friends.ui.PublicChatRoomsActivity;
import com.example.zhanghao.woaisiji.friends.ui.RobotsActivity;
import com.example.zhanghao.woaisiji.friends.widget.ContactItemView;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.serverdata.FriendsListData;
import com.example.zhanghao.woaisiji.view.DialogLoveDriver;
import com.example.zhanghao.woaisiji.view.PopupMenu;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseContactListFragment;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseContactList;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.NetUtils;
import com.jcodecraeer.xrecyclerview.gold.GoldManager;
import com.jcodecraeer.xrecyclerview.gold.UserManager;
/*import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;*/

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/9/28.
 */
//TODO 好友列表有时候显示有时候不显示
@SuppressLint("NewApi")
public class PersonalFriendsFragment extends EaseContactListFragment {

    private static final String TAG = PersonalFriendsFragment.class.getSimpleName();
    private ContactSyncListener contactSyncListener;
    private BlackListSyncListener blackListSyncListener;
    private ContactInfoSyncListener contactInfoSyncListener;
    //    private View loadingView;
    private InviteMessgeDao inviteMessgeDao;
    private ContactItemView applicationItem;

//    private Map<String, EaseUser> m;

    private ProgressDialog pd ;


    @SuppressLint("InflateParams")
    @Override
    protected void initView() {
        super.initView();

        @SuppressLint("InflateParams") View headerView = LayoutInflater.from(getActivity())
                .inflate(R.layout.activity_personal_friends, null);
        HeaderItemClickListener clickListener = new HeaderItemClickListener();
        applicationItem = (ContactItemView) headerView.findViewById(R.id.application_item);
        applicationItem.setOnClickListener(clickListener);
        listView.addHeaderView(headerView);
        registerForContextMenu(listView);
    }

    @Override
    public void refresh() {
////        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
//        FriendsListData friendsListData = new FriendsListData();
//        friendsListData.obtainFriendsListFromServer();
//        friendsListData.setSendDataListener(new FriendsListData.SendDataListener() {
//            @Override
//            public void sendData(Map<String, EaseUser> userList) {
//                WoAiSiJiApp.m = userList;
//            }
//
//            @Override
//            public void sendStringData(String data) {
//
//            }
//        });
//        Map<String, EaseUser> m = WoAiSiJiApp.m;
//        if (m instanceof Hashtable<?, ?>) {
//            //noinspection unchecked
//            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
//        }
//        setContactsMap(m);
//        super.refresh();
//        if(inviteMessgeDao == null){
//            inviteMessgeDao = new InviteMessgeDao(getActivity());
//        }
//        if(inviteMessgeDao.getUnreadMessagesCount() > 0){
//            applicationItem.showUnreadMsgView();
//        }else{
//            applicationItem.hideUnreadMsgView();
//        }
    }
    private PopupMenu popupMenu;

    @SuppressWarnings("unchecked")
    @Override
    protected void setUpView() {
        titleBar.setRightImageResource(R.drawable.em_add);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), AddContactActivity.class));
                NetUtils.hasDataConnection(getActivity());
            }
        });

        //设置联系人数据
        Map<String, EaseUser> m = DemoHelper.getInstance().getContactList();
        if (m instanceof Hashtable<?, ?>) {
            m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>) m).clone();
        }
        setContactsMap(m);
        super.setUpView();

        //TODO 好友列表 给聊天详情传昵称用  传昵称不能直接给他toChatUsername变了
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                if (user != null) {
                    UserManager.toId = GoldManager.toUserId = user.getUsername();
                    UserManager.toName = GoldManager.toUserName = user.getNickname();
                    UserManager.toPic = GoldManager.toUserPic =  user.getAvatar();
                    Log.e("----pic", user.getAvatar() + "=====uid" + user.getUsername()
                            + "---name" + user.getNick()+"--"+user.getNickname());
                    String username = user.getUsername();
                    String userId = user.getNick();
                    // demo中直接进入聊天页面，实际一般是进入用户详情页
//                    startActivity(new Intent(getActivity(), ChatActivity.class).putExtra("userId", username));
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("userId", username);
//                    intent.putExtra("username", userId);
//                    intent.putExtra("pic", user.getAvatar());
                    startActivity(intent);
                }
            }
        });


        // 进入添加好友页
        titleBar.getRightLayout().setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] abs = new String[]{"消息通知", "添加好友"};
                popupMenu = new PopupMenu(getActivity());
                popupMenu.showAtLocation(getView(), Gravity.RIGHT|Gravity.TOP,0,titleBar.getHeight()+30);
                // 设置回调监听，获取点击事件
                popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {

                    @Override
                    public void onClick(PopupMenu.MENUITEM item) {
//                        Toast.makeText(getActivity(), "aaa", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        contactSyncListener = new ContactSyncListener();
        DemoHelper.getInstance().addSyncContactListener(contactSyncListener);

        blackListSyncListener = new BlackListSyncListener();
        DemoHelper.getInstance().addSyncBlackListListener(blackListSyncListener);

        contactInfoSyncListener = new ContactInfoSyncListener();
        DemoHelper.getInstance().getUserProfileManager().addSyncContactInfoListener(contactInfoSyncListener);

        initDeletePop();

        //请求数据
        initData();
    }

    private void initDeletePop() {
        //getResources().getString(R.string.Delete_failed)
        pd = new ProgressDialog(getActivity());
        pd.setMessage(getResources().getString(R.string.deleting));
        pd.setCanceledOnTouchOutside(false);
    }

    private void initData() {
        FriendsListData friendsListData = new FriendsListData();
        friendsListData.obtainFriendsListFromServer();
        friendsListData.setSendDataListener(new FriendsListData.SendDataListener() {
            @Override
            public void sendData(Map<String, EaseUser> userList) {
                WoAiSiJiApp.m = userList;
                Map<String, EaseUser> m = WoAiSiJiApp.m;
                if (m instanceof Hashtable<?, ?>) {
                    //noinspection unchecked
                    m = (Map<String, EaseUser>) ((Hashtable<String, EaseUser>)m).clone();
                }
                setContactsMap(m);
                if(inviteMessgeDao == null){
                    inviteMessgeDao = new InviteMessgeDao(getActivity());
                }
                if(inviteMessgeDao.getUnreadMessagesCount() > 0){
                    applicationItem.showUnreadMsgView();
                }else{
                    applicationItem.hideUnreadMsgView();
                }
                getContactList();
                contactListLayout.refresh();
            }

            @Override
            public void sendStringData(String data) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (contactSyncListener != null) {
            DemoHelper.getInstance().removeSyncContactListener(contactSyncListener);
            contactSyncListener = null;
        }

        if (blackListSyncListener != null) {
            DemoHelper.getInstance().removeSyncBlackListListener(blackListSyncListener);
        }

        if (contactInfoSyncListener != null) {
            DemoHelper.getInstance().getUserProfileManager().removeSyncContactInfoListener(contactInfoSyncListener);
        }
    }

    protected class HeaderItemClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.application_item:
                    // 进入申请与通知页面
                    startActivity(new Intent(getActivity(), NewFriendsMsgActivity.class));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        toBeProcessUser = (EaseUser) listView.getItemAtPosition(((AdapterView.AdapterContextMenuInfo) menuInfo).position);
        toBeProcessUsername = toBeProcessUser.getUsername();
        getActivity().getMenuInflater().inflate(R.menu.em_context_contact_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete_contact) {
            try {
                // delete contact
                deleteContact(toBeProcessUser);
                // remove invitation message
                InviteMessgeDao dao = new InviteMessgeDao(getActivity());
                dao.deleteMessage(toBeProcessUser.getUsername());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } else if (item.getItemId() == R.id.add_to_blacklist) {
            moveToBlacklist(toBeProcessUsername);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * 删除联系人
     * @param tobeDeleteUser
     */
    public void deleteContact(final EaseUser tobeDeleteUser) {

        pd.show();
        StringRequest deleteFriendsRequest = new StringRequest(Request.Method.POST,
                ServerAddress.URL_DRIVER_DELETE_FRIEND, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                deleteFromEase(tobeDeleteUser);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                pd.dismiss();
                deleteFromEase(tobeDeleteUser);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                /*params.put("uid", ((WoAiSiJiApp) (getActivity().getApplication())).getUid());
                params.put("pid", tobeDeleteUser.getUsername());*/
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("token", WoAiSiJiApp.token);
                params.put("friend_id",tobeDeleteUser.getUsername());
                return params;
            }
        };
        WoAiSiJiApp.mRequestQueue.add(deleteFriendsRequest);
//        new Thread(new Runnable() {
//            public void run() {
//                try {
//                    EMClient.getInstance().contactManager().deleteContact(tobeDeleteUser.getUsername());
//                    EMClient.getInstance().contactManager().aysncDeleteContact();
//                    // remove user from memory and database
//                    UserDao dao = new UserDao(getActivity());
//                    dao.deleteContact(tobeDeleteUser.getUsername());
//                    DemoHelper.getInstance().getContactList().remove(tobeDeleteUser.getUsername());
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            pd.dismiss();
//                            contactList.remove(tobeDeleteUser);
//                            contactListLayout.refresh();
//
//                        }
//                    });
//                } catch (final Exception e) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            pd.dismiss();
//                            Toast.makeText(getActivity(), st2 + e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                    });
//
//                }
//
//            }
//        }).start();
    }

    private void deleteFromEase(final EaseUser tobeDeleteUser){
        EMClient.getInstance().contactManager().aysncDeleteContact(tobeDeleteUser.getUsername(), new EMCallBack() {
            @Override
            public void onSuccess() {
                pd.dismiss();
                // remove user from memory and database
                UserDao dao = new UserDao(getActivity());
                dao.deleteContact(tobeDeleteUser.getUsername());
                DemoHelper.getInstance().getContactList().remove(tobeDeleteUser.getUsername());

                contactList.remove(tobeDeleteUser);
                contactListLayout.refresh();
            }
            @Override
            public void onError(int i, String s) {
                pd.dismiss();
                Toast.makeText(getActivity(), getResources().getString(R.string.Delete_failed)+":"+s,
                        Toast.LENGTH_LONG).show();
            }
            @Override
            public void onProgress(int i, String s) {

            }
        });
    }


    class ContactSyncListener implements DemoHelper.DataSyncListener {
        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contact list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            if (success) {
//                                loadingView.setVisibility(View.GONE);
                                refresh();
                            } else {
                                String s1 = getResources().getString(R.string.get_failed_please_check);
                                Toast.makeText(getActivity(), s1, Toast.LENGTH_LONG).show();
//                                loadingView.setVisibility(View.GONE);
                            }
                        }

                    });
                }
            });
        }
    }

    class BlackListSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(boolean success) {
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    refresh();
                }
            });
        }

    }

    class ContactInfoSyncListener implements DemoHelper.DataSyncListener {

        @Override
        public void onSyncComplete(final boolean success) {
            EMLog.d(TAG, "on contactinfo list sync success:" + success);
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
//                    loadingView.setVisibility(View.GONE);
                    if (success) {
                        refresh();
                    }
                }
            });
        }
    }

}
