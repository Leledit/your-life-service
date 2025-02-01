package com.yourlife.your.life.model.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.types.finance.BenefitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class BenefitDTO {
    private String name;
    private Number valueReceived;
    private BenefitType type;
    private String description;
    private List<BenefitItem> itens;
    private LocalDateTime createdAt;
}
