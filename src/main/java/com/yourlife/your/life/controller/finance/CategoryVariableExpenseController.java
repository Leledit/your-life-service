package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class CategoryVariableExpenseController {

    @Autowired
    private UserContext userContext;

    @Autowired
    private CategoryVariableExpenseService categoryVariableExpenseService;

    @ResponseBody
    @PostMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVariableExpense> saveAccountsCategoryExpense(@RequestBody @Valid CategoryVariableExpensePostDTO categoryVariableExpensePostDTO){
        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.save(categoryVariableExpensePostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryVariableExpense);
    }

    @ResponseBody
    @GetMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryVariableExpense>> getAllAccountsCategoryExpense(){
        List<CategoryVariableExpense> categoryVariableExpenses = categoryVariableExpenseService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenses);
    }

    @ResponseBody
    @GetMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVariableExpense> getByIdAccountsCategoryExpense(@PathVariable String id){
        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpense);
    }

    @PatchMapping(value = "/accounts-category-expense/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletedAccountsCategoryExpense(@PathVariable String id){
        categoryVariableExpenseService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PutMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVariableExpense> updatedAccountsCategoryExpense(@RequestBody @Valid CategoryVariableExpensePutDTO categoryVariableExpensePutDTO,
                                                                                  @PathVariable String id){

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.update(id,categoryVariableExpensePutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpense);
    }
}
