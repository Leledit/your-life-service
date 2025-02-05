package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.CardDTO;
import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.vo.finance.card.CardPostVO;
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

    @PostMapping(value = "/installment",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Installment> saveInstallment(@RequestBody @Valid InstallmentPostDTO installmentPostDTO) {
        Installment installment = installmentService.save(installmentPostDTO);
        return ResponseEntity.status(HttpStatus.OK).body(installment);
    }

    @GetMapping(value = "/installment",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Installment>> findAllInstallment(){
        List<Installment> installmentList = installmentService.findAll();
        return  ResponseEntity.status(HttpStatus.OK).body(installmentList);
    }

    @GetMapping(value = "/installment/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Installment> findInstallmentById(@PathVariable String id){
        Installment installment = installmentService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(installment);
    }

    @PatchMapping(value = "/installment/{id}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deletedInstallment(@PathVariable String id){
        installmentService.deleted(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //TODO:Faltou o metodo editar
}
