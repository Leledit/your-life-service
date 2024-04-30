package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.*;
import com.yourlife.your.life.model.entity.finance.*;
import com.yourlife.your.life.model.vo.finance.*;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import com.yourlife.your.life.service.finance.MonthService;
import com.yourlife.your.life.utils.UserContext;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private CategoryVariableExpenseService categoryVariableExpenseService;

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

    @GetMapping(value = "/month",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MonthDTO>> getAll(){

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
    public ResponseEntity<InstallmentDTO> saveInstallment(@RequestBody @Valid InstallmentPostVO installmentPostVO){

        Installment installment = modelMapper.map(installmentPostVO,Installment.class);
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
                                                      @RequestBody @Valid AppetizerPostVO appetizerPostVO){

        Month month = findbyId(id);

        Appetizer appetizer = modelMapper.map(appetizerPostVO,Appetizer.class);
        appetizer.setCreatedAt(LocalDateTime.now());
        appetizer.setDeleted(false);
        appetizer.setId(UUID.randomUUID().toString());


        List<Appetizer> appetizers = month.getAppetizer();
        appetizers.add(appetizer);

        month.setAppetizer(appetizers);

        monthService.save(month);

        return  ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(appetizer,AppetizerDTO.class));
    }

    @PatchMapping(value = "/month/{idMonth}/appetizer/{idAppetizer}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deletedAppetizer(@PathVariable String idMonth,@PathVariable String idAppetizer){

        Month month = findbyId(idMonth);

        Appetizer appetizerFound = returnASpecificAppetizerFromTheArray(month.getAppetizer(),idAppetizer);

        appetizerFound.setDeletedAt(LocalDateTime.now());
        appetizerFound.setDeleted(true);
        appetizerFound.setUpdatedAt(LocalDateTime.now());
        month.getAppetizer().removeIf(a -> Objects.equals(a.getId(), idAppetizer));
        month.getAppetizer().add(appetizerFound);
        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/month/{idMonth}/appetizer/{idAppetizer}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> updatedAppetizer(@PathVariable String idMonth, @PathVariable String idAppetizer, @RequestBody AppetizerPutVO appetizerPutVO){

        Month month = findbyId(idMonth);

        Appetizer appetizerFound = returnASpecificAppetizerFromTheArray(month.getAppetizer(),idAppetizer);

        appetizerFound.setName(appetizerPutVO.getName() != null ? appetizerPutVO.getName() : appetizerFound.getName());
        appetizerFound.setValue(appetizerPutVO.getValue() != null ? appetizerPutVO.getValue() : appetizerFound.getValue());
        appetizerFound.setDescription(appetizerPutVO.getDescription() != null ? appetizerPutVO.getDescription() : appetizerFound.getDescription());
        appetizerFound.setUpdatedAt(LocalDateTime.now());

        month.getAppetizer().removeIf(a -> Objects.equals(a.getId(), idAppetizer));
        month.getAppetizer().add(appetizerFound);
        monthService.save(month);

        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping(value = "/month/{idMonth}/categoryVariableExpense/{idCategory}/exit",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ExitDTO> saveExit(@PathVariable String idMonth ,
                                                @PathVariable String idCategory,
                                                @RequestBody @Valid ExitPostVO exitPostVO){

        Month month = findbyId(idMonth);

        CategoryVariableExpense categoryVariableExpenseFound = categoryVariableExpenseService.getById(idCategory);

        CategoryVariableExpense categoryVariableExpenseMonth = returnASpecificCategoryVariableExpenseFromTheArray(month.getCategoryVariableExpens(),idCategory);

        Exit newExist = modelMapper.map(exitPostVO,Exit.class);
        newExist.setId(UUID.randomUUID().toString());
        newExist.setCreatedAt(LocalDateTime.now());
        newExist.setDeleted(false);

        if(categoryVariableExpenseMonth == null){
            List<Exit> exitList = new ArrayList<>();
            exitList.add(newExist);

            categoryVariableExpenseFound.setExit(exitList);

            List<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();
            categoryVariableExpenses.add(categoryVariableExpenseFound);

            month.setCategoryVariableExpens(categoryVariableExpenses);

        }else{
            categoryVariableExpenseMonth.getExit().add(newExist);
        }

        monthService.save(month);

        return  ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(newExist,ExitDTO.class));
    }

    @PatchMapping(value = "/month/{idMonth}/categoryVariableExpense/{idCategory}/exit/{idExit}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deletedExit( @PathVariable String idMonth ,
                                             @PathVariable String idCategory,
                                             @PathVariable String idExit){

        Month month = findbyId(idMonth);

        Exit exit = returnASpecificExit(month,idCategory,idExit);

        if(exit == null){
            throw new RuntimeException("id exit invalido");
        }else{
            exit.setDeleted(true);
            exit.setDeletedAt(LocalDateTime.now());
            exit.setUpdatedAt(LocalDateTime.now());
        }

        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/month/{idMonth}/categoryVariableExpense/{idCategory}/exit/{idExit}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public  ResponseEntity<ExitDTO> upadtedExit(@PathVariable String idMonth ,
                                                @PathVariable String idCategory,
                                                @PathVariable String idExit,
                                                @RequestBody @Valid ExitPutVO exitPutVO
                                               ){

        Month month = findbyId(idMonth);

        Exit exit = returnASpecificExit(month,idCategory,idExit);

        if(exit == null){
            throw new RuntimeException("Exit não encontrada");
        }

        exit.setName(exitPutVO.getName() != null ? exitPutVO.getName() : exit.getName());
        exit.setPaymentMethods(exitPutVO.getPaymentMethods() != null ? exitPutVO.getPaymentMethods() : exit.getPaymentMethods());
        exit.setValue(exitPutVO.getValue() != null ? exitPutVO.getValue() : exit.getValue());
        exit.setUpdatedAt(LocalDateTime.now());

        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(exit,ExitDTO.class));
    }

    @PostMapping(value = "/month/{idMonth}/fixed-account",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<FixedAccountDTO>> saveFixedAccount (@PathVariable String idMonth ,
                                                                   @RequestBody @Valid List<FixedAccountMonthPostVO> fixedAccountRegisterVOS
                                                                   ){
        Month month = findbyId(idMonth);

        List<FixedAccount> fixedAccountsMonth = month.getFixedAccounts();
        List<FixedAccountDTO> fixedAccountAddInMonth  = new ArrayList<>();

        for (FixedAccountMonthPostVO fixedAccountMonthPostVO : fixedAccountRegisterVOS){
            FixedAccount fixedAccountFound = modelMapper.map(fixedAccountMonthPostVO,FixedAccount.class);;
            for(FixedAccount fixedAccount : fixedAccountsMonth){
                if(Objects.equals(fixedAccount.getId(), fixedAccountMonthPostVO.getId())) {
                    if(!fixedAccount.getDeleted()) {
                        fixedAccountFound = null;
                        break;
                    }
                }
            }

            if(fixedAccountFound != null){
                fixedAccountFound.setCreatedAt(LocalDateTime.now());
                fixedAccountFound.setDeleted(false);
                fixedAccountAddInMonth.add(modelMapper.map(fixedAccountFound,FixedAccountDTO.class));
                fixedAccountsMonth.add(fixedAccountFound);
            }
        }

        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).body(fixedAccountAddInMonth);
    }

    @PatchMapping(value = "/month/{idMonth}/fixed-account/{idFixedAccount}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FixedAccountDTO> deletedFixedAccount (@PathVariable String idMonth ,
                                                                @PathVariable String idFixedAccount){

        Month month = findbyId(idMonth);

        FixedAccount fixedAccountFound = returnASpecificFixedAccount(month,idFixedAccount);

        if(fixedAccountFound == null){
            throw new RuntimeException("Conta-fixa não foi informada");
        }else {
            fixedAccountFound.setDeleted(true);
            fixedAccountFound.setDeletedAt(LocalDateTime.now());
            fixedAccountFound.setUpdatedAt(LocalDateTime.now());
        }

        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(fixedAccountFound,FixedAccountDTO.class));
    }

    @PutMapping(value = "/month/{idMonth}/fixed-account/{idFixedAccount}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FixedAccountDTO> updatedFixedAccount (@PathVariable String idMonth ,
                                                               @PathVariable String idFixedAccount,
                                                               @RequestBody @Valid FixedAccountMonthPutVO fixedAccountMonthPutVO){

        Month month = findbyId(idMonth);

        FixedAccount fixedAccountFound = returnASpecificFixedAccount(month,idFixedAccount);

        if(fixedAccountFound == null){
            throw new RuntimeException("Conta-fixa não foi informada");
        }else {
            fixedAccountFound.setName(fixedAccountMonthPutVO.getName() != null ? fixedAccountMonthPutVO.getName() : fixedAccountFound.getName());
            fixedAccountFound.setValue(fixedAccountMonthPutVO.getValue() != null ? fixedAccountMonthPutVO.getValue() : fixedAccountFound.getValue());
            fixedAccountFound.setDescription(fixedAccountMonthPutVO.getDescription() != null ? fixedAccountMonthPutVO.getDescription() : fixedAccountFound.getDescription());
            fixedAccountFound.setDueDate(fixedAccountMonthPutVO.getDueDate() != null ? fixedAccountMonthPutVO.getDueDate() : fixedAccountFound.getDueDate());
            fixedAccountFound.setUpdatedAt(LocalDateTime.now());
        }

        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(fixedAccountFound,FixedAccountDTO.class));
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
    private Month findbyId(String id){
        Month month = monthService.findById(id);

        if(month == null){
            throw new RuntimeException("Nenhum mes foi cadastrado!");
        }

        return month;
    }
    private Appetizer returnASpecificAppetizerFromTheArray(List<Appetizer> appetizers, String idAppetizer){
        Appetizer appetizerFound = null;
        for (Appetizer appetizer : appetizers) {
            if (Objects.equals(appetizer.getId(), idAppetizer)) {
                appetizerFound = appetizer;
            }
        }
        if(appetizerFound == null){
            throw new RuntimeException("Entrada não encontrada!");
        }else{
            if(appetizerFound.getDeleted()){
                throw new RuntimeException("Entrada não encontrada!");
            }
        }
        return appetizerFound;
    }
    private CategoryVariableExpense returnASpecificCategoryVariableExpenseFromTheArray(List<CategoryVariableExpense> categoryVariableExpenses, String idCategoryVariableExpense){
        for (CategoryVariableExpense categoryVariableExpense : categoryVariableExpenses) {
            if(Objects.equals(categoryVariableExpense.getId(), idCategoryVariableExpense)){
                if(categoryVariableExpense.getDeleted()){
                    throw new RuntimeException("Categoria não foi encontrada!!");
                }
                return categoryVariableExpense;
            }

        }

        return null;
    }
    private Exit returnASpecificExit(Month month, String idCategory, String idExit){
        for (CategoryVariableExpense categoryVariableExpense : month.getCategoryVariableExpens()) {
            if (Objects.equals(categoryVariableExpense.getId(), idCategory)) {
                for (Exit exit : categoryVariableExpense.getExit()) {
                    if (Objects.equals(exit.getId(), idExit)) {
                        if(exit.getDeleted()){
                            return null;
                        }else{
                            return exit;
                        }
                    }
                }
            }
        }
        return null;
    }
    private FixedAccount returnASpecificFixedAccount(Month month , String idFixedAccount){
        for (FixedAccount fixedAccount : month.getFixedAccounts()){
            if(Objects.equals(fixedAccount.getId(),idFixedAccount)){
                if( !fixedAccount.getDeleted()){
                    return fixedAccount;
                }
            }
        }

        return null;
    }
}