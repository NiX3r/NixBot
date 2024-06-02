package cz.iliev.managers.statistics_manager.instances;

import cz.iliev.utils.CommonUtils;

public class ServerStatsInstance {

    // int for members join day
    private int memberJoinDay;
    // int for members join month
    private int memberJoinMonth;
    // int for members join ever
    private int memberJoinEver;
    // int for members leave day;
    private int memberLeaveDay;
    // int for members leave month;
    private int memberLeaveMonth;
    // int for members leave ever;
    private int memberLeaveEver;

    public ServerStatsInstance(int memberJoinDay, int memberJoinMonth, int memberJoinEver, int memberLeaveDay, int memberLeaveMonth, int memberLeaveEver) {
        this.memberJoinDay = memberJoinDay;
        this.memberJoinMonth = memberJoinMonth;
        this.memberJoinEver = memberJoinEver;
        this.memberLeaveDay = memberLeaveDay;
        this.memberLeaveMonth = memberLeaveMonth;
        this.memberLeaveEver = memberLeaveEver;
    }

    public int getMemberJoinDay() {
        return memberJoinDay;
    }

    public int getMemberJoinMonth() {
        return memberJoinMonth;
    }

    public int getMemberJoinEver() {
        return memberJoinEver;
    }

    public int getMemberLeaveDay() {
        return memberLeaveDay;
    }

    public int getMemberLeaveMonth() {
        return memberLeaveMonth;
    }

    public int getMemberLeaveEver() {
        return memberLeaveEver;
    }

    public void incrementMemberJoin(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        memberJoinDay++;
        memberJoinMonth++;
        memberJoinEver++;
    }

    public void incrementMemberLeave(){
        CommonUtils.statisticsManager.checkCurrentDatetime();
        memberLeaveDay++;
        memberLeaveMonth++;
        memberLeaveEver++;
    }

    public void resetMonth(){
        memberLeaveMonth = 0;
        memberJoinMonth = 0;
    }

    public void resetDay(){
        memberLeaveDay = 0;
        memberJoinDay = 0;
    }
}
