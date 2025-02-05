package com.yourlife.your.life.model.dto.finance.installment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentPostDTO {
    @NotBlank
    private String description;
    @NumberFormat
    private Number  value;
    @NumberFormat
    private Number qtd;
}
