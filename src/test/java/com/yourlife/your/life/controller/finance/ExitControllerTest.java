package com.yourlife.your.life.controller.finance;
import com.yourlife.your.life.model.dto.finance.exit.ExitPostDTO;
import com.yourlife.your.life.model.dto.finance.exit.ExitPutDTO;
import com.yourlife.your.life.model.entity.finance.CategoryVariableExpense;
import com.yourlife.your.life.model.entity.finance.Exit;
import com.yourlife.your.life.model.types.finance.PaymentMethods;
import com.yourlife.your.life.service.finance.ExitService;
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
@DisplayName("ExitController")
public class ExitControllerTest {

    @Mock
    private ExitService exitService;

    @InjectMocks
    private ExitController exitController;

    private Exit exitMock;

    @BeforeEach
    public void setUp(){
        CategoryVariableExpense categoryVariableExpense =
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
                .categoryVariableExpense(categoryVariableExpense)
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveExit(){
        ExitPostDTO exitPostDTO =
                ExitPostDTO
                        .builder()
                        .name("Buying clothes")
                        .paymentMethods(PaymentMethods.PIX)
                        .value(350)
                        .build();

        when(exitService.save(exitPostDTO)).thenReturn(exitMock);

        ResponseEntity<Exit> responseEntity = exitController.saveExit(exitPostDTO);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(exitMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllExit(){
        List<Exit> exits = new ArrayList<>();
        exits.add(new Exit());
        exits.add(new Exit());

        when(exitService.findAllByUser()).thenReturn(exits);

        ResponseEntity<List<Exit>> responseEntity = exitController.getAllExit();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateExit(){
        ExitPutDTO exitPutDTO =
                ExitPutDTO
                    .builder()
                    .name("Buying clothes")
                    .paymentMethods(PaymentMethods.PIX)
                    .value(350)
                    .build();

        when(exitService.update("67a782cbf1c9cc32ec877f00",exitPutDTO)).thenReturn(exitMock);

        ResponseEntity<Exit> responseEntity = exitController.updateExit("67a782cbf1c9cc32ec877f00", exitPutDTO);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(exitMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeleteExit(){
        ResponseEntity<Void> responseEntity = exitController.deleteExit("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
