package com.pyassasins.alarmic;

/**
 * Created by lakshmanaram on 3/10/15.
 */
public class MyAlarmy {
        String to,msg,id,time;
        String sender;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
    public void setTo(String to) {
            this.to = to;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getTo() {
            return to;
        }

        public String getMsg() {
            return msg;
        }

        public String getId() {
            return id;
        }

        public String getTime() {
            return time;
        }
}
