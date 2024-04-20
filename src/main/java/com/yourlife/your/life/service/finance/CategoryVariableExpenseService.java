package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpenseRegisterVO;

import java.util.ArrayList;
import java.util.List;

public interface CategoryVariableExpenseService {

    CategoryVariableExpenseDTO created(CategoryVariableExpense categoryVariableExpense);
    ArrayList<CategoryVariableExpenseDTO> getAll();
    CategoryVariableExpenseDTO getById(String id);
    Void deleted(String id);
    CategoryVariableExpenseDTO update(CategoryVariableExpense categoryVariableExpense);
    List<CategoryVariableExpenseDTO> createdSeveral(List<CategoryVariableExpense> categoryVariableExpenses);
}
