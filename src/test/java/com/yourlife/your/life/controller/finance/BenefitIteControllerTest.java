package com.yourlife.your.life.controller.finance;

import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPostDTO;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.types.finance.BenefitType;
import com.yourlife.your.life.service.finance.BenefitItemService;
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
@DisplayName("BenefitIteController")
public class BenefitIteControllerTest {

    @Mock
    private BenefitItemService benefitItemService;

    @InjectMocks
    private BenefitItemController benefitItemController;

    private BenefitItem benefitItemMock;

    @BeforeEach
    public void setUp(){
       Benefit benefitMock = Benefit
                .builder()
                .name("Food voucher")
                .valueReceived(1000)
                .description("Food voucher offered as a benefit by the company")
                .type(BenefitType.FOOD_VOUCHER)
                .deleted(false)
                .build();

       benefitItemMock = BenefitItem
                .builder()
                .name("buy in the market")
                .value(25)
                .description("buy for the week")
                .deleted(false)
                .benefit(benefitMock)
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveBenefitItem() {
        BenefitItemPostDTO benefitItemPostDTO = BenefitItemPostDTO
                .builder()
                .name("buy in the market")
                .value(25)
                .description("buy for the week")
                .benefitId("67a782cbf1c9cc32ec877f00")
                .build();

        when(benefitItemService.save(benefitItemPostDTO)).thenReturn(benefitItemMock);

        ResponseEntity<BenefitItem> responseEntity = benefitItemController.saveBenefitItem(benefitItemPostDTO);

        assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
        assertEquals(benefitItemMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllBenefitItem(){
        List<BenefitItem> benefitItems = new ArrayList<>();
        benefitItems.add(new BenefitItem());
        benefitItems.add(new BenefitItem());

        when(benefitItemService.findAllByUser()).thenReturn(benefitItems);

        ResponseEntity<List<BenefitItem>> responseEntity = benefitItemController.getAllBenefitItem();

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(2, Objects.requireNonNull(responseEntity.getBody()).size());
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateBenefitItem(){
        BenefitItemPutDTO benefitItemPutDTO = BenefitItemPutDTO
                                                .builder()
                                                .name("buy in the market 2")
                                                .value(25)
                                                .description("buy for the week 2")
                                                .build();
        when(benefitItemService.update(benefitItemPutDTO,"67a782cbf1c9cc32ec877f00")).thenReturn(benefitItemMock);

        ResponseEntity<BenefitItem> responseEntity = benefitItemController.updateBenefitItem(benefitItemPutDTO,"67a782cbf1c9cc32ec877f00");

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(benefitItemMock,responseEntity.getBody());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeletedBenefitItem(){
        ResponseEntity<Void> responseEntity = benefitItemController.deleteBenefitItem("67a782cbf1c9cc32ec877f00");
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }
}
