package com.yourlife.your.life.model.dto.finance.installment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class InstallmentPutDTO {
    private String description;
    private Number  value;
    private Number qtd;
}
