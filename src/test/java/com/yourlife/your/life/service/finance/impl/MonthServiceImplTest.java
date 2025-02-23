package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.InstallmentRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
import com.yourlife.your.life.utils.UserContext;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("MonthServiceImpl")
class MonthServiceImplTest {

    @Mock
    private MonthRepository monthRepository;

    @Mock
    private UserContext userContext;

    @Mock
    private InstallmentRepository installmentRepository;

    @InjectMocks
    private MonthServiceImpl monthService;

    private Month monthMock;

    private User userMock;

    private Installment installmentMock;

    @BeforeEach
    public void setUp() {
        userMock = User
                .builder()
                .email("test@teste.com.br")
                .id("67a782cbf1c9cc32ec877f00")
                .name("leandro")
                .password("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS")
                .build();

        monthMock = Month
                .builder()
                .id("67a782cbf1c9cc32ec877f022")
                .name("February")
                .year(2025)
                .month(2)
                .date(LocalDateTime.parse("2025-02-09T10:00:00"))
                .fixedAccounts(new ArrayList<>())
                .installments(new ArrayList<>())
                .exits(new ArrayList<>())
                .entry(new ArrayList<>())
                .benefitItems(new ArrayList<>())
                .build();

        LocalDate localDateMock = LocalDate.now();
        installmentMock = Installment
                .builder()
                .description("Buying a refrigerator")
                .firstInstallmentDate(localDateMock)
                .firstInstallmentDate(localDateMock.plusMonths(2))
                .value(30)
                .qtd(2)
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveMonth() {
        ArrayList<Installment> installmentList = new ArrayList<>();
        installmentList.add(new Installment());
        installmentList.add(new Installment());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(installmentRepository.findByFirstInstallmentDateLessThanEqualAndLastInstallmentDateGreaterThanEqualAndDeleted(
                any(LocalDateTime.class), any(LocalDateTime.class), eq(false)))
                .thenReturn(installmentList);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,userMock.getId())).thenReturn(Optional.empty());
        when(monthRepository.save(any(Month.class))).thenReturn(monthMock);

        Month month = monthService.save();
        assertNotNull(month);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllMonth(){
        ArrayList<Month> months = new ArrayList<>();
        months.add(new Month());
        months.add(new Month());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(monthRepository.findAllByUser_Id(userMock.getId())).thenReturn(Optional.of(months));

        List<Month> monthList = monthService.findAll();

        assertEquals(2,monthList.size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetMonthById(){
        when(monthRepository.findById("67a782cbf1c9cc32ec877f00")).thenReturn(Optional.ofNullable(monthMock));

        Month month = monthService.findById("67a782cbf1c9cc32ec877f00");
        assertNotNull(month);
    }

    @Test
    @DisplayName("GetByMonthAndYear - Searching for a single record")
    void testFindByMonth(){
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,"67a782cbf1c9cc32ec877f00")).thenReturn(Optional.ofNullable(monthMock));
        Month month = monthService.findByMonth(2,2025);
        assertNotNull(month);
    }
}