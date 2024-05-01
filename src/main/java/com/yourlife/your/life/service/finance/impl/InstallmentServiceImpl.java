package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.repository.finance.InstallmentRepository;
import com.yourlife.your.life.service.finance.InstallmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    @Autowired
    private InstallmentRepository installmentRepository;

    @Override
    public Installment save(Installment installmentRequest) {
        return installmentRepository.save(installmentRequest);
    }

    @Override
    public List<Installment> getAll(String userId) {
        return installmentRepository.findAllByUser_IdAndDeleted(userId,false).orElse(null);
    }

    @Override
    public Installment getById(String id) {

        Installment installment = installmentRepository.findById(id).orElse(null);

        if(installment == null || installment.getDeleted()){
            throw new RuntimeException(ExceptionMessages.NOT_FOUND);
        }

        return installment;
    }
}
