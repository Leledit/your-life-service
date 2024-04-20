package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpenseChangingVO;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpenseRegisterVO;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import com.yourlife.your.life.utils.Logger;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class CategoryVariableExpenseController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryVariableExpenseService categoryVariableExpenseService;

    @PostMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CategoryVariableExpenseDTO> registerNewCategoryVariableExpense(@RequestBody @Valid CategoryVariableExpenseRegisterVO categoryVariableExpenseRegisterVO){

        CategoryVariableExpense categoryVariableExpense = modelMapper.map(categoryVariableExpenseRegisterVO,CategoryVariableExpense.class);

        CategoryVariableExpenseDTO categoryVariableExpenseDTO = categoryVariableExpenseService.created(categoryVariableExpense);

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTO);
    }

    @PostMapping(value = "/accounts-category-expense/batch",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CategoryVariableExpenseDTO>> registerSeveralNewCategoryVariableExpense(@RequestBody @Valid List<CategoryVariableExpenseRegisterVO> categoryVariableExpenseRegisterVO){

        List<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();

        categoryVariableExpenseRegisterVO.forEach(categoryVariableExpenseRegisterVO1 -> {
            CategoryVariableExpense categoryVariableExpense = modelMapper.map(categoryVariableExpenseRegisterVO1,CategoryVariableExpense.class);
            categoryVariableExpense.setDeleted(false);
            categoryVariableExpense.setCreatedAt(LocalDateTime.now());
            categoryVariableExpenses.add(categoryVariableExpense);
        });

        List<CategoryVariableExpenseDTO> categoryVariableExpenseDTOS = categoryVariableExpenseService.createdSeveral(categoryVariableExpenses);

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTOS);
    }

    @GetMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<CategoryVariableExpenseDTO>> getAllCategoryVariableExpense(){

        ArrayList<CategoryVariableExpenseDTO> categoryVariableExpenseDTOS = categoryVariableExpenseService.getAll();

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTOS);
    }

    @GetMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVariableExpenseDTO> getCategoryVariableExpense(@PathVariable String id){

        CategoryVariableExpenseDTO categoryVariableExpenseDTO = categoryVariableExpenseService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTO);
    }

    @PatchMapping(value = "/accounts-category-expense/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletingCategoryVariableExpense(@PathVariable String id){

        categoryVariableExpenseService.deleted(id);

        return null;
    }

    @PutMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CategoryVariableExpenseDTO> update(@RequestBody @Valid CategoryVariableExpenseChangingVO categoryVariableExpenseChangingVO){

        CategoryVariableExpense categoryVariableExpense = modelMapper.map(categoryVariableExpenseChangingVO,CategoryVariableExpense.class);

        CategoryVariableExpenseDTO categoryVariableExpenseDTO = categoryVariableExpenseService.update(categoryVariableExpense);

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTO);
    }


}
