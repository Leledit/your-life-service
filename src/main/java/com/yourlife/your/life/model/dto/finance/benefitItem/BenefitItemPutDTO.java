package com.yourlife.your.life.model.dto.finance.benefitItem;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitItemPutDTO {
    private String name;
    private Number value;
    private String description;
}
