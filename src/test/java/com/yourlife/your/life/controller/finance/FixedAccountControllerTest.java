package com.yourlife.your.life.controller.finance;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("FixedAccount")
class FixedAccountControllerTest {
/*
    @Mock
    private FixedAccountService fixedAccountService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private FixedAccountController fixedAccountController;

    private User userMock;

    private FixedAccount fixedAccountMock;

    private FixedAccountDTO fixedAccountDTOMock;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        fixedAccountMock = new FixedAccount();
        fixedAccountMock.setId("6626fbc8b030c6195d5aa007");
        fixedAccountMock.setName("plano de celular");
        fixedAccountMock.setValue(55);
        fixedAccountMock.setDescription("plano usado no meu celular comercial");
        fixedAccountMock.setDueDate(5);

        fixedAccountDTOMock = new FixedAccountDTO();
        fixedAccountDTOMock.setName("plano de celular");
        fixedAccountDTOMock.setValue(55);
        fixedAccountDTOMock.setDescription("plano usado no meu celular comercial");
        fixedAccountDTOMock.setDueDate(5);

    }


    @Test
    @DisplayName("save - Creating new record successfully!")
    void testSave() {
        FixedAccountPostVO fixedAccountPostVOMock = new FixedAccountPostVO();
        fixedAccountPostVOMock.setName("plano de celular");
        fixedAccountPostVOMock.setValue(55);
        fixedAccountPostVOMock.setDescription("plano usado no meu celular comercial");
        fixedAccountPostVOMock.setDueDate(5);

        when(modelMapper.map(any(FixedAccountPostVO.class),eq(FixedAccount.class))).thenReturn(fixedAccountMock);
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(fixedAccountService.save(fixedAccountMock)).thenReturn(fixedAccountMock);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(fixedAccountDTOMock);

        ResponseEntity<FixedAccountDTO> fixedAccountDTOResponseEntity = fixedAccountController.save(fixedAccountPostVOMock);

        assertEquals(HttpStatus.OK, fixedAccountDTOResponseEntity.getStatusCode());
        assertEquals(fixedAccountDTOMock,fixedAccountDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("getAll - Searching multiple records at once")
    void testGetAll() {
        ArrayList<FixedAccount> fixedAccounts = new ArrayList<>();
        fixedAccounts.add(new FixedAccount());
        fixedAccounts.add(new FixedAccount());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(fixedAccountService.findAll(userMock.getId())).thenReturn(fixedAccounts);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(new FixedAccountDTO());

        ResponseEntity<ArrayList<FixedAccountDTO>> listResponseEntity = fixedAccountController.getAll();

        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(2, listResponseEntity.getBody().size());

    }

    @Test
    @DisplayName("getById - Searching for a single record")
    void testGetById() {

        when(fixedAccountService.findById(fixedAccountDTOMock.getId())).thenReturn(fixedAccountMock);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(fixedAccountDTOMock);

        ResponseEntity<FixedAccountDTO> fixedAccountDTOResponseEntity = fixedAccountController.getById(fixedAccountDTOMock.getId());

        assertEquals(HttpStatus.OK, fixedAccountDTOResponseEntity.getStatusCode());
        assertEquals(fixedAccountDTOMock,fixedAccountDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("deleted - Deleting a record")
    void testDeleted() {
        when(fixedAccountService.findById(fixedAccountMock.getId())).thenReturn(fixedAccountMock);
        when(fixedAccountService.save(fixedAccountMock)).thenReturn(fixedAccountMock);

        ResponseEntity<Void> responseEntity = fixedAccountController.deleted(fixedAccountMock.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("updated - Changing a record")
    void testUpdated() {
        FixedAccountPutVO fixedAccountPutVO = new FixedAccountPutVO();
        fixedAccountPutVO.setName("plano de celular");
        fixedAccountPutVO.setValue(55);
        fixedAccountPutVO.setDescription("plano usado no meu celular comercial");
        fixedAccountPutVO.setDueDate(5);

        when(fixedAccountService.findById(fixedAccountMock.getId())).thenReturn(fixedAccountMock);
        when(fixedAccountService.save(fixedAccountMock)).thenReturn(fixedAccountMock);
        when(modelMapper.map(any(FixedAccount.class),eq(FixedAccountDTO.class))).thenReturn(new FixedAccountDTO());

        ResponseEntity<FixedAccountDTO> responseEntity = fixedAccountController.updated(fixedAccountPutVO,fixedAccountMock.getId());

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());;
    }

     */
}