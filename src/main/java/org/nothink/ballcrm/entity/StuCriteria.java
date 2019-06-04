package org.nothink.ballcrm.entity;

import java.util.Date;

public class StuCriteria extends PagedCriteria {
    private Integer sid;

    private Date createDate;

    private String name;

    private String nikiName;

    private Date birthday;

    private String sex;

    private String tel;

    private String status;

    private String verifyStatus;

    private String channel;

    private String channelNote;

    private String type;

    private String wechat;

    private Integer cc;

    private Integer node;

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNikiName() {
        return nikiName;
    }

    public void setNikiName(String nikiName) {
        this.nikiName = nikiName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelNote() {
        return channelNote;
    }

    public void setChannelNote(String channelNote) {
        this.channelNote = channelNote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getCc() {
        return cc;
    }

    public void setCc(Integer cc) {
        this.cc = cc;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "StuCriteria{" +
                "sid=" + sid +
                ", createDate=" + createDate +
                ", name='" + name + '\'' +
                ", nikiName='" + nikiName + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", tel='" + tel + '\'' +
                ", status='" + status + '\'' +
                ", verifyStatus='" + verifyStatus + '\'' +
                ", channel='" + channel + '\'' +
                ", channelNote='" + channelNote + '\'' +
                ", type='" + type + '\'' +
                ", wechat='" + wechat + '\'' +
                ", cc=" + cc +
                ", node=" + node +
                '}';
    }
}
