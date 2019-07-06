package com.example.zhanghao.woaisiji.activity.uploadhead;

/**
 * Created by vgen on 2016/10/12.
 */
public class ImageBeen {

    /**
     * code : 200
     * msg : 上传成功
     * list : {"img1":{"id":"1114","path":"/Uploads/Picture/2016-10-12/57fddd59c1f7c.jpeg","url":"","md5":"a0eb96c4ca0f58602d926aca1e759e2e","sha1":"a2b0fd2eb40d6c786fa8d44d68faa442169aa65e","status":"1","create_time":"1476255065"}}
     * file : {"img1":{"name":"splash.jpeg","type":"image/jpeg","tmp_name":"C:\\Windows\\Temp\\php36FA.tmp","error":0,"size":289002}}
     */

    public int code;
    public String msg;
    /**
     * img1 : {"id":"1114","path":"/Uploads/Picture/2016-10-12/57fddd59c1f7c.jpeg","url":"","md5":"a0eb96c4ca0f58602d926aca1e759e2e","sha1":"a2b0fd2eb40d6c786fa8d44d68faa442169aa65e","status":"1","create_time":"1476255065"}
     */

    public ListBean list;

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

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 1114
         * path : /Uploads/Picture/2016-10-12/57fddd59c1f7c.jpeg
         * url :
         * md5 : a0eb96c4ca0f58602d926aca1e759e2e
         * sha1 : a2b0fd2eb40d6c786fa8d44d68faa442169aa65e
         * status : 1
         * create_time : 1476255065
         */

        public Img1Bean img1;

        public Img1Bean getImg1() {
            return img1;
        }

        public void setImg1(Img1Bean img1) {
            this.img1 = img1;
        }

        public static class Img1Bean {
            public String id;
            public String path;
            public String create_time;

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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
