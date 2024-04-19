package com.yourlife.your.life.model.entity.finance;

import com.yourlife.your.life.model.types.finance.CategoryVariableExpensesTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "categoryVariableExpenses")
public class CategoryVariableExpense {

    @Id
    private String id;
    private String name;
    private String description;
    private CategoryVariableExpensesTypes type;


    //private List<Exit> exits;

}
