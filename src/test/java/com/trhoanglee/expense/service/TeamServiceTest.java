package com.trhoanglee.expense.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.trhoanglee.expense.Application;
import com.trhoanglee.expense.domain.Team;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TeamServiceTest {
    @Autowired
    private TeamService teamService;

    @Autowired
    private MemberService memberService;
	
	@Before
	public void startUp() throws IOException {
		memberService.loadMembersFromFile(getClass().getResource("/members-test.txt").getFile());
		teamService.loadTeamsFromFile(getClass().getResource("/teams-test.txt").getFile());
	}
	
	@After
	public void tearDown() {
	    teamService.deleteAllTeams();
		memberService.deleteAllMembers();
	}
	
	@Test
	public void testSearchTeams() {
	    List<Team> teams;

	    //search all
	    teams = teamService.search("", 0, 10);
	    assertThat(teams.size(), is(equalTo(5)));
	    
	    //search by id
	    teams = teamService.search("t001", 0, 10);
	    assertThat(teams.size(), is(equalTo(1)));
	    assertThat(teams.get(0).getName(), is(equalTo("Team1")));
	    
	    //search by name
	    teams = teamService.search("Team2", 0, 10);
	    assertThat(teams.size(), is(equalTo(1)));
	    assertThat(teams.get(0).getId(), is(equalTo("t002")));

	    //search by description
	    teams = teamService.search("description3", 0, 10);
	    assertThat(teams.size(), is(equalTo(1)));
	    assertThat(teams.get(0).getId(), is(equalTo("t003")));
	}
	
	@Test
	public void testSaveTeam() {
		//create
	    Team team = new Team("newTeam", "newDescription", new Date(0L), memberService.getMember("001"));
	    teamService.saveTeam(team);
	    
	    List<Team> teams = teamService.search("newTeam", 0, 10);
	    assertThat(teams.size(), is(equalTo(1)));
	    assertThat(teams.get(0).getManager().getId(), is(equalTo("001")));
	    
	    team = teamService.getTeam("t001");
	    assertNotNull(team);
	    team.setName("newNameTeam1");
	    teamService.saveTeam(team);
	    team = teamService.getTeam("t001");
	    assertThat(team.getName(), is(equalTo("newNameTeam1")));
	}
	
	@Test
	public void testDeleteTeams() {
	    List<Team> teams = teamService.search("", 0, 10);
	    assertThat(teams.size(), is(equalTo(5)));
	    
	    teamService.deleteTeams("t001", "t002");
	    teams = teamService.search("", 0, 10);
	    assertThat(teams.size(), is(equalTo(3)));
	    teams.forEach(team -> {
	        assertThat(team.getId(), is(not("t001")));
	        assertThat(team.getId(), is(not("t002")));
	    });
	}
}
