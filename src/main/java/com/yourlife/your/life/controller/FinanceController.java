package com.yourlife.your.life.controller;

import com.yourlife.your.life.model.dto.finance.FinanceFixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.model.vo.finance.FinanceRegisterFixedAccountVO;
import com.yourlife.your.life.service.finance.FinanceService;
import com.yourlife.your.life.utils.Logger;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.EntityReference;
import java.util.ArrayList;

@RestController
@RequestMapping("/service/api/v1")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @PostMapping(value = "/accounts/fixed", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FinanceFixedAccountDTO> registerFixedAccount(@RequestBody @Valid FinanceRegisterFixedAccountVO financeRegisterFixedAccountVO){

        FinanceFixedAccountDTO fixedAccounts = financeService.createdFixedAccount(financeRegisterFixedAccountVO);

        return  ResponseEntity.ok(fixedAccounts);
    }

    @GetMapping(value = "/accounts/fixed",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<FinanceFixedAccountDTO>>  returnAllRegisteredFixedAccounts(){

        ArrayList<FinanceFixedAccountDTO> fixedAccount = financeService.returnRegisteredFixedAccounts();

        return ResponseEntity.ok(fixedAccount);
    }

}
