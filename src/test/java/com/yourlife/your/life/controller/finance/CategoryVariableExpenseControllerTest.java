package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.CategoryVariableExpenseDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.vo.finance.CardPostVO;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpensePostVO;
import com.yourlife.your.life.model.vo.finance.CategoryVariableExpensePutVO;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
import com.yourlife.your.life.service.finance.impl.CategoryVariableExpenseServiceImpl;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("categoryVariableExpense")
class CategoryVariableExpenseControllerTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryVariableExpenseService categoryVariableExpenseService;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private CategoryVariableExpenseController categoryVariableExpenseController;

    private User userMock;
    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");
    }

    @Test
    @DisplayName("save - Creating new record successfully!")
    void testSave() {
        CategoryVariableExpensePostVO categoryVariableExpensePostVOMock = new CategoryVariableExpensePostVO();
        categoryVariableExpensePostVOMock.setName("Gastos pessoais");
        categoryVariableExpensePostVOMock.setDescription("Gastos com coisa supérfluos");

        CategoryVariableExpense categoryVariableExpenseMock = new CategoryVariableExpense();
        categoryVariableExpenseMock.setName("Gastos pessoais");
        categoryVariableExpenseMock.setDescription("Gastos com coisa supérfluos");

        CategoryVariableExpenseDTO categoryVariableExpenseDTOMock = new CategoryVariableExpenseDTO();
        categoryVariableExpenseDTOMock.setName("Gastos pessoais");
        categoryVariableExpenseDTOMock.setDescription("Gastos com coisa supérfluos");
        categoryVariableExpenseDTOMock.setId("6626fbc8b030c6195d5aa007");

        when(modelMapper.map(any(CategoryVariableExpensePostVO.class),eq(CategoryVariableExpense.class))).thenReturn(categoryVariableExpenseMock);
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(categoryVariableExpenseService.save(categoryVariableExpenseMock)).thenReturn(categoryVariableExpenseMock);
        when(modelMapper.map(any(CategoryVariableExpense.class),eq(CategoryVariableExpenseDTO.class))).thenReturn(categoryVariableExpenseDTOMock);

        ResponseEntity<CategoryVariableExpenseDTO> categoryVariableExpenseDTOResponseEntity = categoryVariableExpenseController.save(categoryVariableExpensePostVOMock);

        assertEquals(HttpStatus.OK, categoryVariableExpenseDTOResponseEntity.getStatusCode());
        assertEquals(categoryVariableExpenseDTOMock,categoryVariableExpenseDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("save - Causing an exception when creating a record with invalid data!")
    void testSave_InvalidInput() {
        CategoryVariableExpensePostVO categoryVariableExpensePostVOMock = new CategoryVariableExpensePostVO();

        assertThrows(RuntimeException.class, () -> categoryVariableExpenseController.save(categoryVariableExpensePostVOMock), ExceptionMessages.INVALID_REQUEST_COMPONENT);
    }

    @Test
    @DisplayName("saveAll - Saving multiple records at once")
    void testSaveAll() {
        List<CategoryVariableExpensePostVO> categoryVariableExpensePostVOS = new ArrayList<>();
        categoryVariableExpensePostVOS.add(new CategoryVariableExpensePostVO());

        List<CategoryVariableExpense> categoryVariableExpensesMock = new ArrayList<>();
        categoryVariableExpensesMock.add(new CategoryVariableExpense());
        categoryVariableExpensesMock.add(new CategoryVariableExpense());

        CategoryVariableExpenseDTO categoryVariableExpenseDTO = new CategoryVariableExpenseDTO();
        categoryVariableExpenseDTO.setName("Gastos pessoais");
        categoryVariableExpenseDTO.setDescription("Gastos com coisa supérfluos");
        categoryVariableExpenseDTO.setId("6626fbc8b030c6195d5aa007");

        when(modelMapper.map(any(CategoryVariableExpensePostVO.class),eq(CategoryVariableExpense.class))).thenReturn(new CategoryVariableExpense());
        when(categoryVariableExpenseService.createdSeveral(anyList())).thenReturn(categoryVariableExpensesMock);
        when(modelMapper.map(any(CategoryVariableExpense.class),eq(CategoryVariableExpenseDTO.class))).thenReturn(categoryVariableExpenseDTO);

        ResponseEntity<List<CategoryVariableExpenseDTO>> categoryVariableExpenseDTOResponseEntity = categoryVariableExpenseController.saveAll(categoryVariableExpensePostVOS);

        assertEquals(HttpStatus.OK, categoryVariableExpenseDTOResponseEntity.getStatusCode());
        assertEquals(2, categoryVariableExpenseDTOResponseEntity.getBody().size());
    }

    @Test
    @DisplayName("getAll - Searching multiple records at once")
    void testGetAll() {

        ArrayList<CategoryVariableExpense> categoryVariableExpensesMock = new ArrayList<>();
        categoryVariableExpensesMock.add(new CategoryVariableExpense());
        categoryVariableExpensesMock.add(new CategoryVariableExpense());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(categoryVariableExpenseService.getAll(userMock.getId())).thenReturn(categoryVariableExpensesMock);
        when(modelMapper.map(any(CategoryVariableExpense.class),eq(CategoryVariableExpenseDTO.class))).thenReturn(new CategoryVariableExpenseDTO());

        ResponseEntity<List<CategoryVariableExpenseDTO>> listResponseEntity = categoryVariableExpenseController.getAll();

        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
        assertEquals(2, listResponseEntity.getBody().size());
    }


    @Test
    @DisplayName("getById - Searching for a single record")
    void testGetById() {
        String id = "6626fbc8b030c6195d5aa007";

        CategoryVariableExpense categoryVariableExpenseMock = new CategoryVariableExpense();
        categoryVariableExpenseMock.setName("Gastos pessoais");
        categoryVariableExpenseMock.setDescription("Gastos com coisa supérfluos");

        CategoryVariableExpenseDTO categoryVariableExpenseDTOMock = new CategoryVariableExpenseDTO();
        categoryVariableExpenseDTOMock.setName("Gastos pessoais");
        categoryVariableExpenseDTOMock.setDescription("Gastos com coisa supérfluos");

        when(categoryVariableExpenseService.getById(id)).thenReturn(categoryVariableExpenseMock);
        when(modelMapper.map(any(CategoryVariableExpense.class),eq(CategoryVariableExpenseDTO.class))).thenReturn(categoryVariableExpenseDTOMock);

        ResponseEntity<CategoryVariableExpenseDTO> categoryVariableExpenseDTOResponseEntity = categoryVariableExpenseController.getById(id);

        assertEquals(HttpStatus.OK, categoryVariableExpenseDTOResponseEntity.getStatusCode());
        assertEquals(categoryVariableExpenseDTOMock,categoryVariableExpenseDTOResponseEntity.getBody());
    }

    @Test
    @DisplayName("deleted - Deleting a record")
    void testDeleted() {
        String id = "6626fbc8b030c6195d5aa007";

        CategoryVariableExpense categoryVariableExpenseMock = new CategoryVariableExpense();
        categoryVariableExpenseMock.setName("Gastos pessoais");
        categoryVariableExpenseMock.setDescription("Gastos com coisa supérfluos");

        CategoryVariableExpenseDTO categoryVariableExpenseDTOMock = new CategoryVariableExpenseDTO();
        categoryVariableExpenseDTOMock.setName("Gastos pessoais");
        categoryVariableExpenseDTOMock.setDescription("Gastos com coisa supérfluos");

        when(categoryVariableExpenseService.getById(id)).thenReturn(categoryVariableExpenseMock);
        when(categoryVariableExpenseService.save(categoryVariableExpenseMock)).thenReturn(categoryVariableExpenseMock);

        ResponseEntity<Void> voidResponseEntity = categoryVariableExpenseController.deleted(id);

        assertEquals(HttpStatus.OK, voidResponseEntity.getStatusCode());

    }

    @Test
    @DisplayName("updated - Changing a record")
    void updated() {
        String id = "6626fbc8b030c6195d5aa007";

        CategoryVariableExpensePutVO categoryVariableExpensePutVOMock = new CategoryVariableExpensePutVO();
        categoryVariableExpensePutVOMock.setDescription("Gastos pessoais");
        categoryVariableExpensePutVOMock.setName("Gastos com coisa supérfluos");

        CategoryVariableExpense categoryVariableExpenseMock = new CategoryVariableExpense();
        categoryVariableExpenseMock.setName("Gastos pessoais");
        categoryVariableExpenseMock.setDescription("Gastos com coisa supérfluos");

        CategoryVariableExpenseDTO categoryVariableExpenseDTOMock = new CategoryVariableExpenseDTO();
        categoryVariableExpenseDTOMock.setName("Gastos pessoais");
        categoryVariableExpenseDTOMock.setDescription("Gastos com coisa supérfluos");

        when(categoryVariableExpenseService.getById(id)).thenReturn(categoryVariableExpenseMock);
        when(categoryVariableExpenseService.save(categoryVariableExpenseMock)).thenReturn(categoryVariableExpenseMock);
        when(modelMapper.map(any(CategoryVariableExpense.class),eq(CategoryVariableExpenseDTO.class))).thenReturn(categoryVariableExpenseDTOMock);

        ResponseEntity<CategoryVariableExpenseDTO> categoryVariableExpenseDTOResponseEntity = categoryVariableExpenseController.updated(categoryVariableExpensePutVOMock,id);

        assertEquals(HttpStatus.OK, categoryVariableExpenseDTOResponseEntity.getStatusCode());
        assertEquals(categoryVariableExpenseDTOMock,categoryVariableExpenseDTOResponseEntity.getBody());

    }
}