package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPutDTO;
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

        Installment installment = installmentRepository.save(Installment
                                        .builder()
                                        .qtd(installmentPostDTO.getQtd())
                                        .createdAt(LocalDateTime.now())
                                        .firstInstallmentDate(currentDate)
                                        .lastInstallmentDate(getLastInstallmentDateDate(installmentPostDTO.getQtd()))
                                        .value(installmentPostDTO.getValue())
                                        .deleted(false)
                                        .user(userContext.returnUserCorrespondingToTheRequest())
                                        .description(installmentPostDTO.getDescription())
                                        .build());

        Month curretMonth = monthRepository.findByYearAndMonthAndUser_Id(currentDate.getYear(),currentDate.getMonthValue(),userContext.returnUserCorrespondingToTheRequest().getId()).orElse(null);
        if(curretMonth != null){
            List<Installment> installmentList = curretMonth.getInstallments();
            installmentList.add(installment);
            curretMonth.setInstallments(installmentList);
            monthRepository.save(curretMonth);
        }

        return installment;

    }

    @Override
    public List<Installment> findAllByUser() {
        return installmentRepository.findAllByUser_IdAndDeleted(userContext.returnUserCorrespondingToTheRequest().getId(),false).orElse(null);
    }

    @Override
    public Installment findById(String id) {
        return findInstallmentById(id);
    }

    @Override
    public void deleted(String id) {
        Installment installment = findInstallmentById(id);

        installment.setDeleted(true);
        installment.setDeletedAt(LocalDateTime.now());

        installmentRepository.save(installment);
    }

    @Override
    public Installment update(String id, InstallmentPutDTO installmentPutDTO) {
        Installment installment = findInstallmentById(id);
        installment.setQtd(installmentPutDTO.getQtd()!=null?installmentPutDTO.getQtd():installment.getQtd());
        installment.setDescription(installmentPutDTO.getDescription()!=null?installmentPutDTO.getDescription():installment.getDescription());
        installment.setValue(installmentPutDTO.getValue()!=null?installmentPutDTO.getValue():installment.getValue());
        installment.setLastInstallmentDate(installmentPutDTO.getQtd()!=null?getLastInstallmentDateDate(installmentPutDTO.getQtd()):installment.getLastInstallmentDate());
        installment.setUpdatedAt(LocalDateTime.now());

        installmentRepository.save(installment);
        return installment;
    }

    private Installment findInstallmentById(String id){
        Installment installment = installmentRepository.findByIdAndDeleted(id,false).orElse(null);

        if(installment == null){
            throw new RuntimeException(ExceptionMessages.INSTALLMENT_NOT_FOUND);
        }

        return installment;
    }

    private LocalDate getLastInstallmentDateDate(Number qtdInstallment){
        LocalDate currentDate = LocalDate.now();

        int qtdInstallments = Integer.parseInt(qtdInstallment.toString());

        if(qtdInstallments == 1){
            qtdInstallments = 0;
        }else{
            qtdInstallments -=1;
        }
        return currentDate.plusMonths(qtdInstallments);
    }
}
