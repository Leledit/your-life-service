package com.yourlife.your.life.model.entity.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "finance_category_variable_expenses_month")
public class CategoryVariableExpenseMonth {

    @DBRef
    private List<Exit> exits;

    @DBRef
    private String categoryVariableExpense;

}
