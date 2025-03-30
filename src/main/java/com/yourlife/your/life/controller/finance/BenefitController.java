package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.benefit.BenefitPostDTO;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.service.finance.BenefitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class BenefitController {

    @Autowired
    private BenefitService benefitService;

    @ResponseBody
    @PostMapping(value = "/benefit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Benefit> saveBenefit(@Valid @RequestBody BenefitPostDTO benefitPostDTO){
        Benefit benefit = benefitService.save(benefitPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(benefit);
    }

    @ResponseBody
    @GetMapping(value = "/benefit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Benefit>> getAllBenefits(){
        return ResponseEntity.status(HttpStatus.OK).body(benefitService.findAllByUser());
    }

    @ResponseBody
    @GetMapping(value = "/benefit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Benefit> getBenefitById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(benefitService.findById(id));
    }

    @ResponseBody
    @PutMapping(value = "/benefit/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Benefit> updateBenefit(@PathVariable String id,
                                                 @RequestBody BenefitPutDTO benefitPutDTO){

        Benefit benefit = benefitService.update(id,benefitPutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(benefit);
    }

    @ResponseBody
    @DeleteMapping(value = "/benefit/{id}/deleted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletedBenefit(@PathVariable String id){
        benefitService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}