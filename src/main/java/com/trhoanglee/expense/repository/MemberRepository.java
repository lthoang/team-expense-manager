package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
    
    @Query("select m from Member m where "
    		+ "lower(concat(m.id,m.email,m.mobile,m.name.fullNameFML)) like concat('%', :keyword, '%') "
            + "order by m.name.firstName")
    List<Member> searchMembers(@Param("keyword") String keyword, Pageable pageable);

    @Modifying
    @Query("delete from Member where id in (:ids)")
    void deleteMembers(@Param("ids") String... ids);

}
