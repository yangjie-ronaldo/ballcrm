package org.nothink.ballcrm.util;

public class CodeDef {
    //性别
    public static String SEX_BOY = "SEX01";  // 帅哥
    public static String SEX_GIRL = "SEX02";  // 美女
    //类型
    public static String TYPE_PROTENTIAL = "STP01";  // 体验课学员
    public static String TYPE_SALE = "STP02";  // 营销课学员
    public static String TYPE_YEARVIP = "STP03";  // 正式课学员
    public static String TYPE_HOUXUAN = "STP04";  // 潜力（被放弃）
    //处理状态
    public static String HANDLE_WAITING = "PST01";  // 待处理
    public static String HANDLED = "PST02";  // 已处理
    public static String HANDLE_WAITING_VERIFY = "PST03";  // 待经理审核
    public static String HANDLE_ABANDEN = "PST04";  // 作废
    public static String HANDLE_ABANDEN_STU = "PST05";  // 申请放弃客户
    //客户状态
    public static String STU_NEW = "SST01";  // 未承诺上门
    public static String STU_BOOKED = "SST02";  // 承诺上门
    public static String STU_BOOKED_PAUSE = "SST03";  // 承诺未上门
    public static String STU_BOOKED_NODEAL = "SST04";  // 上门未交费
    public static String STU_BOOKED_ABANDON = "SST05";  // 体验课放弃
    public static String STU_SALE_READY = "SST06";  // 营销课准备待开班
    public static String STU_SALE_STUDY = "SST07";  // 营销课正常上课中
    public static String STU_SALE_PAUSE = "SST08";  // 营销课中途暂停
    public static String STU_SALE_NODEAL = "SST09";  // 营销课结课未转化
    public static String STU_SALE_ABANDON = "SST10";  // 营销课放弃
    public static String STU_VIP_READY = "SST11";  // 正课准备待开班
    public static String STU_VIP_STUDY = "SST12";  // 正课正常上课中
    public static String STU_VIP_PAUSE = "SST13";  // 正课中途暂停
    public static String STU_VIP_NODEAL = "SST14";  // 正课结课未续费
    public static String STU_VIP_ABANDON = "SST15";  // 正课放弃

    //学员签到状态
    public static String SIGN_OK = "SIG01";  // 已签到
    public static String SIGN_WAITING = "SIG02";  // 等待上课
    public static String SIGN_TRUANCY = "SIG03";  // 旷课
    public static String SIGN_CHANGE = "SIG04";  // 改期

}