package com.yourlife.your.life.model.dto.finance.exit;

import com.yourlife.your.life.model.types.finance.PaymentMethods;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExitPostDTO {
    @NotBlank
    private String name;
    @NotNull
    private PaymentMethods paymentMethods;
    @NotNull
    private Number value;
    @NotNull
    private String categoryVariableExpense;
}
