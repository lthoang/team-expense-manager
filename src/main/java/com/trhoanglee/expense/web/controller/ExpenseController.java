package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

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

@RestController
@RequestMapping(value = "/api/expenses")
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;

	@RequestMapping(method = GET)
	public List<Expense> searchExpenses(
			@RequestParam(defaultValue="") String keyword, 
			@RequestParam(defaultValue="0") int page, 
			@RequestParam(defaultValue="10") int pageSize) {
		return expenseService.search(keyword, page, pageSize);
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public Expense getExpense(@PathVariable("id") String id) {
	    return expenseService.getExpense(id);
	}
	
	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Expense createExpense(@RequestBody Expense expense) {
		expense.setId(null);
		return expenseService.saveExpense(expense);
	}
	
	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Expense updateExpense(
			@PathVariable("id") String id, 
			@RequestBody Expense expense) {
		expense.setId(id);
		return expenseService.saveExpense(expense);
	}
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam String[] ids) {
	    expenseService.deleteExpenses(ids);
    }
}
