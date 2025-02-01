package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.vo.finance.benefit.BenefitPostVO;

public interface BenefitService {
    Benefit save(Benefit benefit);

    Benefit findById (String id);
}
