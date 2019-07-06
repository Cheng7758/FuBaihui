package com.example.zhanghao.woaisiji.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.zhanghao.woaisiji.bean.ChineseAddressBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/8/16.
 */
public class DbUtils {
    private String DB_NAME = "address.db";
    private Context mContext;

    public DbUtils(Context mContext) {
        this.mContext = mContext;
    }

    //把assets目录下的db文件复制到dbpath下
    public SQLiteDatabase DBManager(String packName) {

        String dbPath = "/data/data/" + packName
                + "/files/" + DB_NAME;

        if (!new File(dbPath).exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbPath);
                InputStream in = mContext.getAssets().open("address.db");

                byte[] buffer = new byte[1024];
                int readBytes = 0;
                while ((readBytes = in.read(buffer)) != -1)
                    out.write(buffer, 0, readBytes);
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        return null;
        return SQLiteDatabase.openOrCreateDatabase(dbPath, null);
    }

    //查询
    public void query(SQLiteDatabase sqliteDB, String[] columns, String selection, String[] selectionArgs) {
        List<ChineseAddressBean> chineseAddressList = new ArrayList<ChineseAddressBean>();
        try {
            String table = "province";
            Cursor  cursor = sqliteDB.query(table, columns, selection, selectionArgs, null, null, null);
            while (cursor.moveToNext()) {

             }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        return city;
    }
}
