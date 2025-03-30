package com.yourlife.your.life.model.dto.finance.categoryVariableExpense;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVariableExpensePostDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
}
