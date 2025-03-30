package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.entity.finance.*;
import com.yourlife.your.life.service.finance.MonthService;
import io.swagger.v3.oas.annotations.Parameter;
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

    @ResponseBody
    @PostMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Month> saveMonth(){
        Month month = monthService.save();
        return ResponseEntity.status(HttpStatus.CREATED).body(month);
    }

    @ResponseBody
    @GetMapping(value = "/month", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Month>> getAllMonth(){
        List<Month> months = monthService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(months);
    }

    @ResponseBody
    @GetMapping(value = "/month/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Month> getMonthById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(monthService.findById(id));
    }

    @ResponseBody
    @GetMapping(value = "/month/data", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Month> getByMonthAndYear(@Parameter Integer monthNumber,
                                                   @Parameter Integer yearNumber) {

        Month month = monthService.findByMonth(monthNumber, yearNumber);
        return ResponseEntity.status(HttpStatus.OK).body(month);
    }
}