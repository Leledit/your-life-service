package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpensePutVO;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpensePostVO;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import com.yourlife.your.life.utils.UserContext;
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

    @Autowired
    private UserContext userContext;

    @PostMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CategoryVariableExpenseDTO> save(@RequestBody @Valid CategoryVariableExpensePostVO categoryVariableExpensePostVO){

        if(categoryVariableExpensePostVO.getName() == null || categoryVariableExpensePostVO.getDescription() == null){
            throw new RuntimeException(ExceptionMessages.INVALID_REQUEST_COMPONENT);
        }

        CategoryVariableExpense categoryVariableExpenseRequest = modelMapper.map(categoryVariableExpensePostVO,CategoryVariableExpense.class);

        categoryVariableExpenseRequest.setUser(userContext.returnUserCorrespondingToTheRequest());
        categoryVariableExpenseRequest.setCreatedAt(LocalDateTime.now());
        categoryVariableExpenseRequest.setDeleted(false);

        CategoryVariableExpenseDTO categoryVariableExpenseDTO = modelMapper.map(categoryVariableExpenseService.save(categoryVariableExpenseRequest), CategoryVariableExpenseDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTO);
    }

    @PostMapping(value = "/accounts-category-expense/batch",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<CategoryVariableExpenseDTO>> saveAll(@RequestBody @Valid List<CategoryVariableExpensePostVO> categoryVariableExpensePostVO){

        List<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();

        categoryVariableExpensePostVO.forEach(categoryVariableExpensePostVO1 -> {
            CategoryVariableExpense categoryVariableExpense = modelMapper.map(categoryVariableExpensePostVO1,CategoryVariableExpense.class);
            categoryVariableExpense.setDeleted(false);
            categoryVariableExpense.setCreatedAt(LocalDateTime.now());
            categoryVariableExpenses.add(categoryVariableExpense);
        });
        List<CategoryVariableExpenseDTO> categoryVariableExpenseDTOS = new ArrayList<>();

        List<CategoryVariableExpense> categoryVariableExpense = categoryVariableExpenseService.createdSeveral(categoryVariableExpenses);

        categoryVariableExpense.forEach(category -> {
            categoryVariableExpenseDTOS.add(modelMapper.map(category,CategoryVariableExpenseDTO.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpenseDTOS);
    }

    @GetMapping(value = "/accounts-category-expense",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CategoryVariableExpenseDTO>> getAll(){

        ArrayList<CategoryVariableExpense> categoryVariableExpenses = categoryVariableExpenseService.getAll(userContext.returnUserCorrespondingToTheRequest().getId());

        List<CategoryVariableExpenseDTO> categoryVariableExpensesDTO = new ArrayList<>();
        categoryVariableExpenses.forEach(categoryVariableExpense -> {
            categoryVariableExpensesDTO.add(modelMapper.map(categoryVariableExpense,CategoryVariableExpenseDTO.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(categoryVariableExpensesDTO);
    }

    @GetMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryVariableExpenseDTO> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(categoryVariableExpenseService.getById(id),CategoryVariableExpenseDTO.class));
    }

    @PatchMapping(value = "/accounts-category-expense/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleted(@PathVariable String id){

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.getById(id);

        categoryVariableExpense.setDeleted(true);
        categoryVariableExpense.setDeletedAt(LocalDateTime.now());
        categoryVariableExpense.setUpdatedAt(LocalDateTime.now());

        categoryVariableExpenseService.save(categoryVariableExpense);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/accounts-category-expense/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<CategoryVariableExpenseDTO> updated(@RequestBody @Valid CategoryVariableExpensePutVO categoryVariableExpensePutVO,
                                                             @PathVariable String id){

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.getById(id);

        categoryVariableExpense.setName(categoryVariableExpensePutVO.getName() != null ? categoryVariableExpensePutVO.getName() : categoryVariableExpense.getName());
        categoryVariableExpense.setDescription(categoryVariableExpensePutVO.getDescription() != null ? categoryVariableExpensePutVO.getDescription() : categoryVariableExpense.getDescription());
        categoryVariableExpense.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(categoryVariableExpenseService.save(categoryVariableExpense),CategoryVariableExpenseDTO.class));
    }

}
