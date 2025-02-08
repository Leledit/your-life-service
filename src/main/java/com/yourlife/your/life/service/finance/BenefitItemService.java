package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPostDTO;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPutDTO;
import com.yourlife.your.life.model.entity.finance.BenefitItem;

import java.util.List;

public interface BenefitItemService {
    BenefitItem save(BenefitItemPostDTO benefitItemPostDTO);
    BenefitItem update(BenefitItemPutDTO benefitItemPutDTO, String id);
    List<BenefitItem> findAllByUser();
    void deleted (String id);
}
