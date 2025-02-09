package com.yourlife.your.life.service.finance.impl;

import com.yourlife.your.life.constants.ExceptionMessages;
import com.yourlife.your.life.model.dto.finance.entry.EntryPostDTO;
import com.yourlife.your.life.model.dto.finance.entry.EntryPutDTO;
import com.yourlife.your.life.model.entity.finance.Entry;
import com.yourlife.your.life.model.entity.finance.Month;
import com.yourlife.your.life.model.entity.user.User;
import com.yourlife.your.life.repository.finance.EntryRepository;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@DisplayName("EntryServiceImpl")
public class EntryServiceImplTest {

    @Mock
    private EntryRepository entryRepository;

    @Mock
    private MonthRepository monthRepository;

    @Mock
    private UserContext userContext;

    @InjectMocks
    private EntryServiceImpl entryService;

    private Entry entryMock;

    private Month monthMock;

    private User userMock;

    @BeforeEach
    public void setUp() {
        entryMock = Entry
                .builder()
                .name("Payment for the project")
                .value(1000)
                .description("Payment for your ze's grocery project")
                .deleted(false)
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

        userMock = User
                .builder()
                .email("test@teste.com.br")
                .id("67a782cbf1c9cc32ec877f00")
                .name("leandro")
                .password("$2a$10$QTwffyaudYllyk9kD54Z3Oy.jbzDHPFCWl0pCswXBRUeWHmYzeQXS")
                .build();
    }

    @Test
    @DisplayName("Save - Creating new record successfully!")
    void testSaveEntry() {
        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(entryRepository.save(any(Entry.class))).thenReturn(entryMock);
        when(monthRepository.findByYearAndMonthAndUser_Id(2025,2,userMock.getId())).thenReturn(Optional.ofNullable(monthMock));
        when(monthRepository.save(monthMock)).thenReturn(monthMock);

        Entry entry = entryService.save(EntryPostDTO
                        .builder()
                        .name("Payment for the project")
                        .value(1000)
                        .description("Payment for your ze's grocery project")
                        .build()
        );

        assertNotNull(entry);
    }

    @Test
    @DisplayName("Updated - Changing a record")
    void testUpdateEntry(){
        when(entryRepository.findByIdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.ofNullable(entryMock));
        when(entryRepository.save(any(Entry.class))).thenReturn(entryMock);

        Entry entry = entryService.update("67a782cbf1c9cc32ec877f00", EntryPutDTO
                .builder()
                .name("Payment for the project")
                .value(1000)
                .description("Payment for your ze's grocery project")
                .build());

        assertNotNull(entry);
    }

    @Test
    @DisplayName("GetAll - Searching multiple records at once")
    void testFindAllByUser(){
        List<Entry> entries = new ArrayList<>(Arrays.asList(
                new Entry(),
                new Entry()
        ));

        when(userContext.returnUserCorrespondingToTheRequest()).thenReturn(userMock);
        when(entryRepository.findAllByUser_IdAndDeleted("67a782cbf1c9cc32ec877f00",false)).thenReturn(Optional.of(entries));

        List<Entry> entryList = entryService.findAllByUser();
        assertEquals(2,entryList.size());
    }

    @Test
    @DisplayName("Deleted - Deleting a record")
    void testDeleteEntry(){
        when(entryRepository.findByIdAndDeleted("662707bea770e96a56b3d049", false)).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> entryService.delete("662707bea770e96a56b3d049"));

        assertEquals(ExceptionMessages.ENTRY_NOT_FOUND, thrown.getMessage());
    }
}
