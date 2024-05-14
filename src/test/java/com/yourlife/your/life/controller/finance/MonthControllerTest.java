package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.MonthDTO;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
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
import java.time.format.TextStyle;
import java.util.ArrayList;
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
    private CategoryVariableExpenseService categoryVariableExpenseService;

    @InjectMocks
    private MonthController monthController;

    private User userMock;

    private Month monthMock;

    private MonthDTO monthDTOMock;

    private LocalDate currentDate;

    @BeforeEach
    public void setUp(){
        currentDate = LocalDate.now();

        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        monthMock = new Month();
        monthMock.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        monthMock.setDate(currentDate);
        monthMock.setMonth(currentDate.getMonthValue());
        monthMock.setYear(currentDate.getYear());
        monthMock.setUser(userMock);
        monthMock.setAppetizer(new ArrayList<>());
        monthMock.setCategoryVariableExpens(new ArrayList<>());
        monthMock.setInstallments(new ArrayList<>());
        monthMock.setFixedAccounts(new ArrayList<>());

        monthDTOMock = new MonthDTO();
        monthDTOMock.setName(currentDate.getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
        monthDTOMock.setId("662e9866e348a57153c48cdd");
        monthDTOMock.setDate(currentDate);
        monthDTOMock.setYear(currentDate.getYear());
        monthDTOMock.setAppetizer(new ArrayList<>());
        monthDTOMock.setCategoryVariableExpens(new ArrayList<>());
        monthDTOMock.setInstallments(new ArrayList<>());
        monthDTOMock.setFixedAccounts(new ArrayList<>());
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
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void saveInstallment() {
    }

    @Test
    void saveAppetizer() {
    }

    @Test
    void deletedAppetizer() {
    }

    @Test
    void updatedAppetizer() {
    }

    @Test
    void saveExit() {
    }

    @Test
    void deletedExit() {
    }

    @Test
    void upadtedExit() {
    }

    @Test
    void saveFixedAccount() {
    }

    @Test
    void deletedFixedAccount() {
    }

    @Test
    void updatedFixedAccount() {
    }
}