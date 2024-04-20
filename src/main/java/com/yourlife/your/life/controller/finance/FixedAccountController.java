package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.vo.finance.FixedAccountChangingVO;
import com.yourlife.your.life.model.vo.finance.FixedAccountRegisterVO;
import com.yourlife.your.life.service.finance.FixedAccountService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/service/api/v1")
public class FixedAccountController {

    @Autowired
    private FixedAccountService fixedAccountService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/accounts-fixed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccountDTO> registerFixedAccount(@RequestBody @Valid FixedAccountRegisterVO fixedAccountRegisterVO){

        FixedAccount account = modelMapper.map(fixedAccountRegisterVO,FixedAccount.class);

        FixedAccountDTO fixedAccounts = fixedAccountService.createdFixedAccount(account);

        return  ResponseEntity.ok(fixedAccounts);
    }

    @GetMapping(value = "/accounts-fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<FixedAccountDTO>>  returnAllRegisteredFixedAccounts(){

        ArrayList<FixedAccountDTO> fixedAccount = fixedAccountService.returnRegisteredFixedAccounts();

        return ResponseEntity.ok(fixedAccount);
    }

    @GetMapping(value = "/account-fixed/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FixedAccountDTO> returningAFixedAccount(@PathVariable String id){

        FixedAccountDTO fixedAccount = fixedAccountService.returningAFixedAccountById(id);

        return ResponseEntity.ok(fixedAccount);
    }

    @PutMapping(value = "/accounts-fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccountDTO> changingDataOnAFixedAccount(@RequestBody @Valid FixedAccountChangingVO fixedAccountChangingVO){

        FixedAccount account = modelMapper.map(fixedAccountChangingVO,FixedAccount.class);

        FixedAccountDTO fixedAccount = fixedAccountService.changingFixedAccount(account);

        return ResponseEntity.ok(fixedAccount);
    }

    @PatchMapping(value = "/account-fixed/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletingAFixedAccount(@PathVariable String id){

        fixedAccountService.deletingAFixedAccount(id);

        return ResponseEntity.noContent().build();
    }


}
