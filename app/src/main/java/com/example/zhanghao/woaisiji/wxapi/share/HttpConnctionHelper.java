package com.example.zhanghao.woaisiji.wxapi.share;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.MimeTypeMap;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class HttpConnctionHelper {
    HttpURLConnection conn;
    Message message = new Message();
    Bundle bundle = new Bundle();
    int totalsize = 0, uploadsize = 0;
    public Handler handler;
    public String receivedata;

    public HttpConnctionHelper(String url, Handler handler) {
        try {
            Log.v("url", url);
            this.conn = (HttpURLConnection) new URL(url).openConnection();
            this.conn.setConnectTimeout(5000);
            this.totalsize = 0;
            this.uploadsize = 0;
            this.handler = handler;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HttpConnctionHelper post(Map<String, String> stringMap) {
        return this.post(stringMap, null);
    }

    public HttpConnctionHelper post(Map<String, String> stringMap, Map<String, File> fileMap) {
        try {
            message = new Message();
            message.what = 3;
            handler.sendMessage(message);

            String boundary = "-----------wwwrsseasycom";
            this.conn.setDoInput(true);
            this.conn.setUseCaches(false);
            this.conn.setRequestMethod("POST");
            this.conn.setRequestProperty("Connection", "Keep-Alive");
            this.conn.setRequestProperty("Charset", "UTF-8");
            this.conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            DataOutputStream outputStream = new DataOutputStream(this.conn.getOutputStream());
            Set<String> keySet;

            if (stringMap != null) {
                keySet = stringMap.keySet();
                for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                    String name = it.next();

                    String value = stringMap.get(name);
                    outputStream.writeBytes("--" + boundary + "\r\n");
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
                    outputStream.writeBytes("\r\n");
                    outputStream.writeBytes(URLEncoder.encode(value, "utf-8") + "\r\n");
                }
            }
            int count = 0;
            if (fileMap != null) {
                this.conn.setConnectTimeout(60000);
                keySet = fileMap.keySet();
                byte[] buffer = new byte[2048];

                File file;
                FileInputStream fileInputStream = null;
                String name;

                for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                    name = it.next();
                    file = fileMap.get(name);
                    totalsize += file.length();
                }
                bundle.putLong("totalsize", totalsize);
                for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
                    name = it.next();
                    file = fileMap.get(name);
                    fileInputStream = new FileInputStream(file);

                    outputStream.writeBytes("--" + boundary + "\r\n");
                    outputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + URLEncoder.encode(file.getName(), "utf-8") + "\"\r\n");
                    outputStream.writeBytes("Content-Type: " + MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(name.lastIndexOf(".") + 1)) + "\r\n");
                    outputStream.writeBytes("\r\n");

                    while ((count = fileInputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, count);
                        uploadsize += count;
                        bundle.putLong("uploadsize", uploadsize);
                        message = new Message();
                        message.what = 1;
                        message.setData(bundle);
                        handler.sendMessage(message);
                    }
                    fileInputStream.close();
                    outputStream.writeBytes("\r\n");
                }
            }
            outputStream.writeBytes("--" + boundary + "--" + "\r\n");
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public HttpConnctionHelper postxml(String xml) {
        try {
            byte[] xmlbyte = xml.getBytes("UTF-8");

            conn.setDoOutput(true);// 允许输出
            conn.setDoInput(true);
            conn.setUseCaches(false);// 不使用缓存
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(xmlbyte.length));
            conn.setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
            conn.setRequestProperty("X-ClientType", "2");//发送自定义的头信息

            conn.getOutputStream().write(xmlbyte);
            conn.getOutputStream().flush();
            conn.getOutputStream().close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    public String getbody() {
        try {
            InputStream is = this.conn.getInputStream();
            if (this.conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.v("HttpConnctionHelper", this.conn.getResponseCode() + ":" + this.conn.getResponseMessage() + "," + this.conn.getURL());
                return "";
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);

                uploadsize += line.length();

                bundle.putLong("uploadsize", uploadsize);
                message = new Message();
                message.what = 1;
                message.setData(bundle);
                handler.sendMessage(message);
            }
            is.close();
            this.receivedata = sb.toString();

            bundle.putString("receivedata", this.receivedata);

            message = new Message();
            message.what = 2;
            message.setData(bundle);
            handler.sendMessage(message);

            Log.v("receivedata", this.receivedata);

            return this.receivedata;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public Bitmap getimage() {
        try {
            InputStream is = this.conn.getInputStream();
            Log.v("getResponseCode()", this.conn.getResponseCode() + "");
            if (this.conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            is.close();
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }
}
