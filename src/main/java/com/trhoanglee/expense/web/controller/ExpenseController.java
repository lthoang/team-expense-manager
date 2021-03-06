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

import com.trhoanglee.expense.domain.Expense;
import com.trhoanglee.expense.service.ExpenseService;
import com.trhoanglee.expense.web.dto.ExpenseInfo;

@RestController
@RequestMapping(value = "/api/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@RequestMapping(method = GET)
	public List<ExpenseInfo> searchExpenses(
			@RequestParam(defaultValue="") String keyword, 
			@RequestParam(defaultValue="0") int page, 
			@RequestParam(defaultValue="10") int pageSize) {
		List<Expense> expenses = expenseService.search(keyword, page, pageSize);
		return convertToDto(expenses);
	}


    @RequestMapping(value = "/{id}", method = GET)
	public ExpenseInfo getExpense(@PathVariable("id") String id) {
	    Expense expense = expenseService.getExpense(id);
        return convertToDto(expense);
	}  

    @RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ExpenseInfo createExpense(@RequestBody ExpenseInfo expense) {
		expense.setId(null);
		Expense expenseEntity = convertToEntity(expense);
		expenseEntity = expenseService.saveExpense(expenseEntity);
		return convertToDto(expenseEntity);
	}


    @RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ExpenseInfo updateExpense(
			@PathVariable("id") String id, 
			@RequestBody ExpenseInfo expense) {
		expense.setId(id);
        Expense expenseEntity = convertToEntity(expense);
        expenseEntity = expenseService.saveExpense(expenseEntity);
        return convertToDto(expenseEntity);
    }
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam String[] ids) {
	    expenseService.deleteExpenses(ids);
    }
	
    private List<ExpenseInfo> convertToDto(List<Expense> expenses) {
        List<ExpenseInfo> response = new ArrayList<>();
        expenses.forEach(expense -> {
            response.add(convertToDto(expense));
        });
        return response;
    }
    
    private ExpenseInfo convertToDto(Expense expense) {
        ExpenseInfo response = new ExpenseInfo();
        BeanUtils.copyProperties(expense, response);
        return response;
    }
    
    private Expense convertToEntity(ExpenseInfo expense) {
        Expense expenseEntity = new Expense();
        BeanUtils.copyProperties(expense, expenseEntity);
        return expenseEntity;
    }
}
