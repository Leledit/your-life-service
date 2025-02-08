package com.yourlife.your.life.controller.finance;


import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.service.finance.MonthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("MonthController")
class MonthControllerTest {

    @Mock
    private MonthService monthService;

    @InjectMocks
    private MonthController monthController;

    private Month monthMock;

    @BeforeEach
    public void setUp() {
        monthMock = Month
                .builder()
                .name("February")
                .year(2025)
                .month(2)
                .date(LocalDateTime.now())
                .fixedAccounts(new ArrayList<>())
                .installments(new ArrayList<>())
                .exits(new ArrayList<>())
                .entry(new ArrayList<>())
                .benefitItems(new ArrayList<>())
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveMonth() {
        when(monthService.save()).thenReturn(monthMock);

        ResponseEntity<Month> responseEntity = monthController.saveMonth();

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(monthMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllMonth(){
        List<Month> months = new ArrayList<>();
        months.add(new Month());
        months.add(new Month());

        when(monthService.findAll()).thenReturn(months);

        ResponseEntity<List<Month>> responseEntity = monthController.getAllMonth();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetMonthById(){
        when(monthService.findById("67a782cbf1c9cc32ec877f00")).thenReturn(monthMock);

        ResponseEntity<Month> responseEntity = monthController.getMonthById("67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(monthMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetById - Searching for a single record by data")
    void testGetByMonthAndYear(){
        when(monthService.findByMonth(4,2025)).thenReturn(monthMock);

        ResponseEntity<Month> responseEntity = monthController.getByMonthAndYear(4,2025);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(monthMock,responseEntity.getBody());
    }
}