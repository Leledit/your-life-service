package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.finance.Exit;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.repository.finance.ExitRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.service.finance.ExitService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExitServiceImpl implements ExitService {

    @Autowired
    private ExitRepository exitRepository;

    @Autowired
    private UserContext userContext;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @Override
    public Exit save(ExitPostDTO exitPostDTO) {
        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseRepository.findById(exitPostDTO.getCategoryVariableExpense()).orElse(null);

        if(categoryVariableExpense == null){
            throw new RuntimeException(ExceptionMessages.CATEGORY_VARIABLE_EXPENSE_NOT_FOUND);
        }

        LocalDate currentDate = LocalDate.now();
        Exit exit = exitRepository.save(Exit
                        .builder()
                        .name(exitPostDTO.getName())
                        .paymentMethods(exitPostDTO.getPaymentMethods())
                        .value(exitPostDTO.getValue())
                        .deleted(false)
                        .createdAt(LocalDateTime.now())
                        .user(userContext.returnUserCorrespondingToTheRequest())
                        .categoryVariableExpense(categoryVariableExpense)
                        .build());

        Month curretMonth = monthRepository.findByYearAndMonthAndUser_Id(currentDate.getYear(),currentDate.getMonthValue(),userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);
        if(curretMonth != null){
            List<Exit> exits = curretMonth.getExits();
            exits.add(exit);
            curretMonth.setExits(exits);
            monthRepository.save(curretMonth);
        }

        return exit;
    }

    @Override
    public List<Exit> findAllByUser() {
        return exitRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public Exit update(String id, ExitPutDTO exitPutDTO) {
        Exit exit = findExitById(id);

        exit.setName(exitPutDTO.getName() != null ? exitPutDTO.getName() : exit.getName());
        exit.setPaymentMethods(exitPutDTO.getPaymentMethods() != null?exitPutDTO.getPaymentMethods() : exit.getPaymentMethods());
        exit.setValue(exitPutDTO.getValue() != null ? exitPutDTO.getValue() : exit.getValue());
        exit.setUpdatedAt(LocalDateTime.now());

        return exitRepository.save(exit);
    }

    @Override
    public void delete(String id) {
        Exit exit = findExitById(id);

        exit.setDeleted(true);
        exit.setDeletedAt(LocalDateTime.now());

        exitRepository.save(exit);
    }

    private Exit findExitById(String id){
        Exit exit = exitRepository.findByIdAndDeleted(id,false).orElse(null);

        if(exit == null){
            throw  new RuntimeException(ExceptionMessages.EXIT_NOT_FOUND);
        }

        return exit;
    }
}
