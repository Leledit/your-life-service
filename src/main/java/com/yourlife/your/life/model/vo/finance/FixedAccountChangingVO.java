package com.yourlife.your.life.model.vo.finance;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FixedAccountChangingVO {
    @NotBlank
    private String Id;
    private String name;
    private Number value;
    private String description;
    private Number dueDate;
}
