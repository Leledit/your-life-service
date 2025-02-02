package com.yourlife.your.life.model.entity.finance;

import com.yourlife.your.life.model.types.finance.BenefitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "finance_benefit_month")
public class BenefitMonth {
    @Id
    private String id;

    private String name;
    private Number valueReceived;
    private BenefitType type;
    private String description;
    private Boolean deleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
