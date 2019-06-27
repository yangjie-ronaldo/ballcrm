package org.nothink.ballcrm.entity;

import lombok.Data;

@Data
public class CcStatistics {
    private Integer cc;
    private String ccName;
    private Integer newStuNum;
    private Integer demoScheduleNum;
    private Integer demoSignNum;
    private Integer demoCloseNum;
    private Integer buy198Num;
    private Integer myStu198Num;

    public CcStatistics() {
        super();
    }

    public CcStatistics(Integer cc, String ccName, Integer newStuNum, Integer demoScheduleNum, Integer demoSignNum, Integer demoCloseNum, Integer buy198Num, Integer myStu198Num) {
        this.cc = cc;
        this.ccName = ccName;
        this.newStuNum = newStuNum;
        this.demoScheduleNum = demoScheduleNum;
        this.demoSignNum = demoSignNum;
        this.demoCloseNum = demoCloseNum;
        this.buy198Num = buy198Num;
        this.myStu198Num = myStu198Num;
    }
}
