package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.vo.finance.benefitItem.BenefitItemPostVO;
import org.springframework.stereotype.Service;

public interface BenefitItemService {

    BenefitItem save(BenefitItem benefitItem);
}
