package com.example.zhanghao.woaisiji.resp;

import java.util.List;

/**
 * Created by zhanghao on 2016/10/13.
 */
public class RespHomePagerJXGG extends RespBase{

    public ContentData data;

    public ContentData getData() {
        return data;
    }
    public void setData(ContentData data) {
        this.data = data;
    }
    public static class ContentData{
        public List<JingXuan> jingxuan;
        public List<GongGao> gonggao;

        public List<JingXuan> getJingxuan() {
            return jingxuan;
        }

        public void setJingxuan(List<JingXuan> jingxuan) {
            this.jingxuan = jingxuan;
        }

        public List<GongGao> getGonggao() {
            return gonggao;
        }

        public void setGonggao(List<GongGao> gonggao) {
            this.gonggao = gonggao;
        }
    }
    /**
     * 精选
     */
    public static class JingXuan{
        private String id;
        private String path;

        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }
        public void setPath(String path) {
            this.path = path;
        }
    }

    /**
     * 公獒
     */
    public static class GongGao{
        private String nid;
        private String title;

        public String getNid() {
            return nid;
        }
        public void setNid(String nid) {
            this.nid = nid;
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
    }

}
