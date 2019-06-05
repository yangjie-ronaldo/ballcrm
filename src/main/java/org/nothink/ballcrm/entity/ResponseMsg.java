package org.nothink.ballcrm.entity;

public class ResponseMsg {
    private int code;
    private String msg;
    private Object data;

    public ResponseMsg() {
        super();
    }

    public ResponseMsg(int code, String msg, Object data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseMsg(String status) {
        if ("ok".equals(status)) {
            this.code = 20000;
            this.msg = "操作成功";
        } else {
        }
    }

    @Override
    public String toString() {
        return "ResponseMsg [code=" + code + ", msg=" + msg + ", data=" + data + "]";
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
