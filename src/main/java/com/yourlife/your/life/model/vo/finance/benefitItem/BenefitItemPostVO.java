package com.yourlife.your.life.model.vo.finance.benefitItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitItemPostVO {
    private String name;
    private Number value;
    private String description;
}
