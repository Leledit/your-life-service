package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.vo.finance.InstallmentPutVO;
import com.yourlife.your.life.model.vo.finance.InstallmentPostVO;
import com.yourlife.your.life.service.finance.InstallmentService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class InstallmentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstallmentService installmentService;

    @Autowired
    private UserContext userContext;

    @PostMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<InstallmentDTO> save(@RequestBody @Valid InstallmentPostVO installmentPostVO){

        Installment installment = modelMapper.map(installmentPostVO,Installment.class);
        installment.setCreatedAt(LocalDateTime.now());
        installment.setDeleted(false);
        installment.setUser(userContext.returnUserCorrespondingToTheRequest());

        Installment installmentSave = installmentService.save(installment);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(installmentSave,InstallmentDTO.class));
    }

    @GetMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstallmentDTO>> getAll(){

        List<Installment> installments = installmentService.getAll(userContext.returnUserCorrespondingToTheRequest().getId());

        List<InstallmentDTO> installmentDTOS = new ArrayList<>();
        installments.forEach(installment -> {
            installmentDTOS.add(modelMapper.map(installment,InstallmentDTO.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(installmentDTOS);
    }

    @GetMapping(value = "/installment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstallmentDTO> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(installmentService.getById(id),InstallmentDTO.class));
    }

    @PutMapping(value = "/installment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<InstallmentDTO> updated(@RequestBody @Valid InstallmentPutVO installmentPutVO,
                                                  @PathVariable String id){

        Installment installment = installmentService.getById(id);

        installment.setDescription(installmentPutVO.getDescription() !=null ? installmentPutVO.getDescription(): installment.getDescription());
        installment.setFirstInstallmentDate(installmentPutVO.getFirstInstallmentDate() !=null ? installmentPutVO.getFirstInstallmentDate(): installment.getFirstInstallmentDate());
        installment.setValue(installmentPutVO.getValue() !=null? installmentPutVO.getValue() : installment.getValue());
        installment.setQtd(installmentPutVO.getQtd() != null ? installmentPutVO.getQtd() : installment.getQtd());
        installment.setUpdatedAt(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(installmentService.save(installment),InstallmentDTO.class));
    }

    @PatchMapping(value = "/installment/{id}/deleted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleted(@PathVariable String id){

        Installment installment = installmentService.getById(id);

        installment.setDeleted(true);
        installment.setDeletedAt(LocalDateTime.now());
        installment.setCreatedAt(LocalDateTime.now());

        installmentService.save(installment);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
