package com.yourlife.your.life.model.entity.finance;

import com.yourlife.your.life.model.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "month")
public class Month {

    @Id
    private String id;
    private String name;
    private Integer year;
    private Integer month;
    private LocalDate date;
    private List<Entry> entry;
    private List<CategoryVariableExpense> categoryVariableExpens;
    private List<FixedAccount> fixedAccounts;
    private List<Installment> installments;

    @DBRef
    private User user;
}
