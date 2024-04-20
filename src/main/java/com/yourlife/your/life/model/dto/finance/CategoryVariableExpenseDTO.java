package com.yourlife.your.life.model.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yourlife.your.life.model.entity.finance.Exit;
import com.yourlife.your.life.model.types.finance.CategoryVariableExpensesTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class CategoryVariableExpenseDTO {
    private String id;
    private String name;
    private String description;
}
