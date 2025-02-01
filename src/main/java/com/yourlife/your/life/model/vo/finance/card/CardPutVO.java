package com.yourlife.your.life.model.vo.finance.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPutVO {
    private String name;
    private String dueDate;
    private String model;
}
