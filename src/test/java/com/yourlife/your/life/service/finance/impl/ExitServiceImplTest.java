package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.finance.Exit;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.types.finance.PaymentMethods;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.repository.finance.ExitRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("ExitServiceImpl")
public class ExitServiceImplTest {

    @Mock
    private ExitRepository exitRepository;

    @Mock
    private UserContext userContext;

    @Mock
    private MonthRepository monthRepository;

    @Mock
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @InjectMocks
    private ExitServiceImpl exitService;

    private Month monthMock;

    private User userMock;

    private Exit exitMock;

    private CategoryVariableExpense categoryVariableExpenseMock;

    @BeforeEach
    public void setUp() {
        categoryVariableExpenseMock =
                CategoryVariableExpense
                        .builder()
                        .name("Buy basics")
                        .description("Buy not very frequently")
                        .deleted(false)
                        .build();

        exitMock = Exit
                .builder()
                .name("Buying clothes")
                .paymentMethods(PaymentMethods.PIX)
                .value(350)
                .deleted(false)
                .categoryVariableExpense(categoryVariableExpenseMock)
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

        userMock = User
                .builder()
                .email("test@teste.com.br")
                .id("67a782cbf1c9cc32ec877f00")
                .name("leandro")
                .password("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS")
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveExit(){
        when(categoryVariableExpenseRepository.findById("67a782cbf1c9cc32ec877f020")).thenReturn(Optional.of(categoryVariableExpenseMock));
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(exitRepository.save(any(Exit.class))).thenReturn(exitMock);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,userMock.getId())).thenReturn(Optional.ofNullable(monthMock));
        when(monthRepository.save(monthMock)).thenReturn(monthMock);

        Exit exit = exitService.save(ExitPostDTO
                .builder()
                .name("Buying clothes")
                .paymentMethods(PaymentMethods.PIX)
                .categoryVariableExpense("67a782cbf1c9cc32ec877f020")
                .value(350)
                .build());

        assertNotNull(exit);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllExit(){
        List<Exit> exits = new ArrayList<>();
        exits.add(new Exit());
        exits.add(new Exit());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(exitRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(exits));

        List<Exit> exitList = exitService.findAllByUser();
        assertEquals(2,exitList.size());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateExit(){
        when(exitRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f020",false)).thenReturn(Optional.ofNullable(exitMock));
        when(exitRepository.save(any(Exit.class))).thenReturn(exitMock);

        Exit exit = exitService.update("67a782cbf1c9cc32ec877f020", ExitPutDTO
                        .builder()
                        .name("Buying clothes")
                        .paymentMethods(PaymentMethods.PIX)
                        .value(350)
                        .build());
        assertNotNull(exit);
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeleteExit(){
        when(exitRepository.findByIdAndDeleted("662707bea770e96a56b3d049", false)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> exitService.delete("662707bea770e96a56b3d049"));

        assertEquals(ExceptionMessages.EXIT_NOT_FOUND, thrown.getMessage());
    }
}
