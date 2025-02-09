package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPostDTO;
import com.yourlife.your.life.model.dto.finance.benefitItem.BenefitItemPutDTO;
import com.yourlife.your.life.model.entity.finance.Benefit;
import com.yourlife.your.life.model.entity.finance.BenefitItem;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.model.types.finance.BenefitType;
import com.yourlife.your.life.repository.finance.BenefitItemReposity;
import com.yourlife.your.life.repository.finance.BenefitRepository;
import com.yourlife.your.life.repository.finance.MonthRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("BenefitItemServiceImpl")
public class BenefitItemServiceImplTest {

    @Mock
    private BenefitItemReposity benefitItemReposity;

    @Mock
    private BenefitRepository benefitRepository;

    @Mock
    private MonthRepository monthRepository;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private BenefitItemServiceImpl benefitItemService;

    private BenefitItem benefitItemMock;

    private Benefit benefitMock;

    private Month monthMock;

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

        benefitItemMock = BenefitItem
                            .builder()
                            .name("buy in the market")
                            .value(25)
                            .description("buy for the week")
                            .deleted(false)
                            .benefit(benefitMock)
                            .build();

        monthMock = Month
                        .builder()
                        .id("67a782cbf1c9cc32ec877f022")
                        .name("February")
                        .year(2025)
                        .month(2)
                        .date(LocalDateTime.parse("2025-02-09T10:00:00"))
                        .fixedAccounts(new ArrayList<>())
                        .installments(new ArrayList<>())
                        .exits(new ArrayList<>())
                        .entry(new ArrayList<>())
                        .benefitItems(new ArrayList<>())
                        .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveBenefitItem() {
        BenefitItemPostDTO benefitItemPostDTO =
                BenefitItemPostDTO
                        .builder()
                        .name("buy in the market")
                        .value(25)
                        .description("buy for the week")
                        .benefitId("67a782cbf1c9cc32ec877f00")
                        .build();

        when(benefitRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.ofNullable(benefitMock));
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(benefitItemReposity.save(any(BenefitItem.class))).thenReturn(benefitItemMock);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,userMock.getId())).thenReturn(Optional.ofNullable(monthMock));
        when(monthRepository.save(monthMock)).thenReturn(monthMock);

       BenefitItem benefitItem = benefitItemService.save(benefitItemPostDTO);

        assertNotNull(benefitItem);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testGetAllBenefitItem(){
        List<BenefitItem> benefitItems = new ArrayList<>();
        benefitItems.add(new BenefitItem());
        benefitItems.add(new BenefitItem());

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(benefitItemReposity.findAllByUser_IdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(benefitItems));

        List<BenefitItem> benefitItemList = benefitItemService.findAllByUser();

        assertEquals(2, Objects.requireNonNull(benefitItemList.size()));
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateBenefitItem() {
        BenefitItemPutDTO benefitItemPutDTO = BenefitItemPutDTO
                .builder()
                .name("buy in the market 2")
                .value(25)
                .description("buy for the week 2")
                .build();

        when(benefitItemReposity.findByIdAndDeleted(userMock.getId(),false)).thenReturn(Optional.of(benefitItemMock));
        when(benefitItemReposity.save(any(BenefitItem.class))).thenReturn(benefitItemMock);

        BenefitItem benefitItem = benefitItemService.update(benefitItemPutDTO,"67a782cbf1c9cc32ec877f00");
        assertNotNull(benefitItem);
    }

    @Test
    @DisplayName("Deleted - throwing an exception when trying to search for a deleted benefit")
    void testDeletedBenefit(){
        when(benefitItemReposity.findByIdAndDeleted("662707bea770e96a56b3d049", false)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> benefitItemService.deleted("662707bea770e96a56b3d049"));

        assertEquals(ExceptionMessages.BENEFIT_ITEM_NOT_FOUND, thrown.getMessage());
    }
}
