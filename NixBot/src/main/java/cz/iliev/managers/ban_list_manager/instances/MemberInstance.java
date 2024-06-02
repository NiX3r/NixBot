package cz.iliev.managers.ban_list_manager.instances;

import java.util.List;

public class MemberInstance {

    private long memberId;
    private String memberName;
    private List<String> rolesName;

    public MemberInstance(long memberId, String memberName, List<String> rolesName) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.rolesName = rolesName;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public List<String> getRolesName() {
        return rolesName;
    }

    public void setRolesName(List<String> rolesName) {
        this.rolesName = rolesName;
    }
}
