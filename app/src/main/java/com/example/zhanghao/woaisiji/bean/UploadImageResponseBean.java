package com.example.zhanghao.woaisiji.bean;


import org.xutils.http.annotation.HttpResponse;

import java.util.List;

/**
 * Created by admin on 2016/9/3.
 */
//@HttpResponse(parser = ResultParser.class)
public class UploadImageResponseBean {
    public int code;
    public String msg;
    public FileImg file;
    public class FileImg{
        public ImgBean img1;

        public ImgBean getImg1() {
            return img1;
        }

        public void setImg1(ImgBean img1) {
            this.img1 = img1;
        }
    }
    public class ImgBean{
        public String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public FileImg getFile() {
        return file;
    }

    public void setFile(FileImg file) {
        this.file = file;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
