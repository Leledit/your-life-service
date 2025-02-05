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
@Document(collection = "finance_month")
public class Month {

    @Id
    private String id;
    private String name;
    private Integer year;
    private Integer month;
    private LocalDate date;

    @DBRef
    private List<FixedAccount> fixedAccounts; //Feito

    @DBRef
    private List<Installment> installments;

    @DBRef
    private List<CategoryVariableExpenseMonth> categoryVariableExpenseMonth;

    @DBRef
    private List<Entry> entry;

    @DBRef
    private List<BenefitMonth> benefitsMonth;

    @DBRef
    private User user;
}
