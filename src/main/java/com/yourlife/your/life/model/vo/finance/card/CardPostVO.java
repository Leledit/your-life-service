package com.yourlife.your.life.model.vo.finance.card;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPostVO {

    @NotBlank
    private String name;
    @NotBlank
    private String dueDate;
    private String model;

}
