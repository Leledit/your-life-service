package com.yourlife.your.life.model.vo.finance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVariableExpenseChangingVO {

    private String id;
    private String name;
    private String description;
}
