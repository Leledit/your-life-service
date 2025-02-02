package com.yourlife.your.life.model.dto.finance.categoryVariableExpense;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVariableExpensePutDTO {
    private String name;
    private String description;
}
