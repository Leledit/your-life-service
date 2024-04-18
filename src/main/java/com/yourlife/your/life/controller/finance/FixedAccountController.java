package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.FinanceFixedAccountDTO;
import com.yourlife.your.life.model.vo.finance.FinanceChangingFixedAccountVO;
import com.yourlife.your.life.model.vo.finance.FinanceRegisterFixedAccountVO;
import com.yourlife.your.life.service.finance.FixedAccountService;
import jakarta.validation.Valid;
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

    @PostMapping(value = "/accounts/fixed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FinanceFixedAccountDTO> registerFixedAccount(@RequestBody @Valid FinanceRegisterFixedAccountVO financeRegisterFixedAccountVO){

        FinanceFixedAccountDTO fixedAccounts = fixedAccountService.createdFixedAccount(financeRegisterFixedAccountVO);

        return  ResponseEntity.ok(fixedAccounts);
    }

    @GetMapping(value = "/accounts/fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<FinanceFixedAccountDTO>>  returnAllRegisteredFixedAccounts(){

        ArrayList<FinanceFixedAccountDTO> fixedAccount = fixedAccountService.returnRegisteredFixedAccounts();

        return ResponseEntity.ok(fixedAccount);
    }

    @GetMapping(value = "/accounts/fixed/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FinanceFixedAccountDTO> returningAFixedAccount(@PathVariable String id){

        FinanceFixedAccountDTO fixedAccount = fixedAccountService.returningAFixedAccountById(id);

        return ResponseEntity.ok(fixedAccount);
    }

    @PutMapping(value = "/accounts/fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FinanceFixedAccountDTO> changingDataOnAFixedAccount(@RequestBody @Valid FinanceChangingFixedAccountVO financeChangingFixedAccountVO){

        FinanceFixedAccountDTO fixedAccount = fixedAccountService.changingFixedAccount(financeChangingFixedAccountVO);

        return ResponseEntity.ok(fixedAccount);
    }

    @DeleteMapping(value = "/accounts/fixed/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletingAFixedAccount(@PathVariable String id){

        fixedAccountService.deletingAFixedAccount(id);

        return ResponseEntity.noContent().build();
    }
}
