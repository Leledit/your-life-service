package com.yourlife.your.life.model.dto.finance.benefit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yourlife.your.life.model.types.finance.BenefitType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class BenefitPostDTO {
    @NotBlank
    private String name;
    @NotNull
    private Number valueReceived;
    @NotNull
    private BenefitType type;
    private String description;
}
