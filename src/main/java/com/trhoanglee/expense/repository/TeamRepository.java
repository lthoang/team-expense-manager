package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.Team;

public interface TeamRepository extends JpaRepository<Team, String>{

	@Query("select t from Team t where "
	        + "lower(concat(t.id,t.name,t.description)) like concat('%',:keyword,'%') "
			+ "order by t.name")
	List<Team> searchTeams(@Param("keyword") String keyword, Pageable pageable);

	@Modifying
	@Query("delete from Team where id in (:ids)")
	void deleteTeams(@Param("ids") String... ids);
	
	@Query("select t from Team t join fetch t.teamMembers where t.id like :id%")
	public Team findByIdAndFetchTeamMembersEagerly(@Param("id") String id);
}
