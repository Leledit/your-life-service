package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.service.finance.VariableExpensesCategoryService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class VariableExpensesCategoryController {

    @Autowired
    private VariableExpensesCategoryService variableExpensesCategoryService;

    @Autowired
    private UserContext userContext;

    @PostMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CategoryVariableExpense> saveAccountsCategoryExpense(@RequestBody @Valid CategoryVariableExpensePostDTO categoryVariableExpensePostDTO){
        CategoryVariableExpense categoryVariableExpense = variableExpensesCategoryService.save(categoryVariableExpensePostDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpense);
    }

    @GetMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryVariableExpense>> getAllAccountsCategoryExpense(){
        ArrayList<CategoryVariableExpense> categoryVariableExpenses = variableExpensesCategoryService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenses);
    }

    @GetMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVariableExpense> getByIdAccountsCategoryExpense(@PathVariable String id){
        CategoryVariableExpense categoryVariableExpense = variableExpensesCategoryService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpense);
    }

    @PatchMapping(value = "/accounts-category-expense/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletedAccountsCategoryExpense(@PathVariable String id){
        variableExpensesCategoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CategoryVariableExpense> updatedAccountsCategoryExpense(@RequestBody @Valid CategoryVariableExpensePutDTO categoryVariableExpensePutDTO,
                                                                                  @PathVariable String id){
        CategoryVariableExpense categoryVariableExpense = variableExpensesCategoryService.update(id,categoryVariableExpensePutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpense);
    }
}
