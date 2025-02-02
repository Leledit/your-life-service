package com.yourlife.your.life.model.dto.finance.Month;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class MonthAddFixedAccountDTO {
    private List<String> fixedAccountList;
}
