package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, String>{
    
    @Query("select e from Expense e where lower(e.description) like :keyword% "
            + "order by e.date desc")
    List<Expense> searchExpenses(@Param("keyword") String keyword, Pageable pageable);
    
    @Query("delete from Expense where id in (:ids)")
    void deleteExpenses(@Param("ids") String... ids);
}
