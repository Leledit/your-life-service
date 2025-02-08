package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPostDTO;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPutDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.service.finance.FixedAccountService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class FixedAccountController {

    @Autowired
    private FixedAccountService fixedAccountService;

    @PostMapping(value = "/fixed-account", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccount> saveFixedAccount(@RequestBody @Valid FixedAccountPostDTO fixedAccountPostDTO){
        FixedAccount fixedAccount = fixedAccountService.save(fixedAccountPostDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(fixedAccount);
    }

    @GetMapping(value = "/fixed-account", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FixedAccount>> getAllFixedAccounts(){
        List<FixedAccount> fixedAccounts = fixedAccountService.findAllByUser();
        return ResponseEntity.status(HttpStatus.OK).body(fixedAccounts);
    }

    @GetMapping(value = "/fixed-account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FixedAccount> getFixedAccountById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(fixedAccountService.findById(id));
    }

    @PutMapping(value = "/fixed-account/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccount> updatedFixedAccount(@RequestBody @Valid FixedAccountPutDTO fixedAccountPutDTO, @PathVariable String id){
        FixedAccount fixedAccount = fixedAccountService.update(id,fixedAccountPutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(fixedAccount);
    }

    @PatchMapping(value = "/fixed-account/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletedFixedAccount(@PathVariable String id){
        fixedAccountService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
