package com.yourlife.your.life.model.entity.finance;

import com.yourlife.your.life.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "installment")
public class Installment {

    @Id
    private String id;
    private String description;
    private String firstInstallmentDate;
    private Number value;
    private Number qtd;
    private String current;
    private Boolean deleted;
    private LocalDateTime deletedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @DBRef
    private User user;
}
