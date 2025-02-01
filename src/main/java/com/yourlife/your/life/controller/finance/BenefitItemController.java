package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.BenefitItemDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.vo.finance.benefitItem.BenefitItemPostVO;
import com.yourlife.your.life.service.finance.BenefitItemService;
import com.yourlife.your.life.service.finance.BenefitService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class BenefitItemController {

    @Autowired
    private BenefitService benefitService;

    @Autowired
    private BenefitItemService benefitItemService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/benefit/{idBenefit}/item",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BenefitItemDTO> saveBenefitItem(@PathVariable String idBenefit, @RequestBody @Valid BenefitItemPostVO benefitItemPostVO){

        Benefit benefit = benefitService.findById(idBenefit);

        if(benefit == null){
            throw new RuntimeException(ExceptionMessages.BENEFIT_NOT_FOUND);
        }

        BenefitItem benefitItem  = benefitItemService.save(BenefitItem
                .builder()
                .name(benefitItemPostVO.getName())
                .value(benefitItemPostVO.getValue())
                .description(benefitItemPostVO.getDescription())
                .createdAt(LocalDateTime.now())
                .deleted(false)
                .build());

        List<BenefitItem> benefitItems = benefit.getItens();
        benefitItems.add(benefitItem);

        benefit.setItens(benefitItems);
        benefitService.save(benefit);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(benefitItem,BenefitItemDTO.class));
    }
}
