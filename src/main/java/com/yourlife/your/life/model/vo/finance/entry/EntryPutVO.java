package com.yourlife.your.life.model.vo.finance.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryPutVO {
    private String name;
    private Number value;
    private String description;
}
