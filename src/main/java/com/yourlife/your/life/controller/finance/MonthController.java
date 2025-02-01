package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.*;
import com.yourlife.your.life.model.entity.finance.*;
import com.yourlife.your.life.model.vo.finance.benefit.BenefitPostVO;
import com.yourlife.your.life.model.vo.finance.entry.EntryPostVO;
import com.yourlife.your.life.model.vo.finance.entry.EntryPutVO;
import com.yourlife.your.life.model.vo.finance.exit.ExitPostVO;
import com.yourlife.your.life.model.vo.finance.exit.ExitPutVO;
import com.yourlife.your.life.model.vo.finance.fixedAccountMonth.FixedAccountMonthPostVO;
import com.yourlife.your.life.model.vo.finance.fixedAccountMonth.FixedAccountMonthPutVO;
import com.yourlife.your.life.model.vo.finance.installment.InstallmentPostVO;
import com.yourlife.your.life.service.finance.BenefitItemService;
import com.yourlife.your.life.service.finance.BenefitService;
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
    private BenefitService benefitService;

    @Autowired
    private CategoryVariableExpenseService categoryVariableExpenseService;

    @Autowired
    private BenefitItemService benefitItemService;

    @PostMapping(value = "/month",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<MonthDTO> save(){

        LocalDate currentDate = LocalDate.now();

        Month monthFound = monthService.findByMonth(currentDate.getDayOfMonth()+1,currentDate.getYear(),userContext.returnUserCorrespondingToTheRequest().getId());

        if(monthFound != null){
            throw new RuntimeException(ExceptionMessages.MONT_ALREADY_REGISTERED);
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
            //installment.setCurrent(STR."\{i + 1}/\{installment.getQtd().intValue()}");

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

    @PostMapping(value = "/month/{id}/entry",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<EntryDTO> saveEntry(@PathVariable String id,
                                                  @RequestBody @Valid EntryPostVO entryPostVO){

        Month month = findbyId(id);

        Entry entry = modelMapper.map(entryPostVO, Entry.class);
        entry.setCreatedAt(LocalDateTime.now());
        entry.setDeleted(false);
        entry.setId(UUID.randomUUID().toString());


        List<Entry> entries = month.getEntry();
        entries.add(entry);

        month.setEntry(entries);

        monthService.save(month);

        return  ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(entry, EntryDTO.class));
    }

    @PatchMapping(value = "/month/{idMonth}/entry/{idEntry}/deleted",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deletedEntry(@PathVariable String idMonth,@PathVariable String idEntry){

        Month month = findbyId(idMonth);

        Entry entryFound = returnASpecificEntryFromTheArray(month.getEntry(),idEntry);

        entryFound.setDeletedAt(LocalDateTime.now());
        entryFound.setDeleted(true);
        entryFound.setUpdatedAt(LocalDateTime.now());
        month.getEntry().removeIf(a -> Objects.equals(a.getId(), idEntry));
        month.getEntry().add(entryFound);
        monthService.save(month);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(value = "/month/{idMonth}/entry/{idEntry}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> updatedEntry(@PathVariable String idMonth, @PathVariable String idEntry, @RequestBody EntryPutVO entryPutVO){

        Month month = findbyId(idMonth);

        Entry entryFound = returnASpecificEntryFromTheArray(month.getEntry(),idEntry);

        entryFound.setName(entryPutVO.getName() != null ? entryPutVO.getName() : entryFound.getName());
        entryFound.setValue(entryPutVO.getValue() != null ? entryPutVO.getValue() : entryFound.getValue());
        entryFound.setDescription(entryPutVO.getDescription() != null ? entryPutVO.getDescription() : entryFound.getDescription());
        entryFound.setUpdatedAt(LocalDateTime.now());

        month.getEntry().removeIf(a -> Objects.equals(a.getId(), idEntry));
        month.getEntry().add(entryFound);
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
            throw new RuntimeException(ExceptionMessages.EXIT_NOT_FOUND);
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
    public  ResponseEntity<ExitDTO> upadtedeExit(@PathVariable String idMonth ,
                                                @PathVariable String idCategory,
                                                @PathVariable String idExit,
                                                @RequestBody @Valid ExitPutVO exitPutVO
                                               ){

        Month month = findbyId(idMonth);

        Exit exit = returnASpecificExit(month,idCategory,idExit);

        if(exit == null){
            throw new RuntimeException(ExceptionMessages.EXIT_NOT_FOUND);
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
            throw new RuntimeException(ExceptionMessages.FIXED_ACCOUNT_NOT_FOUND);
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
            throw new RuntimeException(ExceptionMessages.FIXED_ACCOUNT_NOT_FOUND);
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

    @PostMapping(value = "/month/{idMonth}/benefit",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BenefitDTO> saveBenefit(@PathVariable String idMonth, @RequestBody @Valid BenefitPostVO benefitPostVO){

        Month month = findbyId(idMonth);

        Benefit benefit = benefitService.save(Benefit
                .builder()
                .name(benefitPostVO.getName())
                .description(benefitPostVO.getDescription())
                .createdAt(LocalDateTime.now())
                .type(benefitPostVO.getType())
                .deleted(false)
                .valueReceived(benefitPostVO.getValueReceived())
                .itens(new ArrayList<>())
                .build());

        List<Benefit> benefits = month.getBenefits();
        benefits.add(benefit);

        month.setBenefits(benefits);

        monthService.save(month);

        return ResponseEntity.status(HttpStatus.CREATED).body(modelMapper.map(benefit,BenefitDTO.class));
    }

    @PutMapping(value = "/month/{idMonth}/benefit/",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<BenefitDTO> saveBenefit(@PathVariable String idMonth, @PathVariable String idBenefit, @RequestBody @Valid BenefitPostVO benefitPostVO){

    }

    protected Month createNewMonthRecord(LocalDate currentDate){
        Month month = new Month();
        month.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        month.setDate(currentDate);
        month.setMonth(currentDate.getMonthValue());
        month.setYear(currentDate.getYear());
        month.setUser(userContext.returnUserCorrespondingToTheRequest());
        month.setEntry(new ArrayList<>());
        month.setCategoryVariableExpens(new ArrayList<>());
        month.setInstallments(new ArrayList<>());
        month.setFixedAccounts(new ArrayList<>());
        month.setBenefits(new ArrayList<>());

        return monthService.save(month);
    }
    protected Month findbyId(String id){
        Month month = monthService.findById(id);

        if(month == null){
            throw new RuntimeException(ExceptionMessages.MONTH_NOT_FOUND);
        }

        return month;
    }
    protected Entry returnASpecificEntryFromTheArray(List<Entry> entries, String idEntry){
        Entry entryFound = null;
        for (Entry entry : entries) {
            if (Objects.equals(entry.getId(), idEntry)) {
                entryFound = entry;
            }
        }
        if(entryFound == null){
            throw new RuntimeException(ExceptionMessages.MONTH_NOT_FOUND);
        }else{
            if(entryFound.getDeleted()){
                throw new RuntimeException(ExceptionMessages.MONTH_NOT_FOUND);
            }
        }
        return entryFound;
    }
    private CategoryVariableExpense returnASpecificCategoryVariableExpenseFromTheArray(List<CategoryVariableExpense> categoryVariableExpenses, String idCategoryVariableExpense){
        for (CategoryVariableExpense categoryVariableExpense : categoryVariableExpenses) {
            if(Objects.equals(categoryVariableExpense.getId(), idCategoryVariableExpense)){
                if(categoryVariableExpense.getDeleted()){
                    throw new RuntimeException(ExceptionMessages.CATEGORY_VARIABLE_EXPENSE_NOT_FOUND);
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