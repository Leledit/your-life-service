package com.yourlife.your.life.model.vo.finance.categoryVariableExpense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVariableExpensePostVO {

    private String name;
    private String description;
}
