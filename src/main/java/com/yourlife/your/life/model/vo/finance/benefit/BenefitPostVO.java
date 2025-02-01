package com.yourlife.your.life.model.vo.finance.benefit;

import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.types.finance.BenefitType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenefitPostVO {

    @NotBlank
    private String name;
    private Number valueReceived;
    private BenefitType type;
    private String description;
}
