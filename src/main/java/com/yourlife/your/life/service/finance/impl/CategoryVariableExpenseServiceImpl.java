package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryVariableExpenseServiceImpl implements CategoryVariableExpenseService {

    @Autowired
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryVariableExpense save(CategoryVariableExpense categoryVariableExpense) {
        return categoryVariableExpenseRepository.save(categoryVariableExpense);
    }

    @Override
    public ArrayList<CategoryVariableExpense> getAll(String userId) {
        return categoryVariableExpenseRepository.findAllByUser_IdAndDeleted(userId,false).orElse(null);
    }

    @Override
    public CategoryVariableExpense getById(String id) {

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseRepository.findById(id).orElse(null);

        if(categoryVariableExpense == null || categoryVariableExpense.getDeleted()){
            throw new RuntimeException(ExceptionMessages.NOT_FOUND);
        }

        return categoryVariableExpense;
    }

    @Override
    public List<CategoryVariableExpense> createdSeveral(List<CategoryVariableExpense> categoryVariableExpenses) {
        return categoryVariableExpenseRepository.saveAll(categoryVariableExpenses);
    }

}
