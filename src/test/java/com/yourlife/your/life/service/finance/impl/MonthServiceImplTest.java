package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.MonthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("Month")
class MonthServiceImplTest {

    @Mock
    private MonthRepository monthRepository;

    @InjectMocks
    private MonthServiceImpl monthService;

    private User userMock;

    private Month monthMock;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        monthMock = new Month();
        monthMock.setName("maio");
        monthMock.setId("662e9866e348a57153c48cdd");
        monthMock.setDate(LocalDate.parse("2025-04-28", DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        monthMock.setMonth(5);
        monthMock.setYear(2014);
        monthMock.setUser(userMock);
        monthMock.setEntry(new ArrayList<>());
        monthMock.setCategoryVariableExpens(new ArrayList<>());
        monthMock.setInstallments(new ArrayList<>());
        monthMock.setFixedAccounts(new ArrayList<>());
    }

    @Test
    @DisplayName("Month - Check success when registering a new month")
    void testSave() {
        when(monthRepository.save(monthMock)).thenReturn(monthMock);

        Month month = monthService.save(monthMock);

        assertNotNull(month);
    }

    @Test
    @DisplayName("Month - Looking for a single month")
    void testFindByMonth() {
        when(monthRepository.findByYearAndMonthAndUser_Id(monthMock.getYear(),monthMock.getMonth(),userMock.getId())).thenReturn(monthMock);

        Month month = monthService.findByMonth(monthMock.getMonth(),monthMock.getYear(),userMock.getId());

        assertEquals("662e9866e348a57153c48cdd",month.getId());
    }

    @Test
    @DisplayName("Month - Fetching all months, returning a list of months")
    void testGetAllReturning_List() {
        ArrayList<Month> monthMock = new ArrayList<>();
        monthMock.add(new Month());
        monthMock.add(new Month());

        when(monthRepository.findAllByUser_Id(userMock.getId())).thenReturn(Optional.of(monthMock));

        List<Month> months = monthService.getAll(userMock.getId());

        assertEquals(2,months.size());
    }

    @Test
    @DisplayName("Month - Fetching all months, returning a list of months")
    void testGetAllReturning_Null() {
        when(monthRepository.findAllByUser_Id(userMock.getId())).thenReturn(Optional.empty());

        List<Month> months = monthService.getAll(userMock.getId());

        assertNull(months);
    }

    @Test
    @DisplayName("Month - Searching for a month by id, and returning a record")
    void findByIdReturning_Month() {
        when(monthRepository.findById("662e9866e348a57153c48cdd")).thenReturn(Optional.of(monthMock));

        Month month = monthService.findById("662e9866e348a57153c48cdd");

        assertEquals("662e9866e348a57153c48cdd",month.getId());
    }

    @Test
    @DisplayName("Month - Searching for a month by id, and returning null")
    void findByIdReturning_Null() {
        when(monthRepository.findById("662e9866e348a57153c48cdd")).thenReturn(Optional.empty());

        Month month = monthService.findById("662e9866e348a57153c48cdd");

        assertNull(month);
    }
}