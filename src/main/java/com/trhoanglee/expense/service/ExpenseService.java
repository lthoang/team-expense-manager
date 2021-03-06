package com.trhoanglee.expense.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
    private final AtomicInteger idGeneration = new AtomicInteger(1000);
    
    @Autowired
    private ExpenseRepository expenseRepo;
    
    public String idGenerationIncrementAndGet() {
        return String.valueOf(idGeneration.incrementAndGet());
    }
    
    public List<Expense> search(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();
        return expenseRepo.searchExpenses(keyword, new PageRequest(page, pageSize));
    }

    public Expense getExpense(String id) {
        return expenseRepo.findOne(id);
    }

    @Transactional
    public Expense saveExpense(Expense expense) {
        if (expense == null) {
            return null;
        }
        if (expense.getId() == null) {
            expense.setId(idGenerationIncrementAndGet());
        }
        return expenseRepo.save(expense);
    }

    @Transactional
    public void deleteExpenses(String... ids) {
        expenseRepo.deleteExpenses(ids);
    }
    
    public void deleteAllExpenses() {
        expenseRepo.deleteAllInBatch();
    }

}
