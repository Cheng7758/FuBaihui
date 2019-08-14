package com.example.zhanghao.woaisiji.bean.my;

import java.util.List;

/**
 * Created by Cheng on 2019/7/15.
 */

public class CheckBogisticsBean {
    /**
     * success : true
     * reason :
     * data : [{"time":"2019-06-06 13:09:59","context":"【北京市】快件已在【北京通州】签收,签收人:放电井,如有疑问请电联:15896937390/010-61581891,您的快递已经妥投。风里来雨里去,只为客官您满意。上有老下有小,赏个好评好不好？【请在评价快递员处帮忙点亮五颗星星哦~】"},{"time":"2019-06-06 08:35:29","context":"【北京市】【北京通州】的徐攀锦15896937390[15896937390]（15896937390）正在第1次派件,请保持电话畅通,并耐心等待（95720为中通快递员外呼专属号码，请放心接听）"},{"time":"2019-06-06 08:35:28","context":"【北京市】快件已经到达【北京通州】"},{"time":"2019-06-06 04:31:27","context":"【北京市】快件离开【北京】已发往【北京通州】"},{"time":"2019-06-06 02:07:28","context":"【北京市】快件已经到达【北京】"},{"time":"2019-06-04 20:21:12","context":"【金华市】快件离开【金华中转部】已发往【北京】"},{"time":"2019-06-04 20:18:49","context":"【金华市】快件已经到达【金华中转部】"},{"time":"2019-06-04 17:47:35","context":"【金华市】快件离开【金华江南】已发往【北京】"},{"time":"2019-06-04 17:47:23","context":"【金华市】【金华江南】（0579-82022100、0579-82251250、0579-82251585）的沈志良[0579-82251585]（0579-82251585）已揽收"}]
     * status : 6
     */

    private boolean success;
    private String reason;
    private int status;
    private List<DataBean> data;

    @Override
    public String toString() {
        return "CheckBogisticsBean{" +
                "success=" + success +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * time : 2019-06-06 13:09:59
         * context : 【北京市】快件已在【北京通州】签收,签收人:放电井,如有疑问请电联:15896937390/010-61581891,您的快递已经妥投。风里来雨里去,只为客官您满意。上有老下有小,赏个好评好不好？【请在评价快递员处帮忙点亮五颗星星哦~】
         */

        private String time;
        private String context;

        @Override
        public String toString() {
            return "DataBean{" +
                    "time='" + time + '\'' +
                    ", context='" + context + '\'' +
                    '}';
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }
    }
}
