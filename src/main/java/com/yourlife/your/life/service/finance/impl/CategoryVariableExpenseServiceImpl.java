package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpenseRegisterVO;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryVariableExpenseServiceImpl implements CategoryVariableExpenseService {

    @Autowired
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryVariableExpenseDTO created(CategoryVariableExpense categoryVariableExpense) {

        User user = returnUserCorrespondingToTheRequest();

        categoryVariableExpense.setUser(user);
        categoryVariableExpense.setCreatedAt(LocalDateTime.now());
        categoryVariableExpense.setDeleted(false);

        CategoryVariableExpense categoryVariableExpenseSave = categoryVariableExpenseRepository.save(categoryVariableExpense);

        return modelMapper.map(categoryVariableExpenseSave,CategoryVariableExpenseDTO.class);

    }

    @Override
    public ArrayList<CategoryVariableExpenseDTO> getAll() {

        User user = returnUserCorrespondingToTheRequest();

        Optional<ArrayList<CategoryVariableExpense>> categoryVariableExpenseOptional = categoryVariableExpenseRepository.findAllByUser_Id(user.getId());

        List<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();

        categoryVariableExpenseOptional.ifPresent(categoryVariableExpenses::addAll);

        ArrayList<CategoryVariableExpenseDTO> categoryVariableExpenseDTO = new ArrayList<>();
        categoryVariableExpenses.forEach(categoryVariableExpense -> {
            if(!categoryVariableExpense.getDeleted()){
                categoryVariableExpenseDTO.add(modelMapper.map(categoryVariableExpense,CategoryVariableExpenseDTO.class));
            }
        });

        return categoryVariableExpenseDTO;
    }

    @Override
    public CategoryVariableExpenseDTO getById(String id) {
        return modelMapper.map(findById(id),CategoryVariableExpenseDTO.class);
    }

    @Override
    public Void deleted(String id) {

        CategoryVariableExpense categoryVariableExpense = findById(id);

        categoryVariableExpense.setDeleted(true);
        categoryVariableExpense.setDeletedAt(LocalDateTime.now());
        categoryVariableExpense.setUpdatedAt(LocalDateTime.now());

        categoryVariableExpenseRepository.save(categoryVariableExpense);

        return null;
    }

    @Override
    public CategoryVariableExpenseDTO update(CategoryVariableExpense categoryVariableExpenseRequest) {

        Optional<CategoryVariableExpense> categoryVariableExpenseFound = categoryVariableExpenseRepository.findById(categoryVariableExpenseRequest.getId());

        if(categoryVariableExpenseFound.isEmpty()){
            throw new RuntimeException("ID_NOT_FOUND");
        }

        CategoryVariableExpense categoryVariableExpenseData = returnUpdatedValuesCategoryVariableExpense(categoryVariableExpenseRequest, categoryVariableExpenseFound);

        categoryVariableExpenseRepository.save(categoryVariableExpenseData);
        
        return modelMapper.map(categoryVariableExpenseData,CategoryVariableExpenseDTO.class);
    }

    @Override
    public List<CategoryVariableExpenseDTO> createdSeveral(List<CategoryVariableExpense> categoryVariableExpenses) {

        List<CategoryVariableExpense> categoryVariableExpensesSave = categoryVariableExpenseRepository.saveAll(categoryVariableExpenses);

        List<CategoryVariableExpenseDTO> categoryVariableExpenseDTOS = new ArrayList<>();

        categoryVariableExpensesSave.forEach(categoryVariableExpense -> {
            categoryVariableExpenseDTOS.add(modelMapper.map(categoryVariableExpense,CategoryVariableExpenseDTO.class));
        });

        return categoryVariableExpenseDTOS;
    }

    private static CategoryVariableExpense returnUpdatedValuesCategoryVariableExpense(CategoryVariableExpense categoryVariableExpenseRequest, Optional<CategoryVariableExpense> categoryVariableExpenseFound) {
        CategoryVariableExpense categoryVariableExpenseData = categoryVariableExpenseFound.get();

        if(categoryVariableExpenseData.getDeleted()){
            throw new RuntimeException("ID_NOT_FOUND");
        }

        categoryVariableExpenseData.setName(categoryVariableExpenseRequest.getName() != null ? categoryVariableExpenseRequest.getName():categoryVariableExpenseData.getName());
        categoryVariableExpenseData.setDescription(categoryVariableExpenseRequest.getDescription() != null ? categoryVariableExpenseRequest.getDescription() : categoryVariableExpenseData.getDescription());
        categoryVariableExpenseData.setUpdatedAt(LocalDateTime.now());
        return categoryVariableExpenseData;
    }


    private CategoryVariableExpense findById(String id){
        Optional<CategoryVariableExpense> categoryVariableExpenseFound = categoryVariableExpenseRepository.findById(id);

        if(categoryVariableExpenseFound.isEmpty()){
            throw new RuntimeException("ID_NOT_FOUND");
        }
        return  categoryVariableExpenseFound.get();
    }

    //isolar esse metodo assim que possivel
    private User returnUserCorrespondingToTheRequest(){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        UserAuth userAuth = (UserAuth) authentication.getPrincipal();

        return modelMapper.map(userAuth,User.class);
    }
}
