package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPostDTO;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPutDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.FixedAccountRepository;
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
@DisplayName("FixedAccountServiceImpl")
class FixedAccountServiceImplTest {

    @Mock
    private FixedAccountRepository fixedAccountRepository;

    @Mock
    private UserContext userContext;

    @Mock
    private MonthRepository monthRepository;

    @InjectMocks
    private FixedAccountServiceImpl fixedAccountService;


    private FixedAccount fixedAccountMock;

    private Month monthMock;

    private User userMock;

    @BeforeEach
    public void setUp(){
        fixedAccountMock = FixedAccount
                .builder()
                .name("cell phone plan")
                .value(55)
                .description("plan used on my business cell phone")
                .deleted(false)
                .dueDate(5)
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
    @DisplayName("save - Creating new record successfully!")
    void testSaveFixedAccount() {
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(fixedAccountRepository.save(any(FixedAccount.class))).thenReturn(fixedAccountMock);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,userMock.getId())).thenReturn(Optional.ofNullable(monthMock));
        when(monthRepository.save(monthMock)).thenReturn(monthMock);

        FixedAccount account = fixedAccountService.save(FixedAccountPostDTO
                .builder()
                .name("cell phone plan")
                .value(55)
                .description("plan used on my business cell phone")
                .build());

        assertNotNull(account);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllFixedAccounts(){
        ArrayList<FixedAccount> fixedAccounts = new ArrayList<>();
        fixedAccounts.add(new FixedAccount());
        fixedAccounts.add(new FixedAccount());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(fixedAccountRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(fixedAccounts));

        List<FixedAccount> fixedAccountList = fixedAccountService.findAllByUser();
        assertEquals(2,fixedAccountList.size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetFixedAccountById(){
        when(fixedAccountRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.of(fixedAccountMock));

        FixedAccount fixedAccount = fixedAccountService.findById("67a782cbf1c9cc32ec877f00");

        assertNotNull(fixedAccount);
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdatedFixedAccount(){
        when(fixedAccountRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.of(fixedAccountMock));
        when(fixedAccountRepository.save(any(FixedAccount.class))).thenReturn(fixedAccountMock);

        FixedAccount fixedAccount = fixedAccountService.update("67a782cbf1c9cc32ec877f00", FixedAccountPutDTO
                .builder()
                .name("cell phone plan 2")
                .value(55)
                .description("plan used on my business cell phone 2")
                .build());

        assertNotNull(fixedAccount);
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedFixedAccount(){
        when(fixedAccountRepository.findByIdAndDeleted("662707bea770e96a56b3d049", false)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> fixedAccountService.delete("662707bea770e96a56b3d049"));

        assertEquals(ExceptionMessages.FIXED_ACCOUNT_NOT_FOUND, thrown.getMessage());
    }
}