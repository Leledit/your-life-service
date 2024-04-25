package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.dto.finance.MonthDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.finance.InstallmentRegisterVO;
import com.yourlife.your.life.service.finance.MonthService;
import com.yourlife.your.life.utils.Logger;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("/service/api/v1")
public class MonthController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserContext userContext;


    @Autowired
    private MonthService monthService;

    @PostMapping(value = "/month",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<MonthDTO> save(){

        LocalDate currentDate = LocalDate.now();

        Month monthFound = monthService.findByMonth(currentDate.getDayOfMonth(),currentDate.getYear(),userContext.returnUserCorrespondingToTheRequest().getId());

        if(monthFound != null){
            throw new RuntimeException("Mes ja cadastrado");
        }

        Month monthSave = createNewMonthRecord(currentDate);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(monthSave, MonthDTO.class));
    }

    private Month createNewMonthRecord(LocalDate currentDate){
        Month month = new Month();
        month.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        month.setDate(LocalDateTime.now());
        month.setMonth(currentDate.getMonthValue());
        month.setYear(currentDate.getYear());
        month.setUser(userContext.returnUserCorrespondingToTheRequest());

        return monthService.save(month);
    }


    @GetMapping(value = "/month",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonthDTO>> getALl(){

        List<Month> months = monthService.getAll(userContext.returnUserCorrespondingToTheRequest().getId());

        List<MonthDTO> monthDTOS = new ArrayList<>();
        months.forEach(month -> {
            monthDTOS.add(modelMapper.map(month,MonthDTO.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(monthDTOS);
    }

    @GetMapping(value = "/month/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MonthDTO> getById(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(monthService.findById(id),MonthDTO.class));
    }

    @PostMapping(value = "/month/installment",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InstallmentDTO> saveInstallment(@RequestBody @Valid InstallmentRegisterVO installmentRegisterVO){

        Installment installment = modelMapper.map(installmentRegisterVO,Installment.class);
        installment.setCreatedAt(LocalDateTime.now());
        installment.setDeleted(false);
        installment.setUser(userContext.returnUserCorrespondingToTheRequest());
        installment.setId(UUID.randomUUID().toString());
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < installment.getQtd().intValue(); i++) {

            LocalDate expectedDate = currentDate.plusMonths(i);
            installment.setCurrent(STR."\{i + 1}/\{installment.getQtd().intValue()}");

            Month monthFound = monthService.findByMonth(expectedDate.getMonthValue(),expectedDate.getYear(),userContext.returnUserCorrespondingToTheRequest().getId());

            if(monthFound == null){
                monthFound = createNewMonthRecord(expectedDate);
            }

            List<Installment> saveInstallment = new ArrayList<>();
            if(monthFound.getInstallments() == null || monthFound.getInstallments().isEmpty()){
                saveInstallment.add(installment);
            }else{
                saveInstallment.addAll(monthFound.getInstallments());
                saveInstallment.add(installment);
            }

            monthFound.setInstallments(saveInstallment);
            monthService.save(monthFound);
        }

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(installment,InstallmentDTO.class));
    }
}
