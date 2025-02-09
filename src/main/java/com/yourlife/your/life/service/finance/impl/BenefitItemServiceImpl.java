package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPostDTO;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.repository.finance.BenefitItemReposity;
import com.yourlife.your.life.repository.finance.BenefitRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.service.finance.BenefitItemService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BenefitItemServiceImpl implements BenefitItemService {

    @Autowired
    private BenefitItemReposity benefitItemReposity;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private UserContext userContext;

    @Autowired
    private BenefitRepository benefitRepository;

    @Override
    public BenefitItem save(BenefitItemPostDTO benefitItemPostDTO) {
        Benefit benefit = getBenefitById(benefitItemPostDTO.getBenefitId());

        LocalDateTime currentDate = LocalDateTime.now();
        BenefitItem benefitItem = benefitItemReposity.save(BenefitItem
                .builder()
                .name(benefitItemPostDTO.getName())
                .value(benefitItemPostDTO.getValue())
                .description(benefitItemPostDTO.getDescription())
                .createdAt(currentDate)
                .deleted(false)
                .user(userContext.returnUserCorrespondingToTheRequest())
                .benefit(benefit)
                .build());

        Month curretMonth = monthRepository.findByYearAndMonthAndUser_Id(currentDate.getYear(),currentDate.getMonthValue(),userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);
        if(curretMonth != null) {
            List<BenefitItem> benefitItems = curretMonth.getBenefitItems();
            benefitItems.add(benefitItem);
            curretMonth.setBenefitItems(benefitItems);
            monthRepository.save(curretMonth);
        }

        return benefitItem;
    }

    @Override
    public BenefitItem update(BenefitItemPutDTO benefitItemPutDTO, String id) {
        BenefitItem benefitItem = getBenefitItemById(id);

        benefitItem.setName(benefitItemPutDTO.getName() != null ? benefitItemPutDTO.getName() : benefitItem.getName());
        benefitItem.setValue(benefitItemPutDTO.getValue() != null ? benefitItemPutDTO.getValue() : benefitItem.getValue());
        benefitItem.setDescription(benefitItemPutDTO.getDescription() != null ? benefitItemPutDTO.getDescription() : benefitItem.getDescription());
        benefitItem.setUpdatedAt(LocalDateTime.now());

        return benefitItemReposity.save(benefitItem);
    }

    @Override
    public List<BenefitItem> findAllByUser() {
        return benefitItemReposity.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public void deleted(String id) {
        BenefitItem benefitItem = getBenefitItemById(id);

        benefitItem.setDeleted(true);
        benefitItem.setDeletedAt(LocalDateTime.now());

        benefitItemReposity.save(benefitItem);
    }

    private Benefit getBenefitById(String id){
        Benefit benefit = benefitRepository.findByIdAndDeleted(id,false).orElse(null);

        if(benefit == null){
            throw new RuntimeException(ExceptionMessages.BENEFIT_NOT_FOUND);
        }
        return  benefit;
    }

    private BenefitItem getBenefitItemById(String id){
        BenefitItem benefitItem = benefitItemReposity.findByIdAndDeleted(id,false).orElse(null);

        if(benefitItem == null){
            throw new RuntimeException(ExceptionMessages.BENEFIT_ITEM_NOT_FOUND);
        }
        return  benefitItem;
    }
}
