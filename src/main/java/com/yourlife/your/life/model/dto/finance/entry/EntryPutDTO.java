package com.yourlife.your.life.model.dto.finance.entry;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntryPutDTO {
    private String name;
    private Number value;
    private String description;
}
