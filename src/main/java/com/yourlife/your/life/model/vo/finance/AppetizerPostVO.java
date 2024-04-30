package com.yourlife.your.life.model.vo.finance;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppetizerPostVO {
    @NotBlank
    private String name;
    @NumberFormat
    private Number value;
    @NotBlank
    private String description;
}
