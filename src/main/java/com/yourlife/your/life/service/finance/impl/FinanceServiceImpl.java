package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.dto.finance.FinanceFixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.model.vo.finance.FinanceRegisterFixedAccountVO;
import com.yourlife.your.life.repository.finance.FinanceFixedAccountRepository;
import com.yourlife.your.life.service.finance.FinanceService;
import com.yourlife.your.life.utils.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceFixedAccountRepository financeFixedAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FinanceFixedAccountDTO createdFixedAccount(FinanceRegisterFixedAccountVO financeRegisterFixedAccountVO) {

        FixedAccount fixedAccount = modelMapper.map(financeRegisterFixedAccountVO, FixedAccount.class);

        User user = returnUserCorrespondingToTheRequest();

        fixedAccount.setUser(user);

        FixedAccount fixedAccountSalve =  financeFixedAccountRepository.save(fixedAccount);

        return  modelMapper.map(fixedAccountSalve,FinanceFixedAccountDTO.class);
    }

    private User returnUserCorrespondingToTheRequest(){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        UserAuth userAuth = (UserAuth) authentication.getPrincipal();

        return modelMapper.map(userAuth,User.class);
    }
}
