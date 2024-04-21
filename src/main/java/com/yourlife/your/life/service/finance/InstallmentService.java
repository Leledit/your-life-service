package com.yourlife.your.life.service.finance;

import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.entity.finance.Installment;

import java.util.List;

public interface InstallmentService {

    InstallmentDTO createdInstallment(Installment installment);

    List<InstallmentDTO> getAllInstallment();

    InstallmentDTO getById(String id);

    InstallmentDTO changingInstallment(Installment installment);

    Void deletedInstallment(String id);

}
