package org.nothink.ballcrm.entity;


import java.util.Date;

public class StuEntity extends PagedCriteria {
    private Integer sid;
    private Integer age;
    private Date createDate;
    private Date updateDate;
    private String name;
    private String nikiName;
    private Date birthday;
    private String sex;
    private String sexDef;
    private String tel;
    private String status;
    private String statusDef;
    private String verifyStatus;
    private String verifyStatusDef;
    private String channel;
    private String channelName;
    private String channelNote;
    private String type;
    private String typeDef;
    private String wechat;
    private Integer preCc;
    private Integer cc;
    private String ccName;
    private Integer teacherId;
    private String teacherIdName;
    private Integer popularizeId;
    private String popularizeIdName;
    private Integer node;
    private String nodeName;
    private Integer rentBall;
    private String consultNote;

    private Boolean hasCourse198;
    private Integer Course198Num;
    private Date startDate;
    private Date endDate;

    @Override
    public String toString() {
        return "StuEntity{" +
                "sid=" + sid +
                ", age=" + age +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", name='" + name + '\'' +
                ", nikiName='" + nikiName + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", sexDef='" + sexDef + '\'' +
                ", tel='" + tel + '\'' +
                ", status='" + status + '\'' +
                ", statusDef='" + statusDef + '\'' +
                ", verifyStatus='" + verifyStatus + '\'' +
                ", verifyStatusDef='" + verifyStatusDef + '\'' +
                ", channel='" + channel + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelNote='" + channelNote + '\'' +
                ", type='" + type + '\'' +
                ", typeDef='" + typeDef + '\'' +
                ", wechat='" + wechat + '\'' +
                ", preCc=" + preCc +
                ", cc=" + cc +
                ", ccName='" + ccName + '\'' +
                ", teacherId=" + teacherId +
                ", teacherIdName='" + teacherIdName + '\'' +
                ", popularizeId=" + popularizeId +
                ", popularizeIdName='" + popularizeIdName + '\'' +
                ", node=" + node +
                ", nodeName='" + nodeName + '\'' +
                ", rentBall=" + rentBall +
                ", consultNote='" + consultNote + '\'' +
                ", hasCourse198=" + hasCourse198 +
                ", Course198Num=" + Course198Num +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getRentBall() {
        return rentBall;
    }

    public void setRentBall(Integer rentBall) {
        this.rentBall = rentBall;
    }

    public String getConsultNote() {
        return consultNote;
    }

    public void setConsultNote(String consultNote) {
        this.consultNote = consultNote;
    }

    public String getPopularizeIdName() {
        return popularizeIdName;
    }

    public void setPopularizeIdName(String popularizeIdName) {
        this.popularizeIdName = popularizeIdName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getPopularizeId() {
        return popularizeId;
    }

    public void setPopularizeId(Integer popularizeId) {
        this.popularizeId = popularizeId;
    }

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

    public String getSexDef() {
        return sexDef;
    }

    public void setSexDef(String sexDef) {
        this.sexDef = sexDef;
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

    public String getStatusDef() {
        return statusDef;
    }

    public void setStatusDef(String statusDef) {
        this.statusDef = statusDef;
    }

    public String getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(String verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public String getVerifyStatusDef() {
        return verifyStatusDef;
    }

    public void setVerifyStatusDef(String verifyStatusDef) {
        this.verifyStatusDef = verifyStatusDef;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
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

    public String getTypeDef() {
        return typeDef;
    }

    public void setTypeDef(String typeDef) {
        this.typeDef = typeDef;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getPreCc() {
        return preCc;
    }

    public void setPreCc(Integer preCc) {
        this.preCc = preCc;
    }

    public Integer getCc() {
        return cc;
    }

    public void setCc(Integer cc) {
        this.cc = cc;
    }

    public String getCcName() {
        return ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherIdName() {
        return teacherIdName;
    }

    public void setTeacherIdName(String teacherIdName) {
        this.teacherIdName = teacherIdName;
    }

    public Integer getNode() {
        return node;
    }

    public void setNode(Integer node) {
        this.node = node;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Boolean getHasCourse198() {
        return hasCourse198;
    }

    public void setHasCourse198(Boolean hasCourse198) {
        this.hasCourse198 = hasCourse198;
    }

    public Integer getCourse198Num() {
        return Course198Num;
    }

    public void setCourse198Num(Integer course198Num) {
        Course198Num = course198Num;
    }
}