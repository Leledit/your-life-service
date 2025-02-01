package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.vo.finance.benefitItem.BenefitItemPostVO;
import com.yourlife.your.life.repository.finance.BenefitItemReposity;
import com.yourlife.your.life.service.finance.BenefitItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BenefitItemServiceImpl implements BenefitItemService {

    @Autowired
    private BenefitItemReposity benefitItemReposity;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BenefitItem save(BenefitItem benefitItem) {
        return benefitItemReposity.save(benefitItem);
    }
}
