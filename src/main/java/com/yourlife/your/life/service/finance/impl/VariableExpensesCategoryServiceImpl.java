package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.finance.Exit;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.repository.finance.ExitRepository;
import com.yourlife.your.life.service.finance.VariableExpensesCategoryService;
import com.yourlife.your.life.utils.UserContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VariableExpensesCategoryServiceImpl implements VariableExpensesCategoryService {

    @Autowired
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserContext userContext;

    @Autowired
    private ExitRepository exitRepository;

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
    public ArrayList<CategoryVariableExpense> getAll(String userId) {
        return categoryVariableExpenseRepository.findAllByUser_IdAndDeleted(userId,false).orElse(null);
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

    /*@Override
    public Exit saveExit(String idCategory, ExitPostDTO exitPostDTO) {
        CategoryVariableExpense categoryVariableExpense = findById(idCategory);

        List<Exit> exits = categoryVariableExpense.getExit();
        Exit exit = exitRepository.save(Exit.builder()
                .name(exitPostDTO.getName())
                .paymentMethods(exitPostDTO.getPaymentMethods())
                .value(exitPostDTO.getValue())
                .deleted(false)
                .createdAt(LocalDateTime.now())
                .build());

        exits.add(exit);
        categoryVariableExpense.setExit(exits);
        categoryVariableExpenseRepository.save(categoryVariableExpense);

        return exit;
    }

    @Override
    public Exit updateExit(String id, ExitPutDTO exitPutDTO) {
        Exit exit = exitRepository.findById(id).orElse(null);
        if(exit == null){
            throw new RuntimeException(ExceptionMessages.EXIT_NOT_FOUND);
        }

        exit.setName(exitPutDTO.getName()!=null?exitPutDTO.getName(): exit.getName());
        exit.setValue(exitPutDTO.getValue()!=null?exitPutDTO.getValue():exit.getValue());
        exit.setPaymentMethods(exitPutDTO.getPaymentMethods()!=null?exitPutDTO.getPaymentMethods():exit.getPaymentMethods());
        exit.setUpdatedAt(LocalDateTime.now());

        return exitRepository.save(exit);
    }
*/
    private CategoryVariableExpense findById(String id){
        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseRepository.findByIdAndDeleted(id,false).orElse(null);

        if(categoryVariableExpense == null || categoryVariableExpense.getDeleted()){
            throw new RuntimeException(ExceptionMessages.NOT_FOUND);
        }

        return categoryVariableExpense;
    }
}
