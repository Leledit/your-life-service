package com.yourlife.your.life.model.dto.finance.installment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@AllArgsConstructor
public class InstallmentPostDTO {
    @NotBlank
    private String description;
    @NumberFormat
    private Number  value;
    @NumberFormat
    private Number qtd;
}
