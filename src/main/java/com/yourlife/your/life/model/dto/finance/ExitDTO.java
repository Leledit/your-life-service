package com.yourlife.your.life.model.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yourlife.your.life.model.types.finance.PaymentMethods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class ExitDTO {
    private String id;
    private String name;
    private PaymentMethods paymentMethods;
    private Number value;
}
