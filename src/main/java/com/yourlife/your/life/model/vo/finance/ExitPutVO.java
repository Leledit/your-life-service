package com.yourlife.your.life.model.vo.finance;

import com.yourlife.your.life.model.types.finance.PaymentMethods;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitPutVO {
    private String name;
    private PaymentMethods paymentMethods;
    private Number value;
}
