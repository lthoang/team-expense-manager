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
import com.trhoanglee.expense.service.TeamService;
import com.trhoanglee.expense.web.dto.MemberInfo;
import com.trhoanglee.expense.web.dto.TeamInfo;

@RestController
@RequestMapping(value = "/api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @RequestMapping(method = GET)
    public List<TeamInfo> searchTeams(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<Team> teams = teamService.search(keyword, page, pageSize);
        return convertToDto(teams);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public TeamInfo getTeam(@PathVariable("id") String id) {
        Team team = teamService.getTeam(id);
        return convertToDto(team);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TeamInfo createTeam(@RequestBody TeamInfo team) {
        team.setId(null);
        Team saveTeam = convertToEntity(team);
        saveTeam = teamService.saveTeam(saveTeam);
        return convertToDto(saveTeam);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TeamInfo updateTeam(@PathVariable("id") String id, @RequestBody TeamInfo team) {
        team.setId(id);
        Team savedTeam = convertToEntity(team);
        savedTeam = teamService.saveTeam(savedTeam);
        return convertToDto(savedTeam);
    }

    @RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam String[] ids) {
        teamService.deleteTeams(ids);
    }

    private List<TeamInfo> convertToDto(List<Team> teams) {
        List<TeamInfo> response = new ArrayList<>();
        teams.forEach(team -> {
            response.add(convertToDto(team));
        });
        return response;
    }
    
    private TeamInfo convertToDto(Team team) {
        TeamInfo response = new TeamInfo();
        MemberInfo memberInfo = new MemberInfo();
        BeanUtils.copyProperties(team, response);
        BeanUtils.copyProperties(team.getManager(), memberInfo);
        response.setManager(memberInfo);
        return response;
    }
    
    private Team convertToEntity(TeamInfo team) {
        Team teamEntity = new Team();
        Member manager = new Member();
        BeanUtils.copyProperties(team.getManager(), manager);
        teamEntity.setManager(manager);
        BeanUtils.copyProperties(team, teamEntity);
        return teamEntity;
    }
}
