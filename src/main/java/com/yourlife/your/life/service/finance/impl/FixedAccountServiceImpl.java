package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.dto.finance.FixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.repository.finance.FixedAccountRepository;
import com.yourlife.your.life.service.finance.FixedAccountService;
import org.apache.commons.lang3.StringUtils;
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
public class FixedAccountServiceImpl implements FixedAccountService {

    @Autowired
    private FixedAccountRepository fixedAccountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public FixedAccountDTO createdFixedAccount(FixedAccount fixedAccountRequest) {

        User user = returnUserCorrespondingToTheRequest();

        fixedAccountRequest.setUser(user);
        fixedAccountRequest.setCreatedAt(LocalDateTime.now());
        fixedAccountRequest.setDeleted(false);

        FixedAccount fixedAccountSalve =  fixedAccountRepository.save(fixedAccountRequest);

        return  modelMapper.map(fixedAccountSalve, FixedAccountDTO.class);
    }

    @Override
    public ArrayList<FixedAccountDTO> returnRegisteredFixedAccounts() {

        User user = returnUserCorrespondingToTheRequest();

        Optional<ArrayList<FixedAccount>> fixedAccountOptional = fixedAccountRepository.findAllByUser_Id(user.getId());

        List<FixedAccount> listFixedAccount = new ArrayList<>();

        fixedAccountOptional.ifPresent(listFixedAccount::addAll);

        ArrayList<FixedAccountDTO> fixedAccountDTOS = new ArrayList<>();
        listFixedAccount.forEach(fixedAccount -> {
            if(!fixedAccount.getDeleted()){
                fixedAccountDTOS.add(modelMapper.map(fixedAccount, FixedAccountDTO.class));
            }
        }
        );

        return fixedAccountDTOS;
    }

    @Override
    public FixedAccountDTO returningAFixedAccountById(String id) {

        Optional<FixedAccount> fixedAccount = fixedAccountRepository.findById(id);

        if(fixedAccount.isEmpty()){
            throw new RuntimeException("Nenhuma conta foi encontrada!");
        }

        return modelMapper.map(fixedAccount.get(), FixedAccountDTO.class);
    }

    @Override
    public FixedAccountDTO changingFixedAccount(FixedAccount fixedAccountRequest) {

        Optional<FixedAccount> fixedAccountOptional = fixedAccountRepository.findById(fixedAccountRequest.getId());

        if(fixedAccountOptional.isEmpty()){
            throw new RuntimeException("O id informado é invalido");
        }

        FixedAccount fixedAccount = fixedAccountOptional.get();

        if(fixedAccount.getDeleted()){
            throw new RuntimeException("O id informado é invalido");
        }

        if(StringUtils.isNotBlank(fixedAccountRequest.getName())){
            fixedAccount.setName(fixedAccountRequest.getName());
        }

        if(fixedAccountRequest.getValue() != null && fixedAccountRequest.getValue().intValue() != 0) {
            fixedAccount.setValue(fixedAccountRequest.getValue());
        }

        if(StringUtils.isNotBlank(fixedAccountRequest.getDescription())){
            fixedAccount.setDescription(fixedAccountRequest.getDescription());
        }

        if(fixedAccountRequest.getDueDate() != null && fixedAccountRequest.getDueDate().intValue() != 0 ){
            fixedAccount.setDueDate(fixedAccountRequest.getDueDate());
        }

        fixedAccount.setUpdatedAt(LocalDateTime.now());

        FixedAccount fixedAccountSalved = fixedAccountRepository.save(fixedAccount);

        return modelMapper.map(fixedAccountSalved, FixedAccountDTO.class);
    }

    @Override
    public Void deletingAFixedAccount(String id) {

        Optional<FixedAccount> fixedAccountOptional = fixedAccountRepository.findById(id);

        if(fixedAccountOptional.isEmpty()){
            throw new RuntimeException("O id informado não é valido!");
        }

        FixedAccount fixedAccount = fixedAccountOptional.get();
        fixedAccount.setDeleted(true);
        fixedAccount.setUpdatedAt(LocalDateTime.now());
        fixedAccount.setDeletedAt(LocalDateTime.now());

        fixedAccountRepository.save(fixedAccount);

        return null;
    }


    //isolar esse metodo assim que possivel
    private User returnUserCorrespondingToTheRequest(){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        UserAuth userAuth = (UserAuth) authentication.getPrincipal();

        return modelMapper.map(userAuth,User.class);
    }
}
