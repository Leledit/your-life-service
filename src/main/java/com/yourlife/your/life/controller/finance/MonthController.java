package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.Month.MonthAddFixedAccountDTO;
import com.yourlife.your.life.model.entity.finance.*;
import com.yourlife.your.life.service.finance.MonthService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/service/api/v1")
public class MonthController {

    @Autowired
    private MonthService monthService;

    @PostMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Month> save(){
        Month month = monthService.save();
        return ResponseEntity.status(HttpStatus.OK).body(month);
    }

    @GetMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Month>> getAll(){
        List<Month> months = monthService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(months);
    }

    @GetMapping(value = "/month/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Month> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(monthService.findById(id));
    }

    @GetMapping(value = "/month/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Month> getByMonthAndYear(@Parameter Integer monthNumber, @Parameter Integer yearNumber) {
        Month month = monthService.findByMonth(monthNumber,yearNumber);
        return ResponseEntity.status(HttpStatus.OK).body(month);
    }

    @PostMapping(value = "/month/{id}/fixed-account", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Month> saveMonth (@PathVariable String id, @RequestBody @Valid MonthAddFixedAccountDTO monthAddFixedAccountDTO){
        Month month = monthService.addFixedAccount(id,monthAddFixedAccountDTO);
        return ResponseEntity.status(HttpStatus.OK).body(month);
    }
}