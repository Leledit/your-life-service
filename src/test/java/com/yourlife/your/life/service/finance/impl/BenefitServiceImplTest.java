package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPostDTO;
import com.yourlife.your.life.model.dto.finance.benefit.BenefitPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.types.finance.BenefitType;
import com.yourlife.your.life.repository.finance.BenefitRepository;
import com.yourlife.your.life.utils.UserContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("BenefitServiceImpl")
public class BenefitServiceImplTest {

    @Mock
    private BenefitRepository benefitRepository;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private BenefitServiceImpl benefitService;

    private Benefit benefitMock;

    private User userMock;

    @BeforeEach
    public void setUp(){
        userMock = User
                .builder()
                .email("test@teste.com.br")
                .id("67a782cbf1c9cc32ec877f00")
                .name("leandro")
                .password("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS")
                .build();

        benefitMock = Benefit
                .builder()
                .name("Food voucher")
                .valueReceived(1000)
                .description("Food voucher offered as a benefit by the company")
                .type(BenefitType.FOOD_VOUCHER)
                .createdAt(LocalDateTime.parse("2025-02-09T09:57:23.736141390"))
                .deleted(false)
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveBenefit() {
        BenefitPostDTO benefitPostDTOMock = BenefitPostDTO
                .builder()
                .name("Food voucher")
                .valueReceived(1000)
                .description("Food voucher offered as a benefit by the company")
                .type(BenefitType.FOOD_VOUCHER)
                .build();

        when(benefitRepository.save(any(Benefit.class))).thenReturn(benefitMock);
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);

        Benefit benefitFound = benefitService.save(benefitPostDTOMock);

        assertNotNull(benefitFound);
    }

    @Test
    @DisplayName("GetById - Searching for a single record")
    void testGetBenefitById(){
        when(benefitRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.ofNullable(benefitMock));

        Benefit benefit = benefitService.findById("67a782cbf1c9cc32ec877f00");

        assertNotNull(benefit);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllBenefits(){
        ArrayList<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit());
        benefits.add(new Benefit());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(benefitRepository.findAllByUser_IdAndDeleted(userMock.getId(), false)).thenReturn(Optional.of(benefits));

        List<Benefit> benefitList = benefitService.findAllByUser();

        assertEquals(2, Objects.requireNonNull(benefitList.size()));
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateBenefit() {
        BenefitPutDTO benefitPutDTO = BenefitPutDTO
                .builder()
                .name("Food voucher 2")
                .valueReceived(1000)
                .description("Food voucher offered as a benefit by the company 2")
                .build();

        when(benefitRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.ofNullable(benefitMock));
        when(benefitRepository.save(any(Benefit.class))).thenReturn(benefitMock);

        Benefit benefit = benefitService.update("67a782cbf1c9cc32ec877f00",benefitPutDTO);
        assertNotNull(benefit);
    }

    @Test
    @DisplayName("Deleted - throwing an exception when trying to search for a deleted benefit")
    void testDeletedBenefit(){
        String benefitId = "662707bea770e96a56b3d049";

        when(benefitRepository.findByIdAndDeleted(benefitId, false))
                .thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> benefitService.findById(benefitId));

        assertEquals(ExceptionMessages.BENEFIT_NOT_FOUND, thrown.getMessage());
    }
}
