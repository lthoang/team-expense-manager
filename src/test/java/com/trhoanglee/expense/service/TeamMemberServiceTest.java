package com.trhoanglee.expense.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.trhoanglee.expense.Application;
import com.trhoanglee.expense.domain.Member;
import com.trhoanglee.expense.domain.Team;
import com.trhoanglee.expense.domain.TeamMember;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TeamMemberServiceTest {
    @Autowired
    private TeamMemberService teamMemberService;
    
    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;
	
	@Before
	public void startUp() throws IOException {
		memberService.loadMembersFromFile(getClass().getResource("/members-test.txt").getFile());
		teamService.loadTeamsFromFile(getClass().getResource("/teams-test.txt").getFile());
		teamMemberService.loadTeamMembersFromFile(getClass().getResource("/teams-members-test.txt").getFile());
	}
	
	@After
	public void tearDown() {
	    teamMemberService.deleteAllTeamMembers();
	    teamService.deleteAllTeams();
		memberService.deleteAllMembers();
	}
	
	@Test
	public void testSaveTeamMember() {
		//create
	    List<TeamMember> teamMembers = teamMemberService.getAll();
	    assertThat(teamMembers.size(), is(equalTo(2)));
	    Team team = teamService.getTeam("t001");
	    Member member = memberService.getMember("002");
	    TeamMember teamMember = new TeamMember(team, member);
	    teamMemberService.saveTeamMember(teamMember);
	    teamMembers = teamMemberService.getAll();
	    assertThat(teamMembers.size(), is(equalTo(3)));
	    
	    //update
	    teamMember = teamMemberService.getTeamMember("tm001");
	    member = memberService.getMember("003");
	    teamMember.setMember(member);
	    teamMemberService.saveTeamMember(teamMember);
	    teamMembers = teamMemberService.getAll();
	    assertThat(teamMembers.size(), is(equalTo(3)));
	    
	}
	
	@Test
	public void testDeleteTeamMembers() {
	    List<TeamMember> teamMembers = teamMemberService.getAll();
	    assertThat(teamMembers.size(), is(equalTo(2)));

	    teamMemberService.deleteTeamMembers("tm001", "tm002");
	    teamMembers = teamMemberService.getAll();
	    assertThat(teamMembers.size(), is(equalTo(0)));
	    teamMembers.forEach(teamMember -> {
	        assertThat(teamMember.getId(), is(not("tm001")));
	        assertThat(teamMember.getId(), is(not("tm002")));
	    });
	}
	
	//TODO: Implement fails test
}
