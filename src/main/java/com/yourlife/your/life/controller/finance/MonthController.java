package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.AppetizerDTO;
import com.yourlife.your.life.model.dto.finance.InstallmentDTO;
import com.yourlife.your.life.model.dto.finance.MonthDTO;
import com.yourlife.your.life.model.entity.finance.Appetizer;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.finance.AppetizerRegisterVO;
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
import java.util.*;

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
        month.setAppetizer(new ArrayList<>());
        month.setCategoryVariableExpens(new ArrayList<>());
        month.setInstallments(new ArrayList<>());
        month.setFixedAccounts(new ArrayList<>());

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
            if(monthFound.getInstallments().isEmpty()){
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

    @PostMapping(value = "/month/{id}/appetizer",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<AppetizerDTO> saveAppetizer(@PathVariable String id,
                                                      @RequestBody @Valid AppetizerRegisterVO appetizerRegisterVO){

        Month month = findbyId(id);

        Appetizer appetizer = modelMapper.map(appetizerRegisterVO,Appetizer.class);
        appetizer.setCreatedAt(LocalDateTime.now());
        appetizer.setDeleted(false);
        appetizer.setId(UUID.randomUUID().toString());


        List<Appetizer> appetizers = month.getAppetizer();
        appetizers.add(appetizer);

        month.setAppetizer(appetizers);

        monthService.save(month);

        return  ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(appetizer,AppetizerDTO.class));
    }

    private Month findbyId(String id){
        Month month = monthService.findById(id);

        if(month == null){
            throw new RuntimeException("Nenhum mes foi cadastrado!");
        }

        return month;
    }


    @PatchMapping(value = "/month/{idMonth}/appetizer/{idAppetizer}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deletedAppetizer(@PathVariable String idMonth,@PathVariable String idAppetizer){

        Month month = findbyId(idMonth);

        Appetizer appetizerFound = returnASpecificEntry(month.getAppetizer(),idAppetizer);

        if(appetizerFound != null){

            appetizerFound.setDeletedAt(LocalDateTime.now());
            appetizerFound.setDeleted(true);
            appetizerFound.setUpdatedAt(LocalDateTime.now());
            month.getAppetizer().removeIf(a -> Objects.equals(a.getId(), idAppetizer));
            month.getAppetizer().add(appetizerFound);
            monthService.save(month);
        }

        /*for(Appetizer appetizer :  month.getAppetizer() ){
            if(Objects.equals(appetizer.getId(), idAppetizer)){
                    appetizer.setDeletedAt(LocalDateTime.now());
                    appetizer.setDeleted(true);
                    appetizer.setUpdatedAt(LocalDateTime.now());
                    break;
            }
        }*/




        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private Appetizer returnASpecificEntry(List<Appetizer> appetizers, String idAppetizer){
        for (Appetizer appetizer : appetizers) {
            if (Objects.equals(appetizer.getId(), idAppetizer)) {
                return appetizer;
            }
        }
        return null;
    }


    @PutMapping(value = "/month/{idMonth}/appetizer/{idAppetizer}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> updated(@PathVariable String idMonth,@PathVariable String idAppetizer){
        return  null;
    }


}
