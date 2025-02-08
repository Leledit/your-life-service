package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPostDTO;
import com.yourlife.your.life.model.dto.finance.FixedAccount.FixedAccountPutDTO;
import com.yourlife.your.life.model.entity.finance.FixedAccount;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.service.finance.FixedAccountService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("FixedAccount")
class FixedAccountControllerTest {

    @Mock
    private FixedAccountService fixedAccountService;

    @InjectMocks
    private FixedAccountController fixedAccountController;

    private FixedAccount fixedAccountMock;

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
    }

    @Test
    @DisplayName("save - Creating new record successfully!")
    void testSaveFixedAccount() {
        FixedAccountPostDTO fixedAccountPostDTO =
                FixedAccountPostDTO
                        .builder()
                        .name("cell phone plan")
                        .value(55)
                        .description("plan used on my business cell phone")
                        .build();

        when(fixedAccountService.save(fixedAccountPostDTO)).thenReturn(fixedAccountMock);

        ResponseEntity<FixedAccount> responseEntity = fixedAccountController.saveFixedAccount(fixedAccountPostDTO);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(fixedAccountMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllFixedAccounts(){
        List<FixedAccount> fixedAccounts = new ArrayList<>();
        fixedAccounts.add(new FixedAccount());
        fixedAccounts.add(new FixedAccount());

        when(fixedAccountService.findAllByUser()).thenReturn(fixedAccounts);

        ResponseEntity<List<FixedAccount>> responseEntity = fixedAccountController.getAllFixedAccounts();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetFixedAccountById(){
        when(fixedAccountService.findById("67a782cbf1c9cc32ec877f00")).thenReturn(fixedAccountMock);
        ResponseEntity<FixedAccount> responseEntity = fixedAccountController.getFixedAccountById("67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(fixedAccountMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdatedFixedAccount(){
        FixedAccountPutDTO fixedAccountPutDTO =
                FixedAccountPutDTO
                        .builder()
                        .name("cell phone plan 2")
                        .value(55)
                        .description("plan used on my business cell phone 2")
                        .build();

        when(fixedAccountService.update("67a782cbf1c9cc32ec877f00", fixedAccountPutDTO)).thenReturn(fixedAccountMock);

        ResponseEntity<FixedAccount> responseEntity = fixedAccountController.updatedFixedAccount(fixedAccountPutDTO,"67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(fixedAccountMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedFixedAccount(){
        ResponseEntity<Void> responseEntity = fixedAccountController.deletedFixedAccount("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}