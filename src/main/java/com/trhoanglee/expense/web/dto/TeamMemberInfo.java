package com.trhoanglee.expense.web.dto;

public class TeamMemberInfo {
    private String id;
    private MemberInfo member;
    private TeamInfo team;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MemberInfo getMember() {
        return member;
    }

    public void setMember(MemberInfo member) {
        this.member = member;
    }

    public TeamInfo getTeam() {
        return team;
    }

    public void setTeam(TeamInfo team) {
        this.team = team;
    }
}
