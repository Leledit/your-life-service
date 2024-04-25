package com.yourlife.your.life.model.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class InstallmentDTO {
    private String id;
    private String description;
    private String  firstInstallmentDate;
    private String current;
    private Number value;
    private Number qtd;
}
