package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPostDTO;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPutDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.repository.finance.FixedAccountRepository;
import com.yourlife.your.life.service.finance.FixedAccountService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class FixedAccountServiceImpl implements FixedAccountService {

    @Autowired
    private FixedAccountRepository fixedAccountRepository;

    @Autowired
    private UserContext userContext;

    @Override
    public FixedAccount save(FixedAccountPostDTO fixedAccountPostDTO) {
        return fixedAccountRepository.save(FixedAccount.builder()
                        .name(fixedAccountPostDTO.getName())
                        .description(fixedAccountPostDTO.getDescription())
                        .value(fixedAccountPostDTO.getValue())
                        .dueDate(fixedAccountPostDTO.getDueDate())
                        .user(userContext.returnUserCorrespondingToTheRequest())
                        .dueDate(fixedAccountPostDTO.getDueDate())
                        .deleted(false)
                        .createdAt(LocalDateTime.now())
                        .build());
    }

    @Override
    public ArrayList<FixedAccount> findAll(String userId) {
        return fixedAccountRepository.findAllByUser_IdAndDeleted(userId,false).orElse(null);
    }

    @Override
    public FixedAccount findById(String id) {
        return findByIdFixedAccount(id);
    }

    @Override
    public FixedAccount update(String id, FixedAccountPutDTO fixedAccountPutDTO) {
        FixedAccount account = findByIdFixedAccount(id);

        account.setName(fixedAccountPutDTO.getName()!=null? fixedAccountPutDTO.getName(): account.getName());
        account.setValue(fixedAccountPutDTO.getValue()!=null? fixedAccountPutDTO.getValue() : account.getValue());
        account.setDescription(fixedAccountPutDTO.getDescription()!=null? fixedAccountPutDTO.getDescription() : account.getDescription());
        account.setDueDate(fixedAccountPutDTO.getDueDate()!=null? fixedAccountPutDTO.getDueDate() : account.getDueDate());
        account.setUpdatedAt(LocalDateTime.now());

        return fixedAccountRepository.save(account);
    }

    @Override
    public void delete(String id) {
        FixedAccount account = findByIdFixedAccount(id);

        account.setDeleted(true);
        account.setDeletedAt(LocalDateTime.now());

        fixedAccountRepository.save(account);
    }

    private FixedAccount findByIdFixedAccount(String id){
        FixedAccount fixedAccount = fixedAccountRepository.findById(id).orElse(null);

        if(fixedAccount == null || fixedAccount.getDeleted()){
            throw new RuntimeException(ExceptionMessages.NOT_FOUND);
        }

        return fixedAccount;
    }
}
