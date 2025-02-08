package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePostDTO;
import com.yourlife.your.life.model.dto.finance.categoryVariableExpense.CategoryVariableExpensePutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.service.finance.CategoryVariableExpenseService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("categoryVariableExpense")
class CategoryVariableExpenseControllerTest {

    @Mock
    private CategoryVariableExpenseService categoryVariableExpenseService;

    @InjectMocks
    private CategoryVariableExpenseController categoryVariableExpenseController;

    private CategoryVariableExpense  categoryVariableExpenseMock;

    @BeforeEach
    public void setUp(){
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
        CategoryVariableExpensePostDTO categoryVariableExpensePostDTO =
                CategoryVariableExpensePostDTO
                        .builder()
                        .name("Buy basics")
                        .description("Buy not very frequently")
                        .build();

        when(categoryVariableExpenseService.save(categoryVariableExpensePostDTO)).thenReturn(categoryVariableExpenseMock);

        ResponseEntity<CategoryVariableExpense> responseEntity = categoryVariableExpenseController.saveAccountsCategoryExpense(categoryVariableExpensePostDTO);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(categoryVariableExpenseMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllAccountsCategoryExpense(){
        List<CategoryVariableExpense> categoryVariableExpenses = new ArrayList<>();
        categoryVariableExpenses.add(new CategoryVariableExpense());
        categoryVariableExpenses.add(new CategoryVariableExpense());

        when(categoryVariableExpenseService.getAll()).thenReturn(categoryVariableExpenses);

        ResponseEntity<List<CategoryVariableExpense>> responseEntity = categoryVariableExpenseController.getAllAccountsCategoryExpense();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetByIdAccountsCategoryExpense(){
        when(categoryVariableExpenseService.getById("67a782cbf1c9cc32ec877f00")).thenReturn(categoryVariableExpenseMock);
        ResponseEntity<CategoryVariableExpense> responseEntity = categoryVariableExpenseController.getByIdAccountsCategoryExpense("67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(categoryVariableExpenseMock,responseEntity.getBody());
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

        when(categoryVariableExpenseService.update("67a782cbf1c9cc32ec877f00",categoryVariableExpensePutDTO)).thenReturn(categoryVariableExpenseMock);

        ResponseEntity<CategoryVariableExpense> responseEntity = categoryVariableExpenseController.updatedAccountsCategoryExpense(categoryVariableExpensePutDTO,"67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(categoryVariableExpenseMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedAccountsCategoryExpense(){
        ResponseEntity<Void> responseEntity = categoryVariableExpenseController.deletedAccountsCategoryExpense("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}