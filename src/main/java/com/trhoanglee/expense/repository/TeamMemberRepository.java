package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String>{
    
    @Query("select tm from TeamMember tm where tm.team.id like :id% "
            + "order by tm.member.id asc")
    List<TeamMember> getAllMembersInTeam(@Param("id") String id);

    @Modifying
	@Query("delete from TeamMember where id in (:ids)")
	void deleteTeamMembers(@Param("ids") String... ids);
}
