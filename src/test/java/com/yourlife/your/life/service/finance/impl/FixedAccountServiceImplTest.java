package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.FixedAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("FixedAccount")
class FixedAccountServiceImplTest {
/*
    @Mock
    private FixedAccountRepository fixedAccountRepository;

    @InjectMocks
    private FixedAccountServiceImpl fixedAccountService;

    private User userMock;

    private FixedAccount fixedAccountMock;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        fixedAccountMock = new FixedAccount();
        fixedAccountMock.setName("plano de celular");
        fixedAccountMock.setValue(55);
        fixedAccountMock.setDescription("plano usado no meu celular comercial");
        fixedAccountMock.setDueDate(5);
        fixedAccountMock.setId("662707bea770e96a56b3d049");
        fixedAccountMock.setDeleted(false);
    }

    @Test
    @DisplayName("FixedAccount - Check success when registering a new fixedAccount")
    void testSave() {
        when(fixedAccountRepository.save(fixedAccountMock)).thenReturn(fixedAccountMock);

        FixedAccount fixedAccount = fixedAccountService.save(fixedAccountMock);

        assertNotNull(fixedAccount);
    }

    @Test
    @DisplayName("FixedAccount - Search for all fixed accounts and receive null as a return")
    void testGetAllReturning_Null() {
        when(fixedAccountRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.empty());

        List<FixedAccount> fixedAccounts = fixedAccountService.findAllByUser(userMock.getId());

        assertNull(fixedAccounts);
    }


    @Test
    @DisplayName("FixedAccount - Search for all fixed accounts and receive a list in return")
    void testGetAllReturning_List(){
        ArrayList<FixedAccount> fixedAccountsMock = new ArrayList<>();
        fixedAccountsMock.add(new FixedAccount());

        when(fixedAccountRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(fixedAccountsMock));

        List<FixedAccount> fixedAccounts = fixedAccountService.findAllByUser(userMock.getId());

        assertEquals(1, fixedAccounts.size());
    }

    @Test
    @DisplayName("FixedAccount - looking for a single fixed account")
    void testGetByIdReturning_FixedAccount() {
        when(fixedAccountRepository.findById("662707bea770e96a56b3d049")).thenReturn(Optional.of(fixedAccountMock));

        FixedAccount account = fixedAccountService.findById("662707bea770e96a56b3d049");

        assertEquals("662707bea770e96a56b3d049",account.getId());
    }

    @Test
    @DisplayName("FixedAccount - throwing an exception when trying to search for a deleted FixedAccount")
    void testGetByIdReturning_Exception() {
        fixedAccountMock.setDeleted(true);

        when(fixedAccountRepository.findById("662707bea770e96a56b3d049")).thenReturn(Optional.of(fixedAccountMock));

        assertThrows(RuntimeException.class, () -> fixedAccountService.findById("662707bea770e96a56b3d049"), ExceptionMessages.NOT_FOUND);
    }*/
}