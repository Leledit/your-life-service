package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.CategoryVariableExpenseRepository;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("CategoryVariableExpense")
class VariableExpensesCategoryServiceImplTest {
/*
    @Mock
    private CategoryVariableExpenseRepository categoryVariableExpenseRepository;

    @InjectMocks
    private VariableExpensesCategoryServiceImpl categoryVariableExpenseService;

    private User userMock;

    private CategoryVariableExpense categoryVariableExpenseMock;

    @BeforeEach
    public void setUp(){
        userMock = new User();
        userMock.setId("6621b1c02c3dbe50ac7d6319");
        userMock.setEmail("test@teste.com.br");
        userMock.setName("leandro");
        userMock.setPassword("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS");

        categoryVariableExpenseMock = new CategoryVariableExpense();
        categoryVariableExpenseMock.setName("Gastos pessoais");
        categoryVariableExpenseMock.setDescription("Gastos com coisa supérfluos");
        categoryVariableExpenseMock.setUser(userMock);
        categoryVariableExpenseMock.setCreatedAt(LocalDateTime.now());
        categoryVariableExpenseMock.setId("6626fbc8b030c6195d5aa007");
        categoryVariableExpenseMock.setDeleted(false);
    }
/*
    @Test
    @DisplayName("CategoryVariableExpense - Check success in creating a new varied category")
    void testSave() {
        when(categoryVariableExpenseRepository.save(categoryVariableExpenseMock)).thenReturn(categoryVariableExpenseMock);

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.save(categoryVariableExpenseMock);

        assertNotNull(categoryVariableExpense);
    }

    @Test
    @DisplayName("CategoryVariableExpense - Searching for all variable account categories - returning null ")
    void testGetAllReturning_Null() {
        when(categoryVariableExpenseRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.empty());

        List<CategoryVariableExpense> categoryVariableExpenses = categoryVariableExpenseService.getAll(userMock.getId());

        assertNull(categoryVariableExpenses);
    }

    @Test
    @DisplayName("CategoryVariableExpense - Searching for all variable account categories - returning a list of data ")
    void testGetAllReturning_List() {
        ArrayList<CategoryVariableExpense> categoryVariableExpensesMock = new ArrayList<>();
        categoryVariableExpensesMock.add(new CategoryVariableExpense());

        when(categoryVariableExpenseRepository.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(categoryVariableExpensesMock));

        List<CategoryVariableExpense> categoryVariableExpenses = categoryVariableExpenseService.getAll(userMock.getId());

        assertEquals(1, categoryVariableExpenses.size());
    }

    @Test
    @DisplayName("CategoryVariableExpense - Buscando uma unica categorias, por id ")
    void testGetByIdReturning_CategoryVariableExpense() {
        when(categoryVariableExpenseRepository.findById("6626fbc8b030c6195d5aa007")).thenReturn(Optional.of(categoryVariableExpenseMock));

        CategoryVariableExpense categoryVariableExpense = categoryVariableExpenseService.getById("6626fbc8b030c6195d5aa007");

        assertEquals("6626fbc8b030c6195d5aa007",categoryVariableExpense.getId());
    }
    @Test
    @DisplayName("CategoryVariableExpense - Expecting an exception, as the category belonging to the id is excluded ")
    void testGetByIdReturning_Exception() {
        categoryVariableExpenseMock.setDeleted(true);

        when(categoryVariableExpenseRepository.findById("6626fbc8b030c6195d5aa007")).thenReturn(Optional.of(categoryVariableExpenseMock));

        assertThrows(RuntimeException.class, () -> categoryVariableExpenseService.getById("6626fbc8b030c6195d5aa007"), ExceptionMessages.NOT_FOUND);

    }

    @Test
    @DisplayName("CategoryVariableExpense - Success in carrying out batch registration of several different categories ")
    void createdSeveral() {
        ArrayList<CategoryVariableExpense> categoryVariableExpensesMock = new ArrayList<>();
        categoryVariableExpensesMock.add(new CategoryVariableExpense());
        categoryVariableExpensesMock.add(new CategoryVariableExpense());

        when(categoryVariableExpenseRepository.saveAll(categoryVariableExpensesMock)).thenReturn(categoryVariableExpensesMock);

        List<CategoryVariableExpense> categoryVariableExpenses = categoryVariableExpenseService.createdSeveral(categoryVariableExpensesMock);

        assertEquals(2, categoryVariableExpenses.size());
    }*/
}