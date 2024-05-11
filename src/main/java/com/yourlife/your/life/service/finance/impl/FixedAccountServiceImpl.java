package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.repository.finance.FixedAccountRepository;
import com.yourlife.your.life.service.finance.FixedAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class FixedAccountServiceImpl implements FixedAccountService {

    @Autowired
    private FixedAccountRepository fixedAccountRepository;

    @Override
    public FixedAccount save(FixedAccount fixedAccountRequest) {
        return fixedAccountRepository.save(fixedAccountRequest);
    }

    @Override
    public ArrayList<FixedAccount> getAll(String userId) {
        return fixedAccountRepository.findAllByUser_IdAndDeleted(userId,false).orElse(null);
    }

    @Override
    public FixedAccount getById(String id) {

        FixedAccount fixedAccount = fixedAccountRepository.findById(id).orElse(null);

        if(fixedAccount == null || fixedAccount.getDeleted()){
            throw new RuntimeException(ExceptionMessages.NOT_FOUND);
        }

        return fixedAccount;
    }
}
