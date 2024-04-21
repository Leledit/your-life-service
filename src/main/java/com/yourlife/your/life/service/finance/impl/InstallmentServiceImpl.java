package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.entity.user.UserAuth;
import com.yourlife.your.life.repository.finance.InstallmentRepository;
import com.yourlife.your.life.service.finance.InstallmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InstallmentServiceImpl implements InstallmentService {

    @Autowired
    private InstallmentRepository installmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InstallmentDTO createdInstallment(Installment installmentRequest) {

        User user = returnUserCorrespondingToTheRequest();

        installmentRequest.setCreatedAt(LocalDateTime.now());
        installmentRequest.setDeleted(false);
        installmentRequest.setUser(user);
        Installment installment = installmentRepository.save(installmentRequest);

        return modelMapper.map(installment,InstallmentDTO.class);
    }

    @Override
    public List<InstallmentDTO> getAllInstallment() {

        User user = returnUserCorrespondingToTheRequest();

        Optional<ArrayList<Installment>> installmentsOptional = installmentRepository.findAllByUser_Id(user.getId());

        List<Installment> installments = new ArrayList<>();

        installmentsOptional.ifPresent(installments::addAll);

        List<InstallmentDTO> installmentDTOS = new ArrayList<>();
        installments.forEach(installment -> {
            if(!installment.getDeleted()){
                installmentDTOS.add(modelMapper.map(installment,InstallmentDTO.class));
            }
        });

        return installmentDTOS;
    }

    @Override
    public InstallmentDTO getById(String id) {

        Installment installment = returnInstallmentById(id);

        if(installment.getDeleted()){
            throw new RuntimeException("INSTALLMENT_NOT_FOUND");
        }

        return modelMapper.map(installment,InstallmentDTO.class);
    }

    @Override
    public InstallmentDTO changingInstallment(Installment installmentRequest) {

        Installment installment = returnInstallmentById(installmentRequest.getId());

        installment.setDescription(installmentRequest.getDescription() !=null ? installmentRequest.getDescription(): installment.getDescription());
        installment.setFirstInstallmentDate(installmentRequest.getFirstInstallmentDate() !=null ? installmentRequest.getFirstInstallmentDate(): installment.getFirstInstallmentDate());
        installment.setValue(installmentRequest.getValue() !=null? installmentRequest.getValue() : installment.getValue());
        installment.setQtd(installmentRequest.getQtd() != null ? installmentRequest.getQtd() : installment.getQtd());
        installment.setUpdatedAt(LocalDateTime.now());

        installmentRepository.save(installment);

        return modelMapper.map(installment,InstallmentDTO.class);
    }

    @Override
    public Void deletedInstallment(String id) {

        Installment installment = returnInstallmentById(id);

        installment.setDeleted(true);
        installment.setDeletedAt(LocalDateTime.now());
        installment.setCreatedAt(LocalDateTime.now());

        installmentRepository.save(installment);

        return null;
    }

    //isolar esse metodo assim que possivel
    private User returnUserCorrespondingToTheRequest(){
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();

        UserAuth userAuth = (UserAuth) authentication.getPrincipal();

        return modelMapper.map(userAuth,User.class);
    }

    private Installment returnInstallmentById(String id){
        Optional<Installment> installmentFound = installmentRepository.findById(id);

        if(installmentFound.isEmpty()){
            throw new RuntimeException("INSTALLMENT_NOT_FOUND");
        }

        return installmentFound.get();

    }
}
