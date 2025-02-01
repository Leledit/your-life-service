package com.yourlife.your.life.model.vo.finance.installment;

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
public class InstallmentPostVO {
    @NotBlank
    private String description;
    @NotBlank
    private String firstInstallmentDate;
    @NumberFormat
    private Number  value;
    @NumberFormat
    private Number qtd;
}
