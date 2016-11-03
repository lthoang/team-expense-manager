package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trhoanglee.expense.service.TeamService;
import com.trhoanglee.expense.domain.Team;
import com.trhoanglee.expense.util.CommonUtils;

@RestController
@RequestMapping(value = "/api/teams")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@RequestMapping(method = GET)
	public List<Team> searchTeams(
			@RequestParam(defaultValue="") String keyword, 
			@RequestParam(defaultValue="0") int page, 
			@RequestParam(defaultValue="10") int pageSize) {
		return teamService.search(keyword, page, pageSize);
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public Team getTeam(@PathVariable("id") String id) {
	    return teamService.getTeam(CommonUtils.parsePathVariableId(id));
	}
	
	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Team createTeam(@RequestBody Team team) {
		team.setId(null);
		return teamService.saveTeam(team);
	}
	
	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Team updateTeam(
			@PathVariable("id") String id, 
			@RequestBody Team team) {
		team.setId(CommonUtils.parsePathVariableId(id));
		return teamService.saveTeam(team);
	}
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam Long[] ids) {
	    teamService.deleteTeams(ids);
    }
}
