package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CategoryVariableExpenseServiceImpl implements CategoryVariableExpenseService {

    @Autowired
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @Autowired
    private UserContext userContext;

    @Override
    public CategoryVariableExpense save(CategoryVariableExpensePostDTO categoryVariableExpensePostDTO) {
        return categoryVariableExpenseRepository.save(CategoryVariableExpense
                        .builder()
                        .name(categoryVariableExpensePostDTO.getName())
                        .description(categoryVariableExpensePostDTO.getDescription())
                        .deleted(false)
                        .createdAt(LocalDateTime.now())
                        .user(userContext.returnUserCorrespondingToTheRequest())
                        .build());
    }

    @Override
    public ArrayList<CategoryVariableExpense> getAll() {
        return categoryVariableExpenseRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public CategoryVariableExpense getById(String id) {
        return findById(id);
    }

    @Override
    public CategoryVariableExpense update(String id, CategoryVariableExpensePutDTO categoryVariableExpensePutDTO) {
        CategoryVariableExpense categoryVariableExpense = getById(id);

        categoryVariableExpense.setName(categoryVariableExpensePutDTO.getName() != null ? categoryVariableExpensePutDTO.getName() : categoryVariableExpense.getName());
        categoryVariableExpense.setDescription(categoryVariableExpensePutDTO.getDescription() != null ? categoryVariableExpensePutDTO.getDescription() : categoryVariableExpense.getDescription());
        categoryVariableExpense.setUpdatedAt(LocalDateTime.now());

        categoryVariableExpenseRepository.save(categoryVariableExpense);

        return categoryVariableExpense;
    }

    @Override
    public void delete(String id) {
        CategoryVariableExpense categoryVariableExpense = findById(id);
        categoryVariableExpense.setDeleted(true);
        categoryVariableExpense.setDeletedAt(LocalDateTime.now());
        categoryVariableExpense.setUpdatedAt(LocalDateTime.now());

        categoryVariableExpenseRepository.save(categoryVariableExpense);
    }

    private CategoryVariableExpense findById(String id){
        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseRepository.findByIdAndDeleted(id,false).orElse(null);

        if(categoryVariableExpense == null || categoryVariableExpense.getDeleted()){
            throw new RuntimeException(ExceptionMessages.NOT_FOUND);
        }

        return categoryVariableExpense;
    }
}
