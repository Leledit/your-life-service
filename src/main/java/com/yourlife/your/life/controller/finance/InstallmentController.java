package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.vo.finance.InstallmentChanginVO;
import com.yourlife.your.life.model.vo.finance.InstallmentRegisterVO;
import com.yourlife.your.life.service.finance.InstallmentService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service/api/v1")
public class InstallmentController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InstallmentService installmentService;

    @PostMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<InstallmentDTO> registerInstallment(@RequestBody @Valid InstallmentRegisterVO installmentRegisterVO){

        Installment installment = modelMapper.map(installmentRegisterVO,Installment.class);

        InstallmentDTO installmentDTO = installmentService.createdInstallment(installment);

        return ResponseEntity.status(HttpStatus.OK).body(installmentDTO);
    }

    @GetMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<InstallmentDTO>> listAll(){

        List<InstallmentDTO> installmentDTOS = installmentService.getAllInstallment();

        return ResponseEntity.status(HttpStatus.OK).body(installmentDTOS);
    }

    @GetMapping(value = "/installment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstallmentDTO> listById(@PathVariable String id){

        InstallmentDTO installmentDTO = installmentService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(installmentDTO);
    }

    @PutMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<InstallmentDTO> updated(@RequestBody @Valid InstallmentChanginVO installmentChanginVO){

        Installment installment = modelMapper.map(installmentChanginVO,Installment.class);

        InstallmentDTO installmentDTO = installmentService.changingInstallment(installment);

        return ResponseEntity.status(HttpStatus.OK).body(installmentDTO);
    }

    @PatchMapping(value = "/installment/{id}/deleted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleted(@PathVariable String id){

        installmentService.deletedInstallment(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
