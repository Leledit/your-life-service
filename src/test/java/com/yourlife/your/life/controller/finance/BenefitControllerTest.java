package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.benefit.BenefitPostDTO;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.types.finance.BenefitType;
import com.yourlife.your.life.service.finance.BenefitService;
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
@DisplayName("BenefitController")
public class BenefitControllerTest {

    @Mock
    private BenefitService benefitService;

    @InjectMocks
    private BenefitController benefitController;

    private Benefit benefitMock;

    @BeforeEach
    public void setUp(){
        benefitMock = Benefit
                .builder()
                .name("Food voucher")
                .valueReceived(1000)
                .description("Food voucher offered as a benefit by the company")
                .type(BenefitType.FOOD_VOUCHER)
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveBenefit() {
        BenefitPostDTO benefitPostDTOMock = new BenefitPostDTO();
        benefitPostDTOMock.setName("Food voucher");
        benefitPostDTOMock.setValueReceived(1000);
        benefitPostDTOMock.setDescription("Food voucher offered as a benefit by the company");
        benefitPostDTOMock.setType(BenefitType.FOOD_VOUCHER);

        when(benefitService.save(benefitPostDTOMock)).thenReturn(benefitMock);

        ResponseEntity<Benefit> responseEntity = benefitController.saveBenefit(benefitPostDTOMock);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(benefitMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllBenefits(){
        List<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit());
        benefits.add(new Benefit());

        when(benefitService.findAllByUser()).thenReturn(benefits);

        ResponseEntity<List<Benefit>> responseEntity = benefitController.getAllBenefits();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetBenefitById(){
        when(benefitService.findById("67a782cbf1c9cc32ec877f00")).thenReturn(benefitMock);
        ResponseEntity<Benefit> responseEntity = benefitController.getBenefitById("67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(benefitMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateBenefit(){

        BenefitPutDTO benefitPutDTO = BenefitPutDTO
                                            .builder()
                                            .name("Food voucher 2")
                                            .valueReceived(1000)
                                            .description("Food voucher offered as a benefit by the company 2")
                                            .build();

        when(benefitService.update("67a782cbf1c9cc32ec877f00",benefitPutDTO)).thenReturn(benefitMock);

        ResponseEntity<Benefit> responseEntity = benefitController.updateBenefit("67a782cbf1c9cc32ec877f00",benefitPutDTO);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(benefitMock,responseEntity.getBody());
    }


    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedBenefit(){
        ResponseEntity<Void> responseEntity = benefitController.deletedBenefit("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
