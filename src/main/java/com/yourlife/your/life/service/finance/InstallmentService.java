package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.entity.finance.Installment;
import java.util.List;

public interface InstallmentService {

    Installment save(Installment installment);

    List<Installment> getAll(String userId);

    Installment getById(String id);

}
