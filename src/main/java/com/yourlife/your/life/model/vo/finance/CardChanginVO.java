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
public class CardChanginVO {
    @NotBlank
    private String id;
    private String name;
    private String dueDate;
    private String model;
}
