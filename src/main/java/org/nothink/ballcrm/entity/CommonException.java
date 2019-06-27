package org.nothink.ballcrm.entity;

public class CommonException extends RuntimeException {
    private Integer code;
    private String msg;

    public CommonException() {
        super();
    }

    public CommonException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
