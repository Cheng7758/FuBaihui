/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.zhanghao.woaisiji.friends.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.zhanghao.woaisiji.R;
import com.example.zhanghao.woaisiji.WoAiSiJiApp;
import com.example.zhanghao.woaisiji.bean.AddFriendBean;
import com.example.zhanghao.woaisiji.bean.SocialPagerStickyBean;
import com.example.zhanghao.woaisiji.friends.DemoHelper;
import com.example.zhanghao.woaisiji.global.ServerAddress;
import com.example.zhanghao.woaisiji.refresh.BaseListView;
import com.example.zhanghao.woaisiji.refresh.PullToRefreshLayout;
import com.google.gson.Gson;
import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
/*import com.hyphenate.chatuidemo.DemoHelper;
import com.hyphenate.chatuidemo.R;*/
import com.hyphenate.chat.EMContactManager;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddContactActivity extends BaseActivity {
    private EditText editText;

    private TextView nameText;
    private Button searchBtn;
    private String toAddUsername;
    private ProgressDialog progressDialog;
    private static final int INFO_SEARCH = 0;

    private AddFriendBean addFriendBean;
    private List<AddFriendBean.DataBean> dataBeans;
    private int pager = 1;
    private boolean isFirst = true;
    private BaseListView blvSocialSearchResult;
    private PullToRefreshLayout refreshView;
    private MemberInfoListAdapter memberInfoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_add_contact);
        TextView mTextView = (TextView) findViewById(R.id.add_list_friends);

        editText = (EditText) findViewById(R.id.edit_note);
        String strAdd = getResources().getString(R.string.add_friend);
        mTextView.setText(strAdd);
        String strUserName = getResources().getString(R.string.user_name);
        editText.setHint(strUserName);

//		nameText = (TextView) findViewById(R.id.name);
        searchBtn = (Button) findViewById(R.id.search);
        blvSocialSearchResult = (BaseListView) findViewById(R.id.lv_social_search_result);
    }


    /**
     * search contact
     * @param v
     */
    public void searchContact(View v) {
        final String name = editText.getText().toString();
        String saveText = searchBtn.getText().toString();

        if (getString(R.string.button_search).equals(saveText)) {
            toAddUsername = name;
            if (TextUtils.isEmpty(name)) {
                new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
                return;
            }

            // TODO you can search the user from your app server here.
            memberInfoListAdapter = new MemberInfoListAdapter();
            dataBeans = new ArrayList<>();
            blvSocialSearchResult.setAdapter(memberInfoListAdapter);
            getDataFromServer(toAddUsername);
            //show the userame and add button if user exist
//			searchedUserLayout.setVisibility(View.VISIBLE);
//			nameText.setText(toAddUsername);
        }
    }

    /**
     *  add contact
     * @param
     */
    public void addContact(final String addId, String nameText) {
//		if(EMClient.getInstance().getCurrentUser().equals(nameText)){
////			Log.d("CurrentUser",EMClient.getInstance().getCurrentUser());
//			new EaseAlertDialog(this, R.string.not_add_myself).show();
//			return;
//		}
        if (!TextUtils.isEmpty(addId) && WoAiSiJiApp.getUid().equals(addId)) {
//			Log.d("CurrentUser",EMClient.getInstance().getCurrentUser());
            new EaseAlertDialog(this, R.string.not_add_myself).show();
            return;
        }

//		if(DemoHelper.getInstance().getContactList().containsKey(nameText)){
        if (WoAiSiJiApp.m.containsKey(addId)) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(nameText)) {
                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();
                return;
            }
            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {
                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);
                    EMClient.getInstance().contactManager().addContact(addId, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);
                            Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }).start();
    }

    public void back(View v) {
        finish();
    }


    private void getDataFromServer(final String toAddUsername) {
        // 获取服务器数据
        StringRequest stickyRequest = new StringRequest(Request.Method.POST, ServerAddress.URL_DRIVER_FRIEND,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        transServerData(response, INFO_SEARCH);
                        if (addFriendBean.getCode() == 200) {
                            dataBeans.clear();
                            if (addFriendBean.getData() != null) {
                                for (AddFriendBean.DataBean bean : addFriendBean.getData()) {
                                    dataBeans.add(bean);
                                }
                            } else {
                                Toast.makeText(AddContactActivity.this, "数据已经加载完了!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (memberInfoListAdapter != null)
                        memberInfoListAdapter.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddContactActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uid", WoAiSiJiApp.getUid());
                params.put("token", WoAiSiJiApp.token);
                params.put("keyword", toAddUsername);
                //params.put("nickname", toAddUsername);
                return params;
            }
        };

        WoAiSiJiApp.mRequestQueue.add(stickyRequest);
    }

    private void transServerData(String response, int type) {
        Gson gson = new Gson();
        switch (type) {
            case INFO_SEARCH:
                addFriendBean = gson.fromJson(response, AddFriendBean.class);
                break;
//			case ADDRESS_INFO:
//				memberAddress = gson.fromJson(response, MemberAddressBean.class);
//				break;
        }
    }

    class MemberInfoListAdapter extends BaseAdapter {
        private DisplayImageOptions options;

        public MemberInfoListAdapter() {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.loading)
                    .showImageForEmptyUri(R.drawable.ic_empty)
                    .showImageOnFail(R.drawable.load_failed)
                    .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        @Override
        public int getCount() {
            return dataBeans.size();
        }

        @Override
        public AddFriendBean.DataBean getItem(int i) {
            return dataBeans.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = View.inflate(AddContactActivity.this, R.layout.add_contact_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.ivAvatar = (ImageView) view.findViewById(R.id.avatar);
                viewHolder.tvName = (TextView) view.findViewById(R.id.name);
                viewHolder.btnIndicator = (Button) view.findViewById(R.id.indicator);
                viewHolder.searchedUserLayout = (RelativeLayout) view.findViewById(R.id.ll_user);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            final AddFriendBean.DataBean item = getItem(i);
            ImageLoader.getInstance().displayImage(ServerAddress.SERVER_ROOT + item.getHeadpic(), viewHolder.ivAvatar, options, null);
            viewHolder.tvName.setText(item.getNickname());
            viewHolder.searchedUserLayout.setVisibility(View.VISIBLE);
            viewHolder.btnIndicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addContact(item.getUid(), item.getNickname());
                }
            });
            return view;
        }
    }

    static class ViewHolder {
        public ImageView ivAvatar;
        public TextView tvName;
        public Button btnIndicator;
        private RelativeLayout searchedUserLayout;
    }
}
