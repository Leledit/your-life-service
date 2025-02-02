package com.yourlife.your.life.model.dto.finance.FixedAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedAccountPutDTO {
    private String name;
    private Number value;
    private String description;
    private Number dueDate;
}
