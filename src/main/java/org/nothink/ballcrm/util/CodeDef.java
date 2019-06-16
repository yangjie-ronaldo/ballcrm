package org.nothink.ballcrm.util;

public class CodeDef {
    //性别
    public static String SEX_BOY = "SEX01";  // 帅哥
    public static String SEX_GIRL = "SEX02";  // 美女
    //类型
    public static String TYPE_PROTENTIAL = "STP01";  // 新客户
    public static String TYPE_198 = "STP02";  // 小课包客户
    public static String TYPE_YEARVIP = "STP03";  // 年卡客户
    public static String TYPE_HOUXUAN = "STP04";  // 候选客户
    //处理状态
    public static String HANDLE_WAITING = "PST01";  // 待处理
    public static String HANDLED = "PST02";  // 已处理
    public static String HANDLE_WAITING_VERIFY = "PST03";  // 待经理审核
    public static String HANDLE_ABANDEN = "PST04";  // 作废
    public static String HANDLE_ABANDEN_STU = "PST05";  // 申请放弃客户
    //客户状态
    public static String STU_NEW = "SST01";  // 新客户，考虑中
    public static String STU_BOOKED = "SST02";  // 已预约，待上课
    public static String STU_COURSE_OVER = "SST03";  // 课程结束
    public static String STU_COURSE_SIGNED = "SST04";  // 已上课，待预约
    public static String STU_WAITING = "SST05";  // 闲置中，待预约
    public static String STU_TRUANCY = "SST06";  // 旷课中，待联系
    public static String STU_BUY198 = "SST07";  // 已买小课包
    public static String STU_BUYVIP = "SST08";  // 已购买年卡
    public static String STU_HOUXUAN = "SST09";  // 客户被放弃

    //学员签到状态
    public static String SIGN_OK = "SIG01";  // 已签到
    public static String SIGN_WAITING = "SIG02";  // 等待上课
    public static String SIGN_TRUANCY = "SIG03";  // 旷课
    public static String SIGN_CHANGE = "SIG04";  // 改期

}