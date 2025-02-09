package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPutDTO;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("InstallmentServiceImpl")
public class InstallmentServiceImplTest {

    @Mock
    private InstallmentRepository installmentRepository;

    @Mock
    private UserContext userContext;

    @Mock
    private MonthRepository monthRepository;

    @InjectMocks
    private InstallmentServiceImpl installmentService;

    private Month monthMock;

    private User userMock;

    private Installment installmentMock;

    @BeforeEach
    public void setUp(){

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

        userMock = User
                .builder()
                .email("test@teste.com.br")
                .id("67a782cbf1c9cc32ec877f00")
                .name("leandro")
                .password("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS")
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
    void testSaveInstallment() {
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(installmentRepository.save(any(Installment.class))).thenReturn(installmentMock);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,userMock.getId())).thenReturn(Optional.ofNullable(monthMock));
        when(monthRepository.save(monthMock)).thenReturn(monthMock);

        Installment installment = installmentService.save(
                InstallmentPostDTO
                        .builder()
                        .description("Buying a refrigerator")
                        .value(30)
                        .qtd(2)
                        .build()
        );

        assertNotNull(installment);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllInstallment(){
        ArrayList<Installment> installmentList = new ArrayList<>();
        installmentList.add(new Installment());
        installmentList.add(new Installment());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(installmentRepository.findAllByUser_IdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.of(installmentList));

        List<Installment> installments = installmentService.findAllByUser();
        assertEquals(2,installments.size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetInstallmentById(){
        when(installmentRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.ofNullable(installmentMock));

        Installment installment = installmentService.findById("67a782cbf1c9cc32ec877f00");

        assertNotNull(installment);
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateInstallment(){
        when(installmentRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.ofNullable(installmentMock));
        when(installmentRepository.save(any(Installment.class))).thenReturn(installmentMock);

        Installment installment = installmentService.update("67a782cbf1c9cc32ec877f00", InstallmentPutDTO
                .builder()
                .description("Buying a refrigerator 2")
                .value(30)
                .qtd(4)
                .build());

        assertNotNull(installment);
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedInstallment(){
        when(installmentRepository.findByIdAndDeleted("662707bea770e96a56b3d049", false)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> installmentService.deleted("662707bea770e96a56b3d049"));

        assertEquals(ExceptionMessages.INSTALLMENT_NOT_FOUND, thrown.getMessage());
    }
}
