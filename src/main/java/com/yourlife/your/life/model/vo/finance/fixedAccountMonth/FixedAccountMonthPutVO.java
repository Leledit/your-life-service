package com.yourlife.your.life.model.vo.finance.fixedAccountMonth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedAccountMonthPutVO {
    private String name;
    private Number value;
    private String description;
    private Number dueDate;
}
