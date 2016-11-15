package com.trhoanglee.expense;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.trhoanglee.expense.service.MemberService;
import com.trhoanglee.expense.service.TeamMemberService;
import com.trhoanglee.expense.service.TeamService;

/**
 * @author hoangtle
 */
@EntityScan(basePackageClasses = {Application.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class Application {
	public static void main(String... args) throws IOException {
		ApplicationContext appContext = SpringApplication.run(Application.class, args);
		MemberService memberService = appContext.getBean(MemberService.class);
		TeamService teamService = appContext.getBean(TeamService.class);
		TeamMemberService teamMemberService = appContext.getBean(TeamMemberService.class);
		
		String membersFilePath = (args.length > 0)? args[0] : "etc/members.txt";
		memberService.loadMembersFromFile(membersFilePath);
		String teamsFilePath = (args.length > 1)? args[1] : "etc/teams.txt";
		teamService.loadTeamsFromFile(teamsFilePath);
		String teamsMembersFilePath = (args.length > 2)? args[2] : "etc/teams-members.txt";
		teamMemberService.loadTeamMembersFromFile(teamsMembersFilePath);
	}
}
