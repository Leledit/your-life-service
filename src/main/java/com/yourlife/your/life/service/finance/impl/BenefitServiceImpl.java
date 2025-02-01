package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.vo.finance.benefit.BenefitPostVO;
import com.yourlife.your.life.repository.finance.BenefitRepository;
import com.yourlife.your.life.service.finance.BenefitService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class BenefitServiceImpl implements BenefitService {

    @Autowired
    private BenefitRepository benefitRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Benefit save(Benefit benefit) {
        return benefitRepository.save(benefit);
    }

    @Override
    public Benefit findById(String id) {
        return benefitRepository.findById(id).orElse(null);
    }
}
