package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trhoanglee.expense.domain.Member;
import com.trhoanglee.expense.domain.Team;
import com.trhoanglee.expense.domain.TeamMember;
import com.trhoanglee.expense.service.TeamMemberService;
import com.trhoanglee.expense.web.dto.MemberInfo;
import com.trhoanglee.expense.web.dto.TeamInfo;
import com.trhoanglee.expense.web.dto.TeamMemberInfo;

@RestController
@RequestMapping(value = "/api/teamMembers")
public class TeamMemberController {

	@Autowired
	private TeamMemberService teamMemberService;
	
	@RequestMapping(value = "/team/{id}", method = GET)
	public List<TeamMemberInfo> getAllMemberInTeam(@PathVariable("id") String id) {
	    List<TeamMember> teamMembers = teamMemberService.getAllMembersInTeam(id);
	    return convertToDto(teamMembers);
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public TeamMemberInfo getTeamMember(@PathVariable("id") String id) {
	    TeamMember teamMember = teamMemberService.getTeamMember(id);
	    return convertToDto(teamMember);
	}
	
	
	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public TeamMemberInfo createTeamMember(@RequestBody TeamMemberInfo teamMember) {
		teamMember.setId(null);
		TeamMember teamMemberEntity = convertToEntity(teamMember);
		teamMemberEntity = teamMemberService.saveTeamMember(teamMemberEntity);
		return convertToDto(teamMemberEntity);
	}
	
	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TeamMemberInfo updateTeamMember(
			@PathVariable("id") String id, 
			@RequestBody TeamMemberInfo teamMember) {
        TeamMember teamMemberEntity = convertToEntity(teamMember);
        teamMemberEntity = teamMemberService.saveTeamMember(teamMemberEntity);
        return convertToDto(teamMemberEntity);
	}
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam String[] ids) {
	    teamMemberService.deleteTeamMembers(ids);
    }
	
	   private List<TeamMemberInfo> convertToDto(List<TeamMember> teamMembers) {
	       List<TeamMemberInfo> response = new ArrayList<>();
	       teamMembers.forEach(teamMember -> {
	           response.add(convertToDto(teamMember));
	       });
	       return response;
	    }
	
	private TeamMemberInfo convertToDto(TeamMember teamMember) {
	    TeamMemberInfo teamMemberInfo = new TeamMemberInfo();
	    MemberInfo member = new MemberInfo();
	    TeamInfo team = new TeamInfo();
        BeanUtils.copyProperties(teamMember.getMember(), member);
        BeanUtils.copyProperties(teamMember.getTeam(), team);
	    teamMemberInfo.setId(teamMember.getId());
	    teamMemberInfo.setMember(member);
	    teamMemberInfo.setTeam(team);
	    return teamMemberInfo;
	}
	
	private TeamMember convertToEntity(TeamMemberInfo teamMemberInfo) {
	    TeamMember teamMember = new TeamMember();
	    Team team = new Team();
	    Member member = new Member();
	    BeanUtils.copyProperties(teamMemberInfo.getTeam(), team);
	    BeanUtils.copyProperties(teamMemberInfo.getMember(), member);
	    teamMember.setId(teamMemberInfo.getId());
	    teamMember.setTeam(team);
	    teamMember.setMember(member);
	    return teamMember;
	}
}
