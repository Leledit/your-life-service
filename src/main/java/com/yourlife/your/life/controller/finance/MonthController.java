package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.MonthDTO;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        Month month = new Month();
        month.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        month.setDate(LocalDateTime.now());
        month.setMonth(currentDate.getDayOfMonth());
        month.setYear(currentDate.getYear());
        month.setUser(userContext.returnUserCorrespondingToTheRequest());

        Month monthSave = monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(monthSave, MonthDTO.class));
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
}
