package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPutDTO;
import com.yourlife.your.life.model.entity.finance.Installment;

import java.util.List;

public interface InstallmentService {

    Installment save(InstallmentPostDTO installmentPostDTO);
    List<Installment> findAllByUser();
    Installment findById(String id);
    void deleted(String id);
    Installment update(String id,InstallmentPutDTO installmentPutDTO);
}
