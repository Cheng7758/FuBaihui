package com.example.zhanghao.woaisiji.friends.ui;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.activity.send.SendGoldActivity;
import com.example.zhanghao.woaisiji.activity.send.SendSilverActivity;
import com.example.zhanghao.woaisiji.friends.Constant;
import com.example.zhanghao.woaisiji.friends.DemoHelper;
import com.example.zhanghao.woaisiji.friends.domain.EmojiconExampleGroupData;
import com.example.zhanghao.woaisiji.friends.domain.RobotUser;
import com.example.zhanghao.woaisiji.friends.widget.ChatRowVoiceCall;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.serverdata.ObtainUserInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.MemberShipInfosBean;
import com.hyphenate.easeui.http.Myserver;
import com.hyphenate.easeui.http.NetManager;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.utils.SetUserInfoUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.emojicon.EaseEmojiconMenu;
import com.hyphenate.util.EasyUtils;
import com.jcodecraeer.xrecyclerview.gold.GoldManager;
import com.jcodecraeer.xrecyclerview.gold.UserManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

//TODO   聊天会话 昵称和头像不显示
public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;
    private static final int ITEM_GOLD = 15;
    private static final int ITEM_SLVIER = 16;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;

    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 18;
    private static final int ITEM_RED_PACKET = 17;
    //end of red packet code
    private static final int RECORDS = 20;

    private static final int MINGPIAN = 92;
    private static final int REQUEST_CODE_SELEST_MINGPIAN = 99;
    private static final int REQUEST_TYPE_RECV_MINPIAN_CALL = 5;
    private static final int REQUEST_TYPE_SENT_MINPIAN_CALL = 6;
    /**
     * if it is chatBot
     */
    private boolean isRobot;
    private String name;
    private Bundle params = null;
    private String userName;
    //当前与聊天的用户id 用户名 头像
    public static String toUID, toNickName, toPic;

    public void setParams(Bundle bundle) {
        this.params = bundle;
        fragmentArgs = bundle;
        if (bundle == null)
            return;
        Map<String, String> params = new HashMap<>();
        final String userId = bundle.getString("userId");
        params.put("uid", userId);
        NetManager.getNetManager().getMyService(Myserver.url)
                .getFriendsBean(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SetUserInfoUtils.Bean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(SetUserInfoUtils.Bean value) {
                        Log.e("-----username",value.toString());

                        if (value.code == 200) {
                            try {
                                JSONObject jsonObject = new JSONObject(value.toString());
                                jsonObject = jsonObject.getJSONObject("data");
                                jsonObject = jsonObject.getJSONObject(userId);

                                    UserManager.toName = GoldManager.toUserName = userName = jsonObject.getString("nickname");
                                    UserManager.toId = GoldManager.toUserId = userId;
                                    UserManager.toPic = GoldManager.toUserPic = jsonObject.getString("headpic");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override   //请求成功
                    public void onComplete() {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setUpView() {
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) {
//            Map<String,RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }

        super.setUpView();
        if ("l8888".equals(toChatUsername)) {
            titleBar.setTitle("福百惠客服");
        } else {
            // 聊天的个人信息
            ObtainUserInfo obtainUserInfo = new ObtainUserInfo();
            obtainUserInfo.getUserInfo(toChatUsername);
            obtainUserInfo.setSendDataListener(new ObtainUserInfo.SendDataListener() {
                @Override
                public void sendStringData(MemberShipInfosBean.MemberInfo data) {
                    Log.e("----nickname", data.nickname);
//                    titleBar.setTitle(data.nickname+"----");
//                    name = (data.nickname);
                    TextView titleText = titleBar.getTitleText();
                    SetUserInfoUtils.setUserInfo(getContext(), params.getString("userId"), titleText, null);
                    name = (ServerAddress.SERVER_ROOT + WoAiSiJiApp.getCurrentUserInfo().getPic());
                }
            });
        }
        // set click listener
//        titleBar.setTitle("zhangyawei");
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
//                    Intent intent = new Intent(getActivity(), PersonalFriendsActivity.class);
//                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        ((EaseEmojiconMenu) inputMenu.getEmojiconMenu()).addEmojiconGroup(EmojiconExampleGroupData.getData());
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
        // inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem("金积分转让", R.drawable.gold_coins_small,
                ITEM_GOLD, extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem("银积分转让", R.drawable.silver_coins_small,
                ITEM_SLVIER, extendMenuItemClickListener);
        //    inputMenu.registerExtendMenuItem("名片", R.drawable.ic_gold, RECORDS, extendMenuItemClickListener);

        //        if(chatType == Constant.CHATTYPE_SINGLE){
//            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
//            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
//        }
//        //聊天室暂时不支持红包功能
//        //red packet code : 注册红包菜单选项
//        if (chatType != Constant.CHATTYPE_CHATROOM) {
////            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
//        }
        //end of red packet code
    }

    //转账完回调发送转账消息
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("------send", data.getDataString());
        if (0 == requestCode) {
            if (0 == resultCode) {
//                intent.putExtra("goldStr", num);
//                intent.putExtra("uid", uid);
//                intent.putExtra("toChatUsername", userid);
//                intent.putExtra("nameTitle2", name);
//                intent.putExtra("nameTitle", "银币转让");
                if (data == null) return;
                Bundle bundle2 = data.getBundleExtra("bundle2");
                String pid = bundle2.getString("toChatUsername");
                Log.d(TAG, "onActivityResult: " + pid);
                String nameTitle = bundle2.getString("nameTitle");
                Log.d(TAG, "onActivityResult: " + nameTitle);

                String goldStr = bundle2.getString("goldStr");
                Log.d(TAG, "onActivityResult: " + goldStr);

                String nameTitle2 = bundle2.getString("nameTitle2");
                Log.d(TAG, "onActivityResult: " + nameTitle2);

                EMMessage message = EMMessage.createTxtSendMessage("转账", pid);
                message.setAttribute("nameTitle", nameTitle);
                message.setAttribute("nameTitle2", "转给"+ UserManager.toName);
                message.setAttribute("goldStr", goldStr);
                message.setAttribute("records", true);
                if (chatType == EaseConstant.CHATTYPE_GROUP) {
                    message.setChatType(EMMessage.ChatType.GroupChat);
                } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                    message.setChatType(EMMessage.ChatType.ChatRoom);
                }
                EMClient.getInstance().chatManager().sendMessage(message);
                messageList.refresh();
            }
        }
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;
                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                /*if (data != null) {
                    int duration = data.getIntExtra("dur", 0);
                    String videoPath = data.getStringExtra("path");
                    File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                    try {
                        FileOutputStream fos = new FileOutputStream(file);
                        Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                        ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                        fos.close();
                        sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }*/
                    break;
                case REQUEST_CODE_SELECT_FILE: //send the file
                    if (data != null) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            sendFileByUri(uri);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        String username = data.getStringExtra("username");

                        inputAtUsername(username, false);
                    }
                    break;
                //red packet code : 发送红包消息到聊天界面
                case REQUEST_CODE_SEND_RED_PACKET:
                    if (data != null) {
//                    sendMessage(RedPacketUtil.createRPMessage(getActivity(), data, toChatUsername));
                    }
                    break;
                //end of red packet code
                default:
                    break;
            }
        }
    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }

    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            EMGroup group = EMClient.getInstance().groupManager().getGroup(toChatUsername);
            if (group == null) {
                Toast.makeText(getActivity(), R.string.gorup_not_found, Toast.LENGTH_SHORT).show();
                return;
            }
            startActivityForResult((new Intent(getActivity(), GroupDetailsActivity.class)
                    .putExtra("groupId", toChatUsername)), REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
            //群聊
            startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class)
                    .putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username) {
        //handling when user click avatar
//        Intent intent = new Intent(getActivity(), UserProfileActivity.class);
//        intent.putExtra("username", username);
//        startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }

    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        //消息框点击事件，demo这里不做覆盖，如需覆盖，return true
        //red packet code : 拆红包页面
        /*if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)){
            RedPacketUtil.openRedPacket(getActivity(), chatType, message, toChatUsername, messageList);
            return true;
        }*/
        //end of red packet code
        return false;
    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
           /* if (action.equals(RedPacketConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
                RedPacketUtil.receiveRedPacketAckMessage(message);
                messageList.refresh();
            }*/
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room在聊天室里没有消息转发
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class))
                        .putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
            /*Intent intent = new Intent(getActivity(), ImageGridActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);*/
                break;
            case ITEM_FILE: //file
                selectFileFromLocal();
                break;
            case ITEM_GOLD: //金积分转让
                Intent intent = new Intent(getActivity(), SendGoldActivity.class);
                intent.putExtra("uid", WoAiSiJiApp.getUid());
                intent.putExtra("uesr_pid", toChatUsername);    //作为转账账号的id
                intent.putExtra("name", name);
                startActivityForResult(intent, 0);
                //startActivity(intent);
                break;
            case ITEM_SLVIER: //银积分转让
                Intent intent2 = new Intent(getActivity(), SendSilverActivity.class);
                intent2.putExtra("uid", WoAiSiJiApp.getUid());
                intent2.putExtra("uesr_pid", toChatUsername);
                intent2.putExtra("name", name);
                startActivityForResult(intent2, 0);
                break;
            case RECORDS:
//                EMMessage message = EMMessage.createTxtSendMessage("分享的扩展消息",toChatUsername);
//                message.setChatType(EMMessage.ChatType.GroupChat);
//                message.setAttribute("nameTitle", "金币转让");
//                message.setAttribute("nameTitle2", toChatUsername);
//                message.setAttribute("goldStr", "1个金币");
//                message.setAttribute("senderid", SPUtils.getString(SPUtils.USER_ID));
//                EMClient.getInstance().chatManager().sendMessage(message);

                EMMessage message = EMMessage.createTxtSendMessage("转账", toChatUsername);
                message.setAttribute("nameTitle", "银积分转让");
//                message.setAttribute("nameTitle2", "转给" + name);
                message.setAttribute("nameTitle2", "转给" + UserManager.toName);
                message.setAttribute("goldStr", "1金积分");
                message.setAttribute("records", true);
                if (chatType == EaseConstant.CHATTYPE_GROUP) {
                    message.setChatType(EMMessage.ChatType.GroupChat);
                } else if (chatType == EaseConstant.CHATTYPE_CHATROOM) {
                    message.setChatType(EMMessage.ChatType.ChatRoom);
                }
                EMClient.getInstance().chatManager().sendMessage(message);
                messageList.refresh();
                break;
            case ITEM_VOICE_CALL:
//            startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
//            startVideoCall();
                break;
            //red packet code : 进入发红包页面
            case ITEM_RED_PACKET:
//            RedPacketUtil.startRedPacketActivityForResult(this, chatType, toChatUsername, REQUEST_CODE_SEND_RED_PACKET);
                break;
            //end of red packet code
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    protected void selectFileFromLocal() {
        Intent intent = null;
        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        } else {
            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        }
        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            Toast.makeText(getActivity(), R.string.not_connect_to_server, Toast.LENGTH_SHORT).show();
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 8;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if (message.getType() == EMMessage.Type.TXT) {
                //voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL
                            : MESSAGE_TYPE_SENT_VOICE_CALL;
                } else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL
                            : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //red packet code : 红包消息和红包回执消息的chat row type
               /* else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
                    //发送红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET : MESSAGE_TYPE_SEND_RED_PACKET;
                } else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    //领取红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET_ACK : MESSAGE_TYPE_SEND_RED_PACKET_ACK;
                }*/
                //end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            if (message.getType() == EMMessage.Type.TXT) {
                // voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)) {
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
                //red packet code : 红包消息和红包回执消息的chat row
               /* else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//发送红包消息
                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RedPacketConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//open redpacket message
                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
                }*/
                //end of red packet code
            }
            return null;
        }
    }

}
