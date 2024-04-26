package com.yourlife.your.life.model.entity.finance;

import com.yourlife.your.life.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Appetizer {

    @Id
    private String id;
    private String name;
    private Number value;
    private String description;
    private LocalDateTime createdAt;
    private Boolean deleted;
    private LocalDateTime deletedAt;
    private LocalDateTime updatedAt;

    @DBRef
    private User user;
}
