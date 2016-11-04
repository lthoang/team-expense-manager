package com.trhoanglee.expense.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trhoanglee.expense.domain.TeamMember;
import com.trhoanglee.expense.repository.MemberRepository;
import com.trhoanglee.expense.repository.TeamMemberRepository;
import com.trhoanglee.expense.repository.TeamRepository;

@Service
@Transactional(readOnly = true)
public class TeamMemberService {
    private final AtomicInteger idGeneration = new AtomicInteger(1000);

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Autowired
    private MemberRepository memberRepo;

    @Autowired
    private TeamRepository  teamRepo;
    
    public String idGenerationIncrementAndGet() {
        return String.valueOf(idGeneration.incrementAndGet());
    }

    public List<TeamMember> getAll() {
        return teamMemberRepo.findAll();
    }
    
    public TeamMember getTeamMember(String id) {
        return teamMemberRepo.findOne(id);
    }

    @Transactional
    public TeamMember saveTeamMember(TeamMember teamMember) {
        if (teamMember == null) {
            return null;
        }
        if (teamMember.getId() == null) {
            teamMember.setId(idGenerationIncrementAndGet());
        }
        return teamMemberRepo.save(teamMember);
    }

    @Transactional
    public void deleteTeamMembers(String... ids) {
        teamMemberRepo.deleteTeamMembers(ids);
    }

    public void deleteAllTeamMembers() {
        teamMemberRepo.deleteAllInBatch();
    }
    
    @Transactional
    public long loadTeamMembersFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(this::parseTeamMember)
                    .map(this::saveTeamMember)
                    .count();
        }
    }
    
    /**
     * teamMemberLine format: id|team_id|member_id
     */
    private TeamMember parseTeamMember(String teamMemberLine) {
        String[] items = teamMemberLine.split("\\|");
        if (items.length < 3) {
            throw new IllegalArgumentException(String.format("Invalid teamMember-line format: %s", teamMemberLine));
        }
        
        TeamMember teamMember = new TeamMember();
        teamMember.setId(items[0]);
        teamMember.setTeam(teamRepo.findOne(items[1]));
        teamMember.setMember(memberRepo.findOne(items[2]));
        return teamMember;
    }
}
