package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.repository.finance.InstallmentRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.service.finance.InstallmentService;
import com.yourlife.your.life.utils.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private UserContext userContext;

    @Autowired
    private MonthRepository monthRepository;

    @Override
    public Installment save(InstallmentPostDTO installmentPostDTO) {

        LocalDate currentDate = LocalDate.now();

        int qtdInstallments = Integer.parseInt(installmentPostDTO.getQtd().toString());

        if(qtdInstallments == 1){
            qtdInstallments = 0;
        }else{
            qtdInstallments -=1;
        }

        Installment installment = installmentRepository.save(Installment
                                        .builder()
                                        .qtd(installmentPostDTO.getQtd())
                                        .createdAt(LocalDateTime.now())
                                        .firstInstallmentDate(currentDate)
                                        .lastInstallmentDate(currentDate.plusMonths(qtdInstallments))
                                        .value(installmentPostDTO.getValue())
                                        .deleted(false)
                                        .user(userContext.returnUserCorrespondingToTheRequest())
                                        .description(installmentPostDTO.getDescription())
                                        .build());

        Month curretMonth = monthRepository.findByYearAndMonthAndUser_Id(currentDate.getYear(),currentDate.getMonthValue(),userContext.returnUserCorrespondingToTheRequest().getId());
        if(curretMonth != null){
            List<Installment> installmentList = curretMonth.getInstallments();
            installmentList.add(installment);
            curretMonth.setInstallments(installmentList);
            monthRepository.save(curretMonth);
        }

        return installment;

    }

    @Override
    public List<Installment> findAll() {
        return installmentRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public Installment findById(String id) {
        return installmentRepository.findByIdAndDeleted(id,false).orElse(null);
    }

    @Override
    public void deleted(String id) {
        Installment installment = installmentRepository.findById(id).orElse(null);

        if(installment == null){
            throw new RuntimeException(ExceptionMessages.INSTALLMENT_NOT_FOUND);
        }

        installment.setDeleted(true);
        installment.setDeletedAt(LocalDateTime.now());

        installmentRepository.save(installment);
    }
}
