package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.*;
import com.yourlife.your.life.model.entity.finance.*;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.types.finance.PaymentMethods;
import com.yourlife.your.life.service.finance.VariableExpensesCategoryService;
import com.yourlife.your.life.service.finance.MonthService;
import com.yourlife.your.life.utils.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("month")
class MonthControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserContext userContext;

    @Mock
    private MonthService monthService;

    @Mock
    private VariableExpensesCategoryService variableExpensesCategoryService;

    @InjectMocks
    private MonthController monthController;

    private User userMock;

    private final String idMonthMock = "662e9866e348a57153c48cdd";
    private final String idAppetizerMock = "6621b1c02sdf34wsdfe50ac7d6319";
    private final String idCategoryMock = "6626fbc8b030c6195d5aa007";
    private final String idExitMock = "6626fbc832b53c619cdeg4d5aa007";
    private final String idFixedAccount = "662e83aa87d932594883a386";

    private Month monthMock;

    private MonthDTO monthDTOMock;

    private LocalDate currentDate;

    private Entry entryMock;

    private EntryDTO entryDTOMock;
    private Exit exitMock;
    private ExitDTO exitDTOMock;
    private CategoryVariableExpense categoryVariableExpenseMock ;
    private FixedAccount fixedAccountMock;
    private FixedAccountDTO fixedAccountDTOMock;

    @BeforeEach
    public void setUp(){
        currentDate = LocalDate.now();

        userMock = new User();
        userMock.setId(idMonthMock);
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        monthMock = new Month();
        monthMock.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        monthMock.setDate(currentDate);
        monthMock.setMonth(currentDate.getMonthValue());
        monthMock.setYear(currentDate.getYear());
        monthMock.setUser(userMock);
        monthMock.setEntry(new ArrayList<>());
        monthMock.setCategoryVariableExpens(new ArrayList<>());
        monthMock.setInstallments(new ArrayList<>());
        monthMock.setFixedAccounts(new ArrayList<>());

        monthDTOMock = new MonthDTO();
        monthDTOMock.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        monthDTOMock.setId(idMonthMock);
        monthDTOMock.setDate(currentDate);
        monthDTOMock.setYear(currentDate.getYear());
        monthDTOMock.setEntry(new ArrayList<>());
        monthDTOMock.setCategoryVariableExpens(new ArrayList<>());
        monthDTOMock.setInstallments(new ArrayList<>());
        monthDTOMock.setFixedAccounts(new ArrayList<>());

        entryMock = new Entry();
        entryMock.setName("Salario");
        entryMock.setId(idAppetizerMock);
        entryMock.setValue(1.412);
        entryMock.setDescription("Pagamento mensal");
        entryMock.setDeleted(false);

        entryDTOMock = new EntryDTO();
        entryDTOMock.setName("Salario");
        entryDTOMock.setValue(1.412);
        entryDTOMock.setDescription("Pagamento mensal");

        exitMock = new Exit();
        exitMock.setId(idExitMock);
        exitMock.setName("lanche");
        exitMock.setValue(100);
        exitMock.setDeleted(false);
        exitMock.setPaymentMethods(PaymentMethods.PIX);

        exitDTOMock = new ExitDTO();
        exitDTOMock.setId(idExitMock);
        exitDTOMock.setName("lanche");
        exitDTOMock.setValue(100);
        exitMock.setDeleted(false);
        exitDTOMock.setPaymentMethods(PaymentMethods.PIX);

        categoryVariableExpenseMock = new CategoryVariableExpense();
        categoryVariableExpenseMock.setName("Gastos pessoais");
        categoryVariableExpenseMock.setDescription("Gastos com coisa supérfluos");
        categoryVariableExpenseMock.setUser(userMock);
        categoryVariableExpenseMock.setCreatedAt(LocalDateTime.now());
        categoryVariableExpenseMock.setId("6626fbc8b030c6195d5aa007");
        categoryVariableExpenseMock.setDeleted(false);

        fixedAccountMock = new FixedAccount();
        fixedAccountMock.setId(idFixedAccount);
        fixedAccountMock.setName("Conta de telefone");
        fixedAccountMock.setDescription("");
        fixedAccountMock.setValue(50);
        fixedAccountMock.setDueDate(1);
        fixedAccountMock.setDeleted(false);


        fixedAccountDTOMock = new FixedAccountDTO();
        fixedAccountDTOMock.setId(idFixedAccount);
        fixedAccountDTOMock.setName("Conta de telefone");
        fixedAccountDTOMock.setDescription("");
        fixedAccountDTOMock.setValue(50);
        fixedAccountDTOMock.setDueDate(1);
    }

    @Test
    @DisplayName("save - Creating new record successfully!")
    void testSave(){
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(monthService.findByMonth(currentDate.getDayOfMonth(),currentDate.getYear(),userMock.getId())).thenReturn(null);
        when(monthController.createNewMonthRecord(currentDate)).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(Month.class),eq(MonthDTO.class))).thenReturn(monthDTOMock);

        ResponseEntity<MonthDTO> responseEntity = monthController.save();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(monthDTOMock,responseEntity.getBody());

    }

    @Test
    @DisplayName("save - Creating a new record and returning an exception!")
    void testSaveReturning_Exception(){
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(monthService.findByMonth(currentDate.getDayOfMonth(),currentDate.getYear(),userMock.getId())).thenReturn(monthMock);

        assertThrows(RuntimeException.class, () -> monthController.save(), ExceptionMessages.MONT_ALREADY_REGISTERED);
    }

    @Test
    @DisplayName("getAll - Searching multiple records at once")
    void testGetAll() {
        ArrayList<Month> monthsMock = new ArrayList<>();
        monthsMock.add(new Month());
        monthsMock.add(new Month());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(monthService.findAll(userMock.getId())).thenReturn(monthsMock);
        when(modelMapper.map(any(Month.class),eq(MonthDTO.class))).thenReturn(monthDTOMock);

       ResponseEntity<List<MonthDTO>>listResponseEntity = monthController.getAll();

        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(2, listResponseEntity.getBody().size());
    }

    @Test
    @DisplayName("getById - Searching for a single record")
    void testGetById() {
        when(monthService.findById(idMonthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(Month.class),eq(MonthDTO.class))).thenReturn(monthDTOMock);

        ResponseEntity<MonthDTO> responseEntity =  monthController.getById(idMonthMock);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(monthDTOMock,responseEntity.getBody());
    }

    /*@Test
    @DisplayName("saveInstallment - Creating new record successfully!")
    void testSaveInstallment() {
        InstallmentPostVO installmentPostVO = new InstallmentPostVO();
        installmentPostVO.setDescription("Compra de bike");
        installmentPostVO.setFirstInstallmentDate("2024-04-20T18:21:24.939+00:00");
        installmentPostVO.setValue(50);
        installmentPostVO.setQtd(1);

        Installment installment = new Installment();
        installment.setDescription("Compra de bike");
        installment.setFirstInstallmentDate("2024-04-20T18:21:24.939+00:00");
        installment.setValue(50);
        installment.setQtd(1);

        InstallmentDTO installmentDTO = new InstallmentDTO();
        installmentDTO.setDescription("Compra de bike");
        installmentDTO.setFirstInstallmentDate("2024-04-20T18:21:24.939+00:00");
        installmentDTO.setValue(50);
        installmentDTO.setQtd(1);

        when(modelMapper.map(any(InstallmentPostVO.class),eq(Installment.class))).thenReturn(installment);
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(monthService.findByMonth(currentDate.getMonthValue(),currentDate.getYear(),userMock.getId())).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(Installment.class),eq(InstallmentDTO.class))).thenReturn(installmentDTO);

        ResponseEntity<InstallmentDTO> responseEntity = monthController.saveInstallment(installmentPostVO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(installmentDTO,responseEntity.getBody());

    }

    @Test
    @DisplayName("saveAppetizer - Creating new record successfully!")
    void testSaveAppetizer() {
        EntryPostVO entryPostVO = new EntryPostVO();
        entryPostVO.setName("Salario");
        entryPostVO.setValue(1.412);
        entryPostVO.setDescription("Pagamento mensal");

        when(modelMapper.map(any(EntryPostVO.class),eq(Entry.class))).thenReturn(entryMock);
        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(Entry.class),eq(EntryDTO.class))).thenReturn(entryDTOMock);

        ResponseEntity<EntryDTO>responseEntity = monthController.saveEntry(idMonthMock, entryPostVO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(entryDTOMock,responseEntity.getBody());

    }

    @Test
    @DisplayName("deletedAppetizer - Deleting a record")
    void testDeletedAppetizer() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(entryMock);
        monthMock.setEntry(entries);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);

        ResponseEntity<Void> responseEntity = monthController.deletedEntry(idMonthMock,idAppetizerMock);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("updatedAppetizer - Changing a record")
    void testUpdatedAppetizer() {

        EntryPutVO entryPutVO = new EntryPutVO();
        entryPutVO.setName("Salario");
        entryPutVO.setValue(1.412);
        entryPutVO.setDescription("Pagamento mensal");

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(entryMock);

        monthMock.setEntry(entries);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);

        ResponseEntity<Void> responseEntity = monthController.updatedEntry(idMonthMock,idAppetizerMock, entryPutVO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("saveExit - Creating new record successfully!")
    void testSaveExit() {
        ExitPostVO exitPostVO = new ExitPostVO();
        exitPostVO.setName("lanche");
        exitPostVO.setValue(100);
        exitPostVO.setPaymentMethods(PaymentMethods.PIX);

        ArrayList<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();
        categoryVariableExpenses.add(categoryVariableExpenseMock);

        ArrayList<Exit> exits = new ArrayList<>();
        exits.add(exitMock);

        categoryVariableExpenseMock.setExit(exits);

        monthMock.setCategoryVariableExpens(categoryVariableExpenses);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(categoryVariableExpenseService.getById(idCategoryMock)).thenReturn(categoryVariableExpenseMock);
        when(modelMapper.map(any(ExitPostVO.class),eq(Exit.class))).thenReturn(exitMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(Exit.class),eq(ExitDTO.class))).thenReturn(exitDTOMock);

        ResponseEntity<ExitDTO> responseEntity = monthController.saveExit(idMonthMock,idCategoryMock,exitPostVO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(exitDTOMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("deletedExit - Deleting a record")
    void testDeletedExit() {
        ArrayList<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();

        ArrayList<Exit> exits = new ArrayList<>();
        exits.add(exitMock);

        categoryVariableExpenseMock.setExit(exits);
        categoryVariableExpenses.add(categoryVariableExpenseMock);
        monthMock.setCategoryVariableExpens(categoryVariableExpenses);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);

        ResponseEntity<Void> responseEntity = monthController.deletedExit(idMonthMock,idCategoryMock,idExitMock);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("upadtedExit - Changing a record")
    void testUpadtedExit() {
        ExitPutVO exitPutVO = new ExitPutVO();
        exitPutVO.setName("lanche");
        exitPutVO.setValue(100);
        exitPutVO.setPaymentMethods(PaymentMethods.PIX);

        ArrayList<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();
        ArrayList<Exit> exits = new ArrayList<>();
        exits.add(exitMock);

        categoryVariableExpenseMock.setExit(exits);
        categoryVariableExpenses.add(categoryVariableExpenseMock);
        monthMock.setCategoryVariableExpens(categoryVariableExpenses);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);

        ResponseEntity<ExitDTO> responseEntity = monthController.upadtedeExit(idMonthMock,idCategoryMock,idExitMock,exitPutVO);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }

    @Test
    @DisplayName("saveFixedAccount - Creating new record successfully!")
    void testSaveFixedAccount() {
        List<FixedAccountMonthPostVO> fixedAccountMonthPostVOS = new ArrayList<>();

        FixedAccountMonthPostVO fixedAccountMonthPostVO = new FixedAccountMonthPostVO();
        fixedAccountMonthPostVO.setId("662e83aa87d932594883a386");
        fixedAccountMonthPostVO.setName("Conta de telefone");
        fixedAccountMonthPostVO.setDescription("");
        fixedAccountMonthPostVO.setValue(50);
        fixedAccountMonthPostVO.setDueDate(1);

        fixedAccountMonthPostVOS.add(fixedAccountMonthPostVO);

        ArrayList<FixedAccountDTO> fixedAccountDTOS = new ArrayList<>();
        fixedAccountDTOS.add(new FixedAccountDTO());

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(modelMapper.map(any(FixedAccountMonthPostVO.class),eq(FixedAccount.class))).thenReturn(fixedAccountMock);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(fixedAccountDTOMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);

        ResponseEntity<List<FixedAccountDTO>> listResponseEntity = monthController.saveFixedAccount(idMonthMock,fixedAccountMonthPostVOS);

        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(1,listResponseEntity.getBody().size());
    }

    @Test
    @DisplayName("deletedFixedAccount - Deleting a record")
    void testDeletedFixedAccount() {
        ArrayList<FixedAccount> fixedAccounts = new ArrayList<>();
        fixedAccounts.add(fixedAccountMock);
        monthMock.setFixedAccounts(fixedAccounts);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(fixedAccountDTOMock);

        ResponseEntity<FixedAccountDTO> fixedAccountDTOResponseEntity = monthController.deletedFixedAccount(idMonthMock,idFixedAccount);
        assertEquals(HttpStatus.OK, fixedAccountDTOResponseEntity.getStatusCode());
        assertEquals(fixedAccountDTOMock,fixedAccountDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("updatedFixedAccount - Changing a record")
    void testUpdatedFixedAccount() {
        FixedAccountMonthPutVO fixedAccountMonthPutVO = new FixedAccountMonthPutVO();
        fixedAccountMonthPutVO.setName("Conta de telefone");
        fixedAccountMonthPutVO.setDescription("");
        fixedAccountMonthPutVO.setValue(50);
        fixedAccountMonthPutVO.setDueDate(1);

        ArrayList<FixedAccount> fixedAccounts = new ArrayList<>();
        fixedAccounts.add(fixedAccountMock);
        monthMock.setFixedAccounts(fixedAccounts);

        when((monthService.findById(idMonthMock))).thenReturn(monthMock);
        when(monthService.save(monthMock)).thenReturn(monthMock);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(fixedAccountDTOMock);

        ResponseEntity<FixedAccountDTO> fixedAccountDTOResponseEntity = monthController.updatedFixedAccount(idMonthMock,idFixedAccount,fixedAccountMonthPutVO);
        assertEquals(HttpStatus.OK, fixedAccountDTOResponseEntity.getStatusCode());
        assertEquals(fixedAccountDTOMock,fixedAccountDTOResponseEntity.getBody());

    }

     */
}