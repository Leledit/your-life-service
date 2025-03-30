package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPostDTO;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPutDTO;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.service.finance.BenefitItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class BenefitItemController {

    @Autowired
    private BenefitItemService benefitItemService;

    @ResponseBody
    @PostMapping(value = "/benefit-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BenefitItem> saveBenefitItem(@RequestBody @Valid BenefitItemPostDTO benefitItemPostDTO){
        BenefitItem benefitItem = benefitItemService.save(benefitItemPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(benefitItem);
    }

    @ResponseBody
    @GetMapping(value = "/benefit-item", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BenefitItem>> getAllBenefitItem(){
        List<BenefitItem> benefitItems = benefitItemService.findAllByUser();
        return ResponseEntity.status(HttpStatus.OK).body(benefitItems);
    }

    @ResponseBody
    @PutMapping(value = "/benefit-item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BenefitItem> updateBenefitItem(@RequestBody BenefitItemPutDTO benefitItemPutDTO,
                                                         @PathVariable String id){

        BenefitItem benefitItem = benefitItemService.update(benefitItemPutDTO,id);
        return ResponseEntity.status(HttpStatus.OK).body(benefitItem);
    }

    @DeleteMapping(value = "/benefit-item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBenefitItem(@PathVariable String id){
        benefitItemService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
