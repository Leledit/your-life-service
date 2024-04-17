package com.yourlife.your.life.model.dto.finance;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yourlife.your.life.model.dto.user.UserDTO;
import com.yourlife.your.life.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude
public class FinanceFixedAccountDTO {
    private String name;
    private Number value;
    private String description;
    private Number dueDate;
}