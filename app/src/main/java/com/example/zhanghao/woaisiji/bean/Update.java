package com.example.zhanghao.woaisiji.bean;

public class Update {

    /**
     * info : {"app_id":"1","version_name":"1.3.3","type":"1","apk_url":"http://sj.qq.com/myapp/detail.htm?apkName=com.example.zhanghao.woaisiji","upgrade_point":"亲爱的用户,版本更新到v1.3.3了,修复了较多的BUG,让您体验更加流畅."}
     * code : 200
     * msg : 发现新版本
     */

    private InfoBean info;
    private int code;
    private String msg;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
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

    public static class InfoBean {
        /**
         * app_id : 1
         * version_name : 1.3.3
         * type : 1
         * apk_url : http://sj.qq.com/myapp/detail.htm?apkName=com.example.zhanghao.woaisiji
         * upgrade_point : 亲爱的用户,版本更新到v1.3.3了,修复了较多的BUG,让您体验更加流畅.
         */

        private String app_id;
        private String version_name;
        private String type;
        private String apk_url;
        private String upgrade_point;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public String getVersion_name() {
            return version_name;
        }

        public void setVersion_name(String version_name) {
            this.version_name = version_name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }

        public String getUpgrade_point() {
            return upgrade_point;
        }

        public void setUpgrade_point(String upgrade_point) {
            this.upgrade_point = upgrade_point;
        }
    }
}
