package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.vo.finance.FixedAccountChangingVO;
import com.yourlife.your.life.model.vo.finance.FixedAccountRegisterVO;
import com.yourlife.your.life.service.finance.FixedAccountService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/service/api/v1")
public class FixedAccountController {

    @Autowired
    private FixedAccountService fixedAccountService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserContext userContext;

    @PostMapping(value = "/accounts-fixed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccountDTO> register(@RequestBody @Valid FixedAccountRegisterVO fixedAccountRegisterVO){

        FixedAccount account = modelMapper.map(fixedAccountRegisterVO,FixedAccount.class);
        account.setUser(userContext.returnUserCorrespondingToTheRequest());
        account.setCreatedAt(LocalDateTime.now());
        account.setDeleted(false);

        return  ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(fixedAccountService.save(account),FixedAccountDTO.class));
    }

    @GetMapping(value = "/accounts-fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<FixedAccountDTO>>  getAll(){

        ArrayList<FixedAccount> fixedAccounts = fixedAccountService.getAll(userContext.returnUserCorrespondingToTheRequest().getId());

        ArrayList<FixedAccountDTO> fixedAccountDTO = new ArrayList<>();
        fixedAccounts.forEach(fixedAccount -> {
            fixedAccountDTO.add(modelMapper.map(fixedAccount,FixedAccountDTO.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(fixedAccountDTO);
    }

    @GetMapping(value = "/account-fixed/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FixedAccountDTO> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(fixedAccountService.getById(id),FixedAccountDTO.class));
    }

    @PutMapping(value = "/accounts-fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccountDTO> update(@RequestBody @Valid FixedAccountChangingVO fixedAccountChangingVO){

        FixedAccount fixedAccount = fixedAccountService.getById(fixedAccountChangingVO.getId());

        fixedAccount.setName(fixedAccountChangingVO.getName() != null ? fixedAccountChangingVO.getName() : fixedAccount.getName());
        fixedAccount.setValue(fixedAccountChangingVO.getValue() != null ? fixedAccountChangingVO.getValue() : fixedAccount.getValue());
        fixedAccount.setDescription(fixedAccountChangingVO.getDescription() != null ? fixedAccountChangingVO.getDescription() : fixedAccount.getDescription());
        fixedAccount.setDueDate(fixedAccountChangingVO.getDueDate() != null ? fixedAccountChangingVO.getDueDate() : fixedAccount.getDueDate());
        fixedAccount.setUpdatedAt(LocalDateTime.now());

        FixedAccount fixedAccountSave = fixedAccountService.save(fixedAccount);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(fixedAccountSave,FixedAccountDTO.class));
    }


    @PatchMapping(value = "/account-fixed/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleting(@PathVariable String id){

        FixedAccount fixedAccount = fixedAccountService.getById(id);

        fixedAccount.setDeleted(true);
        fixedAccount.setUpdatedAt(LocalDateTime.now());
        fixedAccount.setDeletedAt(LocalDateTime.now());

        fixedAccountService.save(fixedAccount);
        return ResponseEntity.status(HttpStatus.OK).build();

    }
}
