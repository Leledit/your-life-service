package com.yourlife.your.life.model.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class EntryDTO {
    private String id;
    private String name;
    private Number value;
    private String description;
}
