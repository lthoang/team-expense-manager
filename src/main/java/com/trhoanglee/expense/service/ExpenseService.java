package com.trhoanglee.expense.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trhoanglee.expense.domain.Expense;
import com.trhoanglee.expense.repository.ExpenseRepository;

/**
 * @author hoangtle
 */
@Service
@Transactional(readOnly = true)
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepo;
    
    public List<Expense> search(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();
        return expenseRepo.searchExpenses(keyword, new PageRequest(page, pageSize));
    }

    public Expense getExpense(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    public Expense saveExpense(Expense expense) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteExpenses(String[] ids) {
        // TODO Auto-generated method stub
        
    }

}
