package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPutDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.service.finance.InstallmentService;
import jakarta.validation.Valid;
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
    private InstallmentService installmentService;

    @ResponseBody
    @PostMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Installment> saveInstallment(@RequestBody @Valid InstallmentPostDTO installmentPostDTO) {
        Installment installment = installmentService.save(installmentPostDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(installment);
    }

    @ResponseBody
    @GetMapping(value = "/installment", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Installment>> getAllInstallment(){
        List<Installment> installmentList = installmentService.findAllByUser();
        return  ResponseEntity.status(HttpStatus.OK).body(installmentList);
    }

    @ResponseBody
    @GetMapping(value = "/installment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Installment> getInstallmentById(@PathVariable String id){
        Installment installment = installmentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(installment);
    }

    @ResponseBody
    @PatchMapping(value = "/installment/{id}/deleted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletedInstallment(@PathVariable String id){
        installmentService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ResponseBody
    @PutMapping(value = "/installment/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Installment> updateInstallment(@PathVariable String id,
                                                         @RequestBody InstallmentPutDTO installmentPutDTO){

        Installment installment = installmentService.update(id,installmentPutDTO);
        return ResponseEntity.status(HttpStatus.OK).body(installment);
    }
}
