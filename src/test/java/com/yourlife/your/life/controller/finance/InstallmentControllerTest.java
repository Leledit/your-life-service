package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.installment.InstallmentPostDTO;
import com.yourlife.your.life.model.dto.finance.installment.InstallmentPutDTO;
import com.yourlife.your.life.model.entity.finance.Installment;
import com.yourlife.your.life.service.finance.InstallmentService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("InstallmentController")
public class InstallmentControllerTest {

    @Mock
    private InstallmentService installmentService;

    @InjectMocks
    private InstallmentController installmentController;

    private Installment installmentMock;

    @BeforeEach
    public void setUp(){

        LocalDate localDateMock = LocalDate.now();

        installmentMock = Installment
                        .builder()
                        .description("Buying a refrigerator")
                        .firstInstallmentDate(localDateMock)
                        .firstInstallmentDate(localDateMock.plusMonths(2))
                        .value(30)
                        .qtd(2)
                        .deleted(false)
                        .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveInstallment() {
        InstallmentPostDTO installmentPostDTO =
                InstallmentPostDTO
                        .builder()
                        .description("Buying a refrigerator")
                        .value(30)
                        .qtd(2)
                        .build();

        when(installmentService.save(installmentPostDTO)).thenReturn(installmentMock);

        ResponseEntity<Installment> responseEntity = installmentController.saveInstallment(installmentPostDTO);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(installmentMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllInstallment(){
        List<Installment> installmentList = new ArrayList<>();
        installmentList.add(new Installment());
        installmentList.add(new Installment());

        when(installmentService.findAllByUser()).thenReturn(installmentList);

        ResponseEntity<List<Installment>> responseEntity = installmentController.getAllInstallment();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetInstallmentById(){
        when(installmentService.findById("67a782cbf1c9cc32ec877f00")).thenReturn(installmentMock);

        ResponseEntity<Installment> responseEntity = installmentController.getInstallmentById("67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(installmentMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateInstallment(){
        InstallmentPutDTO installmentPutDTO =
                InstallmentPutDTO
                        .builder()
                        .description("Buying a refrigerator 2")
                        .value(30)
                        .qtd(4)
                        .build();

        when(installmentService.update("67a782cbf1c9cc32ec877f00",installmentPutDTO)).thenReturn(installmentMock);

        ResponseEntity<Installment> responseEntity = installmentController.updateInstallment("67a782cbf1c9cc32ec877f00",installmentPutDTO);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(installmentMock, responseEntity.getBody());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedInstallment(){
        ResponseEntity<Void> responseEntity = installmentController.deletedInstallment("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
