package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPostDTO;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.repository.finance.BenefitRepository;
import com.yourlife.your.life.service.finance.BenefitService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private UserContext userContext;
    @Override
    public Benefit save(BenefitPostDTO benefitPostDTO) {
        return benefitRepository.save(Benefit
                .builder()
                .name(benefitPostDTO.getName())
                .valueReceived(benefitPostDTO.getValueReceived())
                .type(benefitPostDTO.getType())
                .description(benefitPostDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .user(userContext.returnUserCorrespondingToTheRequest())
                .deleted(false)
                .build());
    }

    @Override
    public Benefit findById(String id) {
        return findByIdBenefit(id);
    }

    @Override
    public List<Benefit> findAllByUser() {
        return benefitRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public Benefit update(String id, BenefitPutDTO benefitPutDTO) {
        Benefit benefit = findByIdBenefit(id);

        benefit.setName(benefitPutDTO.getName()!=null?benefitPutDTO.getName():benefit.getName());
        benefit.setValueReceived(benefitPutDTO.getValueReceived()!=null?benefitPutDTO.getValueReceived(): benefit.getValueReceived());
        benefit.setType(benefitPutDTO.getType()!=null? benefitPutDTO.getType():benefit.getType());
        benefit.setDescription(benefit.getDescription()!=null?benefitPutDTO.getDescription():benefit.getDescription());
        benefit.setUpdatedAt(LocalDateTime.now());

        benefitRepository.save(benefit);

        return benefit;
    }

    @Override
    public void deleted(String id) {
        Benefit benefit = findByIdBenefit(id);

        benefit.setDeleted(true);
        benefit.setDeletedAt(LocalDateTime.now());

        benefitRepository.save(benefit);
    }

    private Benefit findByIdBenefit(String id){
        Benefit benefit = benefitRepository.findByIdAndDeleted(id,false).orElse(null);
        if(benefit == null){
            throw new RuntimeException(ExceptionMessages.BENEFIT_NOT_FOUND);
        }

        return benefit;
    }
}
