package com.trhoanglee.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trhoanglee.expense.domain.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
