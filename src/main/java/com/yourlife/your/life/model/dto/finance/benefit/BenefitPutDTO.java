package com.yourlife.your.life.model.dto.finance.benefit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yourlife.your.life.model.types.finance.BenefitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class BenefitPutDTO {
    private String name;
    private Number valueReceived;
    private BenefitType type;
    private String description;
}
