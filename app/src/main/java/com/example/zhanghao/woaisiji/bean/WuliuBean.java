package com.example.zhanghao.woaisiji.bean;

import java.util.List;

public class WuliuBean {

    /**
     * info : {"LogisticCode":"486118218996","ShipperCode":"ZTO","Traces":[{"AcceptStation":"[北京市]快件已在北京丰台三路居签收签收人：本人,感谢您使用中通快递，期待再次为您服务!","AcceptTime":"2018-04-0313:21:49"},{"AcceptStation":"[北京市]北京丰台三路居的路建亚18510200220[18510200220]正在派件","AcceptTime":"2018-04-0308:00:58"},{"AcceptStation":"[北京市]快件已到达北京丰台三路居","AcceptTime":"2018-04-0307:09:19"},{"AcceptStation":"[北京市]快件离开北京已发往北京丰台三路居","AcceptTime":"2018-04-0215:15:36"},{"AcceptStation":"[广州市]快件离开广州中心已发往北京","AcceptTime":"2018-04-0102:12:42"},{"AcceptStation":"[广州市]快件已到达广州中心","AcceptTime":"2018-04-0102:11:39"},{"AcceptStation":"[广州市]快件离开广州广园已发往北京","AcceptTime":"2018-04-0100:10:42"},{"AcceptStation":"[广州市]广州广园的膜法世家[13548526982]已收件","AcceptTime":"2018-03-3119:39:05"}],"State":"3","OrderCode":"1523246724","EBusinessID":"1314930","Success":true}
     * msg : 查找成功
     * code : 200
     */

    private InfoBean info;
    private String msg;
    private int code;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class InfoBean {
        /**
         * LogisticCode : 486118218996
         * ShipperCode : ZTO
         * Traces : [{"AcceptStation":"[北京市]快件已在北京丰台三路居签收签收人：本人,感谢您使用中通快递，期待再次为您服务!","AcceptTime":"2018-04-0313:21:49"},{"AcceptStation":"[北京市]北京丰台三路居的路建亚18510200220[18510200220]正在派件","AcceptTime":"2018-04-0308:00:58"},{"AcceptStation":"[北京市]快件已到达北京丰台三路居","AcceptTime":"2018-04-0307:09:19"},{"AcceptStation":"[北京市]快件离开北京已发往北京丰台三路居","AcceptTime":"2018-04-0215:15:36"},{"AcceptStation":"[广州市]快件离开广州中心已发往北京","AcceptTime":"2018-04-0102:12:42"},{"AcceptStation":"[广州市]快件已到达广州中心","AcceptTime":"2018-04-0102:11:39"},{"AcceptStation":"[广州市]快件离开广州广园已发往北京","AcceptTime":"2018-04-0100:10:42"},{"AcceptStation":"[广州市]广州广园的膜法世家[13548526982]已收件","AcceptTime":"2018-03-3119:39:05"}]
         * State : 3
         * OrderCode : 1523246724
         * EBusinessID : 1314930
         * Success : true
         */

        private String LogisticCode;
        private String ShipperCode;
        private String State;
        private String OrderCode;
        private String EBusinessID;
        private boolean Success;
        private List<TracesBean> Traces;

        public String getLogisticCode() {
            return LogisticCode;
        }

        public void setLogisticCode(String LogisticCode) {
            this.LogisticCode = LogisticCode;
        }

        public String getShipperCode() {
            return ShipperCode;
        }

        public void setShipperCode(String ShipperCode) {
            this.ShipperCode = ShipperCode;
        }

        public String getState() {
            return State;
        }

        public void setState(String State) {
            this.State = State;
        }

        public String getOrderCode() {
            return OrderCode;
        }

        public void setOrderCode(String OrderCode) {
            this.OrderCode = OrderCode;
        }

        public String getEBusinessID() {
            return EBusinessID;
        }

        public void setEBusinessID(String EBusinessID) {
            this.EBusinessID = EBusinessID;
        }

        public boolean isSuccess() {
            return Success;
        }

        public void setSuccess(boolean Success) {
            this.Success = Success;
        }

        public List<TracesBean> getTraces() {
            return Traces;
        }

        public void setTraces(List<TracesBean> Traces) {
            this.Traces = Traces;
        }

        public static class TracesBean {
            /**
             * AcceptStation : [北京市]快件已在北京丰台三路居签收签收人：本人,感谢您使用中通快递，期待再次为您服务!
             * AcceptTime : 2018-04-0313:21:49
             */

            private String AcceptStation;
            private String AcceptTime;

            public String getAcceptStation() {
                return AcceptStation;
            }

            public void setAcceptStation(String AcceptStation) {
                this.AcceptStation = AcceptStation;
            }

            public String getAcceptTime() {
                return AcceptTime;
            }

            public void setAcceptTime(String AcceptTime) {
                this.AcceptTime = AcceptTime;
            }
        }
    }
}
