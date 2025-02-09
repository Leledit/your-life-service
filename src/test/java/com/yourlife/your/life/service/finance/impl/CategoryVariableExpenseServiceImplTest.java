package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
import com.yourlife.your.life.utils.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("CategoryVariableExpenseServiceImpl")
class CategoryVariableExpenseServiceImplTest {

    @Mock
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private CategoryVariableExpenseServiceImpl categoryVariableExpenseService;

    private User userMock;

    private CategoryVariableExpense  categoryVariableExpenseMock;

    @BeforeEach
    public void setUp() {
        userMock = User
                .builder()
                .email("test@teste.com.br")
                .id("67a782cbf1c9cc32ec877f00")
                .name("leandro")
                .password("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS")
                .build();

        categoryVariableExpenseMock =
                CategoryVariableExpense
                        .builder()
                        .name("Buy basics")
                        .description("Buy not very frequently")
                        .deleted(false)
                        .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveAccountsCategoryExpense(){
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(categoryVariableExpenseRepository.save(any(CategoryVariableExpense.class))).thenReturn(categoryVariableExpenseMock);

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.save(
                CategoryVariableExpensePostDTO
                .builder()
                .name("Buy basics")
                .description("Buy not very frequently")
                .build());

        assertNotNull(categoryVariableExpense);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllAccountsCategoryExpense(){
        ArrayList<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>(
                Arrays.asList(
                        new CategoryVariableExpense(),
                        new CategoryVariableExpense()
                )
        );
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(categoryVariableExpenseRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(categoryVariableExpenses));

        ArrayList<CategoryVariableExpense> categoryVariableExpenseArrayList = categoryVariableExpenseService.getAll();

        assertEquals(2, Objects.requireNonNull(categoryVariableExpenseArrayList.size()));
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetByIdAccountsCategoryExpense(){
        when(categoryVariableExpenseRepository.findByIdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(categoryVariableExpenseMock));

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.getById("67a782cbf1c9cc32ec877f00");

        assertNotNull(categoryVariableExpense);
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdatedAccountsCategoryExpense(){
        CategoryVariableExpensePutDTO categoryVariableExpensePutDTO =
                CategoryVariableExpensePutDTO
                        .builder()
                        .name("Buy basics 2")
                        .description("Buy not very frequently 2")
                        .build();

        when(categoryVariableExpenseRepository.findByIdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(categoryVariableExpenseMock));
        when(categoryVariableExpenseRepository.save(any(CategoryVariableExpense.class))).thenReturn(categoryVariableExpenseMock);

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.update("67a782cbf1c9cc32ec877f00" ,categoryVariableExpensePutDTO);

        assertNotNull(categoryVariableExpense);
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedAccountsCategoryExpense(){
        when(categoryVariableExpenseRepository.findByIdAndDeleted(userMock.getId(),false)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> categoryVariableExpenseService.delete("67a782cbf1c9cc32ec877f00"));

        assertEquals(ExceptionMessages.CATEGORY_VARIABLE_EXPENSE_NOT_FOUND, thrown.getMessage());
    }
}