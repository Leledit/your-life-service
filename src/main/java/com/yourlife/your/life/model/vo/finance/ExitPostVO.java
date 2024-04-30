package com.yourlife.your.life.model.vo.finance;

import com.yourlife.your.life.model.types.finance.PaymentMethods;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitPostVO {
    @NotBlank
    private String name;
    @NotBlank
    private PaymentMethods paymentMethods;
    @NotBlank
    private Number value;
}
