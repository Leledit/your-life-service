package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.benefit.BenefitPostDTO;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;

import java.util.List;

public interface BenefitService {
    Benefit save(BenefitPostDTO benefitPostDTO);

    Benefit findById (String id);

    List<Benefit> findAllByUser(String userId);

    Benefit update(String id, BenefitPutDTO benefitPutDTO);

    void deleted(String id);
}
