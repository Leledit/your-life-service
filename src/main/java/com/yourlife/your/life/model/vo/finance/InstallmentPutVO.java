package com.yourlife.your.life.model.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentPutVO {
    private String description;
    private String firstInstallmentDate;
    @NumberFormat
    private Number  value;
    @NumberFormat
    private Number qtd;
}
